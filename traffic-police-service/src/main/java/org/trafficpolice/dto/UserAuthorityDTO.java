package org.trafficpolice.dto;

import org.trafficpolice.po.UserAuthority;

/**
 * @author zhangxiaofei
 * @createdOn 2018年8月1日 下午3:22:02
 */
public class UserAuthorityDTO extends UserAuthority {

	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 权限名称
	 */
	private String authorityName;
	
	/**
	 * 地址
	 */
	private String action;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
