package org.trafficpolice.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.enumeration.NoticeType;
import org.trafficpolice.po.Notice;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月12日 下午5:45:29
 */
@Repository(NoticeDao.BEAN_ID)
public interface NoticeDao {

	public static final String BEAN_ID = "noticeDao";
	
	public Integer doInsert(Notice notice);
	
	public Integer doUpdate(Notice notice);
	
	public Notice findById(@Param("id")Long id);
	
	public Notice findByType(@Param("noticeType")NoticeType noticeType);
	
}
