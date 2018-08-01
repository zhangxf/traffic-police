package org.trafficpolice.dto;

/**
 * 权限查询参数
 * @author zhangxiaofei
 * 2018年7月15日下午12:19:13
 */
public class AuthorityQueryParamDTO extends PageQueryParamDTO {

	/**
	 * 权限名称
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
