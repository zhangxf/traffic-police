package org.trafficpolice.service;

import java.util.List;

import org.trafficpolice.enumeration.CategoryType;
import org.trafficpolice.po.Category;

/**
 * @author zhangxiaofei
 * 2018年7月20日上午12:33:58
 */
public interface CategoryService {

	public static final String BEAN_ID = "categoryService";
	
	public void addCategory(Category category);
	
	public void deleteById(Long id);
	
	public void updateCategory(Category category);
	
	public List<Category> queryByType(CategoryType type);
	
}
