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
import org.trafficpolice.dto.EduRecordDTO;
import org.trafficpolice.dto.EduRecordQueryParamDTO;
import org.trafficpolice.enumeration.EduType;
import org.trafficpolice.po.EduRecord;
import org.trafficpolice.service.EduRecordService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年8月10日 下午4:34:58
 */
@Service(EduRecordService.BEAN_ID)
public class EduRecordServiceImpl implements EduRecordService {

	private static final Logger logger = LoggerFactory.getLogger(EduRecordServiceImpl.class);
	
	@Autowired
	@Qualifier(EduRecordDao.BEAN_ID)
	private EduRecordDao eduRecordDao;
	
	@Autowired
	@Qualifier(GrabRecordDao.BEAN_ID)
	private GrabRecordDao grabRecordDao;
	
	@Override
	@Transactional
	public Integer addEduRecord(EduRecord record) {
		return eduRecordDao.doInsert(record);
	}

	@Override
	@Transactional
	public PageInfo<EduRecordDTO> findByPage(EduRecordQueryParamDTO queryDTO) {
		PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
		List<EduRecordDTO> records = eduRecordDao.findByCondition(queryDTO);
		return new PageInfo<EduRecordDTO>(records);
	}

	@Override
	@Transactional
	public EduRecord findUniqueRecord(Long userId, String batchNum, EduType eduType) {
		return eduRecordDao.findUniqueRecord(userId, batchNum, eduType);
	}

	@Override
	@Transactional
	public EduRecordDTO findById(Long id) {
		return eduRecordDao.findById(id);
	}

}
