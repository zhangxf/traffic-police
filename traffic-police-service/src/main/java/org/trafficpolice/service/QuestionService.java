package org.trafficpolice.service;

import org.trafficpolice.dto.QuestionDTO;
import org.trafficpolice.dto.QuestionQueryParamDTO;
import org.trafficpolice.enumeration.EduType;
import org.trafficpolice.po.Question;
import org.trafficpolice.po.QuestionConfig;
import org.trafficpolice.po.QuestionRecord;
import org.trafficpolice.po.User;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午2:14:01
 */
public interface QuestionService {

	public static final String BEAN_ID = "questionService";
	
	public void addQuestion(QuestionDTO questionDTO);
	
	public void updateQuestion(QuestionDTO questionDTO);
	
	public void doInsert(Question question);
	
	public void doUpdate(Question question);
	
	public void deleteById(Long id);
	
	public PageInfo<QuestionDTO> findByPage(QuestionQueryParamDTO queryDTO);
	
	public QuestionDTO findById(Long id);
	
	public Question findSameQuestion(Question question);
	
	public QuestionConfig initUserQuestions(User user, EduType eduType);
	
	public QuestionDTO nextQuestion(User user, EduType eduType);
	
	public void saveOrUpdateQuestionRecord(QuestionRecord record);
	
}
