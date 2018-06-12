package org.trafficpolice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.commons.json.NULL;
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
	
	@GetMapping("/page")
	public PageInfo<Role> queryBGUserPage(int pageNum, int pageSize) {
		return roleService.queryRolePage(pageNum, pageSize);
	}
	
	@GetMapping("/delete")
	public NULL deleteUser(@RequestParam("id") Long id) {
		roleService.deleteRole(id);
		return NULL.newInstance();
	}
	
}
