package org.trafficpolice.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.dao.UserDao;
import org.trafficpolice.dto.UserDTO;
import org.trafficpolice.dto.VerifyCodeDTO;
import org.trafficpolice.enumeration.AuditState;
import org.trafficpolice.enumeration.IDType;
import org.trafficpolice.enumeration.LicenseType;
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
	public void addUser(UserDTO userDTO) {
		this.checkAddUserParam(userDTO);
		User po = new User();
		po.setIdType(userDTO.getIdType());
		po.setIdNo(userDTO.getIdNo());
		FileInfo idCardImgfileInfo = fileInfoService.queryByToken(userDTO.getIdCardImgUrlToken());
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
		FileInfo headImgfileInfo = fileInfoService.queryByToken(userDTO.getHeadUrlToken());
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
		
	}
	
	private void checkAddUserParam(UserDTO userDTO) {
		IDType idType = userDTO.getIdType();
		if (idType == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "idType");
		}
		String idNo = userDTO.getIdNo();
		if (StringUtils.isBlank(idNo)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "idNo");
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
		verifyCodeService.clearVerifyCodeCache(verifyCodeDTO);
	}

}
