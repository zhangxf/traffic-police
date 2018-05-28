package org.trafficpolice.commons.exception;

import org.apache.commons.lang3.StringUtils;

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
	
}
