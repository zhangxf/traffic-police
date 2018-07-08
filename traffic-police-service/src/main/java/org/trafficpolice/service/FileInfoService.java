package org.trafficpolice.service;

import org.trafficpolice.po.FileInfo;

/**
 * @author zhangxiaofei
 * 2018年7月1日下午3:39:18
 */
public interface FileInfoService {

	public static final String BEAN_ID = "fileUploadService";
	
	/**
	 * 根据token获取上传文件上传信息
	 * @param token
	 * @return
	 */
	public FileInfo queryByToken(String token);
	
	public void deleteByToken(String token);
	
}
