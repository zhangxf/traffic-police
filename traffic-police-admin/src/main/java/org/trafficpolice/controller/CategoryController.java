package org.trafficpolice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.enumeration.CategoryType;
import org.trafficpolice.po.Category;
import org.trafficpolice.service.CategoryService;

/**
 * @author zhangxiaofei
 * 2018年7月20日上午12:05:30
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	@Qualifier(CategoryService.BEAN_ID)
	private CategoryService categoryService;
	
	@PostMapping("/{type}")
	public List<Category> queryAllCategories(CategoryType type) {
		return categoryService.queryByType(type);
	}
	
	@PostMapping("/add")
	public void addCategory(@RequestBody Category category) {
		categoryService.addCategory(category);
	}
	
	@PostMapping("/delete")
	public void deleteById(Long id) {
		categoryService.deleteById(id);
	}
	
	@PostMapping("/update")
	public void updateCategory(@RequestBody Category category) {
		categoryService.updateCategory(category);
	}
	
}
