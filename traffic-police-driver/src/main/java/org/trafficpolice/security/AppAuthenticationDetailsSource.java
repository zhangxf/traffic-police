package org.trafficpolice.security;

import javax.servlet.http.HttpServletRequest;

import org.trafficpolice.security.authentication.DefaultAuthenticationDetails;
import org.trafficpolice.security.authentication.DefaultAuthenticationDetailsSource;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月3日 下午7:26:44
 */
public class AppAuthenticationDetailsSource extends DefaultAuthenticationDetailsSource {

	/**
	 * 验证码token
	 */
	public static final String VERIFY_COD_TOKEN_PARAMNAME = "verifyCodeToken";
	
	@Override
	public DefaultAuthenticationDetails buildDetails(HttpServletRequest context) {
		AppAuthenticationDetails details = new AppAuthenticationDetails(context);
		details.setVerifyCodeToken(context.getParameter(VERIFY_COD_TOKEN_PARAMNAME));
		return details;
	}

}
