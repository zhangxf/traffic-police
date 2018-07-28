package org.trafficpolice.dto;

/**
 * 用户视频学习信息
 * @author zhangxiaofei
 * 2018年7月28日下午2:34:51
 */
public class VideoLearnInfo {

	/**
	 * 已完成时长：秒
	 */
	private Long completeDuration;
	
	/**
	 * 需要学习时长：秒
	 */
	private Long learnDuration;

	public Long getCompleteDuration() {
		return completeDuration;
	}

	public void setCompleteDuration(Long completeDuration) {
		this.completeDuration = completeDuration;
	}

	public Long getLearnDuration() {
		return learnDuration;
	}

	public void setLearnDuration(Long learnDuration) {
		this.learnDuration = learnDuration;
	}
	
}
