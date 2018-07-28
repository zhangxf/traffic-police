package org.trafficpolice.dto;

import java.util.List;

import org.trafficpolice.po.Category;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月25日 下午4:46:55
 */
public class CategoryDTO extends Category {

	private List<VideoDTO> videos;

	public List<VideoDTO> getVideos() {
		return videos;
	}

	public void setVideos(List<VideoDTO> videos) {
		this.videos = videos;
	}

}
