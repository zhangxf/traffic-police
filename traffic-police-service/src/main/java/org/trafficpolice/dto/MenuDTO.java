package org.trafficpolice.dto;

import java.util.List;

import org.trafficpolice.po.Menu;
import org.trafficpolice.po.Role;

/**
 * @author zhangxiaofei
 * 2018年7月14日下午4:06:34
 */
public class MenuDTO extends Menu {

	private List<Long> parentIds;
	
	private List<Long> roleIds;
	
	private List<Role> roles;
	
	private List<MenuDTO> children;

	public List<Long> getParentIds() {
		return parentIds;
	}

	public void setParentIds(List<Long> parentIds) {
		this.parentIds = parentIds;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<MenuDTO> getChildren() {
		return children;
	}

	public void setChildren(List<MenuDTO> children) {
		this.children = children;
	}
	
}
