package org.trafficpolice.commons.exception;

/**
 * @author zhangxiaofei
 * @version 1.0
 * @createdOn 2014年9月28日 下午2:47:07
 * 系统异常基类
 */
public class SystemException extends BaseException {

	private static final long serialVersionUID = -3688269417373853387L;

	public SystemException() {
		super();
	}

	public SystemException(Throwable cause) {
		super(cause);
	}
	
	public SystemException(String message) {
		super(message);
	}
	
	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(ExceptionMessage exceptionMessage, Throwable cause) {
		super(exceptionMessage, cause);
	}

	public SystemException(ExceptionMessage exceptionMessage) {
		super(exceptionMessage);
	}
	
	public SystemException(IException iexception, Object... args) {
		super(new ExceptionMessage(iexception.getStatus(), iexception.getKey(), args));
	}
	
}
