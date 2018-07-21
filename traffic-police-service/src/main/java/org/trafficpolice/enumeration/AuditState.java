package org.trafficpolice.enumeration;

/**
 * 审核状态
 * @author zhangxiaofei
 * 2018年7月1日上午2:24:20
 */
public enum AuditState {
	
	INHAND("inhand", "待审核"),
	
	REJECT("reject", "驳回"),
	
	PASSED("passed", "通过"),
	
	REINHAND("reinhand", "重新提交"),
	
	;
	
	private String type;
	
	private String description;
	
	private AuditState(String type, String description) {
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
