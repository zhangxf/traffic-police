package org.trafficpolice.po;

import java.util.Date;

import org.trafficpolice.enumeration.EduType;

/**
 * 教育记录
 * @author zhangxiaofei
 * @createdOn 2018年8月10日 下午5:48:25
 */
public class EduRecord {

	private Long id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 批次号
	 */
	private String batchNum;
	
	/**
	 * 教育类型
	 */
	private EduType eduType;
	
	/**
	 * 是否完成
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public EduType getEduType() {
		return eduType;
	}

	public void setEduType(EduType eduType) {
		this.eduType = eduType;
	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
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
