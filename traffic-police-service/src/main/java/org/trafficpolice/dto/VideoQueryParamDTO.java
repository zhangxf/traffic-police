package org.trafficpolice.dto;

import java.util.Date;

/**
 * @author zhangxiaofei
 * 2018年7月21日上午12:26:29
 */
public class VideoQueryParamDTO extends PageQueryParamDTO {

	private Long id;
	
	/**
	 * 视频名称
	 */
	private String name;
	
	/**
	 * 分类
	 */
	private Long categoryId;
	
	/**
	 * 创建时间开始
	 */
	private Date createTimeBegin;
	
	/**
	 * 创建时间结束
	 */
	private Date createTimeEnd;
	
	/**
	 * 更新时间开始
	 */
	private Date updateTimeBegin;
	
	/**
	 * 更新时间结束
	 */
	private Date updateTimeEnd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Date getCreateTimeBegin() {
		return createTimeBegin;
	}

	public void setCreateTimeBegin(Date createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getUpdateTimeBegin() {
		return updateTimeBegin;
	}

	public void setUpdateTimeBegin(Date updateTimeBegin) {
		this.updateTimeBegin = updateTimeBegin;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}
	
}
