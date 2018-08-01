package org.trafficpolice.service;

import java.util.List;

import org.trafficpolice.dto.BGUserQueryParamDTO;
import org.trafficpolice.dto.ConfigAuthoritiesParamDTO;
import org.trafficpolice.dto.ConfigMenuParamDTO;
import org.trafficpolice.dto.ConfigRolesParamDTO;
import org.trafficpolice.dto.MenuDTO;
import org.trafficpolice.dto.UserAuthorityDTO;
import org.trafficpolice.po.Authority;
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
	
	public PageInfo<BGUser> queryBGUserPage(BGUserQueryParamDTO queryDTO);
	
	public List<MenuDTO> queryCurrentUserMenu(BGUser bgUser);
	
	public void deleteBGUser(Long id);
	
	public List<Long> queryAuthorityIds(Long userId);
	
	public List<Long> queryMenuIds(Long userId);
	
	public BGUser findByUsername(String username);
	
	public List<UserAuthorityDTO> queryAllUserAuthorities();
	
	public List<UserAuthority> queryUserAuthoritiesByUserId(Long userId);
	
	public void configAuthority(ConfigAuthoritiesParamDTO configDTO);
	
	public void configMenu(ConfigMenuParamDTO configDTO);
	
	public void configRoles(ConfigRolesParamDTO configDTO);
	
	public List<Long> queryRoles(Long userId);
	
	public List<Authority> buttonAuthorities(BGUser user, Long menuId);
	
}
