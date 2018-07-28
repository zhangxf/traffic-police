package org.trafficpolice.po;

import java.util.Date;

import org.trafficpolice.enumeration.EduType;

/**
 * @author zhangxiaofei
 * 2018年7月28日下午5:49:22
 */
public class QuestionRecord {

	private Long id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 教育类型
	 */
	private EduType eduType;
	
	/**
	 * 批次
	 */
	private String batchNum;
	
	/**
	 * 正确题目数
	 */
	private Long correctNum;
	
	/**
	 * 错误题目数
	 */
	private Long wrongNum;
	
	/**
	 * 花费时长:秒
	 */
	private Long costTime;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
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

	public EduType getEduType() {
		return eduType;
	}

	public void setEduType(EduType eduType) {
		this.eduType = eduType;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public Long getCorrectNum() {
		return correctNum;
	}

	public void setCorrectNum(Long correctNum) {
		this.correctNum = correctNum;
	}

	public Long getWrongNum() {
		return wrongNum;
	}

	public void setWrongNum(Long wrongNum) {
		this.wrongNum = wrongNum;
	}

	public Long getCostTime() {
		return costTime;
	}

	public void setCostTime(Long costTime) {
		this.costTime = costTime;
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
