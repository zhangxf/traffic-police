package org.trafficpolice.consts;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author zhangxiaofei
 * @createdOn 2018年5月30日 下午1:08:35
 */
public class ApplicationConsts {
	
	public static final ResourceBundle bundle = PropertyResourceBundle.getBundle("config/application-config");

	public static final Locale DEFAULT_LOCALE = Locale.CHINA;
	
	public static final String REQUEST_ID = "requestId";
	
	public static final String IP = "ip";
	
	public static final String UNIQUE_ID = "uniqueId";
	
	public static final String LOG_SEPARATOR = ":";
	
	public static final String FILE_UPLOAD_DIR = bundle.getString("file.upload.dir");
	
}
