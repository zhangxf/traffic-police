package org.trafficpolice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.json.NULL;
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
	public List<Category> queryAllCategories(@PathVariable("type") String type) {
		return categoryService.queryByType(type);
	}
	
	@PostMapping("/add")
	public NULL addCategory(@RequestBody Category category) {
		categoryService.addCategory(category);
		return NULL.newInstance();
	}
	
	@PostMapping("/delete")
	public NULL deleteById(Long id) {
		categoryService.deleteById(id);
		return NULL.newInstance();
	}
	
	@PostMapping("/update")
	public NULL updateCategory(@RequestBody Category category) {
		categoryService.updateCategory(category);
		return NULL.newInstance();
	}
	
}
