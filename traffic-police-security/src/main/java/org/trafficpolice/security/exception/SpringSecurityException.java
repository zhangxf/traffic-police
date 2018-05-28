package org.trafficpolice.security.exception;

import org.springframework.security.core.AuthenticationException;
import org.trafficpolice.commons.exception.ExceptionMessage;
import org.trafficpolice.commons.exception.IException;

/**
 * @author zhangxiaofei
 * @createdOn 2018年5月28日 下午8:14:42
 */
public class SpringSecurityException extends AuthenticationException {

	private static final long serialVersionUID = -4384699772263601283L;
	
	private ExceptionMessage exceptionMessage;
	
	public SpringSecurityException(String msg) {
		super(msg);
	}

	public SpringSecurityException(String msg, Throwable t) {
		super(msg, t);
	}

	public SpringSecurityException(ExceptionMessage exceptionMessage) {
		super("####【异常】【状态码】【" + exceptionMessage.getStatus() + "】");
		this.exceptionMessage = exceptionMessage;
	}
	
	public SpringSecurityException(IException iexception, Object... args) {
		this(new ExceptionMessage(iexception.getStatus(), iexception.getKey(), args));
	}
	
	public ExceptionMessage getExceptionMessage() {
		return exceptionMessage;
	}
	
}
