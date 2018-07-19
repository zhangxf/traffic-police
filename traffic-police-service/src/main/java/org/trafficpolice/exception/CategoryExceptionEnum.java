package org.trafficpolice.exception;

import org.trafficpolice.commons.exception.IException;

/**
 * @author zhangxiaofei
 * 2018年7月20日上午12:53:57
 */
public enum CategoryExceptionEnum implements IException {

	EXIST_CTG("0x3000", "category.exist"),//该分类已存在
	
	NOT_EXIST_CTG("0x3001", "category.not.exist"),//分类不存在
	
	;
	
	/**
	 * 状态码
	 */
	private String status;
	
	/**
	 * 对应的key
	 */
	private String key;
	
	private CategoryExceptionEnum(String status, String key) {
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
