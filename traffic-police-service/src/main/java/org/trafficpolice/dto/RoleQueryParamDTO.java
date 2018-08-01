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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
