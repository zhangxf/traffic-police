package org.trafficpolice.service;

import org.trafficpolice.enumeration.NoticeType;
import org.trafficpolice.po.Notice;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月12日 下午6:04:51
 */
public interface NoticeService {

	public static final String BEAN_ID = "noticeService";
	
	public void saveOrUpdateNotice(Notice notice);
	
	public Notice findByType(NoticeType noticeType);
	
}
