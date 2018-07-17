package org.trafficpolice.exception;

import org.trafficpolice.commons.exception.IException;

/**
 * @author zhangxiaofei
 * 2018年7月14日下午7:09:01
 */
public enum MenuExceptionEnum implements IException {

	EXIST_MENU("0x5000", "menu.exist"),//菜单已存在
	
	NOT_EXIST_MENU("0x5001", "menu.not.exist"),//菜单不存在
	
	DISSALLOW_DELETE_MENU("0x5002", "menu.disallow.delete"),//不允许删除
	
	;
	
	/**
	 * 状态码
	 */
	private String status;
	
	/**
	 * 对应的key
	 */
	private String key;
	
	private MenuExceptionEnum(String status, String key) {
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
