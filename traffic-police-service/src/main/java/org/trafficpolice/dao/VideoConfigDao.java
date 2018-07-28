package org.trafficpolice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.dto.VideoConfigDTO;
import org.trafficpolice.po.VideoConfig;

/**
 * @author zhangxiaofei
 * 2018年7月23日上午1:14:10
 */
@Repository(VideoConfigDao.BEAN_ID)
public interface VideoConfigDao {

	public static final String BEAN_ID = "videoConfigDao";
	
	public Integer doInsert(VideoConfig videoConfig);
	
	public Integer doUpdate(VideoConfig videoConfig);
	
	public List<VideoConfigDTO> findVideoConfig();
	
	public List<VideoConfigDTO> findVideoConfigWithCompleteNum(@Param("userId")Long userId, @Param("batchNum")String batchNum);
	
}
