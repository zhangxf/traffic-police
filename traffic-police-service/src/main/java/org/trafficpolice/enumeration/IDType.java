package org.trafficpolice.enumeration;

/**
 * @author zhangxiaofei
 * 2018年7月1日上午1:49:04
 */
public enum IDType {

	IDCARD("idcard", "居民身份证"),
	
	OFFICER("officer", "军官证"),
	
	SOLDIER("soldier", "士兵证"),
	
	RETIRED("retired", "军官离退休证"),
	
	OUTSIDE("outside", "境外人员身份证"),
	
	DIPLOMATIC("diplomatic", "外交人员身份证明"),
	
	;
	
	private String type;
	
	private String description;
	
	private IDType(String type, String description) {
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
