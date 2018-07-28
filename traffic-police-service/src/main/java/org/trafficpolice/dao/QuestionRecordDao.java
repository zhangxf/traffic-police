package org.trafficpolice.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.enumeration.EduType;
import org.trafficpolice.po.QuestionRecord;

/**
 * @author zhangxiaofei
 * 2018年7月28日下午5:55:53
 */
@Repository(QuestionRecordDao.BEAN_ID)
public interface QuestionRecordDao {

	public static final String BEAN_ID = "questionRecordDao";
	
	public Integer doInsert(QuestionRecord questionRecord);
	
	public Integer doUpdate(QuestionRecord questionRecord);
	
	public QuestionRecord findUniqueRecord(@Param("userId")Long userId, @Param("batchNum")String batchNum, @Param("eduType")EduType eduType);
	
}
