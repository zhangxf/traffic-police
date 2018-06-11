package org.trafficpolice.commons.enumeration;

import org.trafficpolice.commons.exception.IException;

/**
 * @author zhangxiaofei
 * @version 1.0
 * @createdOn 2014年10月21日 上午11:39:11
 * 统一管理通用异常code以及异常key
 */
public enum GlobalStatusEnum implements IException {

	SUCCESS("0x0000", "status.success"),//成功
	
	FAILURE("0x0001", "status.failure"),//失败
	
	PARAM_ERROR("0x0002", "param.error"),//参数错误
	
	PARAM_MISS("0x0003", "param.miss"),//缺少参数
	
	;
	
	/**
	 * 状态码
	 */
	private String status;
	
	/**
	 * 对应的key
	 */
	private String key;
	
	private GlobalStatusEnum(String status, String key) {
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
