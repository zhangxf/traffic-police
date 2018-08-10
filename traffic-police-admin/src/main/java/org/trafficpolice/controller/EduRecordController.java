package org.trafficpolice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.dto.EduRecordDTO;
import org.trafficpolice.dto.EduRecordQueryParamDTO;
import org.trafficpolice.service.EduRecordService;

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
	@Qualifier(EduRecordService.BEAN_ID)
	private EduRecordService eduRecordService;
	
	@PostMapping("/page")
	public PageInfo<EduRecordDTO> queryByPage(@RequestBody EduRecordQueryParamDTO queryDTO) {
		return eduRecordService.findByPage(queryDTO);
	}
	
}
