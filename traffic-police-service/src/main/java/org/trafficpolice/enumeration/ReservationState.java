package org.trafficpolice.enumeration;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月25日 下午6:52:31
 */
public enum ReservationState {

	RESERVED("reserved", "已预约"),
	
	CANCEL("cancel", "已取消"),
	
	;
	
	private String type;
	
	private String description;
	
	private ReservationState(String type, String description) {
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
