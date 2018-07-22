package org.trafficpolice.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.dao.VideoConfigDao;
import org.trafficpolice.dto.VideoConfigDTO;
import org.trafficpolice.po.VideoConfig;
import org.trafficpolice.service.VideoConfigService;

/**
 * @author zhangxiaofei
 * 2018年7月23日上午2:13:33
 */
@Service(VideoConfigService.BEAN_ID)
public class VideoConfigServiceImpl implements VideoConfigService {

	@Autowired
	@Qualifier(VideoConfigDao.BEAN_ID)
	private VideoConfigDao videoConfigDao;
	
	@Override
	@Transactional
	public void settingVideoConfig(List<VideoConfigDTO> videoConfigList) {
		for (VideoConfigDTO configDTO : videoConfigList) {
			VideoConfig po = new VideoConfig();
			po.setId(configDTO.getId());
			po.setCategoryId(configDTO.getCategoryId());
			po.setLearnNum(configDTO.getLearnNum());
			po.setCreateTime(new Date());
			if (po.getId() != null) {
				po.setUpdateTime(new Date());
				videoConfigDao.doUpdate(po);
			} else {
				videoConfigDao.doInsert(po);
			}
		}
	}

	@Override
	@Transactional
	public List<VideoConfigDTO> findVideoConfig() {
		return videoConfigDao.findVideoConfig();
	}

}
