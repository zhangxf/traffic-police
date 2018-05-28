package org.trafficpolice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangxiaofei
 * @createdOn 2018年5月28日 下午9:19:48
 */
@RestController
public class TestController {

	@GetMapping("/test/auth")
	public Map<String, Object> testAuth() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "0x0000");
		map.put("message", "success");
		return map;
	}
	
}
