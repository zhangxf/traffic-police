package org.trafficpolice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.po.BGUser;
import org.trafficpolice.service.BGUserService;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月11日 下午4:41:15
 */
@RestController
@RequestMapping("/bguser")
public class BGUserController {

	@Autowired
	@Qualifier(BGUserService.BEAN_ID)
	private BGUserService bgUserService;
	
	@PostMapping("/add")
	public BGUser addUser(@RequestBody BGUser bgUser) {
		bgUserService.saveBGUser(bgUser);
		return bgUser;
	}
	
}
