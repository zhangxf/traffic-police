package org.trafficpolice.security.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.trafficpolice.security.userdetails.AuthUser;

/**
 * @author zhangxiaofei
 * @createdOn 2018年1月29日 下午1:02:55
 */
public class AuthenticationUtils {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationUtils.class);
	
	private static final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
	
	public static <T> T getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || trustResolver.isAnonymous(authentication)) {
			return null;
		}
		AuthUser<T> authUser = (AuthUser<T>)authentication.getPrincipal();
		return authUser.getCurrentUser();
	}
	
	public static <T> void resetCurrentUser(T t) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null || trustResolver.isAnonymous(authentication)) {
				return;
			}
			AuthUser<T> authUser = (AuthUser<T>)authentication.getPrincipal();
			PropertyUtils.copyProperties(authUser.getCurrentUser(), t);
		} catch (Exception e) {
			logger.error("####【重设用户信息失败】####", e);
		}
	}
	
	public static boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || trustResolver.isAnonymous(authentication)) {
			return false;
		}
		return authentication.isAuthenticated();
	}
	
}
