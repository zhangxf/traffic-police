package org.trafficpolice.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.po.VideoRecord;

/**
 * @author zhangxiaofei
 * 2018年7月21日上午12:23:19
 */
@Repository(VideoRecordDao.BEAN_ID)
public interface VideoRecordDao {

	public static final String BEAN_ID = "videoRecordDao";
	
	public Integer doInsert(VideoRecord record);
	
	public Integer doUpdate(VideoRecord record);
	
	public VideoRecord findVideoRecord(@Param("userId")Long userId, @Param("videoId")Long videoId, @Param("reservationId")Long reservationId);
	
}
