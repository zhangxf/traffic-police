package org.trafficpolice.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;
import org.trafficpolice.commons.web.IPUtils;
import org.trafficpolice.consts.ApplicationConsts;

/**
 * @author zhangxiaofei
 * @createdOn 2018年5月30日 下午1:03:25
 */
public class HttpRequestMDCFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(HttpRequestMDCFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String ip = IPUtils.getIpAddress(request);
		request.setAttribute(ApplicationConsts.IP, ip);
        // 产生请求id
        String requestId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + Long.toString(System.currentTimeMillis(), Character.MAX_RADIX).toUpperCase();
        request.setAttribute(ApplicationConsts.REQUEST_ID, requestId);
        String uniqueId = ip + ApplicationConsts.LOG_SEPARATOR  + requestId;
        MDC.put(ApplicationConsts.UNIQUE_ID, uniqueId);
        try {
        	filterChain.doFilter(request, response);
        } finally {
        	request.removeAttribute(ApplicationConsts.IP);
        	request.removeAttribute(ApplicationConsts.REQUEST_ID);
        	MDC.clear();
        }
	}

}
