package org.trafficpolice.commons.exception;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月3日 下午4:37:09
 */
public class ExceptionMessage {

	/**
	 * 异常状态码
	 */
	private String status;
	
	/**
	 * message引用的key example: Role.required=角色 未定义 key为：Role.required
	 */
	private String key;

	/**
	 * message的value中的参数 example: Role.codeError=角色代码 {0} 设置有误 args为：{0}
	 */
	private Object[] args;
	
	/**
	 * 异常时需要返回的必要数据（多数情况下不需要，少数情况下需要）
	 */
	private Object data;

	public ExceptionMessage() {

	}

	public ExceptionMessage(String status, String key) {
		this.status = status;
		this.key = key;
	}
	
	public ExceptionMessage(String status, String key, Object data) {
		this.status = status;
		this.key = key;
		this.data = data;
	}
	
	public ExceptionMessage(String status, String key, Object[] args) {
		this.status = status;
		this.key = key;
		this.args = args;
	}
	
	public ExceptionMessage(String status, String key, Object[] args, Object data) {
		this.status = status;
		this.key = key;
		this.args = args;
		this.data = data;
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

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
