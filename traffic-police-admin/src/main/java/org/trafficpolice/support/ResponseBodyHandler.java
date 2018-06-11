package org.trafficpolice.support;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.trafficpolice.commons.json.JsonResult;
import org.trafficpolice.commons.json.JsonResultWrapper;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * @author Zhang Xiaofei
 * @createdOn 2017年7月12日下午4:23:19
 * 
 */
@ControllerAdvice
public class ResponseBodyHandler implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		return converterType == FastJsonHttpMessageConverter.class;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		if (body == null || body instanceof JsonResult) {
			return body;
		}
		return JsonResultWrapper.wrapSuccess(body);
	}

}
