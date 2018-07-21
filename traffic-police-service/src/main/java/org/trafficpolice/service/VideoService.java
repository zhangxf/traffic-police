package org.trafficpolice.service;

import org.trafficpolice.dto.VideoDTO;
import org.trafficpolice.dto.VideoQueryParamDTO;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * 2018年7月21日上午12:50:19
 */
public interface VideoService {

	public static final String BEAN_ID = "videoService";
	
	public void addVideo(VideoDTO videoDTO);
	
	public void updateVideo(VideoDTO videoDTO);
	
	public void deleteById(Long id);
	
	public PageInfo<VideoDTO> findByPage(VideoQueryParamDTO queryDTO);
	
	public VideoDTO findById(Long id);
	
}
