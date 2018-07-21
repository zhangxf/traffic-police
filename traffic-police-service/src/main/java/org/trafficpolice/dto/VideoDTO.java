package org.trafficpolice.dto;

import org.trafficpolice.po.Video;

/**
 * @author zhangxiaofei
 * 2018年7月21日上午12:53:39
 */
public class VideoDTO extends Video {

	/**
	 * 分类名称
	 */
	private String categoryName;
	
	/**
	 * 视频文件token
	 */
	private String videoToken;
	
	/**
	 * 缩略图token
	 */
	private String thumbToken;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getVideoToken() {
		return videoToken;
	}

	public void setVideoToken(String videoToken) {
		this.videoToken = videoToken;
	}

	public String getThumbToken() {
		return thumbToken;
	}

	public void setThumbToken(String thumbToken) {
		this.thumbToken = thumbToken;
	}
	
}
