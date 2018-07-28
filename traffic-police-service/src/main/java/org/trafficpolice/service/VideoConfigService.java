package org.trafficpolice.service;

import java.util.List;

import org.trafficpolice.dto.VideoConfigDTO;

/**
 * @author zhangxiaofei
 * 2018年7月23日上午2:10:20
 */
public interface VideoConfigService {

	public static final String BEAN_ID = "videoConfigService";
	
	public void settingVideoConfig(List<VideoConfigDTO> videoConfigList);
	
	public List<VideoConfigDTO> findVideoConfig();
	
	public List<VideoConfigDTO> findVideoConfigAndCompleteState(Long userId, String batchNum);
	
}
