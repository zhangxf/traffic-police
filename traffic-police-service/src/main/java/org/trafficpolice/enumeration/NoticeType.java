package org.trafficpolice.enumeration;

/**
 * 公告类型
 * @author zhangxiaofei
 * 2018年7月29日下午3:03:21
 */
public enum NoticeType {

	GLOBAL("global", "学习后台全局公告"),
	
	CHECKLEARN("checklearn", "审验教育学习公告"),
	
	FULLLEARN("fulllearn", "满分教育学习公告"),
	
	CHECKVIDEO("checkvideo", "审验教育视频公告"),
	
	FULLVIDEO("fullvideo", "满分教育视频公告"),
	
	CHECKQUESTION("checkquestion", "审验教育考试公告"),
	
	FULLQUESTION("fullquestion", "满分教育考试公告"),
	
	;
	
	private String type;
	
	private String description;
	
	private NoticeType(String type, String description) {
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
