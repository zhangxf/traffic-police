package org.trafficpolice.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.consts.ServiceConsts;
import org.trafficpolice.dao.CategoryDao;
import org.trafficpolice.dao.EduRecordDao;
import org.trafficpolice.dao.VideoDao;
import org.trafficpolice.dao.VideoRecordDao;
import org.trafficpolice.dto.VideoDTO;
import org.trafficpolice.dto.VideoLearnInfo;
import org.trafficpolice.dto.VideoQueryParamDTO;
import org.trafficpolice.enumeration.EduType;
import org.trafficpolice.exception.CategoryExceptionEnum;
import org.trafficpolice.exception.VideoExceptionEnum;
import org.trafficpolice.po.Category;
import org.trafficpolice.po.EduRecord;
import org.trafficpolice.po.FileInfo;
import org.trafficpolice.po.Video;
import org.trafficpolice.po.VideoRecord;
import org.trafficpolice.service.FileInfoService;
import org.trafficpolice.service.VideoService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * 2018年7月21日上午12:50:39
 */
@Service(VideoService.BEAN_ID)
public class VideoServiceImpl implements VideoService {

	@Autowired
	@Qualifier(VideoDao.BEAN_ID)
	private VideoDao videoDao;

	@Autowired
	@Qualifier(CategoryDao.BEAN_ID)
	private CategoryDao categoryDao;
	
	@Autowired
	@Qualifier(FileInfoService.BEAN_ID)
	private FileInfoService fileInfoService;
	
	@Autowired
	@Qualifier(VideoRecordDao.BEAN_ID)
	private VideoRecordDao videoRecordDao;
	
	@Autowired
	@Qualifier(EduRecordDao.BEAN_ID)
	private EduRecordDao eduRecordDao;
	
	@Override
	@Transactional
	public void addVideo(VideoDTO videoDTO) {
		String videoToken = videoDTO.getVideoToken();
		if (StringUtils.isBlank(videoToken)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "videoToken");
		}
		String thumbUrlToken = videoDTO.getThumbToken();
		if (StringUtils.isBlank(thumbUrlToken)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "thumbToken");
		}
		FileInfo videofileInfo = fileInfoService.queryByToken(videoToken);
		if (videofileInfo == null) {
			throw new BizException(GlobalStatusEnum.PARAM_ERROR, "videoToken");
		}
		FileInfo thumbFileInfo = fileInfoService.queryByToken(thumbUrlToken);
		if (thumbFileInfo == null) {
			throw new BizException(GlobalStatusEnum.PARAM_ERROR, "thumbToken");
		}
		Long categoryId = videoDTO.getCategoryId();
		if (categoryId != null) {
			Category category = categoryDao.findById(categoryId);
			if (category == null) {
				throw new BizException(CategoryExceptionEnum.NOT_EXIST_CTG);
			}
		}
		Video video = new Video();
		video.setName(videoDTO.getName());
		video.setOriginName(videofileInfo.getOriginName());
		video.setUrl(videofileInfo.getUrl());
		video.setThumbUrl(thumbFileInfo.getUrl());
		video.setDuration(videoDTO.getDuration());
		video.setFileSize(videofileInfo.getFileSize());
		video.setCategoryId(categoryId);
		video.setIntroduction(videoDTO.getIntroduction());
		video.setCreateTime(new Date());
		videoDao.doInsert(video);
		fileInfoService.deleteByToken(videoToken);
		fileInfoService.deleteByToken(thumbUrlToken);
	}

	@Override
	@Transactional
	public void updateVideo(VideoDTO videoDTO) {
		Long id = videoDTO.getId();
		if (id == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "id");
		}
		Video existVideo = videoDao.findById(id);
		if (existVideo == null) {
			throw new BizException(VideoExceptionEnum.NOT_EXIST);
		}
		String videoToken = videoDTO.getVideoToken();
		if (StringUtils.isNoneBlank(videoToken)) {
			FileInfo videofileInfo = fileInfoService.queryByToken(videoToken);
			if (videofileInfo == null) {
				throw new BizException(GlobalStatusEnum.PARAM_ERROR, "videoToken");
			}
			existVideo.setOriginName(videofileInfo.getOriginName());
			existVideo.setUrl(videofileInfo.getUrl());
			existVideo.setFileSize(videofileInfo.getFileSize());
		}
		String thumbUrlToken = videoDTO.getThumbToken();
		if (StringUtils.isNoneBlank(thumbUrlToken)) {
			FileInfo thumbFileInfo = fileInfoService.queryByToken(thumbUrlToken);
			if (thumbFileInfo == null) {
				throw new BizException(GlobalStatusEnum.PARAM_ERROR, "thumbToken");
			}
			existVideo.setThumbUrl(thumbFileInfo.getUrl());
		}
		existVideo.setName(videoDTO.getName());
		existVideo.setCategoryId(videoDTO.getCategoryId());
		existVideo.setDuration(videoDTO.getDuration());
		existVideo.setIntroduction(videoDTO.getIntroduction());
		existVideo.setUpdateTime(new Date());
		videoDao.doUpdate(existVideo);
		if (StringUtils.isNoneBlank(videoToken)) {
			fileInfoService.deleteByToken(videoToken);
		}
		if (StringUtils.isNoneBlank(thumbUrlToken)) {
			fileInfoService.deleteByToken(thumbUrlToken);
		}
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		if (id == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "id");
		}
		VideoDTO existVideo = videoDao.findById(id);
		if (existVideo == null) {
			throw new BizException(VideoExceptionEnum.NOT_EXIST);
		}
		videoDao.doDelete(id);
	}

	@Override
	@Transactional
	public PageInfo<VideoDTO> findByPage(VideoQueryParamDTO queryDTO) {
		PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
		List<VideoDTO> videos = videoDao.findByCondition(queryDTO);
		if (CollectionUtils.isNotEmpty(videos)) {
			for (VideoDTO v : videos) {
				this.fillNFSAddress(v);
			}
		}
		return new PageInfo<VideoDTO>(videos);
	}

	@Override
	@Transactional
	public PageInfo<VideoDTO> findVideoAndViewRecordPage(Long userId, String batchNum, VideoQueryParamDTO queryDTO) {
		PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
		List<VideoDTO> videos = videoDao.findVideoAndViewRecordPage(userId, batchNum, queryDTO.getCategoryId());
		if (CollectionUtils.isNotEmpty(videos)) {
			for (VideoDTO video : videos) {
				this.fillNFSAddress(video);
			}
		}
		return new PageInfo<VideoDTO>(videos);
	}

	@Override
	@Transactional
	public VideoDTO findById(Long id) {
		VideoDTO video = videoDao.findById(id);
		this.fillNFSAddress(video);
		return video;
	}
	
	private void fillNFSAddress(VideoDTO video) {
		if (video == null) {
			return;
		}
		String url = video.getUrl();
		if (StringUtils.isNoneBlank(url)) {
			video.setUrl(ServiceConsts.NFS_ADDRESS + url);
		}
		String thumbUrl = video.getThumbUrl();
		if (StringUtils.isNoneBlank(thumbUrl)) {
			video.setThumbUrl(ServiceConsts.NFS_ADDRESS + thumbUrl);
		}
	}

	@Override
	@Transactional
	public VideoDTO findVideoAndViewRecord(Long userId, String batchNum, Long videoId) {
		VideoDTO video = videoDao.findById(videoId);
		if (video == null) {
			throw new BizException(VideoExceptionEnum.NOT_EXIST);
		}
		this.fillNFSAddress(video);
		VideoRecord existRecord = videoRecordDao.findUniqueRecord(userId, batchNum, videoId);
		if (existRecord != null) {
			video.setCompletedDuration(existRecord.getCompletedDuration());
			video.setIsCompleted(existRecord.getIsCompleted());
		}
		return video;
	}

	@Override
	@Transactional
	public void saveOrUpdateVideoRecord(VideoRecord videoRecord) {
		VideoDTO video = videoDao.findById(videoRecord.getVideoId());
		if (video == null) {
			throw new BizException(VideoExceptionEnum.NOT_EXIST);
		}
		VideoRecord existRecord = videoRecordDao.findUniqueRecord(videoRecord.getUserId(), videoRecord.getBatchNum(), videoRecord.getVideoId());
		if (existRecord != null) {
			existRecord.setCompletedDuration(videoRecord.getCompletedDuration());
			existRecord.setIsCompleted(videoRecord.getIsCompleted());
			existRecord.setUpdateTime(new Date());
			videoRecordDao.doUpdate(existRecord);
		} else {
			videoRecord.setCategoryId(video.getCategoryId());
			videoRecord.setDuration(video.getDuration());
			videoRecord.setIsCompleted(false);
			videoRecord.setCreateTime(new Date());
			videoRecordDao.doInsert(videoRecord);
		}
		//教育记录处理
		EduRecord eduRecord = eduRecordDao.findUniqueRecord(videoRecord.getUserId(), videoRecord.getBatchNum(), EduType.CHECK);
		if (eduRecord == null) {
			eduRecord = new EduRecord();
			eduRecord.setUserId(videoRecord.getUserId());
			eduRecord.setBatchNum(videoRecord.getBatchNum());
			eduRecord.setEduType(EduType.CHECK);
			eduRecord.setIsCompleted(false);
			eduRecord.setCreateTime(new Date());
			eduRecordDao.doInsert(eduRecord);
		} else {
			Long totalCostTime = eduRecordDao.calculateCostTime(videoRecord.getUserId(), videoRecord.getBatchNum(), EduType.CHECK);
			if (totalCostTime != null && totalCostTime.longValue() >= ServiceConsts.EDU_CHECK_LEARN_SECONDS.longValue()) {
				eduRecord.setIsCompleted(true);
				eduRecord.setUpdateTime(new Date());
				eduRecordDao.doUpdate(eduRecord);
			}
		}
	}

	@Override
	@Transactional
	public VideoLearnInfo queryLearnInfo(Long userId, String batchNum) {
		VideoLearnInfo summaryInfo = new VideoLearnInfo();
		summaryInfo.setLearnDuration(ServiceConsts.EDU_CHECK_LEARN_SECONDS);
		Long completeDuration = videoRecordDao.findCompleteDuration(userId, batchNum);
		summaryInfo.setCompleteDuration(completeDuration);
		return summaryInfo;
	}
	
}
