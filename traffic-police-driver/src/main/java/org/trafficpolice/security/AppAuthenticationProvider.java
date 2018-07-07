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
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.dto.VerifyCodeDTO;
import org.trafficpolice.exception.UserExceptionEnum;
import org.trafficpolice.exception.VerifyCodeExceptionEnum;
import org.trafficpolice.po.User;
import org.trafficpolice.security.authentication.AbstractAuthenticationProvider;
import org.trafficpolice.security.exception.SpringSecurityException;
import org.trafficpolice.service.UserService;
import org.trafficpolice.service.VerifyCodeService;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月3日 下午3:51:56
 */
@Component(AppAuthenticationProvider.BEAN_ID)
public class AppAuthenticationProvider extends AbstractAuthenticationProvider<User> {

	private static final Logger logger = LoggerFactory.getLogger(AppAuthenticationProvider.class);
	
	public static final String BEAN_ID = "appAuthenticationProvider";
	
	@Autowired
	@Qualifier(VerifyCodeService.BEAN_ID)
	private VerifyCodeService verifyCodeService;
	
	@Autowired
	@Qualifier(UserService.BEAN_ID)
	private UserService userService;
	
	@Override
	protected void preLoadUserChecks(String username, UsernamePasswordAuthenticationToken authentication) {
		AppAuthenticationDetails details = (AppAuthenticationDetails)authentication.getDetails();
		VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO();
		verifyCodeDTO.setPhone(username);
		verifyCodeDTO.setCode((String) authentication.getCredentials());
		verifyCodeDTO.setToken(details.getVerifyCodeToken());
		verifyCodeDTO.setType("login");
		boolean isValide = verifyCodeService.checkVerifyCode(verifyCodeDTO);
		if (!isValide) {
			throw new BizException(VerifyCodeExceptionEnum.INCORRECT);
		}
		verifyCodeService.clearVerifyCodeCache(verifyCodeDTO);
	}

	@Override
	public User loadUser(String username, UsernamePasswordAuthenticationToken authentication) {
		return userService.findByPhone(username);
	}

	@Override
	protected void postLoadUserChecks(String username, User currentUser) {
		//用户不存在校验
		if (currentUser == null) {
			throw new SpringSecurityException(UserExceptionEnum.NOT_FOUND);
		}
	}

	/**
	 * 密码等属性的校验
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		
	}
	
	@Override
	public String getUserName(User user) {
		return user.getPhone();
	}

	@Override
	public Set<String> getUserRoles(User user) {
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
