package org.trafficpolice.service;

import org.trafficpolice.po.BGUser;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月11日 下午4:33:35
 */
public interface BGUserService {

	public static final String BEAN_ID = "bgUserService";
	
	public void saveBGUser(BGUser bgUser);
	
	public void updateBGUser(BGUser bgUser);
	
	public PageInfo<BGUser> queryBGUserPage(int pageNum, int pageSize);
	
	public void deleteBGUser(Long id);
	
}
