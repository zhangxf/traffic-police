package org.trafficpolice.service;

import java.util.List;

import org.trafficpolice.po.BGUser;
import org.trafficpolice.po.UserAuthority;

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
	
	public BGUser findByUsername(String username);
	
	public List<UserAuthority> queryAllUserAuthorities();
	
	public List<UserAuthority> queryUserAuthoritiesByUserId(Long userId);
	
}
