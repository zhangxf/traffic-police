package org.trafficpolice.dto;

/**
 * @author zhangxiaofei
 * 2018年7月15日下午3:47:22
 */
public class RoleQueryParamDTO extends PageQueryParamDTO {

	/**
	 * 角色名称
	 */
	private String name;
	
	/**
	 * 角色代码 例如：ADMIN, SUPERADMIN, USER
	 */
	private String code;

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
	
}
