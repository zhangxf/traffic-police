package org.trafficpolice.dto;

import org.trafficpolice.po.GrabRecord;

/**
 * @author zhangxiaofei
 * 2018年8月12日下午4:10:05
 */
public class GrabRecordDTO extends GrabRecord {

	private String base64Photo;

	public String getBase64Photo() {
		return base64Photo;
	}

	public void setBase64Photo(String base64Photo) {
		this.base64Photo = base64Photo;
	}
	
}
