package org.trafficpolice.dto;

import java.util.List;

import org.trafficpolice.po.Category;
import org.trafficpolice.po.Video;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月25日 下午4:46:55
 */
public class CategoryDTO extends Category {

	private List<Video> videos;

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	
}
