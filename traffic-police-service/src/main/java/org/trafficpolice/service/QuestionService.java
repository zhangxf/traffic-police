package org.trafficpolice.service;

import org.trafficpolice.dto.QuestionDTO;
import org.trafficpolice.dto.QuestionQueryParamDTO;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午2:14:01
 */
public interface QuestionService {

	public static final String BEAN_ID = "questionService";
	
	public void addQuestion(QuestionDTO questionDTO);
	
	public void updateQuestion(QuestionDTO questionDTO);
	
	public void deleteById(Long id);
	
	public PageInfo<QuestionDTO> findByPage(QuestionQueryParamDTO queryDTO);
	
	public QuestionDTO findById(Long id);
	
}
