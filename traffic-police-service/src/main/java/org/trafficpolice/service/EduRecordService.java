package org.trafficpolice.service;

import org.trafficpolice.dto.EduRecordDTO;
import org.trafficpolice.dto.EduRecordQueryParamDTO;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午2:14:01
 */
public interface EduRecordService {

	public static final String BEAN_ID = "eduRecordService";
	
	public PageInfo<EduRecordDTO> findByPage(EduRecordQueryParamDTO queryDTO);
	
}
