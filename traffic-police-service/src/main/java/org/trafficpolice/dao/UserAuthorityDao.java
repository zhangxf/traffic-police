package org.trafficpolice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.po.UserAuthority;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月12日 下午8:57:46
 */
@Repository(UserAuthorityDao.BEAN_ID)
public interface UserAuthorityDao {

	public static final String BEAN_ID = "userAuthorityDao";
	
	public Integer doInsert(UserAuthority po);
	
	public void doDelete(@Param("id") Long id);
	
	public List<UserAuthority> findAll();
	
	public List<UserAuthority> findByUserId(@Param("userId") Long userId);
	
}
