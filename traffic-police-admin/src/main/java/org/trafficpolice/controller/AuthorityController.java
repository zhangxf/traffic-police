package org.trafficpolice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.dto.AuthorityQueryParamDTO;
import org.trafficpolice.po.Authority;
import org.trafficpolice.service.AuthorityService;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月12日 下午6:20:51
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController {

	@Autowired
	@Qualifier(AuthorityService.BEAN_ID)
	private AuthorityService authorityService;
	
	@PostMapping("/add")
	public Authority addAuthority(@RequestBody Authority authority) {
		authorityService.addAuthority(authority);
		return authority;
	}
	
	@PostMapping("/update")
	public NULL updateAuthority(@RequestBody Authority authority) {
		if (authority == null || authority.getId() == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "id");
		}
		authorityService.updateAuthority(authority);
		return NULL.newInstance();
	}
	
	@PostMapping("/page")
	public PageInfo<Authority> queryAuthorityPage(@RequestBody AuthorityQueryParamDTO queryDTO) {
		return authorityService.queryAuthorityPage(queryDTO);
	}
	
	@GetMapping("/delete")
	public NULL deleteAuthority(@RequestParam("id") Long id) {
		authorityService.deleteAuthority(id);
		return NULL.newInstance();
	}
	
}
