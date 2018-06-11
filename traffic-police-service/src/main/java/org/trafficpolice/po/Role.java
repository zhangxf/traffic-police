package org.trafficpolice.po;

import java.util.Date;

/**
 * 角色
 * @author zhangxiaofei
 * 2018年6月10日下午7:36:08
 */
public class Role {

	private Long id;
	
	/**
	 * 角色名称
	 */
	private String name;
	
	/**
	 * 角色代码 例如：ADMIN, SUPERADMIN, USER
	 */
	private String code;
	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
