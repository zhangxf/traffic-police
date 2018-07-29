package org.trafficpolice.po;

import java.io.Serializable;
import java.util.Date;

import org.trafficpolice.enumeration.NoticeType;

/**
 * 公告
 * @author zhangxiaofei
 * 2018年7月29日下午2:58:57
 */
public class Notice implements Serializable {

	private static final long serialVersionUID = -3113979840571192380L;

	private Long id;
	
	/**
	 * 公告标题
	 */
	private String title;
	
	/**
	 * 公告内容
	 */
	private String content;
	
	/**
	 * 公告类型
	 */
	private NoticeType noticeType;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public NoticeType getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(NoticeType noticeType) {
		this.noticeType = noticeType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
