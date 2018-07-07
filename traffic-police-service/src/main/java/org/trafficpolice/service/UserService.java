package org.trafficpolice.service;

import org.trafficpolice.dto.UserDTO;
import org.trafficpolice.po.User;

/**
 * @author zhangxiaofei
 * 2018年7月1日上午11:47:57
 */
public interface UserService {

	public static final String BEAN_ID = "userService";
	
	public void register(UserDTO userDTO);
	
	public User findByPhone(String phone);
	
}
