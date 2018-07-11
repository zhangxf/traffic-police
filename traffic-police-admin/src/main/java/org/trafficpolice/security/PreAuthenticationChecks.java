package org.trafficpolice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;
import org.trafficpolice.exception.BGUserExceptionEnum;
import org.trafficpolice.po.BGUser;
import org.trafficpolice.security.exception.SpringSecurityException;
import org.trafficpolice.security.userdetails.AuthUser;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月5日 下午7:15:25
 * 校验密码之前的校验（校验账户状态等）
 */
@Component(PreAuthenticationChecks.BEAN_ID)
public class PreAuthenticationChecks implements UserDetailsChecker {

	private static final Logger logger = LoggerFactory.getLogger(PreAuthenticationChecks.class);
	
	public static final String BEAN_ID = "preAuthenticationChecks";
	
	@Override
	public void check(UserDetails toCheck) {
		AuthUser<BGUser> au = (AuthUser<BGUser>)toCheck;
		BGUser currentUser = au.getCurrentUser();
		if (!currentUser.getIsEnable()) {
			logger.info("####【用户已禁用】--> {} ####", currentUser.getUsername());
			throw new SpringSecurityException(BGUserExceptionEnum.IS_NOT_ENABLE);
		}
	}

}
