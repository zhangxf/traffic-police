package org.trafficpolice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.dto.UserDTO;
import org.trafficpolice.dto.UserQueryParamDTO;
import org.trafficpolice.po.User;
import org.trafficpolice.service.UserService;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午4:58:31
 */
@RestController
@RequestMapping("/driver")
public class DriverController {

	@Autowired
	@Qualifier(UserService.BEAN_ID)
	private UserService userService;
	
	@PostMapping("/add")
	public NULL addUser(@RequestBody UserDTO userDTO) {
		userService.register(userDTO, true);
		return NULL.newInstance();
	}
	
	@GetMapping("/find-by-id")
	public User findById(@RequestParam("id") Long id) {
		return userService.findById(id);
	}
	
	@PostMapping("/update")
	public NULL updateUser(@RequestBody UserDTO userDTO) {
		userService.updateUser(userDTO);
		return NULL.newInstance();
	}
	
	@PostMapping("/page")
	public PageInfo<User> queryByPage(@RequestBody UserQueryParamDTO queryDTO) {
		return userService.queryByPage(queryDTO);
	}
	
	@PostMapping("/audit")
	public NULL audit(@RequestBody User user) {
		userService.audit(user);
		return NULL.newInstance();
	}
	
	@GetMapping("/ops/{state}")
	public NULL ops(@RequestParam("id")Long id, @PathVariable("state") String state) {
		if ("black".equals(state)) {
			userService.updateDisabled(id, true);
		} else if ("white".equals(state)) {
			userService.updateDisabled(id, false);
		}
		return NULL.newInstance();
	}
	
}
