package org.trafficpolice.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.trafficpolice.po.User;
import org.trafficpolice.security.userdetails.AuthUser;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2017年11月28日 下午2:59:40
 */
@Component(ApplicationLogoutHandler.BEAN_ID)
public class ApplicationLogoutHandler implements LogoutHandler, MessageSourceAware {
	
	private static final Logger logger= LoggerFactory.getLogger(ApplicationLogoutHandler.class);
	
	public static final String BEAN_ID = "applicationLogoutHandler";
	
	protected MessageSource messageSource;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		if (authentication != null) {
			AuthUser<User> authUser = (AuthUser<User>) authentication.getPrincipal();
	        User currentUser = authUser.getCurrentUser();
	        logger.info("####【用户登出】--> {} ####", currentUser.getPhone());
		}
	}
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
}
