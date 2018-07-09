package org.trafficpolice.service;

import org.trafficpolice.dto.AuditQueryParamDTO;
import org.trafficpolice.dto.AuditQueryResultDTO;
import org.trafficpolice.dto.UserDTO;
import org.trafficpolice.po.User;

/**
 * @author zhangxiaofei
 * 2018年7月1日上午11:47:57
 */
public interface UserService {

	public static final String BEAN_ID = "userService";
	
	public void register(UserDTO userDTO);
	
	/**
	 * 用户注册信息修改
	 * @param userDTO
	 */
	public void registerUpdate(UserDTO userDTO);
	
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
	
}
