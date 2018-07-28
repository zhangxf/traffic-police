package org.trafficpolice.exception;

import org.trafficpolice.commons.exception.IException;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午2:50:18
 */
public enum QuestionExceptionEnum implements IException {

	NOT_EXIST("0x2000", "question.not.exist"),//题目不存在
	
	CONFIG_ERROR("0x2001", "question.config.error"),//题目设置有误
	
	;
	
	/**
	 * 状态码
	 */
	private String status;
	
	/**
	 * 对应的key
	 */
	private String key;
	
	private QuestionExceptionEnum(String status, String key) {
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
