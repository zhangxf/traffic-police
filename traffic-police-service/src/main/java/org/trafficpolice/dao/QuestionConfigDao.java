package org.trafficpolice.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.enumeration.EduType;
import org.trafficpolice.po.QuestionConfig;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月23日 下午2:07:52
 */
@Repository(QuestionConfigDao.BEAN_ID)
public interface QuestionConfigDao {

	public static final String BEAN_ID = "questionConfigDao";
	
	public Integer doInsert(QuestionConfig questionConfig);
	
	public Integer doUpdate(QuestionConfig questionConfig);
	
	public QuestionConfig findQuestionConfig(@Param("eduType")EduType eduType);
	
}
