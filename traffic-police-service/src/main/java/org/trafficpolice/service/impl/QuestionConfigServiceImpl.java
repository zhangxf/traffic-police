package org.trafficpolice.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.dao.QuestionConfigDao;
import org.trafficpolice.dao.QuestionConfigDetailDao;
import org.trafficpolice.dto.QuestionConfigDTO;
import org.trafficpolice.dto.QuestionConfigDetailDTO;
import org.trafficpolice.enumeration.EduType;
import org.trafficpolice.po.QuestionConfig;
import org.trafficpolice.po.QuestionConfigDetail;
import org.trafficpolice.service.QuestionConfigService;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月23日 下午3:21:39
 */
@Service(QuestionConfigService.BEAN_ID)
public class QuestionConfigServiceImpl implements QuestionConfigService {

	@Autowired
	@Qualifier(QuestionConfigDao.BEAN_ID)
	private QuestionConfigDao questionConfigDao;
	
	@Autowired
	@Qualifier(QuestionConfigDetailDao.BEAN_ID)
	private QuestionConfigDetailDao questionConfigDetailDao;
	
	@Override
	@Transactional
	public void settingQuestionConfig(QuestionConfigDTO questionConfigDTO) {
		QuestionConfig config = new QuestionConfig();
		config.setId(questionConfigDTO.getId());
		config.setTotalNum(questionConfigDTO.getTotalNum());
		config.setCorrectNum(questionConfigDTO.getCorrectNum());
		config.setPeriod(questionConfigDTO.getPeriod());
		config.setEduType(questionConfigDTO.getEduType());
		if (config.getId() != null) {
			config.setUpdateTime(new Date());
			questionConfigDao.doUpdate(config);
		} else {
			config.setCreateTime(new Date());
			questionConfigDao.doInsert(config);
		}
		for (QuestionConfigDetailDTO detailDTO : questionConfigDTO.getDetail()) {
			QuestionConfigDetail detail = new QuestionConfigDetail();
			detail.setId(detailDTO.getId());
			detail.setCategoryId(detailDTO.getCategoryId());
			detail.setLearnNum(detailDTO.getLearnNum());
			if (detail.getId() != null) {
				detail.setUpdateTime(new Date());
				questionConfigDetailDao.doUpdate(detail);
			} else {
				detail.setCreateTime(new Date());
				questionConfigDetailDao.doInsert(detail);
			}
		}
	}

	@Override
	@Transactional
	public QuestionConfigDTO findQuestionConfig(EduType eduType) {
		QuestionConfigDTO configDTO = new QuestionConfigDTO();
		QuestionConfig config = questionConfigDao.findQuestionConfig(eduType);
		if (config != null) {
			configDTO.setId(config.getId());
			configDTO.setTotalNum(config.getTotalNum());
			configDTO.setCorrectNum(config.getCorrectNum());
			configDTO.setPeriod(config.getPeriod());
			configDTO.setCreateTime(config.getCreateTime());
			configDTO.setUpdateTime(config.getUpdateTime());
		}
		List<QuestionConfigDetailDTO> detail = questionConfigDetailDao.findQuestionConfigDetail();
		configDTO.setDetail(detail);
		return configDTO;
	}

}
