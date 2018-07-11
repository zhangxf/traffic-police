package org.trafficpolice.exception;

import org.trafficpolice.commons.exception.IException;

/**
 * @author zhangxiaofei
 * 2018年7月11日下午11:36:12
 */
public enum BGUserExceptionEnum implements IException {

	NOT_FOUND("0x6001", "authentication.user.not.found"),//用户不存在
	
	IS_NOT_ENABLE("0x6002", "authentication.user.is.not.enable"),//用户未启用
	
	PASSWORD_INCORRECT("0x6003", "authentication.username.password.error"),//密码错误
	
	;
	
	/**
	 * 状态码
	 */
	private String status;
	
	/**
	 * 对应的key
	 */
	private String key;
	
	private BGUserExceptionEnum(String status, String key) {
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
