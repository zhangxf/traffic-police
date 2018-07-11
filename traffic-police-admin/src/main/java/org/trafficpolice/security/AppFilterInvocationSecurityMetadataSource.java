package org.trafficpolice.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * @author zhangxiaofei
 * 2018年7月11日上午4:22:43
 */
public class AppFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		FilterInvocation fi = (FilterInvocation) object;
		String url = fi.getRequestUrl();
		String httpMethod = fi.getRequest().getMethod();
		List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>();

		// Lookup your database (or other source) using this information and populate the
		// list of attributes

		return attributes;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
