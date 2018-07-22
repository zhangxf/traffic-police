package org.trafficpolice.service;

import org.trafficpolice.dto.AuditQueryParamDTO;
import org.trafficpolice.dto.AuditQueryResultDTO;
import org.trafficpolice.dto.UserDTO;
import org.trafficpolice.dto.UserQueryParamDTO;
import org.trafficpolice.po.User;

import com.github.pagehelper.PageInfo;

/**
 * @author zhangxiaofei
 * 2018年7月1日上午11:47:57
 */
public interface UserService {

	public static final String BEAN_ID = "userService";
	
	/**
	 * 用户注册
	 * @param userDTO
	 * @param isBGRegister 是否是后台管理员帮忙注册
	 */
	public void register(UserDTO userDTO, boolean isBGRegister);
	
	/**
	 * 用户注册信息修改
	 * @param userDTO
	 */
	public void registerUpdate(UserDTO userDTO);
	
	/**
	 * 修改用户（后台管理员修改）
	 * @param userDTO
	 */
	public void updateUser(UserDTO userDTO);
	
	public User findById(Long id);
	
	public User findByPhone(String phone);
	
	/**
	 * 审核状态查询
	 * @param auditQueryParamDTO
	 * @return
	 */
	public AuditQueryResultDTO auditQuery(AuditQueryParamDTO auditQueryParamDTO);
	
	/**
	 * 根据证件号码以及驾驶证编号查询用户
	 * @param idNo
	 * @param licenseNo
	 * @return
	 */
	public User findByIdNoAndLicenseNo(String idNo, String licenseNo);
	
	/**
	 * 分页查询用户
	 */
	public PageInfo<User> queryByPage(UserQueryParamDTO queryDTO);
	
	/**
	 * 用户审核
	 * @param user
	 */
	public void audit(User user);
	
	/**
	 * 更新禁用状态
	 * @param id
	 * @param disabled
	 */
	public void updateDisabled(Long id, boolean disabled);
	
}
