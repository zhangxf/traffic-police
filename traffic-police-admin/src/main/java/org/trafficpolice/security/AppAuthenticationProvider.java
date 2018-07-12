package org.trafficpolice.security;

import java.util.HashSet;
import java.util.Set;

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
import org.trafficpolice.security.authentication.AbstractAuthenticationProvider;
import org.trafficpolice.security.exception.SpringSecurityException;
import org.trafficpolice.security.userdetails.AuthUser;
import org.trafficpolice.service.BGUserService;

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
		roleSet.add("USER");
		return roleSet;
	}

	@Override
	@Autowired
	@Qualifier(PreAuthenticationChecks.BEAN_ID)
	public void setPreAuthenticationChecks(UserDetailsChecker preAuthenticationChecks) {
		super.setPreAuthenticationChecks(preAuthenticationChecks);
	}

}
