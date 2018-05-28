package org.trafficpolice.security.access;

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
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.trafficpolice.commons.exception.ExceptionMessage;
import org.trafficpolice.commons.json.JsonResult;
import org.trafficpolice.security.enumeration.SpringSecurityExceptionEnum;
import org.trafficpolice.security.exception.SpringSecurityException;

import com.alibaba.fastjson.JSON;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月3日 下午1:51:43
 */
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint, MessageSourceAware {

	private static final Logger logger = LoggerFactory.getLogger(DefaultAuthenticationEntryPoint.class);
	
	protected MessageSource messageSource;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
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
		} 
		if (exception instanceof InsufficientAuthenticationException) {
			logger.warn("####【凭证（authKey）不可信，拒绝访问】####");
			String message = messageSource.getMessage(SpringSecurityExceptionEnum.CREDENCIALS_INSUFFICIENT.getKey(), null, "凭证不足，拒绝访问！", Locale.CHINA);
			return new JsonResult(SpringSecurityExceptionEnum.CREDENCIALS_INSUFFICIENT.getStatus(), message, null);
		}
		logger.error("####【security认证异常】####", exception);
		String message = messageSource.getMessage("system.error", null, "系统错误，请联系管理员！", Locale.CHINA);
		return new JsonResult("500", message, null);
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
