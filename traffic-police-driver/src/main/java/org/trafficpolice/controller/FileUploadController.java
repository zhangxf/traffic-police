package org.trafficpolice.controller;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.trafficpolice.commons.TokenUtils;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.dto.FileUploadResultDTO;
import org.trafficpolice.exception.FileExceptionEnum;
import org.trafficpolice.po.FileInfo;

/**
 * @author zhangxiaofei
 * 2018年7月8日下午11:10:41
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@PostMapping("/upload")
	public FileUploadResultDTO upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new BizException(FileExceptionEnum.EMPTY_FILE);
		}
		logger.info("####【content-type】--> {}####", file.getContentType());//image/jpeg
		logger.info("####【form-file-name】--> {}####", file.getName());//file
		logger.info("####【original-file-name】--> {}####", file.getOriginalFilename());//WechatIMG16.jpeg
		logger.info("####【file-size】--> {}####", file.getSize());//346262B
		File directory = new File("/Users/zhangxiaofei/Desktop/fileupload");
		if (!directory.exists()) {
			directory.mkdirs();
		}
		file.transferTo(new File("/Users/zhangxiaofei/Desktop/fileupload/aa.jpeg"));
		FileInfo fileInfo = new FileInfo();
		String token = TokenUtils.generateToken();
		fileInfo.setToken(token);
		fileInfo.setFileType("image");
		fileInfo.setFileSize(file.getSize());
		fileInfo.setOriginName(file.getOriginalFilename());
		fileInfo.setDestName(Long.toString(System.currentTimeMillis(), Character.MAX_RADIX) + file.getOriginalFilename());
		FileUploadResultDTO result = new FileUploadResultDTO();
		result.setToken(token);
		return result;
	}
	
}
