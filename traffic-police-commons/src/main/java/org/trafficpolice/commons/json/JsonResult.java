package org.trafficpolice.commons.json;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月8日 上午10:33:05
 */
public class JsonResult {

	/**
	 * 状态码
	 */
	private String status;
	
	/**
	 * 成功或失败信息
	 */
	private String message;
	
	/**
	 * 输出数据
	 */
	private Object data;

	public JsonResult() {
		
	}

	public JsonResult(String status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
