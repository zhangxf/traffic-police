package org.trafficpolice.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.dao.RoleDao;
import org.trafficpolice.po.Role;
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
	
	@Override
	@Transactional
	public void addRole(Role role) {
		role.setCode(role.getCode().toUpperCase());
		Date today = new Date();
		role.setCreateTime(today);
		role.setUpdateTime(today);
		roleDao.doInsert(role);
	}

	@Override
	@Transactional
	public void deleteRole(Long id) {
		
	}

	@Override
	@Transactional
	public void updateRole(Role role) {
		role.setUpdateTime(new Date());
		roleDao.doUpdate(role);
	}

	@Override
	@Transactional
	public PageInfo<Role> queryRolePage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Role> roles = roleDao.findAll();
		return new PageInfo<Role>(roles);
	}

}
