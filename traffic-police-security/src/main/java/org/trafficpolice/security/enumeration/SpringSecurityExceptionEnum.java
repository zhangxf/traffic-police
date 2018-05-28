package org.trafficpolice.security.enumeration;

import org.trafficpolice.commons.exception.IException;

/**
 * @author zhangxiaofei
 * @createdOn 2018年5月28日 下午8:02:58
 */
public enum SpringSecurityExceptionEnum implements IException {

	/**
	 * 权限不足（已登录，没有权限）
	 */
	AUTHORITY_INSUFFICIENT("0x5001", "authentication.authority.insufficient"),
	
	/**
	 * 凭证不足（未登录，或者sessionKey错误）
	 */
	CREDENCIALS_INSUFFICIENT("0x5002", "authentication.credencials.insufficient"),
	
	/**
	 * 认证失败
	 */
	AUTHENTICATION_FAILURE("0x5003", "authentication.failure"),
	
	;
	/**
	 * 状态码
	 */
	private String status;
	
	/**
	 * 对应的key
	 */
	private String key;
	
	private SpringSecurityExceptionEnum(String status, String key) {
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
