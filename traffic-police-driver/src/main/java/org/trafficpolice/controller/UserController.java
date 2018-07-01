package org.trafficpolice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.dto.UserDTO;
import org.trafficpolice.service.UserService;

/**
 * @author zhangxiaofei
 * 2018年7月1日下午12:26:49
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	@Qualifier(UserService.BEAN_ID)
	private UserService userService;
	
	@PostMapping("/add")
	public NULL addUser(@RequestBody UserDTO userDTO) {
		userService.addUser(userDTO);
		return NULL.newInstance();
	}
	
}
