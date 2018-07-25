package org.trafficpolice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.dto.CategoryDTO;
import org.trafficpolice.po.Category;
import org.trafficpolice.service.CategoryService;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月25日 下午2:02:51
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
	
	@GetMapping("/video/all")
	public List<CategoryDTO> queryAllVideoCategories() {
		return categoryService.queryAllVideoCategories();
	}
	
}
