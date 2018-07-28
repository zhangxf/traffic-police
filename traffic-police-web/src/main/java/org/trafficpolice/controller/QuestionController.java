package org.trafficpolice.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.dto.QuestionDTO;
import org.trafficpolice.dto.QuestionQueryParamDTO;
import org.trafficpolice.enumeration.EduType;
import org.trafficpolice.po.QuestionConfig;
import org.trafficpolice.po.QuestionRecord;
import org.trafficpolice.po.User;
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
	
	/**
	 * 试题分页查询（试题练习接口）
	 * @param queryDTO
	 * @return
	 */
	@PostMapping("/page")
	public PageInfo<QuestionDTO> queryByPage(@RequestBody QuestionQueryParamDTO queryDTO) {
		return questionService.findByPage(queryDTO);
	}
	
	/**
	 * 初始化试题，为当前用户随机出试题
	 * @return
	 */
	@GetMapping("/init")
	public QuestionConfig initUserQuestions(@AuthenticationPrincipal(expression = "currentUser") User user) {
		return questionService.initUserQuestions(user, EduType.CHECK);
	}
	
	@GetMapping("/next")
	public QuestionDTO nextQuestion(@AuthenticationPrincipal(expression = "currentUser") User user) {
		return questionService.nextQuestion(user, EduType.CHECK);
	}
	
	@PostMapping("/record")
	public NULL record(@AuthenticationPrincipal(expression = "currentUser") User user, @RequestBody QuestionRecord record) {
		record.setUserId(user.getId());
		record.setEduType(EduType.CHECK);
		String batchNum = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		record.setBatchNum(batchNum);
		questionService.saveOrUpdateQuestionRecord(record);
		return NULL.newInstance();
	}
	
}
