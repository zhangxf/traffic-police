package org.trafficpolice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.dto.EduRecordDTO;
import org.trafficpolice.dto.EduRecordQueryParamDTO;
import org.trafficpolice.enumeration.EduType;
import org.trafficpolice.po.EduRecord;

/**
 * @author zhangxiaofei
 * 2018年7月28日下午5:55:53
 */
@Repository(EduRecordDao.BEAN_ID)
public interface EduRecordDao {

	public static final String BEAN_ID = "eduRecordDao";
	
	public Integer doInsert(EduRecord eduRecord);
	
	public Integer doUpdate(EduRecord eduRecord);
	
	public EduRecord findUniqueRecord(@Param("userId")Long userId, @Param("batchNum")String batchNum, @Param("eduType")EduType eduType);
	
	public List<EduRecordDTO> findByCondition(EduRecordQueryParamDTO queryDTO);
	
	public Long calculateCostTime(@Param("userId")Long userId, @Param("batchNum")String batchNum, @Param("eduType")EduType eduType);
	
}
