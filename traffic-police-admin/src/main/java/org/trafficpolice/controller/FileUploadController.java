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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import org.trafficpolice.commons.TokenUtils;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
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
	
	@Autowired
	@Qualifier(FileInfoService.BEAN_ID)
	private FileInfoService fileInfoService;
	
	private static final Set<String> ALLOW_IMG_CONTENT_TYPE = new HashSet<String>();
	
	static {
		String[] allowContentTypeArray = new String[]{
				"image/jpeg",
				"image/png",
		};
		ALLOW_IMG_CONTENT_TYPE.addAll(Arrays.asList(allowContentTypeArray));
	}
	
	private static final String TYPE_VIDEO = "video";
	
	private static final String TYPE_IMAGE = "image";
	
	@PostMapping("/{type}/upload")
	public FileUploadResultDTO upload(@PathVariable("type")String type, @RequestParam("file") MultipartFile file) throws Exception {
		if (StringUtils.isEmpty(type)) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "type");
		}
		if (file.isEmpty()) {
			throw new BizException(FileExceptionEnum.EMPTY_FILE);
		}
		if (!TYPE_VIDEO.equals(type) && !TYPE_IMAGE.equals(type)) {
			throw new BizException(GlobalStatusEnum.PARAM_ERROR, "type");
		}
		logger.info("####【content-type】--> {}####", file.getContentType());//image/jpeg
		logger.info("####【form-file-name】--> {}####", file.getName());//file
		logger.info("####【original-file-name】--> {}####", file.getOriginalFilename());//WechatIMG16.jpeg
		logger.info("####【file-size】--> {}####", file.getSize());//346262B
		String contentType = file.getContentType();
		if (TYPE_IMAGE.equals(type)) {
			if (!ALLOW_IMG_CONTENT_TYPE.contains(contentType)) {
				throw new BizException(FileExceptionEnum.DISALLOW);
			}
		}
		File directory = new File(ApplicationConsts.FILE_UPLOAD_DIR + File.separator + type);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		String extension = UriUtils.extractFileExtension(file.getOriginalFilename());
		String destFileName = UUID.randomUUID().toString() + (StringUtils.isNoneBlank(extension) ? "." + extension : "");
		file.transferTo(new File(directory.getPath() + File.separator + destFileName));
		FileInfo fileInfo = new FileInfo();
		String token = TokenUtils.generateToken();
		fileInfo.setToken(token);
		fileInfo.setFileType(type);
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
