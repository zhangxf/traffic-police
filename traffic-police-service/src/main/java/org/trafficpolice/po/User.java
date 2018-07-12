package org.trafficpolice.po;

import java.io.Serializable;
import java.util.Date;

import org.trafficpolice.enumeration.AuditState;
import org.trafficpolice.enumeration.IDType;
import org.trafficpolice.enumeration.LicenseType;

/**
 * 前台用户
 * @author zhangxiaofei
 * 2018年6月10日下午7:02:24
 */
public class User implements Serializable {

	private static final long serialVersionUID = 7273996644772750973L;

	private Long id;
	
	/**
	 * 证件类型
	 */
	private IDType idType;
	
	/**
	 * 证件号码
	 */
	private String idNo;
	
	/**
	 * 证件图片
	 */
	private String idCardImgUrl;

	/**
	 * 驾驶证类型
	 */
	private LicenseType licenseType;
	
	/**
	 * 驾驶证编号
	 */
	private String licenseNo;
	
	/**
	 * 驾驶证有效期开始时间
	 */
	private Date licenseBeginDate;
	
	/**
	 * 驾驶证有效期结束时间
	 */
	private Date licenseEndDate;
	
	/**
	 * 驾驶人本人头像
	 */
	private String headUrl;
	
	/**
	 * 驾驶人手机号
	 */
	private String phone;

	/**
	 * 审核状态
	 */
	private AuditState auditState;
	
	/**
	 * 审核描述
	 */
	private String auditDesc;
	
	/**
	 * 审核时间
	 */
	private Date auditTime;
	
	/**
	 * 拉黑/洗白
	 */
	private Boolean disabled;
	
	private Date createTime;
	
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getIdCardImgUrl() {
		return idCardImgUrl;
	}

	public void setIdCardImgUrl(String idCardImgUrl) {
		this.idCardImgUrl = idCardImgUrl;
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

	public Date getLicenseBeginDate() {
		return licenseBeginDate;
	}

	public void setLicenseBeginDate(Date licenseBeginDate) {
		this.licenseBeginDate = licenseBeginDate;
	}

	public Date getLicenseEndDate() {
		return licenseEndDate;
	}

	public void setLicenseEndDate(Date licenseEndDate) {
		this.licenseEndDate = licenseEndDate;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
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

	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
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
