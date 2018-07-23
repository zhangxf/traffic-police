package org.trafficpolice.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.trafficpolice.dto.QuestionConfigDetailDTO;
import org.trafficpolice.po.QuestionConfigDetail;

/**
 * @author zhangxiaofei
 * 2018年7月23日上午1:14:10
 */
@Repository(QuestionConfigDetailDao.BEAN_ID)
public interface QuestionConfigDetailDao {

	public static final String BEAN_ID = "questionConfigDetailDao";
	
	public Integer doInsert(QuestionConfigDetail questionConfigDetail);
	
	public Integer doUpdate(QuestionConfigDetail questionConfigDetail);
	
	public List<QuestionConfigDetailDTO> findQuestionConfigDetail();
	
}
