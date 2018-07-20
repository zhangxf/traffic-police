package org.trafficpolice.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.dao.CategoryDao;
import org.trafficpolice.enumeration.CategoryType;
import org.trafficpolice.exception.CategoryExceptionEnum;
import org.trafficpolice.po.Category;
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
	public List<Category> queryByType(CategoryType type) {
		if (type == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "type");
		}
		return categoryDao.findByType(type);
	}

}
