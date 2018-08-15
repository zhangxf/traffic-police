package org.trafficpolice.security.authentication;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.trafficpolice.commons.exception.BaseException;
import org.trafficpolice.security.enumeration.SpringSecurityExceptionEnum;
import org.trafficpolice.security.exception.SpringSecurityException;
import org.trafficpolice.security.userdetails.AuthUser;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月3日 下午3:31:27
 */
public abstract class AbstractAuthenticationProvider<T> extends AbstractUserDetailsAuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(AbstractAuthenticationProvider.class);
	
	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		try {
			preLoadUserChecks(username, authentication);
			T currentUser = loadUser(username, authentication);
			postLoadUserChecks(username, currentUser);
			return buildAuthUser(currentUser);
		} catch (BaseException be) {
			throw new SpringSecurityException(be.getExceptionMessage());
		} catch (SpringSecurityException sse) {
			throw sse;
		} catch (Exception e) {
			logger.error("####【登录异常】####", e);
			throw new SpringSecurityException(SpringSecurityExceptionEnum.AUTHENTICATION_FAILURE);
		}

	}
	
	public AuthUser<T> buildAuthUser(T currentUser) {
		String currentUsername = getUserName(currentUser);
		UserBuilder userBuilder = User.withUsername(currentUsername);
		userBuilder.password("");
		Set<String> roles = getUserRoles(currentUser);
		userBuilder.roles(roles.toArray(new String[roles.size()]));
		userBuilder.accountExpired(false);
		userBuilder.accountLocked(false);
		userBuilder.credentialsExpired(false);
		userBuilder.disabled(false);
		return new AuthUser<T>(currentUser, userBuilder.build());
	}

	/**
	 * 针对使用用户名或者手机号或者....登录的情况，securityContext存储时可以统一使用用户名或者手机号或者...<br/>
	 * 如果返回null，则使用登录时的username名存储
	 * @param currentUser
	 * @return
	 */
	public abstract String getUserName(T currentUser);
	
	/**
	 * 取用户角色例如：USER, ADMIN...(不要以ROLE_开头（authority以ROLE_开头）)
	 * @param currentUser
	 * @return
	 */
	public abstract Set<String> getUserRoles(T currentUser);
	
	/**
	 * 登录之前(取登录用户之前)的校验（例如：图片验证码校验）
	 */
	protected void preLoadUserChecks(String username, UsernamePasswordAuthenticationToken authentication) {
		
	}
	
	/**
	 * 用户查询
	 * @param username
	 * @param authentication
	 * @return
	 */
	public abstract T loadUser(String username, UsernamePasswordAuthenticationToken authentication);
	
	/**
	 * 取登录用户之后的校验
	 * @param currentUser
	 */
	protected void postLoadUserChecks(String username, T currentUser) {
		
	}
	
}
