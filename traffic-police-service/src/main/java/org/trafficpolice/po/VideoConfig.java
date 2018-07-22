package org.trafficpolice.po;

import java.util.Date;

/**
 * 视频学习设置
 * @author zhangxiaofei
 * 2018年7月23日上午1:09:07
 */
public class VideoConfig {

	private Long id;
	
	/**
	 * 分类id
	 */
	private Long categoryId;
	
	/**
	 * 学习个数
	 */
	private Integer learnNum;
	
	private Date createTime;
	
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getLearnNum() {
		return learnNum;
	}

	public void setLearnNum(Integer learnNum) {
		this.learnNum = learnNum;
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
