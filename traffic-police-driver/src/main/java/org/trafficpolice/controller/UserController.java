package org.trafficpolice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.consts.ServiceConsts;
import org.trafficpolice.dto.AuditQueryParamDTO;
import org.trafficpolice.dto.AuditQueryResultDTO;
import org.trafficpolice.dto.UserDTO;
import org.trafficpolice.po.User;
import org.trafficpolice.service.UserService;
import org.trafficpolice.service.VerifyCodeService;

/**
 * @author zhangxiaofei
 * 2018年7月1日下午12:26:49
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	@Qualifier(VerifyCodeService.BEAN_ID)
	private VerifyCodeService verifyCodeService;
	
	@Autowired
	@Qualifier(UserService.BEAN_ID)
	private UserService userService;
	
	/**
	 * 用户注册
	 * @param userDTO
	 * @return
	 */
	@PostMapping("/register")
	public NULL register(@RequestBody UserDTO userDTO) {
		userService.register(userDTO, false);
		return NULL.newInstance();
	}
	
	/**
	 * 用户注册信息修改
	 * @param userDTO
	 * @return
	 */
	@PostMapping("/register/update")
	public NULL registerUpdate(@RequestBody UserDTO userDTO) {
		userService.registerUpdate(userDTO);
		return NULL.newInstance();
	}
	
	/**
	 * 审核状态查询
	 * @param userDTO
	 * @return
	 */
	@PostMapping("/audit/query")
	public AuditQueryResultDTO auditQuery(@RequestBody AuditQueryParamDTO auditQueryParamDTO) {
		AuditQueryResultDTO result = userService.auditQuery(auditQueryParamDTO);
		User user = result.getUser();
		user.setHeadUrl(ServiceConsts.NFS_ADDRESS + user.getHeadUrl());
		user.setIdCardImgUrl(ServiceConsts.NFS_ADDRESS + user.getIdCardImgUrl());
		return result; 
	}
	
	/**
	 * 获取登录用户信息
	 * @param user
	 * @return
	 */
	@PostMapping("/info")
	public User userInfo(@AuthenticationPrincipal(expression = "currentUser") User user) {
		user.setHeadUrl(ServiceConsts.NFS_ADDRESS + user.getHeadUrl());
		user.setIdCardImgUrl(ServiceConsts.NFS_ADDRESS + user.getIdCardImgUrl());
		return user;
	}
	
}
