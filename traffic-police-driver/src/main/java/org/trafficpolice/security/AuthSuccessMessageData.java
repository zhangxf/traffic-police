package org.trafficpolice.security;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2017年11月27日 下午3:04:50
 * 
 */
public class AuthSuccessMessageData {

	private String authKey;
	
	private String phone;

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
