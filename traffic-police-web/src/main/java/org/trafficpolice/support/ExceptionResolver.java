package org.trafficpolice.support;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
import org.trafficpolice.commons.exception.BaseException;
import org.trafficpolice.commons.exception.ExceptionMessage;
import org.trafficpolice.commons.exception.ExceptionUtils;
import org.trafficpolice.commons.exception.SystemException;
import org.trafficpolice.commons.json.JsonResult;
import org.trafficpolice.commons.web.ResponseUtils;

import com.alibaba.fastjson.JSON;

/**
 * 统一异常处理，返回contentType:text/html
 * @author Zhang Xiaofei
 * @createdOn 2017年7月12日下午3:25:54
 * 
 */
@Component
public class ExceptionResolver extends AbstractHandlerExceptionResolver implements MessageSourceAware {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);
	
	private MessageSource messageSource;
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ExceptionMessage exceptionMessage = null;
		if (ex instanceof BaseException) {
			if (ex instanceof SystemException) {
				logger.error("####【system exception】####", ex);
			}
			BaseException baseExcption = (BaseException)ex;
			exceptionMessage = baseExcption.getExceptionMessage();
		} else {
			logger.error("####【系统异常】####", ex);
			exceptionMessage = new ExceptionMessage(GlobalStatusEnum.FAILURE.getStatus(), GlobalStatusEnum.FAILURE.getKey());
		}
		JsonResult result = ExceptionUtils.translate2JsonResult(exceptionMessage, messageSource, Locale.CHINA);
		ResponseUtils.writeHtml(response, JSON.toJSONString(result));
		return new ModelAndView();
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
