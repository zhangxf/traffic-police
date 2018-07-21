package org.trafficpolice.dto;

import java.util.Date;

import org.trafficpolice.enumeration.AuditState;
import org.trafficpolice.enumeration.IDType;
import org.trafficpolice.enumeration.LicenseType;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午5:04:37
 */
public class UserQueryParamDTO extends PageQueryParamDTO {

	private Long id;
	
	/**
	 * 驾驶人手机号
	 */
	private String phone;

	/**
	 * 审核状态
	 */
	private AuditState auditState;
	
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
	private LicenseType licenseType;
	
	/**
	 * 驾驶证编号
	 */
	private String licenseNo;
	
	/**
	 * 拉黑/洗白
	 */
	private Boolean disabled;
	
	/**
	 * 创建时间开始
	 */
	private Date createTimeBegin;
	
	/**
	 * 创建时间结束
	 */
	private Date createTimeEnd;
	
	/**
	 * 审核时间开始
	 */
	private Date auditTimeBegin;
	
	/**
	 * 审核时间结束
	 */
	private Date auditTimeEnd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public AuditState getAuditState() {
		return auditState;
	}

	public void setAuditState(AuditState auditState) {
		this.auditState = auditState;
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

	public LicenseType getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(LicenseType licenseType) {
		this.licenseType = licenseType;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
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

	public Date getAuditTimeBegin() {
		return auditTimeBegin;
	}

	public void setAuditTimeBegin(Date auditTimeBegin) {
		this.auditTimeBegin = auditTimeBegin;
	}

	public Date getAuditTimeEnd() {
		return auditTimeEnd;
	}

	public void setAuditTimeEnd(Date auditTimeEnd) {
		this.auditTimeEnd = auditTimeEnd;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
}
