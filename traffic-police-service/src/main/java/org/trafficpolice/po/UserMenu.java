package org.trafficpolice.po;

import java.util.Date;

/**
 * 用户所拥有的特殊菜单
 * @author zhangxiaofei
 * 2018年7月14日下午12:07:06
 */
public class UserMenu {

	private Long id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
