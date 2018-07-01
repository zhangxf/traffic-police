package org.trafficpolice.dto;

/**
 * @author zhangxiaofei
 * 2018年7月1日下午5:43:21
 */
public class VerifyCodeDTO {
	
	/**
	 * 验证码类型 如：register, login, changepwd......
	 */
	private String type;
	
	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 验证码
	 */
	private String code;
	
	/**
	 * token
	 */
	private String token;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
