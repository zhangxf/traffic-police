package org.trafficpolice.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;
import org.trafficpolice.consts.ServiceConsts;
import org.trafficpolice.exception.BGUserExceptionEnum;
import org.trafficpolice.po.BGUser;
import org.trafficpolice.po.Role;
import org.trafficpolice.po.UserAuthority;
import org.trafficpolice.security.authentication.AbstractAuthenticationProvider;
import org.trafficpolice.security.exception.SpringSecurityException;
import org.trafficpolice.security.userdetails.AuthUser;
import org.trafficpolice.service.BGUserService;
import org.trafficpolice.service.RoleService;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月3日 下午3:51:56
 */
@Component(AppAuthenticationProvider.BEAN_ID)
public class AppAuthenticationProvider extends AbstractAuthenticationProvider<BGUser> {

	private static final Logger logger = LoggerFactory.getLogger(AppAuthenticationProvider.class);
	
	public static final String BEAN_ID = "appAuthenticationProvider";
	
	@Autowired
	@Qualifier(BGUserService.BEAN_ID)
	private BGUserService userService;
	
	@Autowired
	@Qualifier(RoleService.BEAN_ID)
	private RoleService roleService;
	
	@Override
	public BGUser loadUser(String username, UsernamePasswordAuthenticationToken authentication) {
		//内置超级用户
		if (ServiceConsts.SUPER_ADMIN_USER.getUsername().equals(username)) {
			return ServiceConsts.SUPER_ADMIN_USER;
		}
		return userService.findByUsername(username);
	}

	@Override
	protected void postLoadUserChecks(String username, BGUser currentUser) {
		//用户不存在校验
		if (currentUser == null) {
			throw new SpringSecurityException(BGUserExceptionEnum.NOT_FOUND);
		}
	}

	/**
	 * 密码等属性的校验
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		AuthUser<BGUser> au = (AuthUser<BGUser>)userDetails;
		BGUser currentUser = au.getCurrentUser();
		if (!currentUser.getPassword().equals((String)authentication.getCredentials())) {
			throw new SpringSecurityException(BGUserExceptionEnum.PASSWORD_INCORRECT);
		}
	}
	
	@Override
	public String getUserName(BGUser user) {
		return user.getUsername();
	}

	@Override
	public Set<String> getUserRoles(BGUser user) {
		Set<String> roleSet = new HashSet<String>();
		roleSet.add("ANONYMOUS");//登录用户拥有匿名角色权限
//		roleSet.add("ANONYMOUS");//登录用户拥有匿名角色权限
		//内置超级用户
		if (ServiceConsts.SUPER_ADMIN_USER.getUsername().equals(user.getUsername())) {
			roleSet.add(ServiceConsts.SUPER_ADMIN_ROLE);
			return roleSet;
		}
//		Long userId = user.getId();
//		List<Long> roleIds = roleService.queryRoleIdsByUserId(userId);
		List<Role> roleList = roleService.queryRolesByUserId(user.getId());
		if (CollectionUtils.isNotEmpty(roleList)) {
			for (Role role : roleList) {
				roleSet.add(role.getCode());
			}
		}
		List<UserAuthority> userAuthorities = userService.queryUserAuthoritiesByUserId(user.getId());
		if (CollectionUtils.isNotEmpty(userAuthorities)) {
			for (UserAuthority ua : userAuthorities) {
				roleSet.add(String.valueOf(ua.getAuthorityId()));
			}
		}
		return roleSet;
	}

	@Override
	@Autowired
	@Qualifier(PreAuthenticationChecks.BEAN_ID)
	public void setPreAuthenticationChecks(UserDetailsChecker preAuthenticationChecks) {
		super.setPreAuthenticationChecks(preAuthenticationChecks);
	}

}
