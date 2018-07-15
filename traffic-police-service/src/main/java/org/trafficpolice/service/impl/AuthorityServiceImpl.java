package org.trafficpolice.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.dao.AuthorityDao;
import org.trafficpolice.dao.RoleAuthorityDao;
import org.trafficpolice.dao.UserAuthorityDao;
import org.trafficpolice.dto.AuthorityQueryParamDTO;
import org.trafficpolice.po.Authority;
import org.trafficpolice.service.AuthorityService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月12日 下午6:06:45
 */
@Service(AuthorityService.BEAN_ID)
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	@Qualifier(AuthorityDao.BEAN_ID)
	private AuthorityDao authorityDao;
	
	@Autowired
	@Qualifier(RoleAuthorityDao.BEAN_ID)
	private RoleAuthorityDao roleAuthorityDao;
	
	@Autowired
	@Qualifier(UserAuthorityDao.BEAN_ID)
	private UserAuthorityDao userAuthorityDao;
	
	@Override
	@Transactional
	public void addAuthority(Authority authority) {
		Date today = new Date();
		authority.setCreateTime(today);
		authority.setUpdateTime(today);
		authorityDao.doInsert(authority);
	}

	@Override
	@Transactional
	public void deleteAuthority(Long id) {
		roleAuthorityDao.deleteByAuthorityId(id);
		userAuthorityDao.deleteByAuthorityId(id);
		authorityDao.doDelete(id);
	}

	@Override
	@Transactional
	public void updateAuthority(Authority authority) {
		authority.setUpdateTime(new Date());
		authorityDao.doUpdate(authority);
	}

	@Override
	@Transactional
	public PageInfo<Authority> queryAuthorityPage(AuthorityQueryParamDTO queryDTO) {
		PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
		Authority authority = new Authority();
		authority.setCode(queryDTO.getCode());
		authority.setName(queryDTO.getName());
		List<Authority> authorities = authorityDao.findByCondition(authority);
		return new PageInfo<Authority>(authorities);
	}

	@Override
	@Transactional
	public List<Authority> queryAll() {
		return authorityDao.findAll();
	}

	@Override
	@Transactional
	public List<Authority> querByMenuId(Long menuId) {
		return authorityDao.findByMenuId(menuId);
	}

}
