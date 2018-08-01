package org.trafficpolice.dto;

import org.trafficpolice.po.RoleAuthority;

/**
 * @author zhangxiaofei
 * @createdOn 2018年8月1日 下午2:35:44
 */
public class RoleAuthorityDTO extends RoleAuthority {

	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * 权限名称
	 */
	private String authorityName;
	
	/**
	 * 地址
	 */
	private String action;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
