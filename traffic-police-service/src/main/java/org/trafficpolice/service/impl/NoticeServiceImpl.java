package org.trafficpolice.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.dao.NoticeDao;
import org.trafficpolice.enumeration.NoticeType;
import org.trafficpolice.po.Notice;
import org.trafficpolice.service.NoticeService;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月12日 下午6:06:45
 */
@Service(NoticeService.BEAN_ID)
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	@Qualifier(NoticeDao.BEAN_ID)
	private NoticeDao noticeDao;

	@Override
	@Transactional
	public void saveOrUpdateNotice(Notice notice) {
		NoticeType noticeType = notice.getNoticeType();
		if (noticeType == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "noticeType");
		}
		Notice existNotice = noticeDao.findByType(noticeType);
		if (existNotice != null) {
			existNotice.setTitle(notice.getTitle());
			existNotice.setContent(notice.getContent());
			existNotice.setUpdateTime(new Date());
			noticeDao.doUpdate(existNotice);
		} else {
			notice.setCreateTime(new Date());
			noticeDao.doInsert(notice);
		}
	}

	@Override
	@Transactional
	public Notice findByType(NoticeType noticeType) {
		return noticeDao.findByType(noticeType);
	}

}
