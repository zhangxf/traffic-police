package org.trafficpolice.service;

import org.trafficpolice.dto.UserDTO;

/**
 * @author zhangxiaofei
 * 2018年7月1日上午11:47:57
 */
public interface UserService {

	public static final String BEAN_ID = "userService";
	
	public void addUser(UserDTO userDTO);
	
}
