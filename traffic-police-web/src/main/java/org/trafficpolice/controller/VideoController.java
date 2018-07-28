package org.trafficpolice.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.dto.VideoDTO;
import org.trafficpolice.dto.VideoQueryParamDTO;
import org.trafficpolice.po.User;
import org.trafficpolice.po.VideoRecord;
import org.trafficpolice.service.VideoService;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月24日 下午7:54:27
 */
@RestController
@RequestMapping("/video")
public class VideoController {

	@Autowired
	@Qualifier(VideoService.BEAN_ID)
	private VideoService videoService;
	
	@PostMapping("/page")
	public PageInfo<VideoDTO> queryByPage(@RequestBody VideoQueryParamDTO queryDTO) {
		return videoService.findByPage(queryDTO);
	}
	
	@GetMapping("/find-by-id")
	public VideoDTO findById(@AuthenticationPrincipal(expression = "currentUser") User user, Long id) {
		String batchNum = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return videoService.findVideoAndViewRecord(user.getId(), batchNum, id);
	}
	
	@PostMapping("/record")
	public NULL record(@AuthenticationPrincipal(expression = "currentUser") User user, @RequestBody VideoRecord record) {
		record.setUserId(user.getId());
		String batchNum = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		record.setBatchNum(batchNum);
		videoService.saveOrUpdateVideoRecord(record);
		return NULL.newInstance();
	}
	
}
