package org.trafficpolice.po;

import java.util.Date;

/**
 * 抓拍记录
 * @author zhangxiaofei
 * @createdOn 2018年8月10日 下午5:48:25
 */
public class GrabRecord {

	private Long id;
	
	/**
	 * 教育记录id
	 */
	private Long eduRecordId;
	
	/**
	 * video:视频阶段
	 * question：考试阶段
	 */
	private String type;
	
	/**
	 * 图片地址
	 */
	private String imgUrl;
	
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEduRecordId() {
		return eduRecordId;
	}

	public void setEduRecordId(Long eduRecordId) {
		this.eduRecordId = eduRecordId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
