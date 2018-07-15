package org.trafficpolice.po;

import java.util.Date;

/**
 * 角色所拥有的菜单
 * @author zhangxiaofei
 * 2018年7月14日上午9:11:49
 */
public class RoleMenu {

	private Long id;
	
	/**
	 * 角色id
	 */
	private Long roleId;
	
	/**
	 * 菜单id
	 */
	private Long menuId;
	
	/**
	 * 授权时间
	 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
