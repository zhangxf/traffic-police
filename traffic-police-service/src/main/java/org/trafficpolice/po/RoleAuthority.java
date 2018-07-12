package org.trafficpolice.po;

import java.util.Date;

/**
 * 角色权限
 * @author zhangxiaofei
 * 2018年6月11日上午12:12:56
 */
public class RoleAuthority {

	private Long id;
	
	/**
	 * 角色id
	 */
	private Long roleId;
	
	/**
	 * 权限id
	 */
	private Long authorityId;
	
	/**
	 * 逗号分割的所有父权限id
	 */
	private String parentAuthorityIds;

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

	public Long getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
	}

	public String getParentAuthorityIds() {
		return parentAuthorityIds;
	}

	public void setParentAuthorityIds(String parentAuthorityIds) {
		this.parentAuthorityIds = parentAuthorityIds;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
