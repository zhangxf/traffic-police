package org.trafficpolice.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
import org.trafficpolice.dto.RoleAuthorityDTO;
import org.trafficpolice.dto.UserAuthorityDTO;
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
	
	private static final String AUTH_MAPPING_CACHE_KEY = CacheNamespace.TRAFFIC_POLICE + CacheNamespace.SEPARATOR + "security_metadata_source" + CacheNamespace.SEPARATOR + "auth_mapping";
	
	private static final String AUTH_MAPPING_SIGN_CACHE_KEY = CacheNamespace.TRAFFIC_POLICE + CacheNamespace.SEPARATOR + "security_metadata_source" + CacheNamespace.SEPARATOR + "auth_mapping_sign";
	
	private String authMappingSign;
	
	private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
	
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
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Map<RequestMatcher, Collection<ConfigAttribute>> requestMapping = this.getRequestMap();
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMapping.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}
		return allAttributes;
	}

	private Map<RequestMatcher, Collection<ConfigAttribute>> getRequestMap() {
		String authMappingSignCache = (String)redisTemplate.opsForValue().get(AUTH_MAPPING_SIGN_CACHE_KEY);
		if (StringUtils.isNoneBlank(authMappingSignCache) && authMappingSignCache.equals(this.authMappingSign)) {
			return this.requestMap;
		}
		Map<RequestMatcher, Collection<ConfigAttribute>> requestMapping = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
		Map<String, Set<String>> authMapping = this.getAuthMapping(false);
		if (MapUtils.isEmpty(authMapping)) {
			requestMapping.put(AnyRequestMatcher.INSTANCE, Arrays.asList(new SecurityConfig("ROLE_" + ServiceConsts.SUPER_ADMIN_ROLE)));
			this.requestMap = requestMapping;
			this.authMappingSign = (String)redisTemplate.opsForValue().get(AUTH_MAPPING_SIGN_CACHE_KEY);
			return this.requestMap;
		}
		for (Map.Entry<String, Set<String>> entry : authMapping.entrySet()) {
			String path = entry.getKey();
			RequestMatcher matcher = new AntPathRequestMatcher(path);
			Collection<ConfigAttribute> collectionConfig = requestMapping.get(matcher);
			if (collectionConfig == null) {
				requestMapping.put(matcher, new ArrayList<ConfigAttribute>());
			}
			Set<String> codeSet = entry.getValue();
			for (String code : codeSet) {
				requestMapping.get(matcher).add(new SecurityConfig("ROLE_" + code));
			}
			requestMapping.get(matcher).add(new SecurityConfig("ROLE_" + ServiceConsts.SUPER_ADMIN_ROLE));//超管拥有所有权限
		}
		requestMapping.put(AnyRequestMatcher.INSTANCE, Arrays.asList(new SecurityConfig("ROLE_" + ServiceConsts.SUPER_ADMIN_ROLE)));
		this.requestMap = requestMapping;
		this.authMappingSign = (String)redisTemplate.opsForValue().get(AUTH_MAPPING_SIGN_CACHE_KEY);
		return this.requestMap;
	}
	
	public void refreshAuthMapping() {
		this.getAuthMapping(true);
	}
	
	private Map<String, Set<String>> getAuthMapping(boolean forceRefresh) {
		Map<String, Set<String>> authMapping = null;
		if (!forceRefresh) {
			authMapping = (Map<String, Set<String>>)redisTemplate.opsForValue().get(AUTH_MAPPING_CACHE_KEY);
			if (MapUtils.isNotEmpty(authMapping)) {
				return authMapping;
			}
		}
		authMapping = new LinkedHashMap<String, Set<String>>();
		//角色权限
		List<RoleAuthorityDTO> roleAuthorities = roleService.queryAllRoleAuthorities();
		if (CollectionUtils.isNotEmpty(roleAuthorities)) {
			for (RoleAuthorityDTO roleAuth : roleAuthorities) {
				String action = roleAuth.getAction();
				String code = ServiceConsts.ROLE_CODE_PREFIX + roleAuth.getRoleId();
				this.add2AuthMapping(action, code, authMapping);
			}
		}
		//用户权限
		List<UserAuthorityDTO> userAuthorities = userService.queryAllUserAuthorities();
		if (CollectionUtils.isNotEmpty(userAuthorities)) {
			for (UserAuthorityDTO ua : userAuthorities) {
				String action = ua.getAction();
				String code = ServiceConsts.USER_CODE_PREFIX + ua.getUserId();
				this.add2AuthMapping(action, code, authMapping);
			}
		}
		//security-ignore.properties配置文件匿名访问配置
		Map<String, String> patterns = securityIgnore.getPattern();
		if (MapUtils.isNotEmpty(patterns)) {
			Iterator<String> it = patterns.values().iterator();
			while (it.hasNext()) {
				String value = it.next();
				if (value.contains(",")) {
					String[] pts = value.split(",");
					for (String pt : pts) {
						this.add2AuthMapping(pt, "ANONYMOUS", authMapping);
					}
				} else {
					this.add2AuthMapping(value, "ANONYMOUS", authMapping);
				}
			}
		}
		if (MapUtils.isNotEmpty(authMapping)) {
			redisTemplate.opsForValue().set(AUTH_MAPPING_CACHE_KEY, authMapping);
		}
		redisTemplate.opsForValue().set(AUTH_MAPPING_SIGN_CACHE_KEY, UUID.randomUUID().toString());
		return authMapping;
	}
	
	private void add2AuthMapping(String action, String code, Map<String, Set<String>> authMapping) {
		if (!authMapping.containsKey(action)) {
			authMapping.put(action, new HashSet<String>());
		}
		authMapping.get(action).add(code);
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
