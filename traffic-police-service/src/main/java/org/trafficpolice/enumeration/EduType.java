package org.trafficpolice.enumeration;

/**
 * 教育类型
 * @author zhangxiaofei
 * @createdOn 2018年7月23日 上午11:14:18
 */
public enum EduType {

	CHECK("check", "审验教育"),
	
	FULL("full", "满分教育"),
	
	;
	
	private String type;
	
	private String description;
	
	private EduType(String type, String description) {
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
