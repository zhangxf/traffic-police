package org.trafficpolice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.dto.AuditQueryParamDTO;
import org.trafficpolice.dto.AuditQueryResultDTO;
import org.trafficpolice.dto.UserDTO;
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
		userService.register(userDTO);
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
		return userService.auditQuery(auditQueryParamDTO);
	}
	
}
