package org.trafficpolice.security;

import javax.servlet.http.HttpServletRequest;

import org.trafficpolice.security.authentication.DefaultAuthenticationDetails;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月3日 下午7:23:30
 */
public class AppAuthenticationDetails extends DefaultAuthenticationDetails {

	private static final long serialVersionUID = -5685827699632718555L;
	
	/**
	 * 验证码token
	 */
	private String verifyCodeToken;
	
	public AppAuthenticationDetails(HttpServletRequest request) {
		super(request);
	}

	public String getVerifyCodeToken() {
		return verifyCodeToken;
	}

	public void setVerifyCodeToken(String verifyCodeToken) {
		this.verifyCodeToken = verifyCodeToken;
	}

}
