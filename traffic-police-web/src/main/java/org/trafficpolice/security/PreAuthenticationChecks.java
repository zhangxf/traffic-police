package org.trafficpolice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;
import org.trafficpolice.commons.exception.ExceptionMessage;
import org.trafficpolice.enumeration.AuditState;
import org.trafficpolice.exception.UserExceptionEnum;
import org.trafficpolice.po.User;
import org.trafficpolice.security.exception.SpringSecurityException;
import org.trafficpolice.security.userdetails.AuthUser;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月5日 下午7:15:25
 * 校验密码之前的校验（校验账户状态等）
 */
@Component(PreAuthenticationChecks.BEAN_ID)
public class PreAuthenticationChecks implements UserDetailsChecker {

	public static final String BEAN_ID = "preAuthenticationChecks";
	
	@Override
	public void check(UserDetails toCheck) {
		AuthUser<User> au = (AuthUser<User>)toCheck;
		User currentUser = au.getCurrentUser();
		//黑名单校验
		if (currentUser.getDisabled()) {
			throw new SpringSecurityException(UserExceptionEnum.WAS_BLACK);
		}
		//审核状态校验
		AuditState auditState = currentUser.getAuditState();
		if (AuditState.INHAND == auditState || AuditState.REINHAND == auditState) {
			throw new SpringSecurityException(UserExceptionEnum.AUDITSTATE_INHAND);
		}
		if (AuditState.REJECT == auditState) {
			AuthFailureMessageData failureData = new AuthFailureMessageData();
			failureData.setAuditState(auditState);
			failureData.setAuditDesc(currentUser.getAuditDesc());
			ExceptionMessage exceptionMessage = new ExceptionMessage(UserExceptionEnum.AUDITSTATE_REJECT.getStatus(), UserExceptionEnum.AUDITSTATE_REJECT.getKey(), failureData);
			throw new SpringSecurityException(exceptionMessage);
		}
	}

}
