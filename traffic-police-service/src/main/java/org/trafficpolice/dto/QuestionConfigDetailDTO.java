package org.trafficpolice.dto;

import org.trafficpolice.enumeration.CategoryType;
import org.trafficpolice.po.QuestionConfigDetail;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月23日 下午2:53:57
 */
public class QuestionConfigDetailDTO extends QuestionConfigDetail {

	private static final long serialVersionUID = 2763802241375907271L;

	private String categoryName;
	
	private CategoryType categoryType;
	
	/**
	 * 某分类下的试题总数
	 */
	private Long questionNum;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public CategoryType getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(CategoryType categoryType) {
		this.categoryType = categoryType;
	}

	public Long getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Long questionNum) {
		this.questionNum = questionNum;
	}
	
}
