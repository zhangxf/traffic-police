package org.trafficpolice.dto;

import java.util.Date;

/**
 * @author zhangxiaofei
 * @createdOn 2018年8月3日 上午11:07:49
 */
public class EduRecordQueryParamDTO extends PageQueryParamDTO {

	private Long userId;
	
	/**
	 * 真实姓名
	 */
	private String realname;
	
	/**
	 * 驾驶人手机号
	 */
	private String phone;
	
	/**
	 * 创建时间(教育时间范围开始)
	 */
	private Date createTimeBegin;
	
	/**
	 * 创建时间(教育时间范围结束)
	 */
	private Date createTimeEnd;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
	
}
