package org.trafficpolice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.po.BGUserRole;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月12日 下午6:26:55
 */
@Repository(BGUserRoleDao.BEAN_ID)
public interface BGUserRoleDao {

	public static final String BEAN_ID = "bgUserRoleDao";
	
	public Integer doInsert(BGUserRole bgUserRole);
	
	public Integer doDelete(@Param("id") Long id);
	
	public List<BGUserRole> findByUserId(@Param("userId") Long userId);
	
	public List<Long> findRoleIdsByUserId(@Param("userId") Long userId);
	
	public Integer deleteByUserId(@Param("userId") Long userId);
	
	public Integer deleteByRoleId(@Param("roleId") Long roleId);
	
}
