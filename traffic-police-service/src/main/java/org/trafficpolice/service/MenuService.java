package org.trafficpolice.service;

import java.util.List;

import org.trafficpolice.dto.MenuDTO;
import org.trafficpolice.po.Menu;

/**
 * @author zhangxiaofei
 * 2018年7月14日下午12:41:09
 */
public interface MenuService {

	public static final String BEAN_ID = "menuService";
	
	public void addMenu(MenuDTO menu);
	
	public void updateMenu(MenuDTO menu);
	
	public void deleteById(Long id);
	
	public List<MenuDTO> findAll();
	
	public List<Menu> findAllLeaf();
	
	public MenuDTO findById(Long id);
	
}
