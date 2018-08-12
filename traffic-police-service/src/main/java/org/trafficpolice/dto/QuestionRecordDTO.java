package org.trafficpolice.dto;

import org.trafficpolice.enumeration.IDType;
import org.trafficpolice.po.QuestionRecord;

/**
 * @author zhangxiaofei
 * @createdOn 2018年8月10日 下午4:13:27
 */
public class QuestionRecordDTO extends QuestionRecord {

	/**
	 * 真实姓名
	 */
	private String realname;
	
	/**
	 * 驾驶人手机号
	 */
	private String phone;
	
	/**
	 * 证件类型
	 */
	private IDType idType;
	
	/**
	 * 证件号码
	 */
	private String idNo;
	
	/**
	 * 驾驶证编号
	 */
	private String licenseNo;

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

	public IDType getIdType() {
		return idType;
	}

	public void setIdType(IDType idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	
}
