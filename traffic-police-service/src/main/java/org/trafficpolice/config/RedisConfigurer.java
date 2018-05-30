package org.trafficpolice.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.DefaultRedisCachePrefix;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.trafficpolice.commons.cache.CacheNamespace;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfigurer {
	
	@Bean
	public CacheManagerCustomizer<RedisCacheManager> cacheManagerCustomizer() {
	    return new CacheManagerCustomizer<RedisCacheManager>() {
	        @Override
	        public void customize(RedisCacheManager cacheManager) {
	            cacheManager.setUsePrefix(true);
	            cacheManager.setCachePrefix(new DefaultRedisCachePrefix());
	            Map<String, Long> expires = new HashMap<String, Long>();
	            expires.put(CacheNamespace.TRAFFIC_POLICE + CacheNamespace.SEPARATOR + "springcache", 10 * 60L);
	            cacheManager.setExpires(expires);
	            cacheManager.setDefaultExpiration(5 * 60L);
	        }
	    };
	}
	
	@Bean
	<T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, T> template = new RedisTemplate<String, T>();
		template.setConnectionFactory(factory);
		template.setStringSerializer(new StringRedisSerializer());
		template.setEnableDefaultSerializer(true);
		template.setDefaultSerializer(new GenericFastJsonRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new JdkSerializationRedisSerializer());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericFastJsonRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}
	
}