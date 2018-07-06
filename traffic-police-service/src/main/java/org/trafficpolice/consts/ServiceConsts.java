package org.trafficpolice.consts;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author zhangxiaofei
 * 2018年7月2日上午12:20:55
 */
public class ServiceConsts {

	public static final ResourceBundle bundle = PropertyResourceBundle.getBundle("config/service");
	
	public static final String VERIFYCODE_SEND_MOCK = bundle.getString("verifycode.send.mock");
	
	/**
	 * 手机号正则
	 */
	public static final String REGEX_PHONE = "^[1][3,4,5,6,7,8,9][0-9]{9}$";
	
	/**
     * 身份证正则
     */
    public static final String REGEX_ID_CARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$";
	
}
