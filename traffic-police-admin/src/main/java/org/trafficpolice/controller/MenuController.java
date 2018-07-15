package org.trafficpolice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.dto.MenuDTO;
import org.trafficpolice.po.Menu;
import org.trafficpolice.service.MenuService;

/**
 * @author zhangxiaofei
 * 2018年7月15日上午1:10:41
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	@Qualifier(MenuService.BEAN_ID)
	private MenuService menuService;
	
	@PostMapping("/add")
	public NULL add(@RequestBody MenuDTO menu) {
		menuService.addMenu(menu);
		return NULL.newInstance();
	}
	
	@PostMapping("/update")
	public NULL update(@RequestBody MenuDTO menu) {
		menuService.updateMenu(menu);
		return NULL.newInstance();
	}
	
	@PostMapping("/delete")
	public NULL delete(Long id) {
		menuService.deleteById(id);
		return NULL.newInstance();
	}
	
	@PostMapping("/all")
	public List<MenuDTO> findAll() {
		return menuService.findAll();
	}
	
	@PostMapping("/all-leaf")
	public List<Menu> findAllLeaf() {
		return menuService.findAllLeaf();
	}
	
	@PostMapping("/query")
	public MenuDTO query(@RequestParam("id") Long id) {
		return menuService.findById(id);
	}
	
}
