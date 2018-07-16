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
import org.trafficpolice.consts.ServiceConsts;
import org.trafficpolice.dao.AuthorityDao;
import org.trafficpolice.dao.BGUserDao;
import org.trafficpolice.dao.BGUserRoleDao;
import org.trafficpolice.dao.MenuDao;
import org.trafficpolice.dao.RoleAuthorityDao;
import org.trafficpolice.dao.RoleMenuDao;
import org.trafficpolice.dao.UserAuthorityDao;
import org.trafficpolice.dao.UserMenuDao;
import org.trafficpolice.dto.BGUserQueryParamDTO;
import org.trafficpolice.dto.ConfigAuthoritiesParamDTO;
import org.trafficpolice.dto.ConfigMenuParamDTO;
import org.trafficpolice.dto.ConfigRolesParamDTO;
import org.trafficpolice.dto.MenuDTO;
import org.trafficpolice.exception.BGUserExceptionEnum;
import org.trafficpolice.po.Authority;
import org.trafficpolice.po.BGUser;
import org.trafficpolice.po.BGUserRole;
import org.trafficpolice.po.Menu;
import org.trafficpolice.po.UserAuthority;
import org.trafficpolice.po.UserMenu;
import org.trafficpolice.service.BGUserService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月11日 下午4:34:27
 */
@Service(BGUserService.BEAN_ID)
public class BGUserServiceImpl implements BGUserService {

	@Autowired
	@Qualifier(BGUserDao.BEAN_ID)
	private BGUserDao bgUserDao;
	
	@Autowired
	@Qualifier(BGUserRoleDao.BEAN_ID)
	private BGUserRoleDao userRoleDao;
	
	@Autowired
	@Qualifier(RoleAuthorityDao.BEAN_ID)
	private RoleAuthorityDao roleAuthorityDao;
	
	@Autowired
	@Qualifier(UserAuthorityDao.BEAN_ID)
	private UserAuthorityDao userAuthorityDao;
	
	@Autowired
	@Qualifier(UserMenuDao.BEAN_ID)
	private UserMenuDao userMenuDao;
	
	@Autowired
	@Qualifier(RoleMenuDao.BEAN_ID)
	private RoleMenuDao roleMenuDao;
	
	@Autowired
	@Qualifier(MenuDao.BEAN_ID)
	private MenuDao menuDao;
	
	@Autowired
	@Qualifier(AuthorityDao.BEAN_ID)
	private AuthorityDao authorityDao;
	
	@Override
	@Transactional
	public void saveBGUser(BGUser bgUser) {
		if (ServiceConsts.SUPER_ADMIN_USER.getUsername().equals(bgUser.getUsername())) {
			throw new BizException(BGUserExceptionEnum.EXIST_ADMIN_USER);
		}
		Date today = new Date();
		bgUser.setCreateTime(today);
		bgUser.setUpdateTime(today);
		bgUserDao.doInsert(bgUser);
	}

	@Override
	@Transactional
	public BGUser findByUsername(String username) {
		return bgUserDao.queryByUsername(username);
	}

	@Override
	@Transactional
	public void updateBGUser(BGUser bgUser) {
		if (ServiceConsts.SUPER_ADMIN_USER.getUsername().equals(bgUser.getUsername())) {
			throw new BizException(BGUserExceptionEnum.EXIST_ADMIN_USER);
		}
		bgUser.setUpdateTime(new Date());
		bgUserDao.doUpdate(bgUser);
	}

	@Override
	@Transactional
	public PageInfo<BGUser> queryBGUserPage(BGUserQueryParamDTO queryDTO) {
		PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
		BGUser user = new BGUser();
		user.setUsername(queryDTO.getUsername());
		List<BGUser> users = bgUserDao.queryAllBGUserByCondition(user);
//		bgUserDao.queryBGUserPage(pageNum, pageSize);
		PageInfo<BGUser> page = new PageInfo<BGUser>(users);
		return page;
	}

	@Override
	@Transactional
	public List<MenuDTO> queryCurrentUserMenu(BGUser bgUser) {
		if (ServiceConsts.SUPER_ADMIN_USER.getUsername().equals(bgUser.getUsername())) {
			List<Menu> allMenu = menuDao.findAll();
			return this.convert2tree(allMenu, null);
		}
		Long userId = bgUser.getId();
		Set<Long> menuIds = new HashSet<Long>();
		List<Long> roleIds = userRoleDao.findRoleIdsByUserId(userId);
		if (CollectionUtils.isNotEmpty(roleIds)) {
			List<Long> menuIdsByRole = roleMenuDao.findDistinctMenuIdByRoleIds(roleIds);
			menuIds.addAll(menuIdsByRole);
		}
		List<Long> menuIdsByUser = userMenuDao.findMenuIdsByUserId(userId);
		menuIds.addAll(menuIdsByUser);
//		List<BGUserRole> userRoleList = userRoleDao.findByUserId(userId);
//		if (CollectionUtils.isNotEmpty(userRoleList)) {
//			Set<Long> roleIdSet = new HashSet<Long>();
//			for (BGUserRole userRole : userRoleList) {
//				roleIdSet.add(userRole.getRoleId());
//			}
//			List<RoleMenu> roleMenuList = roleMenuDao.findByRoleIds(roleIdSet);
//			if (CollectionUtils.isNotEmpty(roleMenuList)) {
//				for (RoleMenu rm : roleMenuList) {
//					menuIds.add(rm.getMenuId());
//				}
//			}
//		}
//		List<UserMenu> userMenuList = userMenuDao.findByUserId(userId);
//		if (CollectionUtils.isNotEmpty(userMenuList)) {
//			for (UserMenu um : userMenuList) {
//				menuIds.add(um.getMenuId());
//			}
//		}
		if (CollectionUtils.isEmpty(menuIds)) {
			return Collections.emptyList();
		}
		List<Menu> menuList = menuDao.findByIds(menuIds);
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
	public void deleteBGUser(Long id) {
		userAuthorityDao.deleteByUserId(id);
		userMenuDao.deleteByUserId(id);
		bgUserDao.doDelete(id);
	}

	@Override
	@Transactional
	public List<Long> queryAuthorityIds(Long userId) {
		return userAuthorityDao.findAuthorityIdsByUserId(userId);
	}

	@Override
	@Transactional
	public List<Long> queryMenuIds(Long userId) {
		return userMenuDao.findMenuIdsByUserId(userId);
	}

	@Override
	@Transactional
	public void configAuthority(ConfigAuthoritiesParamDTO configDTO) {
		Long userId = configDTO.getUserId();
		Set<Long> needMergeAuthorityIds = configDTO.getAuthorityIds();
		if (CollectionUtils.isEmpty(needMergeAuthorityIds)) {//取消所有权限
			userAuthorityDao.deleteByUserId(userId);
			return;
		}
		List<UserAuthority> existUserAuthorities = userAuthorityDao.findByUserId(userId);
		Map<Long, UserAuthority> authorityIdKeyMap = new HashMap<Long, UserAuthority>();
		if (CollectionUtils.isNotEmpty(existUserAuthorities)) {
			for (UserAuthority existUA : existUserAuthorities) {
				Long existAuthorityId = existUA.getAuthorityId();
				if (!needMergeAuthorityIds.contains(existAuthorityId)) {
					userAuthorityDao.doDelete(existUA.getId());
				}
				authorityIdKeyMap.put(existAuthorityId, existUA);
			}
		}
		for (Long authorityId : needMergeAuthorityIds) {
			if (!authorityIdKeyMap.containsKey(authorityId)) {
				UserAuthority ua = new UserAuthority();
				ua.setUserId(userId);
				ua.setAuthorityId(authorityId);
				ua.setCreateTime(new Date());
				userAuthorityDao.doInsert(ua);
			}
		}
	}

	@Override
	@Transactional
	public void configMenu(ConfigMenuParamDTO configDTO) {
		Long userId = configDTO.getUserId();
		Set<Long> needMergeMenuIds = configDTO.getMenuIds();
		if (CollectionUtils.isEmpty(needMergeMenuIds)) {//取消所有菜单
			userMenuDao.deleteByUserId(userId);
			return;
		}
		List<UserMenu> existUserMenuList = userMenuDao.findByUserId(userId);
		Map<Long, UserMenu> existMenuIdKeyMap = new HashMap<Long, UserMenu>();
		if (CollectionUtils.isNotEmpty(existUserMenuList)) {
			for (UserMenu existUM : existUserMenuList) {
				Long existMenuId = existUM.getMenuId();
				if (!needMergeMenuIds.contains(existMenuId)) {
					userMenuDao.doDelete(existUM.getId());
				}
				existMenuIdKeyMap.put(existMenuId, existUM);
			}
		}
		for (Long menuId : needMergeMenuIds) {
			if (!existMenuIdKeyMap.containsKey(menuId)) {
				UserMenu userMenu = new UserMenu();
				userMenu.setUserId(userId);
				userMenu.setMenuId(menuId);
				userMenu.setCreateTime(new Date());
				userMenuDao.doInsert(userMenu);
			}
		}
	}

	@Override
	@Transactional
	public void configRoles(ConfigRolesParamDTO configDTO) {
		Long userId = configDTO.getUserId();
		Set<Long> needMergeRoleIds = configDTO.getRoleIds();
		if (CollectionUtils.isEmpty(needMergeRoleIds)) {//取消所有角色
			userRoleDao.deleteByUserId(userId);
			return;
		}
		List<BGUserRole> existUserRoles = userRoleDao.findByUserId(userId);
		Map<Long, BGUserRole> roleIdKeyMap = new HashMap<Long, BGUserRole>();
		if (CollectionUtils.isNotEmpty(existUserRoles)) {
			for (BGUserRole existUR : existUserRoles) {
				Long existRoleId = existUR.getRoleId();
				if (!needMergeRoleIds.contains(existRoleId)) {
					userRoleDao.doDelete(existUR.getId());
				}
				roleIdKeyMap.put(existRoleId, existUR);
			}
		}
		for (Long roleId : needMergeRoleIds) {
			if (!roleIdKeyMap.containsKey(roleId)) {
				BGUserRole ur = new BGUserRole();
				ur.setUserId(userId);
				ur.setRoleId(roleId);
				ur.setCreateTime(new Date());
				userRoleDao.doInsert(ur);
			}
		}
	}

	@Override
	@Transactional
	public List<Long> queryRoles(Long userId) {
		return userRoleDao.findRoleIdsByUserId(userId);
	}

	@Override
	@Transactional
	public List<Authority> buttonAuthorities(BGUser user, Long menuId) {
		List<Authority> authorityList = authorityDao.findByMenuId(menuId);
		if (CollectionUtils.isEmpty(authorityList)) {
			return Collections.emptyList();
		}
		if (ServiceConsts.SUPER_ADMIN_USER.getUsername().equals(user.getUsername())) {
			return authorityList;
		}
		Map<Long, Authority> authorityMap = new HashMap<Long, Authority>();
		for (Authority authority : authorityList) {
			authorityMap.put(authority.getId(), authority);
		}
		Long userId = user.getId();
		List<Long> userAuthorityIds = userAuthorityDao.filterAuthorityIdsByUserIdAndAuthorityIds(userId, authorityMap.keySet());
		List<Authority> result = new ArrayList<Authority>();
		if (CollectionUtils.isNotEmpty(userAuthorityIds)) {
			Iterator<Authority> it = authorityList.iterator();
			while (it.hasNext()) {
				Authority authority = it.next();
				if (userAuthorityIds.contains(authority.getId())) {
					it.remove();
					result.add(authority);
				}
			}
		}
		if (CollectionUtils.isEmpty(authorityList)) {
			return result;
		}
		List<Long> roleIds = userRoleDao.findRoleIdsByUserId(userId);
		if (CollectionUtils.isEmpty(roleIds)) {
			return result;
		}
		Map<Long, Authority> remainAuthorityMap = new HashMap<Long, Authority>();
		for (Authority authority : authorityList) {
			remainAuthorityMap.put(authority.getId(), authority);
		}
		List<Long> roleAuthorityIds = roleAuthorityDao.filterAuthorityIdsByRoleIdsAndAuthorityIds(roleIds, remainAuthorityMap.keySet());
		if (CollectionUtils.isNotEmpty(roleAuthorityIds)) {
			Iterator<Authority> it = authorityList.iterator();
			while (it.hasNext()) {
				Authority authority = it.next();
				if (roleAuthorityIds.contains(authority.getId())) {
					it.remove();
					result.add(authority);
				}
			}
		}
		return result;
	}

	@Override
	@Transactional
	public List<UserAuthority> queryAllUserAuthorities() {
		return userAuthorityDao.findAll();
	}

	@Override
	@Transactional
	public List<UserAuthority> queryUserAuthoritiesByUserId(Long userId) {
		return userAuthorityDao.findByUserId(userId);
	}

}
