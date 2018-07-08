package org.trafficpolice.po;

import java.util.Date;

/**
 * 上传的文件信息
 * @author zhangxiaofei
 * 2018年7月1日下午3:55:09
 */
public class FileInfo {

	/**
	 * 文件唯一标识
	 */
	private String token;
	
	/**
	 * 原文件名字
	 */
	private String originName;
	
	/**
	 * 上传后的目标文件名字
	 */
	private String destName;
	
	/**
	 * 上传后的目标文件相对路径
	 */
	private String url;
	
	/**
	 * 文件类型
	 * image: 图片
	 * video: 视频
	 */
	private String fileType;
	
	/**
	 * 时长:秒
	 */
	private Long duration;
	
	/**
	 * 文件大小B
	 */
	private Long fileSize;
	
	/**
	 * 上传时间
	 */
	private Date createTime;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getDestName() {
		return destName;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
