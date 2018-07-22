package org.trafficpolice.exception;

import org.trafficpolice.commons.exception.IException;

/**
 * @author zhangxiaofei
 * 2018年7月1日下午1:38:54
 */
public enum VerifyCodeExceptionEnum implements IException {
	
	SEND_TYPE_INCORRECT("0x7000", "verifycode.send.type.incorrect"),//发送验证码类型不正确
	
	INCORRECT("0x7001", "verifycode.incorrect"),//验证码不正确
	
	;
	
	/**
	 * 状态码
	 */
	private String status;
	
	/**
	 * 对应的key
	 */
	private String key;
	
	private VerifyCodeExceptionEnum(String status, String key) {
		this.status = status;
		this.key = key;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
