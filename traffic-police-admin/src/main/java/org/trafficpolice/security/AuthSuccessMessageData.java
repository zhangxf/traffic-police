package org.trafficpolice.security;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2017年11月27日 下午3:04:50
 * 
 */
public class AuthSuccessMessageData {

	private String authKey;
	
	private String username;

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
