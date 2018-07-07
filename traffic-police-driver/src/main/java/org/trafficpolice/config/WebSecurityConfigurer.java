package org.trafficpolice.config;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.trafficpolice.properties.SecurityIgnoreProperties;
import org.trafficpolice.security.AppAuthenticationDetailsSource;
import org.trafficpolice.security.AppAuthenticationProvider;
import org.trafficpolice.security.ApplicationLogoutHandler;
import org.trafficpolice.security.LoginSuccessHandler;
import org.trafficpolice.security.authentication.AbstractAuthenticationProvider;
import org.trafficpolice.security.authentication.DefaultAuthenticationSuccessHandler;
import org.trafficpolice.security.config.AbstractSecurityConfigurerAdapter;

/**
 * @author zhangxiaofei
 * 2018年7月7日上午11:01:24
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigurer extends AbstractSecurityConfigurerAdapter {
	
	public static final AntPathRequestMatcher LOGIN_MATCHER = DEFAULT_LOGIN_MATCHER;
	
	public static final String USERNAME_PARAMETER = DEFAULT_USERNAME_PARAMETER;
	
	public static final String PASSWORD_PARAMETER = DEFAULT_PASSWORD_PARAMETER;
	
	@Autowired
	private SecurityIgnoreProperties securityIgnore;
	
	@Autowired
	@Qualifier(AppAuthenticationProvider.BEAN_ID)
	private AppAuthenticationProvider authenticationProvider;
	
	@Autowired
	@Qualifier(LoginSuccessHandler.BEAN_ID)
	private LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	@Qualifier(ApplicationLogoutHandler.BEAN_ID)
	private ApplicationLogoutHandler applicationLogoutHandler;
	
	@Override
	protected void configurerAnonymousUrlPattern(Set<String> antPatterns) {
		Map<String, String> patterns = securityIgnore.getPattern();
		if (MapUtils.isNotEmpty(patterns)) {
			Iterator<String> it = patterns.values().iterator();
			while (it.hasNext()) {
				String value = it.next();
				if (value.contains(",")) {
					antPatterns.addAll(Arrays.asList(value.split(",")));
				} else {
					antPatterns.add(value);
				}
			}
		}
	}
	
	@Override
	protected AuthenticationDetailsSource<HttpServletRequest, ?> getAuthenticationDetailsSource() {
		return new AppAuthenticationDetailsSource();
	}

	@Override
	public AbstractAuthenticationProvider<?> getAuthenticationProvider() {
		return authenticationProvider;
	}
	
	@Override
	protected DefaultAuthenticationSuccessHandler getLoginSuccessHandler() {
		return loginSuccessHandler;
	}
	
	@Override
	protected LogoutHandler getLogoutHandler() {
		return applicationLogoutHandler;
	}

}
