package org.trafficpolice.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.trafficpolice.commons.cache.CacheNamespace;
import org.trafficpolice.po.Authority;
import org.trafficpolice.po.Role;
import org.trafficpolice.properties.SecurityIgnoreProperties;
import org.trafficpolice.service.AuthorityService;
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
		List<Role> roleList = roleService.queryAllRoles();
		List<Authority> authorities = authorityService.queryAll();
		
		//security-ignore.properties配置文件匿名访问配置处理
//		Map<String, String> patterns = securityIgnore.getPattern();
//		if (MapUtils.isEmpty(patterns)) {
//			return requestMap;
//		}
//		Iterator<String> it = patterns.values().iterator();
//		while (it.hasNext()) {
//			String value = it.next();
//			if (value.contains(",")) {
//				String[] pts = value.split(",");
//				for (String pt : pts) {
//					requestMap.put(new AntPathRequestMatcher(pt), value);
//				}
//				antPatterns.addAll(Arrays.asList(value.split(",")));
//			} else {
//				antPatterns.add(value);
//			}
//		}
		return requestMap;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
