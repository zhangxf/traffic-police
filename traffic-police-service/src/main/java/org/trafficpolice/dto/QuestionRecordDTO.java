package org.trafficpolice.dto;

import org.trafficpolice.enumeration.IDType;
import org.trafficpolice.po.QuestionRecord;

/**
 * @author zhangxiaofei
 * @createdOn 2018年8月10日 下午4:13:27
 */
public class QuestionRecordDTO extends QuestionRecord {

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
