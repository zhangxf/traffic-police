package org.trafficpolice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.po.GrabRecord;

/**
 * @author zhangxiaofei
 * 2018年7月28日下午5:55:53
 */
@Repository(GrabRecordDao.BEAN_ID)
public interface GrabRecordDao {

	public static final String BEAN_ID = "grabRecordDao";
	
	public Integer doInsert(GrabRecord grabRecord);
	
	public List<GrabRecord> findByEduIdAndType(@Param("eduRecordId")Long eduRecordId, @Param("type")String type);
	
	public List<GrabRecord> findByEduId(@Param("eduRecordId")Long eduRecordId);
	
}
