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
	
}
