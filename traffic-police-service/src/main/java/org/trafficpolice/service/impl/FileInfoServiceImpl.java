package org.trafficpolice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.dao.FileInfoDao;
import org.trafficpolice.po.FileInfo;
import org.trafficpolice.service.FileInfoService;

/**
 * @author zhangxiaofei
 * 2018年7月1日下午4:42:04
 */
@Service(FileInfoService.BEAN_ID)
public class FileInfoServiceImpl implements FileInfoService {

	@Autowired
	@Qualifier(FileInfoDao.BEAN_ID)
	private FileInfoDao fileInfoDao;

	@Override
	@Transactional
	public void saveFileInfo(FileInfo fileInfo) {
		fileInfoDao.doInsert(fileInfo);
	}

	@Override
	@Transactional
	public FileInfo queryByToken(String token) {
		return fileInfoDao.findByToken(token);
	}
	
	@Transactional
	public void deleteByToken(String token) {
		fileInfoDao.deleteByToken(token);
	}
	
}
