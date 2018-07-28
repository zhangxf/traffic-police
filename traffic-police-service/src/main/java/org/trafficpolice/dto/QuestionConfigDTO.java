package org.trafficpolice.dto;

import java.util.List;

import org.trafficpolice.po.QuestionConfig;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月23日 下午3:10:53
 */
public class QuestionConfigDTO extends QuestionConfig {

	private static final long serialVersionUID = 5935869173171441629L;
	
	private List<QuestionConfigDetailDTO> detail;

	public List<QuestionConfigDetailDTO> getDetail() {
		return detail;
	}

	public void setDetail(List<QuestionConfigDetailDTO> detail) {
		this.detail = detail;
	}
	
}
