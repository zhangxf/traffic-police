package org.trafficpolice.dto;

import org.trafficpolice.po.Question;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午2:21:44
 */
public class QuestionDTO extends Question {

	/**
	 * 试题图片token
	 */
	private String imgUrlToken;

	public String getImgUrlToken() {
		return imgUrlToken;
	}

	public void setImgUrlToken(String imgUrlToken) {
		this.imgUrlToken = imgUrlToken;
	}
	
}
