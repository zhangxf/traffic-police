package org.trafficpolice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.po.BGUser;

/**
 * @author zhangxiaofei
 * 2018年6月11日上午12:24:15
 */
@Repository(BGUserDao.BEAN_ID)
public interface BGUserDao {

	public static final String BEAN_ID = "bgUserDao";
	
	public Integer doInsert(BGUser bgUser);
	
	public Integer doUpdate(BGUser bgUser);
	
	public BGUser queryByUsername(@Param("username") String username);
	
	public List<BGUser> queryBGUserPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
	
	public List<BGUser> queryAllBGUser();
	
	public List<BGUser> queryAllBGUserByCondition(BGUser user);
	
	public Integer doDelete(@Param("id") Long id);
	
}
