package org.trafficpolice.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.po.Role;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月12日 下午5:13:37
 */
@Repository(RoleDao.BEAN_ID)
public interface RoleDao {

	public static final String BEAN_ID = "roleDao";
	
	public Integer doInsert(Role role);
	
	public Integer doUpdate(Role role);
	
	public Integer doDelete(@Param("id") Long id);
	
	public Role findById(@Param("id") Long id);
	
	public Role findByName(@Param("name") String name);
	
	public List<Role> findAll();
	
	public List<Role> findByIds(Collection<Long> ids);
	
	public List<Role> findByCondition(Role role);
	
}
