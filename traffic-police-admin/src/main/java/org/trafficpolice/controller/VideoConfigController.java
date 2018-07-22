package org.trafficpolice.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.dto.VideoConfigDTO;
import org.trafficpolice.service.VideoConfigService;

/**
 * @author zhangxiaofei
 * 2018年7月23日上午2:23:25
 */
@RestController
@RequestMapping("/video/config")
public class VideoConfigController {

	@Autowired
	@Qualifier(VideoConfigService.BEAN_ID)
	private VideoConfigService videoConfigService;
	
	@GetMapping("/{edutype}")
	public List<VideoConfigDTO> findVideoConfig(@PathVariable("edutype") String edutype) {
		if ("checkedu".equals(edutype)) {//审验教育
			return videoConfigService.findVideoConfig();
		} else if ("fullscore".equals(edutype)) {//满分教育
			return Collections.emptyList();
		}
		return Collections.emptyList();
	}
	
	@PostMapping("/setting")
	public NULL settingVideoConfig(@PathVariable("edutype") String edutype, @RequestBody List<VideoConfigDTO> videoConfigList) {
		videoConfigService.settingVideoConfig(videoConfigList);
		return NULL.newInstance();
	}
	
}
