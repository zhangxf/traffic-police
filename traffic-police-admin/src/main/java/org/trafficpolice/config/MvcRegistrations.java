package org.trafficpolice.config;

import org.springframework.boot.autoconfigure.web.WebMvcRegistrationsAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

/**
 * @author zhangxiaofei
 * @createdOn 2018年5月29日 下午5:30:00
 */
@Configuration
public class MvcRegistrations extends WebMvcRegistrationsAdapter {

	@Override
	public ExceptionHandlerExceptionResolver getExceptionHandlerExceptionResolver() {
		return super.getExceptionHandlerExceptionResolver();
	}

	
	
}
