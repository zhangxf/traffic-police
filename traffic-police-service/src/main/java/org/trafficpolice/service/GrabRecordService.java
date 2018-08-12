package org.trafficpolice.service;

import java.util.List;

import org.trafficpolice.po.GrabRecord;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午2:14:01
 */
public interface GrabRecordService {

	public static final String BEAN_ID = "grabRecordService";
	
	public Integer addGrabRecord(GrabRecord record);
	
	public List<GrabRecord> findByEduIdAndType(Long eduRecordId, String type);
	
	public List<GrabRecord> findByEduId(Long eduRecordId);
	
}
