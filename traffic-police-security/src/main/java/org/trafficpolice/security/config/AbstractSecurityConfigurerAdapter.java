package org.trafficpolice.security.config;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.trafficpolice.security.access.DefaultAccessDeniedHandler;
import org.trafficpolice.security.access.DefaultAuthenticationEntryPoint;
import org.trafficpolice.security.authentication.AbstractAuthenticationProvider;
import org.trafficpolice.security.authentication.DefaultAuthenticationDetailsSource;
import org.trafficpolice.security.authentication.DefaultAuthenticationFailureHandler;
import org.trafficpolice.security.authentication.DefaultAuthenticationSuccessHandler;
import org.trafficpolice.security.authentication.RedisSecurityContextRepository;
import org.trafficpolice.security.consts.SecurityBeanNames;
import org.trafficpolice.security.logout.DefaultLogoutSuccessHandler;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月2日 下午3:18:22
 */
public abstract class AbstractSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	public static final String DEFAULT_LOGIN_URL = "/user/login";
	
	public static final AntPathRequestMatcher DEFAULT_LOGIN_MATCHER = new AntPathRequestMatcher(DEFAULT_LOGIN_URL, HttpMethod.POST.name());
	
	public static final String DEFAULT_LOGOUT_URL = "/user/logout";
	
	public static final String DEFAULT_USERNAME_PARAMETER = "username";
	
	public static final String DEFAULT_PASSWORD_PARAMETER = "password";
	
	private RedisTemplate<String, Object> redisTemplate;
	
	@Bean(SecurityBeanNames.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean(SecurityBeanNames.ACCESS_DENIED_HANDLER)
	@ConditionalOnMissingBean(DefaultAccessDeniedHandler.class)
	public DefaultAccessDeniedHandler accessDeniedHandler() {
		return new DefaultAccessDeniedHandler();
	}
	
	@Bean(SecurityBeanNames.AUTHENTICATION_ENTRY_POINT)
	@ConditionalOnMissingBean(DefaultAuthenticationEntryPoint.class)
	public DefaultAuthenticationEntryPoint authenticationEntryPoint() {
		return new DefaultAuthenticationEntryPoint();
	}
	
	@Bean(SecurityBeanNames.AUTHENTICATION_SUCCESS_HANDLER)
	@ConditionalOnMissingBean(DefaultAuthenticationSuccessHandler.class)
	public DefaultAuthenticationSuccessHandler authenticationSuccessHandler() {
		return new DefaultAuthenticationSuccessHandler();
	}
	
	@Bean(SecurityBeanNames.AUTHENTICATION_FAILURE_HANDLER)
	@ConditionalOnMissingBean(DefaultAuthenticationFailureHandler.class)
	public DefaultAuthenticationFailureHandler authenticationFailureHandler() {
		return new DefaultAuthenticationFailureHandler();
	}
	
	@Bean(SecurityBeanNames.LOGOUT_SUCCESS_HANDLER)
	@ConditionalOnMissingBean(DefaultLogoutSuccessHandler.class)
	public DefaultLogoutSuccessHandler logoutSuccessHandler() {
		return new DefaultLogoutSuccessHandler();
	}
	
	/**
	 * session配置
	 * @param sessionConfigurer
	 */
	protected void configurerSession(SessionManagementConfigurer<HttpSecurity> sessionConfigurer) {
		sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	/**
	 * security context配置
	 * @param contextConfigurer
	 */
	protected void configurerSecurityContext(SecurityContextConfigurer<HttpSecurity> contextConfigurer) {
		contextConfigurer.securityContextRepository(new RedisSecurityContextRepository(redisTemplate));
	}
	
	/**
	 * 匿名url配置
	 * @param registry
	 */
	private void configurerAnonymousUrlPattern(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
		Set<String> antPatterns = new HashSet<String>();
		antPatterns.add(getLoginUrl());
		configurerAnonymousUrlPattern(antPatterns);
		registry.antMatchers(antPatterns.toArray(new String[antPatterns.size()])).permitAll();
	}
	
	/**
	 * 匿名url配置(默认将loginurl配置可匿名访问，logouturl不需要配置(security默认logouturl允许匿名访问))
	 * @param antPatterns
	 */
	protected void configurerAnonymousUrlPattern(Set<String> antPatterns) {
		
	}
	
	private void configurerIgnoredRequests(IgnoredRequestConfigurer ignoredRequestConfigurer) {
		Set<String> antPatterns = new HashSet<String>();
		antPatterns.add("/**/favicon.ico");//spring boot ico
		configurerIgnoredStaticResources(antPatterns);
		ignoredRequestConfigurer.antMatchers(antPatterns.toArray(new String[antPatterns.size()]));
	}
	
	/**
	 * 配置要忽略的（可匿名访问的）静态资源
	 * @param antPatterns
	 */
	protected void configurerIgnoredStaticResources(Set<String> antPatterns) {
		
	}
	
	/**
	 * 配置登陆认证拦截器
	 * @param http
	 */
	protected void configurerAuthenticationFilter(HttpSecurity http) throws Exception {
		UsernamePasswordAuthenticationFilter loginFilter = new UsernamePasswordAuthenticationFilter();
		loginFilter.setUsernameParameter(getUsernameParameter());
		loginFilter.setPasswordParameter(getPasswordParameter());
		loginFilter.setRequiresAuthenticationRequestMatcher(DEFAULT_LOGIN_MATCHER);
		loginFilter.setAuthenticationManager(authenticationManagerBean());
		loginFilter.setAuthenticationDetailsSource(getAuthenticationDetailsSource());
		loginFilter.setAuthenticationSuccessHandler(getLoginSuccessHandler());
		loginFilter.setAuthenticationFailureHandler(getLoginFailureHandler());
		http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	/**
	 * 配置自定义filter
	 * @param http
	 */
	protected void configurerCustomFilter(HttpSecurity http) {
		
	}
	
	/**
	 * 登出配置
	 * @param logoutConfigurer
	 */
	private void configurerLogout(LogoutConfigurer<HttpSecurity> logoutConfigurer) {
		logoutConfigurer.logoutUrl(getLogoutUrl());
		LogoutHandler logoutHandler = getLogoutHandler();
		if (logoutHandler != null) {
			logoutConfigurer.addLogoutHandler(logoutHandler);
		}
		LogoutSuccessHandler logoutSuccessHandler = getLogoutSuccessHandler();
		if (logoutSuccessHandler != null) {
			logoutConfigurer.logoutSuccessHandler(logoutSuccessHandler);
		}
	}
	
	/**
	 * 除username password之外的一些额外参数detail source
	 * @return
	 */
	protected AuthenticationDetailsSource<HttpServletRequest, ?> getAuthenticationDetailsSource() {
		return new DefaultAuthenticationDetailsSource();
	}
	
	/**
	 * 异常处理配置
	 * @param exceptionConfigurer
	 */
	private void configurerExceptionHandler(ExceptionHandlingConfigurer<HttpSecurity> exceptionConfigurer) {
		exceptionConfigurer.accessDeniedHandler(getAccessDeniedHandler()).authenticationEntryPoint(getAuthenticationEntryPoint());//异常处理
	}
	
	/**
	 * 配置请求访问策略(未配置的任何请求)
	 * @param registry
	 */
	private void configurerAnyRequestAccessStrategy(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
		String role = getAnyRequestAccessRole();
		if (StringUtils.isNoneBlank(role)) {
			registry.anyRequest().hasRole(role);
		} else {
			registry.anyRequest().authenticated();
		}
	}
	
	protected DefaultAccessDeniedHandler getAccessDeniedHandler() {
		return accessDeniedHandler();
	}
	
	protected DefaultAuthenticationEntryPoint getAuthenticationEntryPoint() {
		return authenticationEntryPoint();
	}
	
	public abstract AbstractAuthenticationProvider<?> getAuthenticationProvider();
	
	protected DefaultAuthenticationSuccessHandler getLoginSuccessHandler() {
		return authenticationSuccessHandler();
	}
	
	protected DefaultAuthenticationFailureHandler getLoginFailureHandler() {
		return authenticationFailureHandler();
	}
	
	protected LogoutHandler getLogoutHandler() {
		return null;
	}
	
	protected DefaultLogoutSuccessHandler getLogoutSuccessHandler() {
		return logoutSuccessHandler();
	}
	
	protected String getLoginUrl() {
		return DEFAULT_LOGIN_URL;
	}
	
	protected String getLogoutUrl() {
		return DEFAULT_LOGOUT_URL;
	}
	
	protected String getUsernameParameter() {
		return DEFAULT_USERNAME_PARAMETER;
	}
	
	protected String getPasswordParameter() {
		return DEFAULT_PASSWORD_PARAMETER;
	}
	
	protected String getAnyRequestAccessRole() {
		return "USER";
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getAuthenticationProvider());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		configurerIgnoredRequests(web.ignoring());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		//configurer session management(SessionManagementFilter)
		configurerSession(http.sessionManagement());
		//configurer securityContext(SecurityContextPersistenceFilter)
		configurerSecurityContext(http.securityContext());
		//configurer AuthenticationFilter
		configurerAuthenticationFilter(http);
		//configure custom filters
		configurerCustomFilter(http);
		//configure logout
		configurerLogout(http.logout());
		//configurer exception handler
		configurerExceptionHandler(http.exceptionHandling());
		//configure anonymous url
		configurerAnonymousUrlPattern(http.authorizeRequests());
		//兜底访问限制策略
		configurerAnyRequestAccessStrategy(http.authorizeRequests());
		//使用java web token认证，所以不需要csrf
		http.csrf().disable();
		//禁用缓存
		http.headers().cacheControl();
	}
	
	@Autowired
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

}
