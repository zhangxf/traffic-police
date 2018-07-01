package org.trafficpolice.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.trafficpolice.commons.cache.CacheNamespace;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.consts.ServiceConsts;
import org.trafficpolice.dto.VerifyCodeDTO;
import org.trafficpolice.exception.VerifyCodeExceptionEnum;
import org.trafficpolice.service.VerifyCodeService;

import com.alibaba.fastjson.JSON;

/**
 * @author zhangxiaofei
 * 2018年7月1日下午6:13:43
 */
@Service(VerifyCodeService.BEAN_ID)
public class VerifyCodeServiceImpl implements VerifyCodeService {

	private static final Logger logger = LoggerFactory.getLogger(VerifyCodeServiceImpl.class);
	
	private static final String VERIFY_CODE_CACHE = CacheNamespace.TRAFFIC_POLICE + CacheNamespace.SEPARATOR + "verifycode" + CacheNamespace.SEPARATOR;
	
	private static final long CACHE_DURATION = 5 * 60;//缓存时长5分钟
	
	public static final Set<String> verifyCodeTypes = new HashSet<String>();
	
	static {
		String[] verifyCodeTypeArray = new String[]{
				"register"
		};
		verifyCodeTypes.addAll(Arrays.asList(verifyCodeTypeArray));
	}
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public VerifyCodeDTO sendVerifyCode(String type, String phone) {
		if (!verifyCodeTypes.contains(type)) {
			logger.info("####【发送验证码】【type不正确】####");
			throw new BizException(VerifyCodeExceptionEnum.SEND_TYPE_INCORRECT);
		}
		if (StringUtils.isBlank(phone)) {
			logger.info("####【发送验证码】【手机号为空】####");
			throw new BizException(VerifyCodeExceptionEnum.MISS_PHONE);
		}
		VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO();
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', '9').build();
		String code = generator.generate(6);
		if ("n".equals(ServiceConsts.VERIFYCODE_SEND_MOCK)) {//真实模式发送
			//调用短信服务商接口发送短信
		} else {
			verifyCodeDTO.setCode(code);
		}
		verifyCodeDTO.setType(type);
		verifyCodeDTO.setPhone(phone);
		String token = Base64Utils.encodeToString(UUID.randomUUID().toString().getBytes());
		verifyCodeDTO.setToken(token);
		logger.debug("####【发送验证码】--> {}####", JSON.toJSONString(verifyCodeDTO));
		redisTemplate.opsForValue().set(VERIFY_CODE_CACHE + type + CacheNamespace.SEPARATOR + token, verifyCodeDTO, CACHE_DURATION, TimeUnit.SECONDS);
		return verifyCodeDTO;
	}

	@Override
	public boolean checkVerifyCode(VerifyCodeDTO verifyCodeDTO) {
		String type = verifyCodeDTO.getType();
		if (StringUtils.isBlank(type)) {
			logger.info("####【校验验证码】【type为空】####");
			return false;
		}
		String token = verifyCodeDTO.getToken();
		if (StringUtils.isBlank(token)) {
			logger.info("####【校验验证码】【token为空】####");
			return false;
		}
		VerifyCodeDTO cacheVerifyCodeDTO = (VerifyCodeDTO)redisTemplate.opsForValue().get(VERIFY_CODE_CACHE + type + CacheNamespace.SEPARATOR + token);
		if (cacheVerifyCodeDTO == null) {
			logger.info("####【校验验证码】【token不正确】--> {}####", token);
			return false;
		}
		String phone = verifyCodeDTO.getPhone();
		String cachePhone = cacheVerifyCodeDTO.getPhone();
		if (!cachePhone.equals(phone)) {
			logger.info("####【校验验证码】【手机号不匹配】phone --> {}, cachePhone --> {}####", phone, cachePhone);
			return false;
		}
		String code = verifyCodeDTO.getCode();
		String cacheCode = cacheVerifyCodeDTO.getCode();
		if (!cacheCode.equals(code)) {
			logger.info("####【校验验证码】【验证码不正确】code --> {}, cacheCode --> {}####", code, cacheCode);
			return false;
		}
		return true;
	}

	@Override
	public void clearVerifyCodeCache(VerifyCodeDTO verifyCodeDTO) {
		String type = verifyCodeDTO.getType();
		String token = verifyCodeDTO.getToken();
		if (StringUtils.isNoneBlank(type) && StringUtils.isNoneBlank(token)) {
			redisTemplate.delete(VERIFY_CODE_CACHE + type + CacheNamespace.SEPARATOR + token);
		}
	}

}
