package org.trafficpolice.dto;

/**
 * @author zhangxiaofei
 * 2018年7月15日下午12:21:57
 */
public class PageQueryParamDTO {

	private int pageNum;
	
	private int pageSize;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
