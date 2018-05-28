package org.trafficpolice.security.authentication;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.trafficpolice.commons.json.JsonResult;
import org.trafficpolice.commons.json.JsonResultWrapper;

import com.alibaba.fastjson.JSON;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月4日 下午2:20:03
 */
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String authKey = UUID.randomUUID().toString();
		request.setAttribute(RedisSecurityContextRepository.SESSION_KEY, authKey);
		JsonResult result = handle(request, response, authentication);
		String msg = JSON.toJSONString(result);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.getWriter().write(msg);
	}

	protected JsonResult handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		return JsonResultWrapper.wrapSuccess(null);
	}
	
}
