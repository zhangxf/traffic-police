package org.trafficpolice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.dto.QuestionDTO;
import org.trafficpolice.dto.QuestionQueryParamDTO;
import org.trafficpolice.po.Question;
import org.trafficpolice.service.QuestionService;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午3:56:37
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	@Qualifier(QuestionService.BEAN_ID)
	private QuestionService questionService;
	
	@PostMapping("/add")
	public NULL addQuestion(@RequestBody QuestionDTO questionDTO) {
		questionService.addQuestion(questionDTO);
		return NULL.newInstance();
	}
	
	@GetMapping("/delete")
	public NULL deleteById(Long id) {
		questionService.deleteById(id);
		return NULL.newInstance();
	}
	
	@PostMapping("/update")
	public NULL updateQuestion(@RequestBody QuestionDTO questionDTO) {
		questionService.updateQuestion(questionDTO);
		return NULL.newInstance();
	}
	
	@PostMapping("/page")
	public PageInfo<QuestionDTO> queryByPage(@RequestBody QuestionQueryParamDTO queryDTO) {
		return questionService.findByPage(queryDTO);
	}
	
	@GetMapping("/find-by-id")
	public QuestionDTO findById(Long id) {
		return questionService.findById(id);
	}
	
}
