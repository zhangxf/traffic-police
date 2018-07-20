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
import org.trafficpolice.dao.QuestionDao;
import org.trafficpolice.dto.QuestionDTO;
import org.trafficpolice.dto.QuestionQueryParamDTO;
import org.trafficpolice.exception.CategoryExceptionEnum;
import org.trafficpolice.exception.QuestionExceptionEnum;
import org.trafficpolice.po.Authority;
import org.trafficpolice.po.Category;
import org.trafficpolice.po.FileInfo;
import org.trafficpolice.po.Question;
import org.trafficpolice.service.FileInfoService;
import org.trafficpolice.service.QuestionService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午2:14:42
 */
@Service(QuestionService.BEAN_ID)
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	@Qualifier(QuestionDao.BEAN_ID)
	private QuestionDao questionDao;

	@Autowired
	@Qualifier(CategoryDao.BEAN_ID)
	private CategoryDao categoryDao;
	
	@Autowired
	@Qualifier(FileInfoService.BEAN_ID)
	private FileInfoService fileInfoService;
	
	@Override
	@Transactional
	public void addQuestion(QuestionDTO questionDTO) {
		String imgUrlToken = questionDTO.getImgUrlToken();
		String url = null;
		if (StringUtils.isNoneBlank(imgUrlToken)) {
			FileInfo imgfileInfo = fileInfoService.queryByToken(imgUrlToken);
			if (imgfileInfo != null) {
				url = imgfileInfo.getUrl();
			} else {
				throw new BizException(GlobalStatusEnum.PARAM_ERROR, "imgUrlToken");
			}
		}
		Long categoryId = questionDTO.getCategoryId();
		if (categoryId != null) {
			Category category = categoryDao.findById(categoryId);
			if (category == null) {
				throw new BizException(CategoryExceptionEnum.NOT_EXIST_CTG);
			}
		}
		Question question = new Question();
		question.setQuestion(questionDTO.getQuestion());
		question.setAnswer(questionDTO.getAnswer());
		question.setItem1(questionDTO.getItem1());
		question.setItem2(questionDTO.getItem2());
		question.setItem3(questionDTO.getItem3());
		question.setItem4(questionDTO.getItem4());
		question.setExplains(questionDTO.getExplains());
		question.setUrl(url);
		question.setCategoryId(categoryId);
		question.setSubject(questionDTO.getSubject());
		question.setType(questionDTO.getType());
		question.setCreateTime(new Date());
		questionDao.doInsert(question);
		if (StringUtils.isNoneBlank(imgUrlToken)) {
			fileInfoService.deleteByToken(imgUrlToken);
		}
	}

	@Override
	@Transactional
	public void updateQuestion(QuestionDTO questionDTO) {
		Long id = questionDTO.getId();
		if (id == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "id");
		}
		Question question = questionDao.findById(id);
		if (question == null) {
			throw new BizException(QuestionExceptionEnum.NOT_EXIST);
		}
		String imgUrlToken = questionDTO.getImgUrlToken();
		if (StringUtils.isNoneBlank(imgUrlToken)) {
			FileInfo imgfileInfo = fileInfoService.queryByToken(imgUrlToken);
			if (imgfileInfo != null) {
				String url = imgfileInfo.getUrl();
				question.setUrl(url);
			} else {
				throw new BizException(GlobalStatusEnum.PARAM_ERROR, "imgUrlToken");
			}
		}
		Long categoryId = questionDTO.getCategoryId();
		if (categoryId != null) {
			Category category = categoryDao.findById(categoryId);
			if (category == null) {
				throw new BizException(CategoryExceptionEnum.NOT_EXIST_CTG);
			}
			question.setCategoryId(categoryId);
		}
		question.setQuestion(questionDTO.getQuestion());
		question.setAnswer(questionDTO.getAnswer());
		question.setItem1(questionDTO.getItem1());
		question.setItem2(questionDTO.getItem2());
		question.setItem3(questionDTO.getItem3());
		question.setItem4(questionDTO.getItem4());
		question.setExplains(questionDTO.getExplains());
		question.setSubject(questionDTO.getSubject());
		question.setType(questionDTO.getType());
		question.setUpdateTime(new Date());
		questionDao.doUpdate(question);
		if (StringUtils.isNoneBlank(imgUrlToken)) {
			fileInfoService.deleteByToken(imgUrlToken);
		}
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		if (id == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "id");
		}
		Question question = questionDao.findById(id);
		if (question == null) {
			throw new BizException(QuestionExceptionEnum.NOT_EXIST);
		}
		questionDao.doDelete(id);
	}

	@Override
	@Transactional
	public PageInfo<Question> findByPage(QuestionQueryParamDTO queryDTO) {
		PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
		List<Question> questions = questionDao.findByCondition(queryDTO);
		return new PageInfo<Question>(questions);
	}

	@Override
	@Transactional
	public Question findById(Long id) {
		return questionDao.findById(id);
	}
	
}
