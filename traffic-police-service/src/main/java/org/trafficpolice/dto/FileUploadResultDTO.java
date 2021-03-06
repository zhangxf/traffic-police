package org.trafficpolice.dto;

/**
 * 文件上传结果
 * @author zhangxiaofei
 * 2018年7月9日上午12:02:10
 */
public class FileUploadResultDTO {

	/**
	 * 文件唯一标识
	 */
	private String token;

	private String url;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
