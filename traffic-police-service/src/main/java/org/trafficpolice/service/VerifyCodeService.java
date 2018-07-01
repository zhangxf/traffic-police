package org.trafficpolice.service;

import org.trafficpolice.dto.VerifyCodeDTO;

/**
 * 验证码
 * @author zhangxiaofei
 * 2018年7月1日下午6:06:54
 */
public interface VerifyCodeService {

	public static final String BEAN_ID = "verifyCodeService";
	
	/**
	 * 发送验证码
	 * @param type 验证码类型 如：register, login, changepwd......
	 * @param phone 手机号
	 * @return
	 */
	public VerifyCodeDTO sendVerifyCode(String type, String phone);
	
	/**
	 * 校验验证码
	 * @param verifyCodeDTO
	 * @return
	 */
	public boolean checkVerifyCode(VerifyCodeDTO verifyCodeDTO);
	
	/**
	 * 清楚验证码
	 * @param verifyCodeDTO
	 */
	public void clearVerifyCodeCache(VerifyCodeDTO verifyCodeDTO);
	
}
