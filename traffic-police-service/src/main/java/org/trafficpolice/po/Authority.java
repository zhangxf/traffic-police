package org.trafficpolice.po;

import java.util.Date;

import org.trafficpolice.enumeration.AuthorityType;

/**
 * 权限
 * @author zhangxiaofei
 * 2018年6月10日下午7:40:19
 */
public class Authority {

	private Long id;
	
	/**
	 * 权限名称
	 */
	private String name;
	
	/**
	 * 地址
	 */
	private String action;

	/**
	 * 是否叶子节点 Y:是 N:否
	 */
	private Boolean isLeaf;
	
	/**
	 * 父节点id
	 */
	private Long parentId;
	
	/**
	 * 权限类型
	 */
	private AuthorityType authorityType; 
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public AuthorityType getAuthorityType() {
		return authorityType;
	}

	public void setAuthorityType(AuthorityType authorityType) {
		this.authorityType = authorityType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
