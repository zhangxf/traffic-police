package org.trafficpolice.po;

import java.util.Date;

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
	 * 所属菜单
	 */
	private Long menuId;
	
	/**
	 * 在页面上的位置唯一标识。用于前端处理显示不显示该按钮
	 */
	private String idOnPage;
	
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

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getIdOnPage() {
		return idOnPage;
	}

	public void setIdOnPage(String idOnPage) {
		this.idOnPage = idOnPage;
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
