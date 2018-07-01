package org.trafficpolice.dao;

import org.springframework.stereotype.Repository;
import org.trafficpolice.po.User;

/**
 * @author zhangxiaofei
 * 2018年6月11日上午12:24:15
 */
@Repository(UserDao.BEAN_ID)
public interface UserDao {

	public static final String BEAN_ID = "userDao";
	
	public Integer doInsert(User user);
	
}
