package org.trafficpolice.security.authentication;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.trafficpolice.commons.cache.CacheNamespace;
import org.trafficpolice.commons.web.IPUtils;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2017年12月20日 上午10:25:57
 */
public class RedisSecurityContextRepository implements SecurityContextRepository {

	private static final Logger logger = LoggerFactory.getLogger(RedisSecurityContextRepository.class);
	
	public static final String SESSION_KEY = "authKey";
	
	public static final String SESSION_CACHE_PREFIX = CacheNamespace.TRAFFIC_POLICE + CacheNamespace.SEPARATOR + "sessions" + CacheNamespace.SEPARATOR;
	
	private static final String SPRING_SECURITY_CONTEXT = "spring_security_context";
	
	public static final int SESSION_EXPIRE_MINUTES = 30;
	
	private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
	
	private RedisTemplate<String, Object> redisTemplate;
	
	public RedisSecurityContextRepository(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public static String getSessionKey(HttpServletRequest request) {
		String sessionKey = (String)request.getAttribute(SESSION_KEY);
		if (StringUtils.isBlank(sessionKey)) {
			sessionKey = request.getHeader(SESSION_KEY);
		}
		return sessionKey;
	}

	@Override
	public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
		HttpServletRequest request = requestResponseHolder.getRequest();
//		HttpServletResponse response = requestResponseHolder.getResponse();
		String sessionKey = getSessionKey(request);
		SecurityContext context = readSecurityContextFromRedis(sessionKey);
		if (context == null) {
			logger.debug("####【No SecurityContext was available from the redis sessionkey: {} A new one will be created.】####", sessionKey);
			context = generateNewContext();
		}
		return context;
	}

	@Override
	public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
		String sessionKey = getSessionKey(request);
		if (StringUtils.isBlank(sessionKey)) {
			logger.debug("####【Can not save SecurityContext, because sessionKey is blank】####");
			return;
		}
		final Authentication authentication = context.getAuthentication();
		if (authentication == null || trustResolver.isAnonymous(authentication)) {
			logger.debug("####【SecurityContext is empty or contents are anonymous - context will not be stored in redis.】####");
			redisTemplate.delete(SESSION_CACHE_PREFIX + sessionKey);
			return;
		}
		BoundHashOperations<String, String, Object> boundHashOps = redisTemplate.boundHashOps(SESSION_CACHE_PREFIX + sessionKey);
		boundHashOps.put(SPRING_SECURITY_CONTEXT, context);
		boundHashOps.put("ip", IPUtils.getIpAddress(request));
		boundHashOps.expire(SESSION_EXPIRE_MINUTES, TimeUnit.MINUTES);
	}

	@Override
	public boolean containsContext(HttpServletRequest request) {
		String sessionKey = getSessionKey(request);
		if (StringUtils.isBlank(sessionKey)) {
			return false;
		}
		return readSecurityContextFromRedis(sessionKey) != null;
	}

	private SecurityContext generateNewContext() {
		return SecurityContextHolder.createEmptyContext();
	}
	
	private SecurityContext readSecurityContextFromRedis(String sessionKey) {
		if (StringUtils.isBlank(sessionKey)) {
			logger.debug("####【sessionKey is blank】####");
			return null;
		}
		BoundHashOperations<String, String, Object> boundHashOps = redisTemplate.boundHashOps(SESSION_CACHE_PREFIX + sessionKey);
		Object contextFromRedis = boundHashOps.get(SPRING_SECURITY_CONTEXT);
		if (contextFromRedis == null) {
			logger.debug("####【spring security context is null】####");
			return null;
		}
		if (!(contextFromRedis instanceof SecurityContext)) {
			logger.debug("####【sessionKey:{} did not contain a SecurityContext but contained:{}】####", sessionKey, contextFromRedis);
			return null;
		}
		return (SecurityContext) contextFromRedis;
	}
	
}
