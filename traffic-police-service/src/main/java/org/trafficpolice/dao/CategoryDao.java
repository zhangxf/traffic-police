package org.trafficpolice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.enumeration.CategoryType;
import org.trafficpolice.po.Category;

/**
 * @author zhangxiaofei
 * 2018年7月20日上午12:18:00
 */
@Repository(CategoryDao.BEAN_ID)
public interface CategoryDao {

	public static final String BEAN_ID = "categoryDao";
	
	public Integer doInsert(Category category);
	
	public Integer doUpdate(Category category);
	
	public Integer doDelete(@Param("id") Long id);
	
	public List<Category> findByType(@Param("type")CategoryType type);
	
	public Category findByNameAndType(@Param("name")String name, @Param("type")CategoryType type);
	
	public Category findById(@Param("id") Long id);
	
}
