package org.trafficpolice.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.consts.ServiceConsts;
import org.trafficpolice.po.User;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月9日 下午6:27:16
 */
@RestController
@RequestMapping("/user")
public class UserController {

	/**
	 * 获取登录用户信息
	 * @param user
	 * @return
	 */
	@PostMapping("/info")
	public User userInfo(@AuthenticationPrincipal(expression = "currentUser") User user) {
		String headImgUrl = user.getHeadUrl();
		if (StringUtils.isNoneBlank(headImgUrl) && !headImgUrl.startsWith("http")) {
			user.setHeadUrl(ServiceConsts.NFS_ADDRESS + headImgUrl);
		}
		String idCardImgUrl = user.getIdCardImgUrl();
		if (StringUtils.isNoneBlank(idCardImgUrl) && !idCardImgUrl.startsWith("http")) {
			user.setIdCardImgUrl(ServiceConsts.NFS_ADDRESS + idCardImgUrl);
		}
		return user;
	}
	
}
