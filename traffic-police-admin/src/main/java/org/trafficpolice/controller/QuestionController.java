package org.trafficpolice.controller;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.commons.web.AliyunHttpUtils;
import org.trafficpolice.commons.web.IHangmeiHttpClientUtil;
import org.trafficpolice.consts.ApplicationConsts;
import org.trafficpolice.dto.QuestionDTO;
import org.trafficpolice.dto.QuestionQueryParamDTO;
import org.trafficpolice.po.Question;
import org.trafficpolice.service.QuestionService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月20日 下午3:56:37
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
	
	@Autowired
	@Qualifier(QuestionService.BEAN_ID)
	private QuestionService questionService;
	
	@PostMapping("/add")
	public NULL addQuestion(@RequestBody QuestionDTO questionDTO) {
		questionService.addQuestion(questionDTO);
		return NULL.newInstance();
	}
	
	@GetMapping("/delete")
	public NULL deleteById(Long id) {
		questionService.deleteById(id);
		return NULL.newInstance();
	}
	
	@PostMapping("/update")
	public NULL updateQuestion(@RequestBody QuestionDTO questionDTO) {
		questionService.updateQuestion(questionDTO);
		return NULL.newInstance();
	}
	
	@PostMapping("/page")
	public PageInfo<QuestionDTO> queryByPage(@RequestBody QuestionQueryParamDTO queryDTO) {
		return questionService.findByPage(queryDTO);
	}
	
	@GetMapping("/find-by-id")
	public QuestionDTO findById(Long id) {
		return questionService.findById(id);
	}
	
	@GetMapping("/pull")
	public NULL pull() throws Exception {
		logger.info("####【pull questions begin】####");
		long begin = System.currentTimeMillis();
		String host = "https://jisujiakao.market.alicloudapi.com";
	    String path = "/driverexam/query";
	    String method = "GET";
	    String appcode = "fcf113fe472a473bbbf4e8f499a8ba89";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("sort", "normal");
	    String[] subjectArray = new String[] {"1", "4"};
	    String[] typeArray = new String[] {"A1", "A2", "A3", "B1", "B2", "C1", "C2", "C3"};
	    int pagesize = 100;
	    querys.put("pagesize", String.valueOf(pagesize));
	    for (String subject : subjectArray) {
	    	querys.put("subject", subject);
	    	for (String type : typeArray) {
	    		querys.put("type", type);
	    		int pagenum = 1;
	    		while (true) {
	    			querys.put("pagenum", String.valueOf(pagenum));
	    			HttpResponse response = AliyunHttpUtils.doGet(host, path, method, headers, querys);
	    			String json = EntityUtils.toString(response.getEntity());
	    			JSONObject josnObject = JSON.parseObject(json);
	    			JSONObject resultJosnObject = josnObject.getJSONObject("result");
	    			JSONArray listJsonArray = resultJosnObject.getJSONArray("list");
	    			for (int i = 0; i < listJsonArray.size(); i++) {
	    				JSONObject obj = listJsonArray.getJSONObject(i);
	    				String question = obj.getString("question");
	    				String answer = obj.getString("answer");
    					if ("错".equals(answer)) {
    						answer = "B";
    					} else if ("对".equals(answer)) {
    						answer = "A";
    					}
    					if (answer.length() > 1) {
    						continue;
    					}
    					String option1 = obj.getString("option1");
    					if (StringUtils.isNoneBlank(option1) && option1.startsWith("A、")) {
    						option1 = option1.substring(option1.indexOf("A、") + 2);
    					}
    					if (StringUtils.isBlank(option1)) {
    						option1 = "正确";
    					}
    					String option2 = obj.getString("option2");
    					if (StringUtils.isNoneBlank(option2) && option2.startsWith("B、")) {
    						option2 = option2.substring(option2.indexOf("B、") + 2);
    					}
    					if (StringUtils.isBlank(option2)) {
    						option2 = "错误";
    					}
    					String option3 = obj.getString("option3");
    					if (StringUtils.isNoneBlank(option3) && option3.startsWith("C、")) {
    						option3 = option3.substring(option3.indexOf("C、") + 2);
    					}
    					String option4 = obj.getString("option4");
    					if (StringUtils.isNoneBlank(option4) && option4.startsWith("D、")) {
    						option4 = option4.substring(option4.indexOf("D、") + 2);
    					}
    					String explains = obj.getString("explain");
    					String pic = obj.getString("pic");
    					Question po = new Question();
    					po.setQuestion(question);
    					po.setSubject(subject);
    					po.setAnswer(answer);
    					po.setItem1(option1);
    					po.setItem2(option2);
    					po.setItem3(option3);
    					po.setItem4(option4);
    					po.setExplains(explains);
    					po.setOriginUrl(pic);
	    				Question existPO = questionService.findSameQuestion(po);
	    				if (existPO != null) {
	    					String existType = existPO.getType();
	    					if (!new HashSet<String>(Arrays.asList(existType.split(","))).contains(type)) {
	    						existPO.setType(existType + "," + type);
	    						questionService.doUpdate(existPO);
	    					}
	    				} else {
	    					if (StringUtils.isNoneBlank(pic)) {
	    						File directory = new File(ApplicationConsts.FILE_UPLOAD_DIR + File.separator + "image");
		    					if (!directory.exists()) {
		    						directory.mkdirs();
		    					}
		    					String extension = UriUtils.extractFileExtension(pic.substring(pic.lastIndexOf("/") + 1));
		    					String destFileName = UUID.randomUUID().toString() + (StringUtils.isNoneBlank(extension) ? "." + extension : "");
		    					IHangmeiHttpClientUtil client = IHangmeiHttpClientUtil.getInstance();
		    					client.doDownload(pic, directory.getAbsolutePath() + "/" + destFileName);
		    					po.setUrl("image" + "/" + destFileName);
	    					} else {
	    						po.setUrl(pic);
	    					}
	    					po.setType(type);
	    					po.setCreateTime(new Date());
	    					questionService.doInsert(po);
	    				}
	    			}
	    			int total = resultJosnObject.getIntValue("total");
	    			if (pagenum * pagesize >= total) {
	    				break;
	    			}
	    			pagenum++;
	    		}
	    		
	    	}
	    }
//	    querys.put("type", "A1,A2,A3,B1,B2");
//	    科目一+A1、A2、A3、B1、B2、C1、C2、C3、C4
//	    科目四+A1、A2、A3、B1、B2、C1、C2、C3、C4
//	    try {
//	    	/**
//	    	* 重要提示如下:
//	    	* HttpUtils请从
//	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
//	    	* 下载
//	    	*
//	    	* 相应的依赖请参照
//	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
//	    	*/
//	    	HttpResponse response = AliyunHttpUtils.doGet(host, path, method, headers, querys);
//	    	System.out.println(response.toString());
//	    	//获取response的body
//	    	System.out.println(EntityUtils.toString(response.getEntity()));
//	    } catch (Exception e) {
//	    	e.printStackTrace();
//	    }
	    logger.info("####【pull questions end cost:{}s】####", (System.currentTimeMillis() - begin)/1000.0);
		return NULL.newInstance();
	}
}
