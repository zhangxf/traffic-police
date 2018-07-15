package org.trafficpolice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.dto.ConfigAuthoritiesParamDTO;
import org.trafficpolice.dto.ConfigMenuParamDTO;
import org.trafficpolice.dto.RoleQueryParamDTO;
import org.trafficpolice.po.Role;
import org.trafficpolice.service.RoleService;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月12日 下午5:37:31
 */
@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	@Qualifier(RoleService.BEAN_ID)
	private RoleService roleService;
	
	@PostMapping("/add")
	public Role addRole(@RequestBody Role role) {
		roleService.addRole(role);
		return role;
	}
	
	@PostMapping("/update")
	public NULL updateRole(@RequestBody Role role) {
		if (role == null || role.getId() == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "id");
		}
		roleService.updateRole(role);
		return NULL.newInstance();
	}
	
	@PostMapping("/authorities")
	public List<Long> queryAuthorities(@RequestParam("roleId") Long roleId) {
		return roleService.queryAuthorityIds(roleId);
	}
	
	@PostMapping("/menu")
	public List<Long> queryMenus(@RequestParam("roleId") Long roleId) {
		return roleService.queryMenuIds(roleId);
	}
	
	@PostMapping("/config/authorities")
	public NULL configAuthority(@RequestBody ConfigAuthoritiesParamDTO configDTO) {
		roleService.configAuthority(configDTO);
		return NULL.newInstance();
	}
	
	@PostMapping("/config/menu")
	public NULL configMenu(@RequestBody ConfigMenuParamDTO configDTO) {
		roleService.configMenu(configDTO);
		return NULL.newInstance();
	}
	
	@PostMapping("/page")
	public PageInfo<Role> queryRolePage(@RequestBody RoleQueryParamDTO queryDTO) {
		return roleService.queryRolePage(queryDTO);
	}
	
	@PostMapping("/delete")
	public NULL deleteUser(@RequestParam("id") Long id) {
		roleService.deleteRole(id);
		return NULL.newInstance();
	}
	
}
