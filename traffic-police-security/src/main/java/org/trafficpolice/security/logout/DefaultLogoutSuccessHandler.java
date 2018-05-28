package org.trafficpolice.security.logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.trafficpolice.commons.json.JsonResult;
import org.trafficpolice.commons.json.JsonResultWrapper;

import com.alibaba.fastjson.JSON;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月4日 下午6:40:40
 */
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		JsonResult result = handle(request, response, authentication);
		String msg = JSON.toJSONString(result);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.getWriter().write(msg);
	}

	protected JsonResult handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		return JsonResultWrapper.wrapSuccess(null);
	}
	
}
