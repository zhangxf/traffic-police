package org.trafficpolice.dto;

import org.trafficpolice.po.User;

/**
 * 审核状态查询结果数据传输对象
 * @author zhangxiaofei
 * 2018年7月8日上午11:18:40
 */
public class AuditQueryResultDTO {

	private User user;
	
	/**
	 * 审核状态查询返回的token
	 */
	private String token;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
