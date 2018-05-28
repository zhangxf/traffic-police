package org.trafficpolice.security.authentication;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.trafficpolice.commons.exception.ExceptionMessage;
import org.trafficpolice.commons.json.JsonResult;
import org.trafficpolice.security.exception.SpringSecurityException;

import com.alibaba.fastjson.JSON;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月4日 下午3:07:26
 */
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler, MessageSourceAware {

	private static final Logger logger = LoggerFactory.getLogger(DefaultAuthenticationFailureHandler.class);
	
	protected MessageSource messageSource;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		JsonResult result = handle(request, response, exception);
		String msg = JSON.toJSONString(result);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(msg);
	}

	protected JsonResult handle(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
		if (exception instanceof SpringSecurityException) {
			SpringSecurityException sse = (SpringSecurityException)exception;
			ExceptionMessage em = sse.getExceptionMessage();
			String key = em.getKey();
			String status = em.getStatus();
			Object[] args = em.getArgs();
			String message = messageSource.getMessage(key, args, Locale.CHINA);
			return new JsonResult(status, message, em.getData());
		} else {
			logger.error("####【登录失败，异常】####", exception);
			return new JsonResult("500", "系统错误，请联系管理员！", null);
		}
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
}
