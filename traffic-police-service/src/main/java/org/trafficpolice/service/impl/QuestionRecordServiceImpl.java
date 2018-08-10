package org.trafficpolice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.dao.QuestionRecordDao;
import org.trafficpolice.dto.QuestionRecordDTO;
import org.trafficpolice.dto.QuestionRecordQueryParamDTO;
import org.trafficpolice.service.QuestionRecordService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年8月10日 下午4:34:58
 */
@Service(QuestionRecordService.BEAN_ID)
public class QuestionRecordServiceImpl implements QuestionRecordService {

	@Autowired
	@Qualifier(QuestionRecordDao.BEAN_ID)
	private QuestionRecordDao questionRecordDao;
	
	@Override
	@Transactional
	public PageInfo<QuestionRecordDTO> findByPage(QuestionRecordQueryParamDTO queryDTO) {
		PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
		List<QuestionRecordDTO> records = questionRecordDao.findByCondition(queryDTO);
		return new PageInfo<QuestionRecordDTO>(records);
	}

}
