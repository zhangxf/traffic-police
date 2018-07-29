package org.trafficpolice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.enumeration.NoticeType;
import org.trafficpolice.po.Notice;
import org.trafficpolice.service.NoticeService;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月12日 下午6:20:51
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

	@Autowired
	@Qualifier(NoticeService.BEAN_ID)
	private NoticeService noticeService;
	
	@GetMapping("/find/{noticetype}")
	public Notice find(@PathVariable("noticetype")String noticetype) {
		return noticeService.findByType(NoticeType.valueOf(noticetype.toUpperCase()));
	}
	
}
