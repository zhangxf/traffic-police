package org.trafficpolice.commons.exception;


/**
 * @author zhangxiaofei
 * @version 1.0
 * @createdOn 2014年9月28日 下午2:41:15
 * 异常基类
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -821574242321397324L;
	
	/**
	 * 封装异常信息
	 */
	private ExceptionMessage exceptionMessage;

	public BaseException() {
		super();
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
	
	public BaseException(String message) {
		super(message);
	}
	
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BaseException(ExceptionMessage exceptionMessage, Throwable cause) {
		super(cause);
		this.exceptionMessage = exceptionMessage;
	}
	
	public BaseException(ExceptionMessage exceptionMessage) {
		super("####【异常】【状态码】【" + exceptionMessage.getStatus() + "】####");
		this.exceptionMessage = exceptionMessage;
	}
	
	public String getFullMessage() {
		return ExceptionUtils.getFullMessage(this);
	}
	
	public ExceptionMessage getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
}
