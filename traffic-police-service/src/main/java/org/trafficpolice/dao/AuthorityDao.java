package org.trafficpolice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.po.Authority;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月12日 下午5:45:29
 */
@Repository(AuthorityDao.BEAN_ID)
public interface AuthorityDao {

	public static final String BEAN_ID = "authorityDao";
	
	public Integer doInsert(Authority authority);
	
	public Integer doUpdate(Authority authority);
	
	public Integer doDelete(@Param("id") Long id);
	
	public Authority findById(@Param("id") Long id);
	
	public Authority findByName(@Param("name") String name);
	
	public List<Authority> findAll();
	
	public List<Authority> findByMenuId(@Param("menuId") Long menuId);
	
	public List<Authority> findByCondition(Authority authority);
	
}
