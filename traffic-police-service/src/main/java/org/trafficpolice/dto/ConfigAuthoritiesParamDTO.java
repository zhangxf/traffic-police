package org.trafficpolice.dto;

import java.util.Set;

/**
 * 配置权限请求参数
 * @author zhangxiaofei
 * 2018年7月15日下午7:48:14
 */
public class ConfigAuthoritiesParamDTO {

	private Long roleId;
	
	private Long userId;
	
	private Set<Long> authorityIds;

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

	public Set<Long> getAuthorityIds() {
		return authorityIds;
	}

	public void setAuthorityIds(Set<Long> authorityIds) {
		this.authorityIds = authorityIds;
	}
	
}
