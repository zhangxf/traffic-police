package org.trafficpolice.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.dao.MenuDao;
import org.trafficpolice.dao.RoleDao;
import org.trafficpolice.dao.RoleMenuDao;
import org.trafficpolice.dao.UserMenuDao;
import org.trafficpolice.dto.MenuDTO;
import org.trafficpolice.exception.MenuExceptionEnum;
import org.trafficpolice.po.Menu;
import org.trafficpolice.po.RoleMenu;
import org.trafficpolice.service.MenuService;

/**
 * @author zhangxiaofei
 * 2018年7月14日下午4:10:40
 */
@Service(MenuService.BEAN_ID)
public class MenuServiceImpl implements MenuService {

	@Autowired
	@Qualifier(MenuDao.BEAN_ID)
	private MenuDao menuDao;
	
	@Autowired
	@Qualifier(RoleDao.BEAN_ID)
	private RoleDao roleDao;
	
	@Autowired
	@Qualifier(RoleMenuDao.BEAN_ID)
	private RoleMenuDao roleMenuDao;
	
	@Autowired
	@Qualifier(UserMenuDao.BEAN_ID)
	private UserMenuDao userMenuDao;
	
	@Override
	@Transactional
	public void addMenu(MenuDTO menu) {
//		Menu existMenu = menuDao.findByName(menu.getName());
//		if (existMenu != null) {
//			throw new BizException(MenuExceptionEnum.EXIST_MENU);
//		}
		Menu po = new Menu();
		po.setName(menu.getName());
		po.setAction(menu.getAction());
		po.setSortedOrder(menu.getSortedOrder());
		Long parentId = menu.getParentId();
		if (parentId != null) {
			Menu parentMenu = menuDao.findById(parentId);
			if (parentMenu.getIsLeaf()) {
				parentMenu.setIsLeaf(false);
				menuDao.doUpdate(parentMenu);
			}
			po.setParentId(parentId);
		}
		po.setIsLeaf(true);
		Date today = new Date();
		po.setCreateTime(today);
		po.setUpdateTime(today);
		menuDao.doInsert(po);
//		List<Long> roleIds = menu.getRoleIds();
//		if (CollectionUtils.isEmpty(roleIds)) {
//			return;
//		}
//		List<Long> parentIds = menu.getParentIds();
//		Map<Long, Set<Long>> roleIdKeyMenuIdSetValueMap = this.getRoleMenuByMenuIdsAndConvert2Map(parentIds);
//		for (Long roleId : roleIds) {
//			//父菜单授权
//			if (CollectionUtils.isNotEmpty(parentIds)) {
//				for (Long pid : parentIds) {
//					if (!roleIdKeyMenuIdSetValueMap.get(roleId).contains(pid)) {
//						this.createRoleMenu(roleId, pid);
//					}
//				}
//			}
//			//当前菜单授权
//			this.createRoleMenu(roleId, po.getId());
//		}
	}

	private void createRoleMenu(Long roleId, Long menuId) {
		RoleMenu roleMenu = new RoleMenu();
		roleMenu.setMenuId(menuId);
		roleMenu.setRoleId(roleId);
		roleMenu.setCreateTime(new Date());
		roleMenuDao.doInsert(roleMenu);
	}
	
	@Override
	@Transactional
	public void updateMenu(MenuDTO menu) {
		Menu existMenuById = menuDao.findById(menu.getId());
		if (existMenuById == null) {
			throw new BizException(MenuExceptionEnum.NOT_EXIST_MENU);
		}
//		String name = menu.getName();
//		if (!existMenuById.getName().equals(name)) {
//			Menu existMenuByName = menuDao.findByName(name);
//			if (existMenuByName != null) {
//				throw new BizException(MenuExceptionEnum.EXIST_MENU);
//			}
//		}
		Menu po = new Menu();
		po.setId(menu.getId());
		po.setName(menu.getName());
		po.setAction(menu.getAction());
		po.setSortedOrder(menu.getSortedOrder());
//		Long parentId = menu.getParentId();
//		if (parentId != null) {
//			Menu parentMenu = menuDao.findById(parentId);
//			if (parentMenu.getIsLeaf()) {
//				parentMenu.setIsLeaf(false);
//				menuDao.doUpdate(menu);
//			}
//			po.setParentId(parentId);
//		}
		Date today = new Date();
		po.setUpdateTime(today);
		menuDao.doUpdate(po);
		//更新完当前菜单后，处理原父菜单
//		if (parentId == null) {
//			List<Menu> parentMenus = menuDao.findByParentId(existMenuById.getParentId());
//			if (CollectionUtils.isEmpty(parentMenus)) {
//				menuDao.doUpdateLeafById(existMenuById.getParentId(), true);
//			}
//		}
//		List<Long> roleIds = menu.getRoleIds();
//		if (CollectionUtils.isEmpty(roleIds)) {
//			roleMenuDao.deleteByMenuId(menu.getId());
//			return;
//		}
//		List<Long> parentIds = menu.getParentIds();
//		Map<Long, Set<Long>> roleIdKeyMenuIdSetValueMap = this.getRoleMenuByMenuIdsAndConvert2Map(parentIds);
//		Set<Long> existRoleIds = new HashSet<Long>();
//		List<RoleMenu> roleMenus = roleMenuDao.findByMenuId(menu.getId());
//		if (CollectionUtils.isEmpty(roleMenus)) {
//			for (RoleMenu rm : roleMenus) {
//				existRoleIds.add(rm.getRoleId());
//			}
//		}
//		for (Long roleId : roleIds) {
//			//父菜单授权
//			if (CollectionUtils.isNotEmpty(parentIds)) {
//				for (Long pid : parentIds) {
//					if (!roleIdKeyMenuIdSetValueMap.get(roleId).contains(pid)) {
//						this.createRoleMenu(roleId, pid);
//					}
//				}
//			}
//			//当前菜单授权
//			if (!existRoleIds.contains(roleId)) {
//				this.createRoleMenu(roleId, po.getId());
//			}
//		}
//		//删除当前菜单取消的授权
//		if (CollectionUtils.isEmpty(roleMenus)) {
//			for (RoleMenu rm : roleMenus) {
//				if (!roleIds.contains(rm.getRoleId())) {
//					roleMenuDao.doDelete(rm.getId());
//				}
//			}
//		}
	}

	private Map<Long, Set<Long>> getRoleMenuByMenuIdsAndConvert2Map(List<Long> menuIds) {
		if (CollectionUtils.isEmpty(menuIds)) {
			return Collections.emptyMap();
		}
		List<RoleMenu> roleMenus = Collections.emptyList();
		if (menuIds.size() == 1) {
			roleMenus = roleMenuDao.findByMenuId(menuIds.get(0));
		} else {
			roleMenus = roleMenuDao.findByMenuIds(menuIds);
		}
		if (CollectionUtils.isEmpty(roleMenus)) {
			return Collections.emptyMap();
		}
		Map<Long, Set<Long>> roleIdKeyMenuIdSetValueMap = new HashMap<Long, Set<Long>>();
		for (RoleMenu rm : roleMenus) {
			Long roleId = rm.getRoleId();
			if (roleIdKeyMenuIdSetValueMap.get(roleId) == null) {
				roleIdKeyMenuIdSetValueMap.put(roleId, new HashSet<Long>());
			}
			roleIdKeyMenuIdSetValueMap.get(roleId).add(rm.getMenuId());
		}
		return roleIdKeyMenuIdSetValueMap;
	}
	
	@Override
	@Transactional
	public void deleteById(Long id) {
		Menu existMenuById = menuDao.findById(id);
		if (existMenuById == null) {
			throw new BizException(MenuExceptionEnum.NOT_EXIST_MENU);
		}
		if (!existMenuById.getIsLeaf()) {
			throw new BizException(MenuExceptionEnum.DISSALLOW_DELETE_MENU);
		}
		roleMenuDao.deleteByMenuId(id);
		userMenuDao.deleteByMenuId(id);
		menuDao.doDelete(id);
		//删除当前菜单后，处理原父菜单
		List<Menu> parentMenus = menuDao.findByParentId(existMenuById.getParentId());
		if (CollectionUtils.isEmpty(parentMenus)) {
			menuDao.doUpdateLeafById(existMenuById.getParentId(), true);
		}
	}

	@Override
	@Transactional
	public List<MenuDTO> findAll() {
		List<Menu> menuList = menuDao.findAll();
		return this.convert2tree(menuList, null);
	}

	private List<MenuDTO> convert2tree(List<Menu> menuList, Long parentId) {
		if (CollectionUtils.isEmpty(menuList)) {
			return Collections.emptyList();
		}
		List<MenuDTO> result = new ArrayList<MenuDTO>();
		Iterator<Menu> it = menuList.iterator();
		while (it.hasNext()) {
			Menu menu = it.next();
			if ((parentId == null && menu.getParentId() == null) || (parentId != null && parentId.equals(menu.getParentId()))) {
				it.remove();
				MenuDTO menuDTO = new MenuDTO();
				menuDTO.setId(menu.getId());
				menuDTO.setName(menu.getName());
				menuDTO.setAction(menu.getAction());
				menuDTO.setIsLeaf(menu.getIsLeaf());
				menuDTO.setSortedOrder(menu.getSortedOrder());
				menuDTO.setParentId(menu.getParentId());
				menuDTO.setCreateTime(menu.getCreateTime());
				menuDTO.setUpdateTime(menu.getUpdateTime());
				if (CollectionUtils.isNotEmpty(menuList)) {
					menuDTO.setChildren(this.convert2tree(new ArrayList<Menu>(menuList), menu.getId()));
				}
				result.add(menuDTO);
			}
		}
		return result;
	}
	
	@Override
	@Transactional
	public List<Menu> findAllLeaf() {
		return menuDao.findAllLeaf();
	}

	@Override
	@Transactional
	public MenuDTO findById(Long id) {
		Menu menu = menuDao.findById(id);
		MenuDTO result = new MenuDTO();
		result.setId(menu.getId());
		result.setName(menu.getName());
		result.setParentId(menu.getParentId());
		result.setAction(menu.getAction());
		result.setIsLeaf(menu.getIsLeaf());
		result.setSortedOrder(menu.getSortedOrder());
		result.setCreateTime(menu.getCreateTime());
		result.setUpdateTime(menu.getUpdateTime());
//		List<RoleMenu> roleMenuList = roleMenuDao.findByMenuId(id);
//		if (CollectionUtils.isEmpty(roleMenuList)) {
//			return result;
//		}
//		Set<Long> roleIds = new HashSet<Long>();
//		for (RoleMenu rm : roleMenuList) {
//			roleIds.add(rm.getRoleId());
//		}
//		List<Role> roles = roleDao.findByIds(roleIds);
//		result.setRoles(roles);
		return result;
	}

}
