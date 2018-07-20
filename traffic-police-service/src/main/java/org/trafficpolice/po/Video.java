package org.trafficpolice.po;

import java.util.Date;

/**
 * @author zhangxiaofei
 * 2018年7月21日上午12:10:40
 */
public class Video {

	private Long id;
	
	/**
	 * 分类（七大类）
	 */
	private Long categoryId;
	
	/**
	 * 视频名称
	 */
	private String name;
	
	/**
	 * 原始文件名
	 */
	private String originName;
	
	
	/**
	 * 视频简介
	 */
	private String introduction;
	
	/**
	 * 时长:秒
	 */
	private Long duration;
	
	/**
	 * 文件大小B
	 */
	private Long fileSize;
	
	private String url;
	
	private String thumbUrl;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
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
