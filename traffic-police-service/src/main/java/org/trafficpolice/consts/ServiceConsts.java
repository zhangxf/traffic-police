package org.trafficpolice.consts;

import java.util.Date;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.lang3.time.DateUtils;
import org.trafficpolice.po.BGUser;

/**
 * @author zhangxiaofei
 * 2018年7月2日上午12:20:55
 */
public class ServiceConsts {

	public static final ResourceBundle bundle = PropertyResourceBundle.getBundle("config/service");
	
	public static final BGUser SUPER_ADMIN_USER = new BGUser();
	
	public static final String SUPER_ADMIN_ROLE = "SUPER_ADMIN";
	
	public static final String ROLE_CODE_PREFIX = "R_";
	
	public static final String USER_CODE_PREFIX = "U_";
	
	static {
		SUPER_ADMIN_USER.setUsername(bundle.getString("superadmin.username"));
		SUPER_ADMIN_USER.setPassword(bundle.getString("superadmin.password"));
		SUPER_ADMIN_USER.setEmail("superadmin@163.com");
		SUPER_ADMIN_USER.setIsEnable(true);
		SUPER_ADMIN_USER.setRealname("超级管理员");
		SUPER_ADMIN_USER.setTelephone("18900000077");
		Date createDate = null;
		try {
			createDate = DateUtils.parseDate("2018-07-12 19:07:11", "yyyy-MM-dd hh:mm:ss");
		} catch (Exception e) {
			//ignore
		}
		SUPER_ADMIN_USER.setCreateTime(createDate);
		SUPER_ADMIN_USER.setUpdateTime(createDate);
	}
	
	public static final String VERIFYCODE_SEND_MOCK = bundle.getString("verifycode.send.mock");
	
	/**
	 * 文件服务器地址
	 */
	public static final String NFS_ADDRESS = bundle.getString("nfs.address");
	
	/**
	 * 手机号正则
	 */
	public static final String REGEX_PHONE = "^[1][3,4,5,6,7,8,9][0-9]{9}$";
	
	/**
     * 身份证正则
     */
    public static final String REGEX_ID_CARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$";
	
}
