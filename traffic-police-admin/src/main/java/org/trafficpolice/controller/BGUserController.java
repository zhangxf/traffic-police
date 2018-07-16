package org.trafficpolice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.trafficpolice.commons.enumeration.GlobalStatusEnum;
import org.trafficpolice.commons.exception.BizException;
import org.trafficpolice.commons.json.NULL;
import org.trafficpolice.dto.BGUserQueryParamDTO;
import org.trafficpolice.dto.ConfigAuthoritiesParamDTO;
import org.trafficpolice.dto.ConfigMenuParamDTO;
import org.trafficpolice.dto.ConfigRolesParamDTO;
import org.trafficpolice.dto.MenuDTO;
import org.trafficpolice.po.Authority;
import org.trafficpolice.po.BGUser;
import org.trafficpolice.service.BGUserService;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * @createdOn 2018年6月11日 下午4:41:15
 */
@RestController
@RequestMapping("/bguser")
public class BGUserController {

	@Autowired
	@Qualifier(BGUserService.BEAN_ID)
	private BGUserService bgUserService;
	
	@PostMapping("/add")
	public BGUser addUser(@RequestBody BGUser bgUser) {
		bgUserService.saveBGUser(bgUser);
		return bgUser;
	}
	
	@PostMapping("/update")
	public NULL updateUser(@RequestBody BGUser bgUser) {
		if (bgUser == null || bgUser.getId() == null) {
			throw new BizException(GlobalStatusEnum.PARAM_MISS, "id");
		}
		bgUserService.updateBGUser(bgUser);
		return NULL.newInstance();
	}
	
	@PostMapping("/page")
	public PageInfo<BGUser> queryBGUserPage(@RequestBody BGUserQueryParamDTO queryDTO) {
		return bgUserService.queryBGUserPage(queryDTO);
	}
	
	@PostMapping("/delete")
	public NULL deleteUser(@RequestParam("id") Long id) {
		bgUserService.deleteBGUser(id);
		return NULL.newInstance();
	}
	
	@PostMapping("/privilege/authorities")
	public List<Long> queryAuthorities(@RequestParam("userId") Long userId) {
		return bgUserService.queryAuthorityIds(userId);
	}
	
	@PostMapping("/privilege/menu")
	public List<Long> queryMenus(@RequestParam("userId") Long userId) {
		return bgUserService.queryMenuIds(userId);
	}
	
	@PostMapping("/config/privilege/authorities")
	public NULL configAuthority(@RequestBody ConfigAuthoritiesParamDTO configDTO) {
		bgUserService.configAuthority(configDTO);
		return NULL.newInstance();
	}
	
	@PostMapping("/config/privilege/menu")
	public NULL configMenu(@RequestBody ConfigMenuParamDTO configDTO) {
		bgUserService.configMenu(configDTO);
		return NULL.newInstance();
	}
	
	@PostMapping("/config/roles")
	public NULL configRoles(@RequestBody ConfigRolesParamDTO configDTO) {
		bgUserService.configRoles(configDTO);
		return NULL.newInstance();
	}
	
	@PostMapping("/roles")
	public List<Long> queryRoles(@RequestParam("userId") Long userId) {
		return bgUserService.queryRoles(userId);
	}
	
	@PostMapping("/buttons")
	public List<Authority> buttonAuthorities(@AuthenticationPrincipal(expression = "currentUser") BGUser user, @RequestParam("menuId") Long menuId) {
		return bgUserService.buttonAuthorities(user, menuId);
	}
	
	/**
	 * 获取登录用户信息
	 * @param user
	 * @return
	 */
	@PostMapping("/info")
	public BGUser userInfo(@AuthenticationPrincipal(expression = "currentUser") BGUser user) {
		return user;
	}
	
	@PostMapping("/menu")
	public List<MenuDTO> menu(@AuthenticationPrincipal(expression = "currentUser") BGUser user) {
		return bgUserService.queryCurrentUserMenu(user);
	}
	
}
