package org.trafficpolice.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.trafficpolice.commons.json.JsonResult;
import org.trafficpolice.commons.json.JsonResultWrapper;
import org.trafficpolice.po.User;
import org.trafficpolice.security.authentication.DefaultAuthenticationSuccessHandler;
import org.trafficpolice.security.authentication.RedisSecurityContextRepository;
import org.trafficpolice.security.userdetails.AuthUser;

/**
 * @author zhangxiaofei
 * @createdOn 2017年12月20日 下午4:23:39
 */
@Component(LoginSuccessHandler.BEAN_ID)
public class LoginSuccessHandler extends DefaultAuthenticationSuccessHandler implements MessageSourceAware {

    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    public static final String BEAN_ID = "loginSuccessHandler";
    
    protected MessageSource messageSource;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected JsonResult handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //存当前用户CurrentUser
        AuthUser<User> authUser = (AuthUser<User>) authentication.getPrincipal();
        User currentUser = authUser.getCurrentUser();
        AuthSuccessMessageData authMessageData = this.buildAuthMessageData(request, currentUser);
        logger.debug("####【登录成功】####phone:{}", currentUser.getPhone());
        return JsonResultWrapper.wrapSuccess(authMessageData);
    }
    
    public AuthSuccessMessageData buildAuthMessageData(HttpServletRequest request, User currentUser) {
        AuthSuccessMessageData authMessageData = new AuthSuccessMessageData();
        authMessageData.setPhone(currentUser.getPhone());
        authMessageData.setAuthKey((String) request.getAttribute(RedisSecurityContextRepository.SESSION_KEY));
        return authMessageData;
    }
    
    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}