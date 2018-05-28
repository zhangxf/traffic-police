package org.trafficpolice.security.authentication;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.trafficpolice.commons.web.IPUtils;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月3日 下午2:38:37
 */
public class DefaultAuthenticationDetails implements Serializable {

	private static final long serialVersionUID = 2575514857153336031L;
	
	private String ip;
	
	public DefaultAuthenticationDetails(HttpServletRequest request) {
		this.ip = IPUtils.getIpAddress(request);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
