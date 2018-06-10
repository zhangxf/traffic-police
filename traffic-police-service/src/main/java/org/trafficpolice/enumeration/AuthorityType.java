package org.trafficpolice.enumeration;

/**
 * 权限类型
 * @author zhangxiaofei
 * 2018年6月10日下午11:50:08
 */
public enum AuthorityType {

	MENU("menu", "菜单"),
	
	BUTTON("button", "按钮"),
	
	API("api", "接口"),
	
	;
	
	private String type;
	
	private String description;
	
	private AuthorityType(String type, String description) {
		this.type = type;
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}