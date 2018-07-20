package org.trafficpolice.dto;

import java.util.Date;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午2:19:26
 */
public class QuestionQueryParamDTO extends PageQueryParamDTO {

	private Long id;
	
	/**
	 * 问题
	 */
	private String question;
	
	/**
	 * 分类（七大类）
	 */
	private Long categoryId;
	
	/**
	 * 科目类型，1：科目1；4：科目4
	 */
	private String subject;

	/**
	 * 题目类型 分为A1, A2, A3, B1, B2
	 */
	private String type;
	
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

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
