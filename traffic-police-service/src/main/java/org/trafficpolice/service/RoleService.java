package org.trafficpolice.service;

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
	
	public PageInfo<Role> queryRolePage(int pageNum, int pageSize);
	
}
