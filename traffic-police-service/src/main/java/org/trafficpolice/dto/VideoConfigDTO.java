package org.trafficpolice.dto;

import org.trafficpolice.enumeration.CategoryType;
import org.trafficpolice.po.VideoConfig;

/**
 * @author zhangxiaofei
 * 2018年7月23日上午1:16:55
 */
public class VideoConfigDTO extends VideoConfig {

	private Boolean isCompleted;
	
	private String categoryName;
	
	private CategoryType categoryType;
	
	/**
	 * 视频个数
	 */
	private Long videoNum;

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public CategoryType getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(CategoryType categoryType) {
		this.categoryType = categoryType;
	}

	public Long getVideoNum() {
		return videoNum;
	}

	public void setVideoNum(Long videoNum) {
		this.videoNum = videoNum;
	}
	
}
