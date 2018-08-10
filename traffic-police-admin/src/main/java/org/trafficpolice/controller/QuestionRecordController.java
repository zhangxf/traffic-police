package org.trafficpolice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.dto.QuestionRecordDTO;
import org.trafficpolice.dto.QuestionRecordQueryParamDTO;
import org.trafficpolice.service.QuestionRecordService;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午3:56:37
 */
@RestController
@RequestMapping("/questionrecord")
public class QuestionRecordController {

	private static final Logger logger = LoggerFactory.getLogger(QuestionRecordController.class);
	
	@Autowired
	@Qualifier(QuestionRecordService.BEAN_ID)
	private QuestionRecordService questionRecordService;
	
	@PostMapping("/page")
	public PageInfo<QuestionRecordDTO> queryByPage(@RequestBody QuestionRecordQueryParamDTO queryDTO) {
		return questionRecordService.findByPage(queryDTO);
	}
	
}
