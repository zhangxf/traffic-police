package org.trafficpolice.dto;

/**
 * 审核状态查询参数
 * @author zhangxiaofei
 * 2018年7月8日上午11:45:18
 */
public class AuditQueryParamDTO {

	/**
	 * 驾驶人手机号
	 */
	private String phone;
	
	/**
	 * 验证码
	 */
	private String verifyCode;
	
	/**
	 * 验证码token
	 */
	private String verifyCodeToken;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
	
}
