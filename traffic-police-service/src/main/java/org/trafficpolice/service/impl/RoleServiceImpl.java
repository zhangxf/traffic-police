package org.trafficpolice.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.dao.BGUserRoleDao;
import org.trafficpolice.dao.RoleAuthorityDao;
import org.trafficpolice.dao.RoleDao;
import org.trafficpolice.dao.RoleMenuDao;
import org.trafficpolice.dto.ConfigAuthoritiesParamDTO;
import org.trafficpolice.dto.ConfigMenuParamDTO;
import org.trafficpolice.dto.RoleAuthorityDTO;
import org.trafficpolice.dto.RoleQueryParamDTO;
import org.trafficpolice.exception.RoleExceptionEnum;
import org.trafficpolice.po.BGUserRole;
import org.trafficpolice.po.Role;
import org.trafficpolice.po.RoleAuthority;
import org.trafficpolice.po.RoleMenu;
import org.trafficpolice.service.RoleService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月12日 下午5:29:57
 */
@Service(RoleService.BEAN_ID)
public class RoleServiceImpl implements RoleService {

	@Autowired
	@Qualifier(RoleDao.BEAN_ID)
	private RoleDao roleDao;
	
	@Autowired
	@Qualifier(RoleAuthorityDao.BEAN_ID)
	private RoleAuthorityDao roleAuthorityDao;
	
	@Autowired
	@Qualifier(BGUserRoleDao.BEAN_ID)
	private BGUserRoleDao userRoleDao;
	
	@Autowired
	@Qualifier(RoleMenuDao.BEAN_ID)
	private RoleMenuDao roleMenuDao;
	
	@Override
	@Transactional
	public void addRole(Role role) {
		if (role == null || StringUtils.isEmpty(role.getName())) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "name");
		}
		Role existRole = roleDao.findByName(role.getName());
		if (existRole != null) {
			throw new BizException(RoleExceptionEnum.EXIST_ROLE);
		}
		Date today = new Date();
		role.setCreateTime(today);
		role.setUpdateTime(today);
		roleDao.doInsert(role);
	}

	@Override
	@Transactional
	public void deleteRole(Long id) {
		userRoleDao.deleteByRoleId(id);
		roleAuthorityDao.deleteByRoleId(id);
		roleMenuDao.deleteByRoleId(id);
		roleDao.doDelete(id);
	}

	@Override
	@Transactional
	public void updateRole(Role role) {
		Long id = role.getId();
		if (role == null || id == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "id");
		}
		Role existRole = roleDao.findById(id);
		if (existRole == null) {
			throw new BizException(RoleExceptionEnum.NOT_EXIST_ROLE);
		}
		if (!existRole.getName().equals(role.getName())) {
			Role nameExistRole = roleDao.findByName(role.getName());
			if (nameExistRole != null) {
				throw new BizException(RoleExceptionEnum.EXIST_ROLE);
			}
		}
		existRole.setName(role.getName());
		existRole.setUpdateTime(new Date());
		roleDao.doUpdate(existRole);
	}

	@Override
	@Transactional
	public PageInfo<Role> queryRolePage(RoleQueryParamDTO queryDTO) {
		PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
		Role role = new Role();
		role.setName(queryDTO.getName());
		List<Role> roles = roleDao.findByCondition(role);
		return new PageInfo<Role>(roles);
	}

	@Override
	@Transactional
	public List<Role> queryAllRoles() {
		return roleDao.findAll();
	}

	@Override
	@Transactional
	public List<RoleAuthorityDTO> queryAllRoleAuthorities() {
		return roleAuthorityDao.findAll();
	}

	@Override
	@Transactional
	public List<Role> queryRolesByUserId(Long userId) {
		List<BGUserRole> userRoleList = userRoleDao.findByUserId(userId);
		if (CollectionUtils.isEmpty(userRoleList)) {
			return Collections.emptyList();
		}
		Set<Long> roleIds = new HashSet<Long>();
		for (BGUserRole userRole : userRoleList) {
			roleIds.add(userRole.getRoleId());
		}
		return roleDao.findByIds(roleIds);
	}

	@Override
	@Transactional
	public List<Long> queryRoleIdsByUserId(Long userId) {
		return userRoleDao.findRoleIdsByUserId(userId);
	}

	@Override
	@Transactional
	public List<Long> queryAuthorityIds(Long roleId) {
		return roleAuthorityDao.findAuthorityIdsByRoleId(roleId);
	}

	@Override
	@Transactional
	public List<Long> queryMenuIds(Long roleId) {
		return roleMenuDao.findMenuIdsByRoleId(roleId);
	}

	@Override
	@Transactional
	public void configAuthority(ConfigAuthoritiesParamDTO configDTO) {
		Long roleId = configDTO.getRoleId();
		Set<Long> needMergeAuthorityIds = configDTO.getAuthorityIds();
		if (CollectionUtils.isEmpty(needMergeAuthorityIds)) {//取消所有权限
			roleAuthorityDao.deleteByRoleId(roleId);
			return;
		}
		List<RoleAuthority> existRoleAuthorities = roleAuthorityDao.findByRoleId(roleId);
		Map<Long, RoleAuthority> authorityIdKeyMap = new HashMap<Long, RoleAuthority>();
		if (CollectionUtils.isNotEmpty(existRoleAuthorities)) {
			for (RoleAuthority existRA : existRoleAuthorities) {
				Long existAuthorityId = existRA.getAuthorityId();
				if (!needMergeAuthorityIds.contains(existAuthorityId)) {
					roleAuthorityDao.doDelete(existRA.getId());
				}
				authorityIdKeyMap.put(existAuthorityId, existRA);
			}
		}
		for (Long authorityId : needMergeAuthorityIds) {
			if (!authorityIdKeyMap.containsKey(authorityId)) {
				RoleAuthority ra = new RoleAuthority();
				ra.setRoleId(roleId);
				ra.setAuthorityId(authorityId);
				ra.setCreateTime(new Date());
				roleAuthorityDao.doInsert(ra);
			}
		}
	}

	@Override
	@Transactional
	public void configMenu(ConfigMenuParamDTO configDTO) {
		Long roleId = configDTO.getRoleId();
		Set<Long> needMergeMenuIds = configDTO.getMenuIds();
		if (CollectionUtils.isEmpty(needMergeMenuIds)) {//取消所有菜单
			roleMenuDao.deleteByRoleId(roleId);
			return;
		}
		List<RoleMenu> existRoleMenuList = roleMenuDao.findByRoleId(roleId);
		Map<Long, RoleMenu> existMenuIdKeyMap = new HashMap<Long, RoleMenu>();
		if (CollectionUtils.isNotEmpty(existRoleMenuList)) {
			for (RoleMenu existRM : existRoleMenuList) {
				Long existMenuId = existRM.getMenuId();
				if (!needMergeMenuIds.contains(existMenuId)) {
					roleMenuDao.doDelete(existRM.getId());
				}
				existMenuIdKeyMap.put(existMenuId, existRM);
			}
		}
		for (Long menuId : needMergeMenuIds) {
			if (!existMenuIdKeyMap.containsKey(menuId)) {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(menuId);
				roleMenu.setCreateTime(new Date());
				roleMenuDao.doInsert(roleMenu);
			}
		}
	}

}
