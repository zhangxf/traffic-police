package org.trafficpolice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
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
	
	public Integer doUpdateByPhone(User user);
	
	public List<User> findUsers(@Param("idNo")String idNo, @Param("licenseNo")String licenseNo, @Param("phone")String phone);
	
	public User findByPhone(@Param("phone")String phone);
	
	public User findByIdNo(@Param("idNo")String idNo);
	
	public User findByLicenseNo(@Param("licenseNo")String licenseNo);
	
	public User findByIdNoAndLicenseNo(@Param("idNo")String idNo, @Param("licenseNo")String licenseNo);
	
}
