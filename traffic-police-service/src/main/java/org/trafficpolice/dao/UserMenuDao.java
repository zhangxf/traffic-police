package org.trafficpolice.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.po.UserMenu;

/**
 * @author zhangxiaofei
 * 2018年7月15日下午1:20:50
 */
@Repository(UserMenuDao.BEAN_ID)
public interface UserMenuDao {

	public static final String BEAN_ID = "userMenuDao";
	
	public Integer doInsert(UserMenu po);
	
	public Integer doDelete(@Param("id") Long id);
	
	public Integer deleteByMenuId(@Param("menuId") Long menuId);
	
	public Integer deleteByUserId(@Param("userId") Long userId);
	
	public List<UserMenu> findAll();
	
	public List<UserMenu> findByMenuIds(Collection<Long> menuIds);
	
	public List<UserMenu> findByMenuId(@Param("menuId") Long menuId);
	
	public List<UserMenu> findByUserId(@Param("userId") Long userId);
	
	public List<Long> findMenuIdsByUserId(@Param("userId") Long userId);
	
}
