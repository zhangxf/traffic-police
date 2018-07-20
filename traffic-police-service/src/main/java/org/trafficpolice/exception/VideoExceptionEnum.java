package org.trafficpolice.exception;

import org.trafficpolice.commons.exception.IException;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午2:50:18
 */
public enum VideoExceptionEnum implements IException {

	NOT_EXIST("0x1000", "video.not.exist");//视频不存在
	
	;
	
	/**
	 * 状态码
	 */
	private String status;
	
	/**
	 * 对应的key
	 */
	private String key;
	
	private VideoExceptionEnum(String status, String key) {
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
