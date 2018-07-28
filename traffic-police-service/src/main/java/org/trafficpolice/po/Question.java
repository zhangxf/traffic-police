package org.trafficpolice.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 试题，题目
 * @author zhangxiaofei
 * 2018年7月19日下午11:16:27
 */
public class Question implements Serializable {

	private static final long serialVersionUID = -2800409348081790116L;

	private Long id;
	
	/**
	 * 问题
	 */
	private String question;
	
	/**
	 * 答案
	 */
	private String answer;
	
	/**
	 * 选项，当内容为空时表示判断题正确选项
	 */
	private String item1;
	
	/**
	 * 选项，当内容为空时表示判断题错误选项
	 */
	private String item2;
	
	private String item3;
	
	private String item4;
	
	private String explains;
	
	private String url;
	
	private String originUrl;
	
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

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getItem1() {
		return item1;
	}

	public void setItem1(String item1) {
		this.item1 = item1;
	}

	public String getItem2() {
		return item2;
	}

	public void setItem2(String item2) {
		this.item2 = item2;
	}

	public String getItem3() {
		return item3;
	}

	public void setItem3(String item3) {
		this.item3 = item3;
	}

	public String getItem4() {
		return item4;
	}

	public void setItem4(String item4) {
		this.item4 = item4;
	}

	public String getExplains() {
		return explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOriginUrl() {
		return originUrl;
	}

	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
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
