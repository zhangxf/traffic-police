package org.trafficpolice.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.dto.VideoConfigDTO;
import org.trafficpolice.enumeration.EduType;
import org.trafficpolice.po.User;
import org.trafficpolice.service.VideoConfigService;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月25日 下午5:56:24
 */
@RestController
@RequestMapping("/video/config")
public class VideoConfigController {

	@Autowired
	@Qualifier(VideoConfigService.BEAN_ID)
	private VideoConfigService videoConfigService;
	
	/**
	 * 视频设置以及完成状态查询
	 * @param edutype
	 * @return
	 */
	@GetMapping("/{edutype}/state")
	public List<VideoConfigDTO> findVideoConfigAndCompleteState(@AuthenticationPrincipal(expression = "currentUser") User user, @PathVariable("edutype") String edutype) {
		Long userId = user.getId();
		if (EduType.CHECK.getType().equals(edutype)) {//审验教育
			String batchNum = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			return videoConfigService.findVideoConfigAndCompleteState(userId, batchNum);
		} else if (EduType.FULL.getType().equals(edutype)) {//满分教育
			return Collections.emptyList();
		}
		return Collections.emptyList();
	}
	
}
