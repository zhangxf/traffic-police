package org.trafficpolice.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.dao.EduRecordDao;
import org.trafficpolice.dao.GrabRecordDao;
import org.trafficpolice.po.GrabRecord;
import org.trafficpolice.service.GrabRecordService;

/**
 * @author zhangxiaofei
 * @createdOn 2018年8月10日 下午4:34:58
 */
@Service(GrabRecordService.BEAN_ID)
public class GrabRecordServiceImpl implements GrabRecordService {

	private static final Logger logger = LoggerFactory.getLogger(GrabRecordServiceImpl.class);
	
	@Autowired
	@Qualifier(EduRecordDao.BEAN_ID)
	private EduRecordDao eduRecordDao;
	
	@Autowired
	@Qualifier(GrabRecordDao.BEAN_ID)
	private GrabRecordDao grabRecordDao;

	@Override
	@Transactional
	public Integer addGrabRecord(GrabRecord record) {
		return grabRecordDao.doInsert(record);
	}

	@Override
	@Transactional
	public List<GrabRecord> findByEduIdAndType(Long eduRecordId, String type) {
		return grabRecordDao.findByEduIdAndType(eduRecordId, type);
	}

	@Override
	@Transactional
	public List<GrabRecord> findByEduId(Long eduRecordId) {
		return grabRecordDao.findByEduId(eduRecordId);
	}
	

}
