package org.trafficpolice.dto;

import java.util.Set;

/**
 * 菜单配置参数对象
 * @author zhangxiaofei
 * 2018年7月15日下午8:22:37
 */
public class ConfigMenuParamDTO {

	private Long roleId;
	
	private Long userId;
	
	private Set<Long> menuIds;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Set<Long> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(Set<Long> menuIds) {
		this.menuIds = menuIds;
	}
	
}
