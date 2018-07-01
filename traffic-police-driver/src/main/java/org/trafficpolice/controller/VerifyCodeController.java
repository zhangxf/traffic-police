package org.trafficpolice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.dto.VerifyCodeDTO;
import org.trafficpolice.service.VerifyCodeService;

/**
 * @author zhangxiaofei
 * 2018年7月1日下午8:23:29
 */
@RestController
@RequestMapping("/verifycode")
public class VerifyCodeController {

	@Autowired
	@Qualifier(VerifyCodeService.BEAN_ID)
	private VerifyCodeService verifyCodeService;
	
	@GetMapping("/send/{type}")
	public VerifyCodeDTO send(@PathVariable("type")String type, String phone) {
		return verifyCodeService.sendVerifyCode(type, phone);
	}
	
}
