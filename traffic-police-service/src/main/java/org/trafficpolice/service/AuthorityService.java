package org.trafficpolice.service;

import java.util.List;

import org.trafficpolice.dto.AuthorityQueryParamDTO;
import org.trafficpolice.po.Authority;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月12日 下午6:04:51
 */
public interface AuthorityService {

	public static final String BEAN_ID = "authorityService";
	
	public void addAuthority(Authority authority);
	
	public void deleteAuthority(Long id);
	
	public void updateAuthority(Authority authority);
	
	public PageInfo<Authority> queryAuthorityPage(AuthorityQueryParamDTO queryDTO);
	
	public List<Authority> queryAll();
	
	/**
	 * 根据菜单查询权限
	 * @return
	 */
	public List<Authority> querByMenuId(Long menuId);
	
}
