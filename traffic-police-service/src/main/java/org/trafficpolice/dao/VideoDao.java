package org.trafficpolice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.dto.VideoDTO;
import org.trafficpolice.dto.VideoQueryParamDTO;
import org.trafficpolice.po.Video;

/**
 * @author zhangxiaofei
 * 2018年7月21日上午12:23:19
 */
@Repository(VideoDao.BEAN_ID)
public interface VideoDao {

	public static final String BEAN_ID = "videoDao";
	
	public Integer doInsert(Video video);
	
	public Integer doUpdate(Video video);
	
	public Integer doDelete(@Param("id")Long id);
	
	public List<Video> findAll();
	
	public List<VideoDTO> findAllDTO();
	
	public VideoDTO findById(@Param("id")Long id);
	
	public List<VideoDTO> findByCondition(VideoQueryParamDTO queryDTO);
	
}
