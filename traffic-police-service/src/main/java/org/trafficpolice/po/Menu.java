package org.trafficpolice.po;

import java.util.Date;

/**
 * 菜单
 * @author zhangxiaofei
 * 2018年7月14日上午8:44:33
 */
public class Menu {

	private Long id;
	
	/**
	 * 名称
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
	 * 排序
	 */
	private long sortedOrder;
	
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

	public long getSortedOrder() {
		return sortedOrder;
	}

	public void setSortedOrder(long sortedOrder) {
		this.sortedOrder = sortedOrder;
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
