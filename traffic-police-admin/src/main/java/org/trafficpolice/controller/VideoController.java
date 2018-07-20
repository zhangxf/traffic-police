package org.trafficpolice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.dto.VideoDTO;
import org.trafficpolice.dto.VideoQueryParamDTO;
import org.trafficpolice.po.Video;
import org.trafficpolice.service.VideoService;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午3:56:37
 */
@RestController
@RequestMapping("/video")
public class VideoController {

	@Autowired
	@Qualifier(VideoService.BEAN_ID)
	private VideoService videoService;
	
	@PostMapping("/add")
	public NULL addVideo(@RequestBody VideoDTO videoDTO) {
		videoService.addVideo(videoDTO);
		return NULL.newInstance();
	}
	
	@PostMapping("/delete")
	public NULL deleteById(Long id) {
		videoService.deleteById(id);
		return NULL.newInstance();
	}
	
	@PostMapping("/update")
	public NULL updateVideo(@RequestBody VideoDTO videoDTO) {
		videoService.updateVideo(videoDTO);
		return NULL.newInstance();
	}
	
	@PostMapping("/page")
	public PageInfo<Video> queryByPage(@RequestBody VideoQueryParamDTO queryDTO) {
		return videoService.findByPage(queryDTO);
	}
	
	@PostMapping("/find-by-id")
	public Video findById(Long id) {
		return videoService.findById(id);
	}
	
}
