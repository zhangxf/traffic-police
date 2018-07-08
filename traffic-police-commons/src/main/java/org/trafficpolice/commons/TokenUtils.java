package org.trafficpolice.commons;

import java.util.UUID;

import org.springframework.util.Base64Utils;

/**
 * @author zhangxiaofei
 * 2018年7月8日上午11:59:39
 */
public class TokenUtils {

	public static String generateToken() {
		return Base64Utils.encodeToString(UUID.randomUUID().toString().getBytes());
	}
	
}
