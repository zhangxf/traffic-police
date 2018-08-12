package org.trafficpolice.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.consts.ApplicationConsts;
import org.trafficpolice.consts.ServiceConsts;
import org.trafficpolice.dto.EduRecordDTO;
import org.trafficpolice.dto.EduRecordQueryParamDTO;
import org.trafficpolice.dto.GrabRecordDTO;
import org.trafficpolice.dto.QuestionRecordDTO;
import org.trafficpolice.dto.QuestionRecordQueryParamDTO;
import org.trafficpolice.enumeration.EduType;
import org.trafficpolice.po.EduRecord;
import org.trafficpolice.po.GrabRecord;
import org.trafficpolice.po.User;
import org.trafficpolice.service.EduRecordService;
import org.trafficpolice.service.GrabRecordService;
import org.trafficpolice.service.QuestionRecordService;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月9日 下午6:27:16
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	@Qualifier(QuestionRecordService.BEAN_ID)
	private QuestionRecordService questionRecordService;
	
	@Autowired
	@Qualifier(EduRecordService.BEAN_ID)
	private EduRecordService eduRecordService;
	
	@Autowired
	@Qualifier(GrabRecordService.BEAN_ID)
	private GrabRecordService grabRecordService;
	
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
	
	/**
	 * 教育记录抓图
	 * @param id
	 * @return
	 */
	@PostMapping("/grabgraph")
	public NULL grabGraph(@AuthenticationPrincipal(expression = "currentUser") User user, @RequestBody GrabRecordDTO grabRecordDTO) {
		String grabType = grabRecordDTO.getType();
		if (StringUtils.isBlank(grabType)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "type");
		}
		String base64Photo = grabRecordDTO.getBase64Photo();
		if (StringUtils.isBlank(base64Photo)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "base64Photo");
		}
		Long userId = user.getId();
		String batchNum = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		EduRecord eduRecord = eduRecordService.findUniqueRecord(userId, batchNum, EduType.CHECK);
		if (eduRecord == null) {
			eduRecord = new EduRecord();
			eduRecord.setUserId(userId);
			eduRecord.setBatchNum(batchNum);
			eduRecord.setEduType(EduType.CHECK);
			eduRecord.setIsCompleted(false);
			eduRecord.setCreateTime(new Date());
			eduRecordService.addEduRecord(eduRecord);
		}
		List<GrabRecord> grabList = grabRecordService.findByEduIdAndType(eduRecord.getId(), grabType);
		if (CollectionUtils.isEmpty(grabList) || grabList.size() < 3) {
			GrabRecord record = new GrabRecord();
			record.setEduRecordId(eduRecord.getId());
			record.setType(grabType);
			try {
				File directory = new File(ApplicationConsts.FILE_UPLOAD_DIR + File.separator + "image" + File.separator + "grab");
				if (!directory.exists()) {
					directory.mkdirs();
				}
				String destFileName = UUID.randomUUID().toString() + ".jpg";
				File grabFile = new File(directory.getPath() + File.separator + destFileName);
				FileUtils.writeByteArrayToFile(grabFile, Base64Utils.decodeFromString(base64Photo));
				record.setImgUrl("image/grab/" + destFileName);
			} catch (IOException e) {
				logger.error("####【保存抓拍图片失败】####", e);
			}
			record.setCreateTime(new Date());
			grabRecordService.addGrabRecord(record);
		}
		return NULL.newInstance();
	}
	
}
