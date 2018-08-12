package org.trafficpolice.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.consts.ServiceConsts;
import org.trafficpolice.dto.EduRecordDTO;
import org.trafficpolice.dto.EduRecordQueryParamDTO;
import org.trafficpolice.po.GrabRecord;
import org.trafficpolice.service.EduRecordService;
import org.trafficpolice.service.GrabRecordService;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午3:56:37
 */
@RestController
@RequestMapping("/edurecord")
public class EduRecordController {

	private static final Logger logger = LoggerFactory.getLogger(EduRecordController.class);
	
	@Autowired
	@Qualifier(GrabRecordService.BEAN_ID)
	private GrabRecordService grabRecordService;
	
	@Autowired
	@Qualifier(EduRecordService.BEAN_ID)
	private EduRecordService eduRecordService;
	
	@PostMapping("/page")
	public PageInfo<EduRecordDTO> queryByPage(@RequestBody EduRecordQueryParamDTO queryDTO) {
		return eduRecordService.findByPage(queryDTO);
	}
	
	@GetMapping("/detail")
	public EduRecordDTO queryEduRecordDetail(@RequestParam("id") Long id) {
		EduRecordDTO eduRecord = eduRecordService.findById(id);
		if (eduRecord == null) {
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
	
	@GetMapping("/grabrecord")
	public List<GrabRecord> queryGrabRecordByEduId(@RequestParam("eduRecordId") Long eduRecordId) {
		List<GrabRecord> list = grabRecordService.findByEduId(eduRecordId);
		if (CollectionUtils.isNotEmpty(list)) {
			for (GrabRecord gr : list) {
				String imgUrl = gr.getImgUrl();
				if (StringUtils.isNoneBlank(imgUrl) && !imgUrl.startsWith("http")) {
					gr.setImgUrl(ServiceConsts.NFS_ADDRESS + imgUrl);
				}
			}
		}
		return list;
	}
	
}
