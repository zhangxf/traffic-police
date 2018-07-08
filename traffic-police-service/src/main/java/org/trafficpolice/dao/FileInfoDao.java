package org.trafficpolice.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.po.FileInfo;

/**
 * @author zhangxiaofei
 * 2018年7月1日下午4:07:32
 */
@Repository(FileInfoDao.BEAN_ID)
public interface FileInfoDao {

	public static final String BEAN_ID = "fileInfoDao";
	
	public Integer doInsert(FileInfo fileInfo);
	
	public FileInfo findByToken(@Param("token")String token);
	
	public Integer deleteByToken(@Param("token")String token);
	
}
