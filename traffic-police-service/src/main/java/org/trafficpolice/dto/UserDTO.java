package org.trafficpolice.dto;

import org.trafficpolice.po.User;

/**
 * @author zhangxiaofei
 * 2018年7月1日下午5:29:05
 */
public class UserDTO extends User {

	/**
	 * 证件图片token
	 */
	private String idCardImgUrlToken;
	
	/**
	 * 驾驶人本人头像
	 */
	private String headUrlToken;
	
	/**
	 * 验证码
	 */
	private String verifyCode;
	
	/**
	 * 验证码token
	 */
	private String verifyCodeToken;
	
	/**
	 * 审核状态查询返回的token
	 */
	private String auditQueryToken;

	public String getIdCardImgUrlToken() {
		return idCardImgUrlToken;
	}

	public void setIdCardImgUrlToken(String idCardImgUrlToken) {
		this.idCardImgUrlToken = idCardImgUrlToken;
	}

	public String getHeadUrlToken() {
		return headUrlToken;
	}

	public void setHeadUrlToken(String headUrlToken) {
		this.headUrlToken = headUrlToken;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getVerifyCodeToken() {
		return verifyCodeToken;
	}

	public void setVerifyCodeToken(String verifyCodeToken) {
		this.verifyCodeToken = verifyCodeToken;
	}

	public String getAuditQueryToken() {
		return auditQueryToken;
	}

	public void setAuditQueryToken(String auditQueryToken) {
		this.auditQueryToken = auditQueryToken;
	}

}
