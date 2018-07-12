package org.trafficpolice.service;

import java.util.List;

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
	
	public PageInfo<Authority> queryAuthorityPage(int pageNum, int pageSize);
	
	/**
	 * 查询所有叶子节点的权限
	 * @return
	 */
	public List<Authority> queryAllLeafAuthorities();
	
}
