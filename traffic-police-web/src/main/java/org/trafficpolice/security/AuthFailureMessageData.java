package org.trafficpolice.security;

import org.trafficpolice.enumeration.AuditState;

/**
 * 认证失败时需要返回的数据
 * @author zhangxiaofei
 * @createdOn 2018年2月11日 上午10:05:11
 */
public class AuthFailureMessageData {

	/**
	 * 审核状态
	 */
	private AuditState auditState;
	
	/**
	 * 审核描述
	 */
	private String auditDesc;

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
	
}
