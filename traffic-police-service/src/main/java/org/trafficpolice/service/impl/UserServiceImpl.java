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
import org.trafficpolice.consts.ServiceConsts;
import org.trafficpolice.dao.UserDao;
import org.trafficpolice.dto.AuditQueryParamDTO;
import org.trafficpolice.dto.AuditQueryResultDTO;
import org.trafficpolice.dto.UserDTO;
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
	public void register(UserDTO userDTO) {
		this.checkRegisterParam(userDTO);
		User po = new User();
		po.setIdType(userDTO.getIdType());
		po.setIdNo(userDTO.getIdNo());
		String idCardImgUrlToken = userDTO.getIdCardImgUrlToken();
		FileInfo idCardImgfileInfo = fileInfoService.queryByToken(idCardImgUrlToken);
		String idCardImgUrl = null;
		if (idCardImgfileInfo != null) {
			idCardImgUrl = idCardImgfileInfo.getUrl();
		}
		if (StringUtils.isBlank(idCardImgUrl)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "idCardImgUrl");
		}
		po.setIdCardImgUrl(idCardImgUrl);
		po.setLicenseType(userDTO.getLicenseType());
		po.setLicenseNo(userDTO.getLicenseNo());
		po.setLicenseBeginDate(userDTO.getLicenseBeginDate());
		po.setLicenseEndDate(userDTO.getLicenseEndDate());
		String headUrl = null;
		String headUrlToken = userDTO.getHeadUrlToken();
		FileInfo headImgfileInfo = fileInfoService.queryByToken(headUrlToken);
		if (headImgfileInfo != null) {
			headUrl = headImgfileInfo.getUrl();
		}
		if (StringUtils.isBlank(headUrl)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "headUrl");
		}
		po.setHeadUrl(headUrl);
		po.setPhone(userDTO.getPhone());
		po.setAuditState(AuditState.INHAND);
		po.setDisabled(false);
		po.setCreateTime(new Date());
		userDao.doInsert(po);
		fileInfoService.deleteByToken(idCardImgUrlToken);
		fileInfoService.deleteByToken(headUrlToken);
	}
	
	private void checkRegisterParam(UserDTO userDTO) {
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
		if (StringUtils.isBlank(idCardImgToken)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "idCardImgUrlToken");
		}
		LicenseType licenseType = userDTO.getLicenseType();
		if (licenseType == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "licenseType");
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
		if (StringUtils.isBlank(headUrlToken)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "headUrlToken");
		}
		String phone = userDTO.getPhone();
		if (StringUtils.isBlank(phone)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "phone");
		}
		VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO();
		verifyCodeDTO.setPhone(phone);
		verifyCodeDTO.setCode(userDTO.getVerifyCode());
		verifyCodeDTO.setToken(userDTO.getVerifyCodeToken());
		verifyCodeDTO.setType("register");
		boolean isValide = verifyCodeService.checkVerifyCode(verifyCodeDTO);
		if (!isValide) {
			throw new BizException(VerifyCodeExceptionEnum.INCORRECT);
		}
		List<User> users = userDao.findUsers(idNo, licenseNo, phone);
		if (CollectionUtils.isNotEmpty(users)) {
			throw new BizException(UserExceptionEnum.EXIST_USER);
		}
		verifyCodeService.clearVerifyCodeCache(verifyCodeDTO);
	}

	@Override
	@Transactional
	public User findByPhone(String phone) {
		return userDao.findByPhone(phone);
	}

	@Override
	@Transactional
	public AuditQueryResultDTO auditQuery(AuditQueryParamDTO auditQueryParamDTO) {
		String phone = auditQueryParamDTO.getPhone();
		if (StringUtils.isBlank(phone)) {
			throw new BizException(VerifyCodeExceptionEnum.MISS_PHONE);
		}
		boolean flag = Pattern.matches(ServiceConsts.REGEX_PHONE, phone);
		if (!flag) {
			throw new BizException(VerifyCodeExceptionEnum.PHONE_INCORRECT);
		}
		VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO();
		verifyCodeDTO.setPhone(phone);
		verifyCodeDTO.setCode(auditQueryParamDTO.getVerifyCode());
		verifyCodeDTO.setToken(auditQueryParamDTO.getVerifyCodeToken());
		verifyCodeDTO.setType("auditstate");
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
		this.checkRegisterUpdateParam(userDTO);
		User po = new User();
		po.setIdType(userDTO.getIdType());
		po.setIdNo(userDTO.getIdNo());
		String idCardImgToken = userDTO.getIdCardImgUrlToken();
		if (StringUtils.isNoneBlank(idCardImgToken)) {
			FileInfo idCardImgfileInfo = fileInfoService.queryByToken(idCardImgToken);
			if (idCardImgfileInfo != null) {
				po.setIdCardImgUrl(idCardImgfileInfo.getUrl());
			} else {
				logger.info("####【修改用户注册信息】【idCardImgToken不正确】--> {} ####", idCardImgToken);
			}
		}
		po.setLicenseType(userDTO.getLicenseType());
		po.setLicenseNo(userDTO.getLicenseNo());
		po.setLicenseBeginDate(userDTO.getLicenseBeginDate());
		po.setLicenseEndDate(userDTO.getLicenseEndDate());
		String headUrlToken = userDTO.getHeadUrlToken();
		if (StringUtils.isNoneBlank(headUrlToken)) {
			FileInfo headImgfileInfo = fileInfoService.queryByToken(headUrlToken);
			if (headImgfileInfo != null) {
				po.setHeadUrl(headImgfileInfo.getUrl());
			} else {
				logger.info("####【修改用户注册信息】【headUrlToken不正确】--> {} ####", headUrlToken);
			}
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
	
	public void checkRegisterUpdateParam(UserDTO userDTO) {
		String auditQueryToken = userDTO.getAuditQueryToken();
		if (StringUtils.isBlank(auditQueryToken)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "auditQueryToken");
		}
		String phone = userDTO.getPhone();
		if (StringUtils.isBlank(phone)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "phone");
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
		LicenseType licenseType = userDTO.getLicenseType();
		if (licenseType == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "licenseType");
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
		user = userDao.findByIdNo(idNo);
		if (user != null && !user.getPhone().equals(phone)) {
			throw new BizException(UserExceptionEnum.EXIST_IDNO_LISENCENO);
		}
		user = userDao.findByLicenseNo(licenseNo);
		if (user != null && !user.getPhone().equals(phone)) {
			throw new BizException(UserExceptionEnum.EXIST_IDNO_LISENCENO);
		}
		redisTemplate.delete(AUDIT_QUERY_CACHE + auditQueryToken);
	}

}
