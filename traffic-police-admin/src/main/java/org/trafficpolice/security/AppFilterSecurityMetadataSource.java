package org.trafficpolice.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.trafficpolice.commons.cache.CacheNamespace;
import org.trafficpolice.consts.ServiceConsts;
import org.trafficpolice.po.Authority;
import org.trafficpolice.po.Role;
import org.trafficpolice.po.RoleAuthority;
import org.trafficpolice.po.UserAuthority;
import org.trafficpolice.properties.SecurityIgnoreProperties;
import org.trafficpolice.service.AuthorityService;
import org.trafficpolice.service.BGUserService;
import org.trafficpolice.service.RoleService;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月11日 上午10:12:26
 */
@Component(AppFilterSecurityMetadataSource.BEAN_ID)
public class AppFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	public static final String BEAN_ID = "filterSecurityMetadataSource";
	
	private static final String SECURITY_METADATA_SOURCE_EXPIRE_FLAG = CacheNamespace.TRAFFIC_POLICE + CacheNamespace.SEPARATOR + "security_metadata_source" + CacheNamespace.SEPARATOR + "expire_flag";
	
	private static final long METADATA_EXPIRE_FLAG_MINUTES = 24 * 60;//失效时间(分钟)
	
	private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;
	
	@Autowired
	private SecurityIgnoreProperties securityIgnore;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	@Qualifier(RoleService.BEAN_ID)
	private RoleService roleService;
	
	@Autowired
	@Qualifier(BGUserService.BEAN_ID)
	private BGUserService userService;
	
	@Autowired
	@Qualifier(AuthorityService.BEAN_ID)
	private AuthorityService authorityService;
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		Map<RequestMatcher, Collection<ConfigAttribute>> requestMapping = this.getRequestMap();
		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMapping.entrySet()) {
			if (entry.getKey().matches(request)) {
				return entry.getValue();
			}
		}
		return null;
//		FilterInvocation fi = (FilterInvocation) object;
//		String url = fi.getRequestUrl();
//		String httpMethod = fi.getRequest().getMethod();
//		List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>();
//		attributes.add(new SecurityConfig("ROLE_AUTH_CUSTOM_1"));
//		// Lookup your database (or other source) using this information and populate the
//		// list of attributes
//
//		return attributes;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Map<RequestMatcher, Collection<ConfigAttribute>> requestMapping = this.getRequestMap();
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMapping.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}
		return allAttributes;
//		List<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
//		list.add(new SecurityConfig("AUTH_CUSTOM"));
//		list.add(new SecurityConfig("AUTH_CUSTOM_1"));
//		list.add(new SecurityConfig("ROLE_AUTH_CUSTOM_1"));
//		return list;
	}

	private Map<RequestMatcher, Collection<ConfigAttribute>> getRequestMap() {
		String expireFlag = (String)redisTemplate.opsForValue().get(SECURITY_METADATA_SOURCE_EXPIRE_FLAG);
		if (StringUtils.isNoneBlank(expireFlag)) {
			return requestMap;
		}
		requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
		List<Authority> authorities = authorityService.queryAllLeafAuthorities();
		Map<Long, Authority> authorityMap = new HashMap<Long, Authority>();
		if (CollectionUtils.isNotEmpty(authorities)) {
			for (Authority au : authorities) {
				authorityMap.put(au.getId(), au);
			}
		}
		List<Role> roleList = roleService.queryAllRoles();
		Map<Long, Role> roleMap = new HashMap<Long, Role>();
		if (CollectionUtils.isNotEmpty(roleList)) {
			for (Role role : roleList) {
				roleMap.put(role.getId(), role);
			}
		}
		List<RoleAuthority> roleAuthorities = roleService.queryAllRoleAuthorities();
		if (CollectionUtils.isNotEmpty(roleAuthorities)) {
			for (RoleAuthority roleAuth : roleAuthorities) {
				Long authorityId = roleAuth.getAuthorityId();
				Authority authority = authorityMap.get(authorityId);
				RequestMatcher matcher = new AntPathRequestMatcher(authority.getAction());
				Collection<ConfigAttribute> collectionConfig = requestMap.get(matcher);
				if (collectionConfig == null) {
					requestMap.put(matcher, new ArrayList<ConfigAttribute>());
				}
				Long roleId = roleAuth.getRoleId();
				Role role = roleMap.get(roleId);
				requestMap.get(matcher).add(new SecurityConfig("ROLE_" + role.getCode()));
			}
		}
		List<UserAuthority> userAuthorities = userService.queryAllUserAuthorities();
		if (CollectionUtils.isNotEmpty(userAuthorities)) {
			for (UserAuthority ua : userAuthorities) {
				Authority authority = authorityMap.get(ua.getAuthorityId());
				RequestMatcher matcher = new AntPathRequestMatcher(authority.getAction());
				Collection<ConfigAttribute> collectionConfig = requestMap.get(matcher);
				if (collectionConfig == null) {
					requestMap.put(matcher, new ArrayList<ConfigAttribute>());
				}
				requestMap.get(matcher).add(new SecurityConfig("ROLE_" + authority.getId()));
			}
		}
		//security-ignore.properties配置文件匿名访问配置处理
//		Map<String, String> patterns = securityIgnore.getPattern();
//		if (MapUtils.isEmpty(patterns)) {
//			requestMap.put(AnyRequestMatcher.INSTANCE, Arrays.asList(new SecurityConfig("ROLE_" + ServiceConsts.SUPER_ADMIN_ROLE)));
//			return requestMap;
//		}
//		Iterator<String> it = patterns.values().iterator();
//		while (it.hasNext()) {
//			String value = it.next();
//			if (value.contains(",")) {
//				String[] pts = value.split(",");
//				for (String pt : pts) {
//					RequestMatcher matcher = new AntPathRequestMatcher(pt);
//					Collection<ConfigAttribute> collectionConfig = requestMap.get(matcher);
//					if (collectionConfig == null) {
//						requestMap.put(matcher, new ArrayList<ConfigAttribute>());
//					}
//					requestMap.get(matcher).put(new AntPathRequestMatcher(pt), value);
//				}
//				antPatterns.addAll(Arrays.asList(value.split(",")));
//			} else {
//				antPatterns.add(value);
//			}
//		}
		requestMap.put(AnyRequestMatcher.INSTANCE, Arrays.asList(new SecurityConfig("ROLE_" + ServiceConsts.SUPER_ADMIN_ROLE)));
		return requestMap;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
