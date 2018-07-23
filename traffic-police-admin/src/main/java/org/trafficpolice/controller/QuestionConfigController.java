package org.trafficpolice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.dto.QuestionConfigDTO;
import org.trafficpolice.enumeration.EduType;
import org.trafficpolice.service.QuestionConfigService;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月23日 下午3:43:34
 */
@RestController
@RequestMapping("/question/config")
public class QuestionConfigController {

	@Autowired
	@Qualifier(QuestionConfigService.BEAN_ID)
	private QuestionConfigService questionConfigService;
	
	@GetMapping("/{edutype}")
	public QuestionConfigDTO findQuestionConfig(@PathVariable("edutype") String edutype) {
		return questionConfigService.findQuestionConfig(EduType.valueOf(edutype.toUpperCase()));
	}
	
	@PostMapping("/{edutype}/setting")
	public NULL settingVideoConfig(@PathVariable("edutype") String edutype, @RequestBody QuestionConfigDTO questionConfigDTO) {
		questionConfigDTO.setEduType(EduType.valueOf(edutype.toUpperCase()));
		questionConfigService.settingQuestionConfig(questionConfigDTO);
		return NULL.newInstance();
	}
	
}
