package org.trafficpolice.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.commons.TokenUtils;
import org.trafficpolice.commons.cache.CacheNamespace;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.consts.BizTypeConsts;
import org.trafficpolice.consts.ServiceConsts;
import org.trafficpolice.dao.UserDao;
import org.trafficpolice.dto.AuditQueryParamDTO;
import org.trafficpolice.dto.AuditQueryResultDTO;
import org.trafficpolice.dto.UserDTO;
import org.trafficpolice.dto.UserQueryParamDTO;
import org.trafficpolice.dto.VerifyCodeDTO;
import org.trafficpolice.enumeration.AuditState;
import org.trafficpolice.enumeration.IDType;
import org.trafficpolice.enumeration.LicenseType;
import org.trafficpolice.exception.UserExceptionEnum;
import org.trafficpolice.exception.VerifyCodeExceptionEnum;
import org.trafficpolice.po.FileInfo;
import org.trafficpolice.po.User;
import org.trafficpolice.service.FileInfoService;
import org.trafficpolice.service.UserService;
import org.trafficpolice.service.VerifyCodeService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * 2018年7月1日下午1:28:09
 */
@Service(UserService.BEAN_ID)
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private static final String AUDIT_QUERY_CACHE = CacheNamespace.TRAFFIC_POLICE + CacheNamespace.SEPARATOR + "auditquery" + CacheNamespace.SEPARATOR;
	
	private static final long AUDIT_QUERY_CACHE_DURATION = 20 * 60;//缓存时长20分钟
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	@Qualifier(VerifyCodeService.BEAN_ID)
	private VerifyCodeService verifyCodeService;
	
	@Autowired
	@Qualifier(UserDao.BEAN_ID)
	private UserDao userDao;
	
	@Autowired
	@Qualifier(FileInfoService.BEAN_ID)
	private FileInfoService fileInfoService;
	
	@Override
	@Transactional
	public void register(UserDTO userDTO, boolean isBGRegister) {
		this.checkRegisterParam(userDTO, isBGRegister);
		User po = new User();
		po.setIdType(userDTO.getIdType());
		po.setIdNo(userDTO.getIdNo());
		String idCardImgUrlToken = userDTO.getIdCardImgUrlToken();
		if (!isBGRegister) {
			FileInfo idCardImgfileInfo = fileInfoService.queryByToken(idCardImgUrlToken);
			if (idCardImgfileInfo == null) {
				throw new BizException(GlobalStatusEnum.PARAM_ERROR, "idCardImgUrlToken");
			}
			po.setIdCardImgUrl(idCardImgfileInfo.getUrl());
		}
		po.setLicenseType(userDTO.getLicenseType().toUpperCase());
		po.setLicenseNo(userDTO.getLicenseNo());
		po.setLicenseBeginDate(userDTO.getLicenseBeginDate());
		po.setLicenseEndDate(userDTO.getLicenseEndDate());
		String headUrlToken = userDTO.getHeadUrlToken();
		if (!isBGRegister) {
			FileInfo headImgfileInfo = fileInfoService.queryByToken(headUrlToken);
			if (headImgfileInfo == null) {
				throw new BizException(GlobalStatusEnum.PARAM_ERROR, "headUrlToken");
			}
			po.setHeadUrl(headImgfileInfo.getUrl());
		}
		po.setPhone(userDTO.getPhone());
		if (isBGRegister) {
			po.setAuditState(AuditState.PASSED);
		} else {
			po.setAuditState(AuditState.INHAND);
		}
		po.setDisabled(false);
		po.setCreateTime(new Date());
		userDao.doInsert(po);
		if (StringUtils.isNoneBlank(idCardImgUrlToken)) {
			fileInfoService.deleteByToken(idCardImgUrlToken);
		}
		if (StringUtils.isNoneBlank(headUrlToken)) {
			fileInfoService.deleteByToken(headUrlToken);
		}
	}
	
	/**
	 * 用户注册参数检查
	 * @param userDTO
	 * @param isBGRegister 是否是后台管理员帮忙添加
	 */
	private void checkRegisterParam(UserDTO userDTO, boolean isBGRegister) {
		IDType idType = userDTO.getIdType();
		if (idType == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "idType");
		}
		String idNo = userDTO.getIdNo();
		if (StringUtils.isBlank(idNo)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "idNo");
		}
		if (IDType.IDCARD == idType) {
			boolean flag = Pattern.matches(ServiceConsts.REGEX_ID_CARD, idNo);
			if (!flag) {
				throw new BizException(UserExceptionEnum.ID_CARD_INCORRECT);
			}
		}
		String idCardImgToken = userDTO.getIdCardImgUrlToken();
		if (!isBGRegister && StringUtils.isBlank(idCardImgToken)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "idCardImgUrlToken");
		}
		String licenseType = userDTO.getLicenseType();
		if (StringUtils.isBlank(licenseType)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "licenseType");
		}
		if (licenseType.indexOf(",") != -1) {
			String[] lts = licenseType.split(",");
			for (String lt : lts) {
				if (!LicenseType.getNames().contains(lt)) {
					throw new BizException(GlobalStatusEnum.PARAM_ERROR, "licenseType");
				}
			}
		} else if (!LicenseType.getNames().contains(licenseType)) {
			throw new BizException(GlobalStatusEnum.PARAM_ERROR, "licenseType");
		}
		String licenseNo = userDTO.getLicenseNo();
		if (StringUtils.isBlank(licenseNo)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "licenseNo");
		}
		Date licenseBeginDate = userDTO.getLicenseBeginDate();
		if (licenseBeginDate == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "licenseBeginDate");
		}
		Date licenseEndDate = userDTO.getLicenseEndDate();
		if (licenseEndDate == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "licenseEndDate");
		}
		String headUrlToken = userDTO.getHeadUrlToken();
		if (!isBGRegister && StringUtils.isBlank(headUrlToken)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "headUrlToken");
		}
		String phone = userDTO.getPhone();
		if (StringUtils.isBlank(phone)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "phone");
		}
		List<User> users = userDao.findUsers(idNo, licenseNo, phone);
		if (CollectionUtils.isNotEmpty(users)) {
			throw new BizException(UserExceptionEnum.EXIST_USER);
		}
		if (!isBGRegister) {
			VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO();
			verifyCodeDTO.setPhone(phone);
			verifyCodeDTO.setCode(userDTO.getVerifyCode());
			verifyCodeDTO.setToken(userDTO.getVerifyCodeToken());
			verifyCodeDTO.setType(BizTypeConsts.REGISTER);
			boolean isValide = verifyCodeService.checkVerifyCode(verifyCodeDTO);
			if (!isValide) {
				throw new BizException(VerifyCodeExceptionEnum.INCORRECT);
			}
			verifyCodeService.clearVerifyCodeCache(verifyCodeDTO);
		}
	}

	@Override
	@Transactional
	public User findByPhone(String phone) {
		return userDao.findByPhone(phone);
	}

	@Override
	@Transactional
	public User findByIdNoAndLicenseNo(String idNo, String licenseNo) {
		return userDao.findByIdNoAndLicenseNo(idNo, licenseNo);
	}

	@Override
	@Transactional
	public PageInfo<User> queryByPage(UserQueryParamDTO queryDTO) {
		PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
		List<User> users = userDao.findByCondition(queryDTO);
		return new PageInfo<User>(users);
	}

	@Override
	@Transactional
	public void audit(User user) {
		Long id = user.getId();
		if (id == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "id");
		}
		if (user.getAuditState() == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "auditState");
		}
		if (StringUtils.isBlank(user.getAuditDesc())) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "auditDesc");
		}
		User existUser = userDao.findById(id);
		if (existUser == null) {
			throw new BizException(UserExceptionEnum.NOT_FOUND);
		}
		user.setId(id);
		user.setAuditTime(new Date());
		userDao.auditUser(user);
	}

	@Override
	@Transactional
	public void updateDisabled(Long id, boolean disabled) {
		if (id == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "id");
		}
		User existUser = userDao.findById(id);
		if (existUser == null) {
			throw new BizException(UserExceptionEnum.NOT_FOUND);
		}
		userDao.updateDisable(id, disabled);
	}

	@Override
	@Transactional
	public AuditQueryResultDTO auditQuery(AuditQueryParamDTO auditQueryParamDTO) {
		String phone = auditQueryParamDTO.getPhone();
		if (StringUtils.isBlank(phone)) {
			throw new BizException(GlobalStatusEnum.MISS_PHONE);
		}
		boolean flag = Pattern.matches(ServiceConsts.REGEX_PHONE, phone);
		if (!flag) {
			throw new BizException(GlobalStatusEnum.PHONE_INCORRECT);
		}
		VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO();
		verifyCodeDTO.setPhone(phone);
		verifyCodeDTO.setCode(auditQueryParamDTO.getVerifyCode());
		verifyCodeDTO.setToken(auditQueryParamDTO.getVerifyCodeToken());
		verifyCodeDTO.setType(BizTypeConsts.AUDITQUERY);
		boolean isValide = verifyCodeService.checkVerifyCode(verifyCodeDTO);
		if (!isValide) {
			throw new BizException(VerifyCodeExceptionEnum.INCORRECT);
		}
		User user = userDao.findByPhone(phone);
		if (user == null) {
			throw new BizException(UserExceptionEnum.NOT_FOUND);
		}
		if (user.getDisabled()) {
			throw new BizException(UserExceptionEnum.WAS_BLACK);
		}
		AuditQueryResultDTO result = new AuditQueryResultDTO();
		result.setUser(user);
		String token = TokenUtils.generateToken();
		result.setToken(token);
		verifyCodeService.clearVerifyCodeCache(verifyCodeDTO);
		redisTemplate.opsForValue().set(AUDIT_QUERY_CACHE + token, phone, AUDIT_QUERY_CACHE_DURATION, TimeUnit.SECONDS);
		return result;
	}

	@Override
	@Transactional
	public void registerUpdate(UserDTO userDTO) {
		this.checkUpdateParam(userDTO);
		User po = new User();
		po.setIdType(userDTO.getIdType());
		po.setIdNo(userDTO.getIdNo());
		String idCardImgToken = userDTO.getIdCardImgUrlToken();
		if (StringUtils.isNoneBlank(idCardImgToken)) {
			FileInfo idCardImgfileInfo = fileInfoService.queryByToken(idCardImgToken);
			if (idCardImgfileInfo == null) {
				throw new BizException(GlobalStatusEnum.PARAM_ERROR, "idCardImgUrlToken");
			}
			po.setIdCardImgUrl(idCardImgfileInfo.getUrl());
		}
		po.setLicenseType(userDTO.getLicenseType().toUpperCase());
		po.setLicenseNo(userDTO.getLicenseNo());
		po.setLicenseBeginDate(userDTO.getLicenseBeginDate());
		po.setLicenseEndDate(userDTO.getLicenseEndDate());
		String headUrlToken = userDTO.getHeadUrlToken();
		if (StringUtils.isNoneBlank(headUrlToken)) {
			FileInfo headImgfileInfo = fileInfoService.queryByToken(headUrlToken);
			if (headImgfileInfo == null) {
				throw new BizException(GlobalStatusEnum.PARAM_ERROR, "headUrlToken");
			}
			po.setHeadUrl(headImgfileInfo.getUrl());
		}
		po.setPhone(userDTO.getPhone());
		po.setAuditState(AuditState.REINHAND);
		po.setUpdateTime(new Date());
		userDao.doUpdateByPhone(po);
		if (StringUtils.isNoneBlank(idCardImgToken)) {
			fileInfoService.deleteByToken(idCardImgToken);
		}
		if (StringUtils.isNoneBlank(headUrlToken)) {
			fileInfoService.deleteByToken(headUrlToken);
		}
	}
	
	public void checkUpdateParam(UserDTO userDTO) {
		String phone = userDTO.getPhone();
		if (StringUtils.isBlank(phone)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "phone");
		}
		String auditQueryToken = userDTO.getAuditQueryToken();
		if (StringUtils.isBlank(auditQueryToken)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "auditQueryToken");
		}
		String cachePhone = (String)redisTemplate.opsForValue().get(AUDIT_QUERY_CACHE + auditQueryToken);
		if (!cachePhone.equals(phone)) {
			throw new BizException(GlobalStatusEnum.OPS_ILLEGAL);
		}
		IDType idType = userDTO.getIdType();
		if (idType == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "idType");
		}
		String idNo = userDTO.getIdNo();
		if (StringUtils.isBlank(idNo)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "idNo");
		}
		if (IDType.IDCARD == idType) {
			boolean flag = Pattern.matches(ServiceConsts.REGEX_ID_CARD, idNo);
			if (!flag) {
				throw new BizException(UserExceptionEnum.ID_CARD_INCORRECT);
			}
		}
		String licenseType = userDTO.getLicenseType();
		if (StringUtils.isBlank(licenseType)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "licenseType");
		}
		if (licenseType.indexOf(",") != -1) {
			String[] lts = licenseType.split(",");
			for (String lt : lts) {
				if (!LicenseType.getNames().contains(lt)) {
					throw new BizException(GlobalStatusEnum.PARAM_ERROR, "licenseType");
				}
			}
		} else if (!LicenseType.getNames().contains(licenseType)) {
			throw new BizException(GlobalStatusEnum.PARAM_ERROR, "licenseType");
		}
		String licenseNo = userDTO.getLicenseNo();
		if (StringUtils.isBlank(licenseNo)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "licenseNo");
		}
		Date licenseBeginDate = userDTO.getLicenseBeginDate();
		if (licenseBeginDate == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "licenseBeginDate");
		}
		Date licenseEndDate = userDTO.getLicenseEndDate();
		if (licenseEndDate == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "licenseEndDate");
		}
		User user = userDao.findByPhone(phone);
		if (user == null) {
			throw new BizException(UserExceptionEnum.NOT_FOUND);
		}
		if (user.getDisabled()) {
			throw new BizException(UserExceptionEnum.WAS_BLACK);
		}
		AuditState auditState = user.getAuditState();
		if (auditState != AuditState.REJECT) {
			throw new BizException(GlobalStatusEnum.OPS_ILLEGAL);
		}
		if (!idNo.equals(user.getIdNo())) {
			User u = userDao.findByIdNo(idNo);
			if (u != null) {
				throw new BizException(UserExceptionEnum.EXIST_IDNO_LISENCENO);
			}
		}
		if (!licenseNo.equals(user.getLicenseNo())) {
			User u = userDao.findByLicenseNo(licenseNo);
			if (u != null) {
				throw new BizException(UserExceptionEnum.EXIST_IDNO_LISENCENO);
			}
		}
		redisTemplate.delete(AUDIT_QUERY_CACHE + auditQueryToken);
	}

	@Override
	@Transactional
	public User findById(Long id) {
		if (id == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "id");
		}
		User user = userDao.findById(id);
		if (user == null) {
			return user;
		}
		String headImgUrl = user.getHeadUrl();
		if (StringUtils.isNoneBlank(headImgUrl) && !headImgUrl.startsWith("http")) {
			user.setHeadUrl(ServiceConsts.NFS_ADDRESS + headImgUrl);
		}
		String idCardImgUrl = user.getIdCardImgUrl();
		if (StringUtils.isNoneBlank(idCardImgUrl) && !idCardImgUrl.startsWith("http")) {
			user.setIdCardImgUrl(ServiceConsts.NFS_ADDRESS + idCardImgUrl);
		}
		return user;
	}

	@Override
	@Transactional
	public void updateUser(UserDTO userDTO) {
		Long id = userDTO.getId();
		if (id == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "id");
		}
		IDType idType = userDTO.getIdType();
		if (idType == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "idType");
		}
		String idNo = userDTO.getIdNo();
		if (StringUtils.isBlank(idNo)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "idNo");
		}
		if (IDType.IDCARD == idType) {
			boolean idcardFlag = Pattern.matches(ServiceConsts.REGEX_ID_CARD, idNo);
			if (!idcardFlag) {
				throw new BizException(UserExceptionEnum.ID_CARD_INCORRECT);
			}
		}
		String licenseType = userDTO.getLicenseType();
		if (StringUtils.isBlank(licenseType)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "licenseType");
		}
		if (licenseType.indexOf(",") != -1) {
			String[] lts = licenseType.split(",");
			for (String lt : lts) {
				if (!LicenseType.getNames().contains(lt)) {
					throw new BizException(GlobalStatusEnum.PARAM_ERROR, "licenseType");
				}
			}
		} else if (!LicenseType.getNames().contains(licenseType)) {
			throw new BizException(GlobalStatusEnum.PARAM_ERROR, "licenseType");
		}
		String licenseNo = userDTO.getLicenseNo();
		if (StringUtils.isBlank(licenseNo)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "licenseNo");
		}
		Date licenseBeginDate = userDTO.getLicenseBeginDate();
		if (licenseBeginDate == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "licenseBeginDate");
		}
		Date licenseEndDate = userDTO.getLicenseEndDate();
		if (licenseEndDate == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "licenseEndDate");
		}
		String phone = userDTO.getPhone();
		if (StringUtils.isBlank(phone)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "phone");
		}
		boolean flag = Pattern.matches(ServiceConsts.REGEX_PHONE, phone);
		if (!flag) {
			throw new BizException(GlobalStatusEnum.PHONE_INCORRECT);
		}
		User existUser = userDao.findById(id);
		if (existUser == null) {
			throw new BizException(UserExceptionEnum.NOT_FOUND);
		}
		if (!phone.equals(existUser.getPhone())) {
			User user = userDao.findByPhone(phone);
			if (user != null) {
				throw new BizException(UserExceptionEnum.EXIST_USER);
			}
		}
		if (!idNo.equals(existUser.getIdNo())) {
			User user = userDao.findByIdNo(idNo);
			if (user != null) {
				throw new BizException(UserExceptionEnum.EXIST_USER);
			}
		}
		if (!licenseNo.equals(existUser.getLicenseNo())) {
			User user = userDao.findByLicenseNo(licenseNo);
			if (user != null) {
				throw new BizException(UserExceptionEnum.EXIST_USER);
			}
		}
		existUser.setIdType(idType);
		existUser.setIdNo(idNo);
		existUser.setLicenseType(licenseType.toUpperCase());
		existUser.setLicenseNo(licenseNo);
		existUser.setLicenseBeginDate(licenseBeginDate);
		existUser.setLicenseEndDate(licenseEndDate);
		String idCardImgToken = userDTO.getIdCardImgUrlToken();
		if (StringUtils.isNoneBlank(idCardImgToken)) {
			FileInfo idCardImgfileInfo = fileInfoService.queryByToken(idCardImgToken);
			if (idCardImgfileInfo == null) {
				throw new BizException(GlobalStatusEnum.PARAM_ERROR, "idCardImgUrlToken");
			}
			existUser.setIdCardImgUrl(idCardImgfileInfo.getUrl());
		}
		String headUrlToken = userDTO.getHeadUrlToken();
		if (StringUtils.isNoneBlank(headUrlToken)) {
			FileInfo headImgfileInfo = fileInfoService.queryByToken(headUrlToken);
			if (headImgfileInfo == null) {
				throw new BizException(GlobalStatusEnum.PARAM_ERROR, "headUrlToken");
			}
			existUser.setHeadUrl(headImgfileInfo.getUrl());
		}
		existUser.setUpdateTime(new Date());
		userDao.doUpdate(existUser);
		if (StringUtils.isNoneBlank(idCardImgToken)) {
			fileInfoService.deleteByToken(idCardImgToken);
		}
		if (StringUtils.isNoneBlank(headUrlToken)) {
			fileInfoService.deleteByToken(headUrlToken);
		}
	}

}
