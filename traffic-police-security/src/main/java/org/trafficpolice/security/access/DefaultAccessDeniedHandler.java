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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.trafficpolice.commons.json.JsonResult;
import org.trafficpolice.security.enumeration.SpringSecurityExceptionEnum;

import com.alibaba.fastjson.JSON;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月3日 下午1:34:41
 * 已登录，无权限
 */
public class DefaultAccessDeniedHandler implements AccessDeniedHandler, MessageSourceAware {

	private static final Logger logger = LoggerFactory.getLogger(DefaultAccessDeniedHandler.class);
	
	protected MessageSource messageSource;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		JsonResult result = process(request, response, accessDeniedException);
		String msg = JSON.toJSONString(result);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(msg);
	}

	protected JsonResult process(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
		String message = messageSource.getMessage(SpringSecurityExceptionEnum.AUTHORITY_INSUFFICIENT.getKey(), null, "权限不足，拒绝访问！", Locale.CHINA);
		return new JsonResult(SpringSecurityExceptionEnum.AUTHORITY_INSUFFICIENT.getStatus(), message, null);
	}
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
}
