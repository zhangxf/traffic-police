package org.trafficpolice.support;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
import org.trafficpolice.commons.exception.BaseException;
import org.trafficpolice.commons.exception.ExceptionMessage;
import org.trafficpolice.commons.exception.ExceptionUtils;
import org.trafficpolice.commons.exception.SystemException;
import org.trafficpolice.commons.json.JsonResult;

/**
 * 处理rest请求返回json的异常
 * @author zhangxiaofei
 * @createdOn 2018年5月29日 下午8:07:35
 */
@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler implements MessageSourceAware {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);
	
	private MessageSource messageSource;
	
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<Object> handleBaseException(BaseException ex, WebRequest request) {
		if (ex instanceof SystemException) {
			logger.error("####【system exception】####", ex);
		}
		JsonResult result = ExceptionUtils.translate2JsonResult(ex.getExceptionMessage(), messageSource, Locale.CHINA);
		return new ResponseEntity<Object>(result, new HttpHeaders(), HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUnexpectedException(Exception ex, WebRequest request) {
		logger.error("####【系统异常】####", ex);
		ExceptionMessage exceptionMessage = new ExceptionMessage(GlobalStatusEnum.FAILURE.getStatus(), GlobalStatusEnum.FAILURE.getKey());
		JsonResult result = ExceptionUtils.translate2JsonResult(exceptionMessage, messageSource, Locale.CHINA);
		return new ResponseEntity<Object>(result, new HttpHeaders(), HttpStatus.OK);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("####【internal Spring MVC exceptions】####", ex);
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
}
