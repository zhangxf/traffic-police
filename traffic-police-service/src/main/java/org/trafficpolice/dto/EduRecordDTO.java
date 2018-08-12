package org.trafficpolice.dto;

import org.trafficpolice.enumeration.IDType;
import org.trafficpolice.po.EduRecord;

/**
 * @author zhangxiaofei
 * @createdOn 2018年8月10日 下午4:13:27
 */
public class EduRecordDTO extends EduRecord {

	/**
	 * 驾驶人手机号
	 */
	private String phone;
	
	/**
	 * 真实姓名
	 */
	private String realname;
	
	/**
	 * 证件类型
	 */
	private IDType idType;
	
	/**
	 * 证件号码
	 */
	private String idNo;
	
	/**
	 * 驾驶证类型
	 */
	private String licenseType;
	
	/**
	 * 驾驶证编号
	 */
	private String licenseNo;
	
	/**
	 * 驾驶人本人头像
	 */
	private String headUrl;
	
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

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

}
