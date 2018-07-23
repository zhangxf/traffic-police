package org.trafficpolice.service;

import org.trafficpolice.dto.QuestionConfigDTO;
import org.trafficpolice.enumeration.EduType;

/**
 * @author zhangxiaofei
 * 2018年7月23日上午2:10:20
 */
public interface QuestionConfigService {

	public static final String BEAN_ID = "questionConfigService";
	
	public void settingQuestionConfig(QuestionConfigDTO questionConfigDTO);
	
	public QuestionConfigDTO findQuestionConfig(EduType eduType);
	
}
