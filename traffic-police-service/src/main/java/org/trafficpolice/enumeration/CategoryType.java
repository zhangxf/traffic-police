package org.trafficpolice.enumeration;

/**
 * @author zhangxiaofei
 * 2018年7月20日上午12:12:14
 */
public enum CategoryType {

	QUESTION("question", "试题分类"),
	
	VIDEO("video", "视频分类"),
	
	;
	
	private String type;
	
	private String description;
	
	private CategoryType(String type, String description) {
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
