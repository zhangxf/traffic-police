package org.trafficpolice.service;

import java.util.List;

import org.trafficpolice.dto.ConfigAuthoritiesParamDTO;
import org.trafficpolice.dto.ConfigMenuParamDTO;
import org.trafficpolice.dto.RoleAuthorityDTO;
import org.trafficpolice.dto.RoleQueryParamDTO;
import org.trafficpolice.po.Role;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月12日 下午5:26:22
 */
public interface RoleService {

	public static final String BEAN_ID = "roleService";
	
	public void addRole(Role role);
	
	public void deleteRole(Long id);
	
	public void updateRole(Role role);
	
	public PageInfo<Role> queryRolePage(RoleQueryParamDTO queryDTO);
	
	public List<Role> queryAllRoles();
	
	public List<RoleAuthorityDTO> queryAllRoleAuthorities();
	
	public List<Role> queryRolesByUserId(Long userId);
	
	public List<Long> queryRoleIdsByUserId(Long userId);
	
	public List<Long> queryAuthorityIds(Long roleId);
	
	public List<Long> queryMenuIds(Long roleId);
	
	public void configAuthority(ConfigAuthoritiesParamDTO configDTO);
	
	public void configMenu(ConfigMenuParamDTO configDTO);
	
}
