package org.trafficpolice.po;

import java.util.Date;

/**
 * 视频观看记录
 * @author zhangxiaofei
 * @createdOn 2018年7月25日 下午6:28:13
 */
public class VideoRecord {

	private Long id;
	
	/**
	 * 视频id
	 */
	private Long videoId;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 批次号
	 */
	private String batchNum;
	
	/**
	 * 视频时长(秒)
	 */
	private Long duration;
	
	/**
	 * 已完成时长（已看时长）秒
	 */
	private Long completedDuration;
	
	/**
	 * 是否已看完
	 */
	private Boolean isCompleted;
	
	private Date createTime;
	
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVideoId() {
		return videoId;
	}

	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Long getCompletedDuration() {
		return completedDuration;
	}

	public void setCompletedDuration(Long completedDuration) {
		this.completedDuration = completedDuration;
	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}