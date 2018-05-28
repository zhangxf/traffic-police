package org.trafficpolice.commons.exception;

/**
 * @author zhangxiaofei
 * @version 1.0
 * @createdOn 2014年9月28日 下午2:45:25
 * 业务异常基类
 */
public class BizException extends BaseException {

	private static final long serialVersionUID = 2131050134272106109L;

	public BizException() {
		super();
	}

	public BizException(Throwable cause) {
		super(cause);
	}
	
	public BizException(String message) {
		super(message);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BizException(ExceptionMessage exceptionMessage, Throwable cause) {
		super(exceptionMessage, cause);
	}

	public BizException(ExceptionMessage exceptionMessage) {
		super(exceptionMessage);
	}
	
	public BizException(IException iexception, Object... args) {
		super(new ExceptionMessage(iexception.getStatus(), iexception.getKey(), args));
	}
	
}
