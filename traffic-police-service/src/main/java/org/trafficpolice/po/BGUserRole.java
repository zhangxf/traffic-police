package org.trafficpolice.po;

/**
 * 用户角色
 * @author zhangxiaofei
 * 2018年6月11日上午12:16:39
 */
public class BGUserRole {

	private Long id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 角色id
	 */
	private Long roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
}
