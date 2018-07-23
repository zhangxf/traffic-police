package org.trafficpolice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.dto.QuestionDTO;
import org.trafficpolice.dto.QuestionQueryParamDTO;
import org.trafficpolice.po.Question;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午12:38:16
 */
@Repository(QuestionDao.BEAN_ID)
public interface QuestionDao {

	public static final String BEAN_ID = "questionDao";
	
	public Integer doInsert(Question question);
	
	public Integer doUpdate(Question question);
	
	public Integer doDelete(@Param("id")Long id);
	
	public List<Question> findAll();
	
	public QuestionDTO findById(@Param("id")Long id);
	
	public List<QuestionDTO> findByCondition(QuestionQueryParamDTO queryDTO);
	
	public Question findByQuestion(@Param("question")String question);
	
}
