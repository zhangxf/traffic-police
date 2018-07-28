package org.trafficpolice.dto;

import org.trafficpolice.po.Question;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午2:21:44
 */
public class QuestionDTO extends Question {

	private static final long serialVersionUID = -9148443424541220733L;

	/**
	 * 分类名称
	 */
	private String categoryName;
	
	/**
	 * 试题图片token
	 */
	private String imgUrlToken;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getImgUrlToken() {
		return imgUrlToken;
	}

	public void setImgUrlToken(String imgUrlToken) {
		this.imgUrlToken = imgUrlToken;
	}
	
}
