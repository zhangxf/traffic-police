package org.trafficpolice.po;

import java.util.Date;

import org.trafficpolice.enumeration.EduType;

/**
 * 题目测试设置
 * @author zhangxiaofei
 * @createdOn 2018年7月23日 上午10:53:16
 */
public class QuestionConfig {

	private Long id;
	
	/**
	 * 题目总数
	 */
	private Long totalNum;
	
	/**
	 * 正确题目数
	 */
	private Long correctNum;
	
	/**
	 * 考试时长
	 */
	private Long period;
	
	/**
	 * 教育类型
	 */
	private EduType eduType;
	
	private Date createTime;
	
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public Long getCorrectNum() {
		return correctNum;
	}

	public void setCorrectNum(Long correctNum) {
		this.correctNum = correctNum;
	}

	public Long getPeriod() {
		return period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}

	public EduType getEduType() {
		return eduType;
	}

	public void setEduType(EduType eduType) {
		this.eduType = eduType;
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
