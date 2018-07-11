package org.trafficpolice.controller;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import org.trafficpolice.commons.TokenUtils;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.consts.ApplicationConsts;
import org.trafficpolice.consts.ServiceConsts;
import org.trafficpolice.dto.FileUploadResultDTO;
import org.trafficpolice.exception.FileExceptionEnum;
import org.trafficpolice.po.FileInfo;
import org.trafficpolice.service.FileInfoService;

/**
 * @author zhangxiaofei
 * 2018年7月8日下午11:10:41
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	private static final Set<String> ALLOW_CONTENT_TYPE = new HashSet<String>();
	
	static {
		String[] allowContentTypeArray = new String[]{
				"image/jpeg",
				"image/png",
		};
		ALLOW_CONTENT_TYPE.addAll(Arrays.asList(allowContentTypeArray));
	}
	
	@Autowired
	@Qualifier(FileInfoService.BEAN_ID)
	private FileInfoService fileInfoService;
	
	@PostMapping("/upload")
	public FileUploadResultDTO upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new BizException(FileExceptionEnum.EMPTY_FILE);
		}
		logger.info("####【content-type】--> {}####", file.getContentType());//image/jpeg
		logger.info("####【form-file-name】--> {}####", file.getName());//file
		logger.info("####【original-file-name】--> {}####", file.getOriginalFilename());//WechatIMG16.jpeg
		logger.info("####【file-size】--> {}####", file.getSize());//346262B
		String contentType = file.getContentType();
		if (!ALLOW_CONTENT_TYPE.contains(contentType)) {
			throw new BizException(FileExceptionEnum.DISALLOW);
		}
		File directory = new File(ApplicationConsts.FILE_UPLOAD_DIR + File.separator + "image");
		if (!directory.exists()) {
			directory.mkdirs();
		}
		String extension = UriUtils.extractFileExtension(file.getOriginalFilename());
		String destFileName = UUID.randomUUID().toString() + (StringUtils.isNoneBlank(extension) ? "." + extension : "");
		file.transferTo(new File(directory.getPath() + File.separator + destFileName));
		FileInfo fileInfo = new FileInfo();
		String token = TokenUtils.generateToken();
		fileInfo.setToken(token);
		fileInfo.setFileType("image");
		fileInfo.setFileSize(file.getSize());
		fileInfo.setOriginName(file.getOriginalFilename());
		fileInfo.setDestName(destFileName);
		fileInfo.setUrl(fileInfo.getFileType() + "/" + destFileName);
		fileInfo.setCreateTime(new Date());
		fileInfoService.saveFileInfo(fileInfo);
		FileUploadResultDTO result = new FileUploadResultDTO();
		result.setToken(token);
		result.setUrl(ServiceConsts.NFS_ADDRESS + fileInfo.getUrl());
		return result;
	}
	
}
