package org.trafficpolice.exception;

import org.trafficpolice.commons.exception.IException;

/**
 * @author zhangxiaofei
 * 2018年7月1日下午1:38:54
 */
public enum FileExceptionEnum implements IException {
	
	EMPTY_FILE("0x9000", "file.empty"),//文件为空
	
	;
	
	/**
	 * 状态码
	 */
	private String status;
	
	/**
	 * 对应的key
	 */
	private String key;
	
	private FileExceptionEnum(String status, String key) {
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
