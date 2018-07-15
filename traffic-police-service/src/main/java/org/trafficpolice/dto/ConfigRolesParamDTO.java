package org.trafficpolice.dto;

import java.util.Set;

/**
 * 配置角色参数对象
 * @author zhangxiaofei
 * 2018年7月15日下午11:54:04
 */
public class ConfigRolesParamDTO {

	private Long userId;
	
	private Set<Long> roleIds;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Set<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Set<Long> roleIds) {
		this.roleIds = roleIds;
	}
	
}
