package org.trafficpolice.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.po.RoleAuthority;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月12日 下午8:57:46
 */
@Repository(RoleAuthorityDao.BEAN_ID)
public interface RoleAuthorityDao {

	public static final String BEAN_ID = "roleAuthorityDao";
	
	public Integer doInsert(RoleAuthority po);
	
	public void doDelete(@Param("id") Long id);
	
	public List<RoleAuthority> findAll();
	
	public List<RoleAuthority> findByRoleId(@Param("roleId")Long roleId);
	
	public List<Long> findAuthorityIdsByRoleId(@Param("roleId")Long roleId);
	
	public Integer deleteByAuthorityId(@Param("authorityId")Long authorityId);
	
	public Integer deleteByRoleId(@Param("roleId")Long roleId);
	
	public List<Long> filterAuthorityIdsByRoleIdsAndAuthorityIds(@Param("roleIds") Collection<Long> roleIds, @Param("authorityIds")Collection<Long> authorityIds);
	
}
