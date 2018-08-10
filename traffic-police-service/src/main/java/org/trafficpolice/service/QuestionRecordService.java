package org.trafficpolice.service;

import org.trafficpolice.dto.QuestionRecordDTO;
import org.trafficpolice.dto.QuestionRecordQueryParamDTO;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午2:14:01
 */
public interface QuestionRecordService {

	public static final String BEAN_ID = "questionRecordService";
	
	public PageInfo<QuestionRecordDTO> findByPage(QuestionRecordQueryParamDTO queryDTO);
	
}
