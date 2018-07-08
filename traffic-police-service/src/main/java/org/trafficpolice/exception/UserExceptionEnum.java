package org.trafficpolice.exception;

import org.trafficpolice.commons.exception.IException;

/**
 * @author zhangxiaofei
 * 2018年7月1日下午1:38:54
 */
public enum UserExceptionEnum implements IException {
	
	ID_CARD_INCORRECT("0x8000", "user.idcard.incorrect"),//证件号码不正确
	
	EXIST_USER("0x8001", "user.exist"),//用户已存在
	
	NOT_FOUND("0x8002", "user.not.found"),//用户不存在
	
	WAS_BLACK("0x8003", "user.was.black"),//黑名单用户
	
	AUDITSTATE_INHAND("0x8004", "user.auditstate.inhand"),//审核中
	
	AUDITSTATE_REJECT("0x8005", "user.auditstate.reject"),//审核失败，驳回
	
	EXIST_IDNO_LISENCENO("0x8006", "user.exist.idno.lisenceno"),//用户已存在，idno或者lisenceno已被注册过
	;
	
	/**
	 * 状态码
	 */
	private String status;
	
	/**
	 * 对应的key
	 */
	private String key;
	
	private UserExceptionEnum(String status, String key) {
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
