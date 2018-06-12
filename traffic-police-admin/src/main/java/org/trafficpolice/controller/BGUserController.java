package org.trafficpolice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.po.BGUser;
import org.trafficpolice.service.BGUserService;

import com.github.pagehelper.PageInfo;

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
	
	@PostMapping("/update")
	public NULL updateUser(@RequestBody BGUser bgUser) {
		if (bgUser == null || bgUser.getId() == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "id");
		}
		bgUserService.updateBGUser(bgUser);
		return NULL.newInstance();
	}
	
	@GetMapping("/page")
	public PageInfo<BGUser> queryBGUserPage(int pageNum, int pageSize) {
		return bgUserService.queryBGUserPage(pageNum, pageSize);
	}
	
}
