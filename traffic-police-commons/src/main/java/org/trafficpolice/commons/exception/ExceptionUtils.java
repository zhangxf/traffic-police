package org.trafficpolice.commons.exception;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.trafficpolice.commons.json.JsonResult;

/**
 * @author zhangxiaofei
 * @version 1.0
 * @createdOn 2014年9月28日 下午2:44:09
 */
public class ExceptionUtils {

	public static String getFullMessage(Throwable ex) {
		StringBuilder builder = new StringBuilder();
		if (StringUtils.isEmpty(ex.getMessage())) {
			builder.append(ex.toString());
		} else {
			builder.append(ex.getMessage());
		}
		if (ex.getCause() != null) {
			builder.append(" caused by: [ ").append(getFullMessage(ex.getCause())).append(" ]");
		}
		return builder.toString();
	}
	
	public static JsonResult translate2JsonResult(ExceptionMessage exceptionMessage, MessageSource messageSource, Locale locale) {
		String key = exceptionMessage.getKey();
		String status = exceptionMessage.getStatus();
		Object[] args = exceptionMessage.getArgs();
		Object data = exceptionMessage.getData();
		String message = messageSource.getMessage(key, args, locale);
		return new JsonResult(status, message, data);
	}
	
}
