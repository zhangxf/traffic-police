package org.trafficpolice.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.dao.BGUserDao;
import org.trafficpolice.po.BGUser;
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
	
	@Override
	@Transactional
	public void saveBGUser(BGUser bgUser) {
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
		bgUser.setUpdateTime(new Date());
		bgUserDao.doUpdate(bgUser);
	}

	@Override
	@Transactional
	public PageInfo<BGUser> queryBGUserPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<BGUser> users = bgUserDao.queryAllBGUser();
//		bgUserDao.queryBGUserPage(pageNum, pageSize);
		PageInfo<BGUser> page = new PageInfo<BGUser>(users);
		return page;
	}

	@Override
	@Transactional
	public void deleteBGUser(Long id) {
		bgUserDao.doDelete(id);
	}

}
