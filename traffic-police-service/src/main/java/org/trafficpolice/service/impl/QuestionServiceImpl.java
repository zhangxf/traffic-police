package org.trafficpolice.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trafficpolice.commons.cache.CacheNamespace;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.consts.ServiceConsts;
import org.trafficpolice.dao.CategoryDao;
import org.trafficpolice.dao.EduRecordDao;
import org.trafficpolice.dao.QuestionDao;
import org.trafficpolice.dao.QuestionRecordDao;
import org.trafficpolice.dto.QuestionConfigDTO;
import org.trafficpolice.dto.QuestionConfigDetailDTO;
import org.trafficpolice.dto.QuestionDTO;
import org.trafficpolice.dto.QuestionQueryParamDTO;
import org.trafficpolice.enumeration.EduType;
import org.trafficpolice.enumeration.LicenseType;
import org.trafficpolice.exception.CategoryExceptionEnum;
import org.trafficpolice.exception.QuestionExceptionEnum;
import org.trafficpolice.po.Category;
import org.trafficpolice.po.EduRecord;
import org.trafficpolice.po.FileInfo;
import org.trafficpolice.po.Question;
import org.trafficpolice.po.QuestionRecord;
import org.trafficpolice.po.User;
import org.trafficpolice.service.FileInfoService;
import org.trafficpolice.service.QuestionConfigService;
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
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	@Qualifier(QuestionDao.BEAN_ID)
	private QuestionDao questionDao;

	@Autowired
	@Qualifier(CategoryDao.BEAN_ID)
	private CategoryDao categoryDao;
	
	@Autowired
	@Qualifier(QuestionRecordDao.BEAN_ID)
	private QuestionRecordDao questionRecordDao;
	
	@Autowired
	@Qualifier(QuestionConfigService.BEAN_ID)
	private QuestionConfigService questionConfigService;
	
	@Autowired
	@Qualifier(FileInfoService.BEAN_ID)
	private FileInfoService fileInfoService;
	
	@Autowired
	@Qualifier(EduRecordDao.BEAN_ID)
	private EduRecordDao eduRecordDao;
	
	private static final String QUESTION_CONFIG_CACHE = CacheNamespace.TRAFFIC_POLICE + CacheNamespace.SEPARATOR + "question_config" + CacheNamespace.SEPARATOR;
	
	private static final String QUESTION_CACHE = CacheNamespace.TRAFFIC_POLICE + CacheNamespace.SEPARATOR + "question" + CacheNamespace.SEPARATOR;
	
	private static final long CACHE_DURATION_SECONDS = 30 * 60;//缓存时长30分钟
	
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
	public void doInsert(Question question) {
		questionDao.doInsert(question);
	}

	@Override
	@Transactional
	public void doUpdate(Question question) {
		question.setUpdateTime(new Date());
		questionDao.doUpdate(question);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		if (id == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "id");
		}
		QuestionDTO question = questionDao.findById(id);
		if (question == null) {
			throw new BizException(QuestionExceptionEnum.NOT_EXIST);
		}
		questionDao.doDelete(id);
	}

	@Override
	@Transactional
	public PageInfo<QuestionDTO> findByPage(QuestionQueryParamDTO queryDTO) {
		PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
		List<QuestionDTO> questions = questionDao.findByCondition(queryDTO);
		return new PageInfo<QuestionDTO>(questions);
	}

	@Override
	@Transactional
	public QuestionDTO findById(Long id) {
		QuestionDTO result = questionDao.findById(id);
		if (result != null && StringUtils.isNoneBlank(result.getUrl())) {
			result.setUrl(ServiceConsts.NFS_ADDRESS + result.getUrl());
		}
		return result;
	}

	@Override
	@Transactional
	public Question findSameQuestion(Question question) {
		return questionDao.findSameQuestion(question);
	}

	@Override
	@Transactional
	public QuestionConfigDTO initUserQuestions(User user, EduType eduType) {
		Long userId = user.getId();
		LicenseType licenceType = user.getLicenseType();
		QuestionConfigDTO questionConfig = (QuestionConfigDTO)redisTemplate.opsForValue().get(QUESTION_CONFIG_CACHE + eduType.getType());
		if (questionConfig == null) {
			questionConfig = questionConfigService.findQuestionConfig(eduType);
			redisTemplate.opsForValue().set(QUESTION_CONFIG_CACHE + eduType.getType(), questionConfig, CACHE_DURATION_SECONDS, TimeUnit.SECONDS);
		}
		List<QuestionDTO> questions = (List<QuestionDTO>)redisTemplate.opsForValue().get(QUESTION_CACHE + eduType.getType() + CacheNamespace.SEPARATOR + userId);
		if (CollectionUtils.isNotEmpty(questions)) {
			return questionConfig;
		}
		List<QuestionConfigDetailDTO> configDetails = questionConfig.getDetail();
		if (CollectionUtils.isEmpty(configDetails)) {
			throw new BizException(QuestionExceptionEnum.CONFIG_ERROR);
		}
		questions = new ArrayList<QuestionDTO>();
		for (QuestionConfigDetailDTO detail : configDetails) {
			Long learNum = detail.getLearnNum();
			Long categoryId = detail.getCategoryId();
			if (categoryId != null && learNum != null && learNum > 0) {
				List<QuestionDTO> categoryQuestions = questionDao.randomQuestions(categoryId, learNum);
				questions.addAll(categoryQuestions);
			}
		}
		if (CollectionUtils.isEmpty(questions)) {
			throw new BizException(QuestionExceptionEnum.CONFIG_ERROR);
		}
		redisTemplate.opsForValue().set(QUESTION_CACHE + eduType.getType() + CacheNamespace.SEPARATOR + userId, questions, CACHE_DURATION_SECONDS, TimeUnit.SECONDS);
		return questionConfig;
	}

	@Override
	public QuestionDTO nextQuestion(User user, EduType eduType) {
		Long userId = user.getId();
		List<QuestionDTO> questions = (List<QuestionDTO>)redisTemplate.opsForValue().get(QUESTION_CACHE + eduType.getType() + CacheNamespace.SEPARATOR + userId);
		Iterator<QuestionDTO> it = questions.iterator();
		QuestionDTO result = it.next();
		it.remove();
		if (CollectionUtils.isNotEmpty(questions)) {
			redisTemplate.opsForValue().set(QUESTION_CACHE + eduType.getType() + CacheNamespace.SEPARATOR + userId, questions, CACHE_DURATION_SECONDS, TimeUnit.SECONDS);
		} else {
			redisTemplate.delete(QUESTION_CACHE + eduType.getType() + CacheNamespace.SEPARATOR + userId);
		}
		String url = result.getUrl();
		if (StringUtils.isNoneBlank(url)) {
			result.setUrl(ServiceConsts.NFS_ADDRESS + url);
		}
		return result;
	}

	@Override
	@Transactional
	public void saveOrUpdateQuestionRecord(QuestionRecord record) {
		Long userId = record.getUserId();
		String batchNum = record.getBatchNum();
		EduType eduType = record.getEduType();
		QuestionRecord existRecord = questionRecordDao.findUniqueRecord(userId, batchNum, eduType);
		if (existRecord != null) {
			existRecord.setCorrectNum(record.getCorrectNum());
			existRecord.setWrongNum(record.getWrongNum());
			existRecord.setCostTime(existRecord.getCostTime() != null ? existRecord.getCostTime().longValue() + record.getCostTime().longValue() : record.getCostTime());
			existRecord.setUpdateTime(new Date());
			questionRecordDao.doUpdate(existRecord);
		} else {
			record.setCreateTime(new Date());
			questionRecordDao.doInsert(record);
		}
		//教育记录处理
		EduRecord eduRecord = eduRecordDao.findUniqueRecord(userId, batchNum, eduType);
		if (eduRecord == null) {
			eduRecord = new EduRecord();
			eduRecord.setUserId(userId);
			eduRecord.setBatchNum(batchNum);
			eduRecord.setEduType(eduType);
			eduRecord.setIsCompleted(false);
			eduRecord.setCreateTime(new Date());
			eduRecordDao.doInsert(eduRecord);
		} else {
			Long totalCostTime = eduRecordDao.calculateCostTime(userId, batchNum, eduType);
			if (totalCostTime != null && totalCostTime.longValue() >= ServiceConsts.EDU_CHECK_LEARN_SECONDS.longValue()) {
				eduRecord.setIsCompleted(true);
				eduRecord.setUpdateTime(new Date());
				eduRecordDao.doUpdate(eduRecord);
			}
		}
	}
	
}
