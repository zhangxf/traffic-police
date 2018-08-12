package org.trafficpolice.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.consts.ServiceConsts;
import org.trafficpolice.dto.EduRecordDTO;
import org.trafficpolice.dto.EduRecordQueryParamDTO;
import org.trafficpolice.dto.QuestionRecordDTO;
import org.trafficpolice.dto.QuestionRecordQueryParamDTO;
import org.trafficpolice.po.User;
import org.trafficpolice.service.EduRecordService;
import org.trafficpolice.service.QuestionRecordService;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月9日 下午6:27:16
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	@Qualifier(QuestionRecordService.BEAN_ID)
	private QuestionRecordService questionRecordService;
	
	@Autowired
	@Qualifier(EduRecordService.BEAN_ID)
	private EduRecordService eduRecordService;
	
	/**
	 * 获取登录用户信息
	 * @param user
	 * @return
	 */
	@PostMapping("/info")
	public User userInfo(@AuthenticationPrincipal(expression = "currentUser") User user) {
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
	
	@PostMapping("/questionrecord")
	public PageInfo<QuestionRecordDTO> queryQuestionRecordByPage(@AuthenticationPrincipal(expression = "currentUser") User user, @RequestBody QuestionRecordQueryParamDTO queryDTO) {
		if (queryDTO == null) {
			queryDTO = new QuestionRecordQueryParamDTO();
		}
		queryDTO.setUserId(user.getId());
		return questionRecordService.findByPage(queryDTO);
	}
	
	@PostMapping("/edurecord")
	public PageInfo<EduRecordDTO> queryEduRecordByPage(@AuthenticationPrincipal(expression = "currentUser") User user, @RequestBody EduRecordQueryParamDTO queryDTO) {
		if (queryDTO == null) {
			queryDTO = new EduRecordQueryParamDTO();
		}
		queryDTO.setUserId(user.getId());
		return eduRecordService.findByPage(queryDTO);
	}
	
	@GetMapping("/edurecord/detail")
	public EduRecordDTO queryEduRecordDetail(@AuthenticationPrincipal(expression = "currentUser") User user, @RequestParam("id") Long id) {
		EduRecordDTO eduRecord = eduRecordService.findById(id);
		if (eduRecord == null || (!eduRecord.getUserId().equals(user.getId()))) {
			return new EduRecordDTO();
		}
		String licenseTyp = eduRecord.getLicenseType();
		eduRecord.setLicenseType(licenseTyp.replaceAll(",", ""));
		String headImgUrl = eduRecord.getHeadUrl();
		if (StringUtils.isNoneBlank(headImgUrl) && !headImgUrl.startsWith("http")) {
			eduRecord.setHeadUrl(ServiceConsts.NFS_ADDRESS + headImgUrl);
		}
		return eduRecord;
	}
	
//	@PostMapping("/edurecord")
//	public NULL add
	
}
