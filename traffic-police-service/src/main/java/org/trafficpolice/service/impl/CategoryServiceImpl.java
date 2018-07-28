package org.trafficpolice.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.trafficpolice.dao.VideoDao;
import org.trafficpolice.dao.VideoRecordDao;
import org.trafficpolice.dto.CategoryDTO;
import org.trafficpolice.dto.VideoDTO;
import org.trafficpolice.enumeration.CategoryType;
import org.trafficpolice.exception.CategoryExceptionEnum;
import org.trafficpolice.po.Category;
import org.trafficpolice.po.Video;
import org.trafficpolice.po.VideoRecord;
import org.trafficpolice.service.CategoryService;

/**
 * @author zhangxiaofei
 * 2018年7月20日上午12:41:41
 */
@Service(CategoryService.BEAN_ID)
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	@Qualifier(CategoryDao.BEAN_ID)
	private CategoryDao categoryDao;
	
	@Autowired
	@Qualifier(VideoDao.BEAN_ID)
	private VideoDao videoDao;
	
	@Autowired
	@Qualifier(VideoRecordDao.BEAN_ID)
	private VideoRecordDao videoRecordDao;
	
	@Override
	@Transactional
	public void addCategory(Category category) {
		if (category.getType() == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "type");
		}
		if (StringUtils.isBlank(category.getName())) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "name");
		}
		Category existCategory = categoryDao.findByNameAndType(category.getName(), category.getType());
		if (existCategory != null) {
			throw new BizException(CategoryExceptionEnum.EXIST_CTG);
		}
		Date today = new Date();
		category.setCreateTime(today);
		category.setUpdateTime(today);
		categoryDao.doInsert(category);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		Category category = categoryDao.findById(id);
		if (category == null) {
			throw new BizException(CategoryExceptionEnum.NOT_EXIST_CTG);
		}
		categoryDao.doDelete(id);
	}

	@Override
	@Transactional
	public void updateCategory(Category category) {
		if (category == null || category.getId() == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "id");
		}
		Category existCategory = categoryDao.findById(category.getId());
		if (existCategory == null) {
			throw new BizException(CategoryExceptionEnum.NOT_EXIST_CTG);
		}
		if (!existCategory.getName().equals(category.getName())) {
			Category nameExistCategory = categoryDao.findByNameAndType(category.getName(), category.getType());
			if (nameExistCategory != null) {
				throw new BizException(CategoryExceptionEnum.EXIST_CTG);
			}
		}
		Date today = new Date();
		category.setUpdateTime(today);
		categoryDao.doUpdate(category);
	}

	@Override
	@Transactional
	public List<Category> queryByType(String type) {
		if (StringUtils.isBlank(type)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "type");
		}
		if (!CategoryType.VIDEO.getType().equals(type) && !CategoryType.QUESTION.getType().equals(type)) {
			throw new BizException(GlobalStatusEnum.PARAM_ERROR, "type");
		}
		CategoryType ct = CategoryType.valueOf(type.toUpperCase());
		return categoryDao.findByType(ct);
	}

	@Override
	@Transactional
	public List<CategoryDTO> queryAllVideoCategories(Long userId) {
		List<Category> categoryList = categoryDao.findByType(CategoryType.VIDEO);
		if (CollectionUtils.isEmpty(categoryList)) {
			return Collections.emptyList();
		}
		Map<Long, VideoRecord> videoRecordMap = new HashMap<Long, VideoRecord>();
		if (userId != null) {
			String batchNum = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			List<VideoRecord> records = videoRecordDao.findByUserIdAndBatchNum(userId, batchNum);
			if (CollectionUtils.isNotEmpty(records)) {
				for (VideoRecord vr : records) {
					videoRecordMap.put(vr.getId(), vr);
				}
			}
		}
		List<VideoDTO> videoList = videoDao.findAllDTO();
		Map<Long, List<VideoDTO>> categoryVideoMap = new HashMap<Long, List<VideoDTO>>();
		if (CollectionUtils.isNotEmpty(videoList)) {
			for (VideoDTO v : videoList) {
				Long cid = v.getCategoryId();
				if (cid != null) {
					if (!categoryVideoMap.containsKey(cid)) {
						categoryVideoMap.put(cid, new ArrayList<VideoDTO>());
					}
					this.fillNFSAddress(v);
					VideoRecord vr = videoRecordMap.get(v.getId());
					if (vr != null) {
						v.setCompletedDuration(vr.getCompletedDuration());
						v.setIsCompleted(vr.getIsCompleted());
					}
					categoryVideoMap.get(cid).add(v);
				}
			}
		}
		List<CategoryDTO> result = new ArrayList<CategoryDTO>();
		for (Category c : categoryList) {
			CategoryDTO cdto = new CategoryDTO();
			cdto.setId(c.getId());
			cdto.setName(c.getName());
			cdto.setCreateTime(c.getCreateTime());
			cdto.setUpdateTime(c.getUpdateTime());
			cdto.setVideos(categoryVideoMap.get(c.getId()));
			result.add(cdto);
		}
		return result;
	}

	private void fillNFSAddress(Video video) {
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
	
}
