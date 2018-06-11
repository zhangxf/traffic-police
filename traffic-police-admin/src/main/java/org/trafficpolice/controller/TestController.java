package org.trafficpolice.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.cache.CacheNamespace;

/**
 * @author zhangxiaofei
 * @createdOn 2018年5月28日 下午9:19:48
 */
@RestController
public class TestController {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@GetMapping("/test/auth")
	public Map<String, Object> testAuth() {
		Map<String, Object> map = new HashMap<String, Object>();
		redisTemplate.opsForValue().set(CacheNamespace.TRAFFIC_POLICE + CacheNamespace.SEPARATOR + "test", "你好", 300, TimeUnit.SECONDS);
		map.put("status", "0x0000");
		map.put("message", "success");
		return map;
	}
	
	@GetMapping(name = "/test/auth", produces = MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
	public Map<String, Object> testHtmlAuth() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "0x0000");
		map.put("message", "success");
		return map;
	}
	
}
