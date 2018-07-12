package org.trafficpolice.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.dao.AuthorityDao;
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
	public PageInfo<Authority> queryAuthorityPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Authority> authorities = authorityDao.findAll();
		return new PageInfo<Authority>(authorities);
	}

	@Override
	@Transactional
	public List<Authority> queryAllLeafAuthorities() {
		return authorityDao.findAllLeaf();
	}

}
