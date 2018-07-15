package org.trafficpolice.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.po.Menu;

/**
 * @author zhangxiaofei
 * 2018年7月14日下午12:16:32
 */
@Repository(MenuDao.BEAN_ID)
public interface MenuDao {

	public static final String BEAN_ID = "menuDao";
	
	public Integer doInsert(Menu menu);
	
	public Integer doUpdate(Menu menu);
	
	public Integer doUpdateLeafById(@Param("id")Long id, @Param("isLeaf")Boolean isLeaf);
	
	public Integer doDelete(@Param("id")Long id);
	
	public List<Menu> findAll();
	
	public Menu findById(@Param("id")Long id);
	
	public List<Menu> findAllLeaf();
	
	public List<Menu> findByParentId(@Param("parentId")Long parentId);
	
	public Menu findByName(@Param("name")String name);
	
	public List<Menu> findByIds(Collection<Long> ids);
	
}
