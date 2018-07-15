package org.trafficpolice.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.po.RoleMenu;

/**
 * @author zhangxiaofei
 * 2018年7月14日下午5:15:28
 */
@Repository(RoleMenuDao.BEAN_ID)
public interface RoleMenuDao {

	public static final String BEAN_ID = "roleMenuDao";
	
	public Integer doInsert(RoleMenu po);
	
	public Integer doDelete(@Param("id") Long id);
	
	public Integer deleteByMenuId(@Param("menuId") Long menuId);
	
	public Integer deleteByRoleId(@Param("roleId") Long roleId);
	
	public List<Long> findMenuIdsByRoleId(@Param("roleId") Long roleId);
	
	public List<RoleMenu> findAll();
	
	public List<RoleMenu> findByMenuIds(Collection<Long> menuIds);
	
	public List<RoleMenu> findByRoleIds(Collection<Long> roleIds);
	
	public List<Long> findDistinctMenuIdByRoleIds(Collection<Long> roleIds);
	
	public List<RoleMenu> findByMenuId(@Param("menuId") Long menuId);
	
	public List<RoleMenu> findByRoleId(@Param("roleId") Long roleId);
	
}
