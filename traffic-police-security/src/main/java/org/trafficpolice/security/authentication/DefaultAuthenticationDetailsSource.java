package org.trafficpolice.security.authentication;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月3日 下午3:09:39
 */
public class DefaultAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, DefaultAuthenticationDetails> {

	@Override
	public DefaultAuthenticationDetails buildDetails(HttpServletRequest context) {
		return new DefaultAuthenticationDetails(context);
	}

}
