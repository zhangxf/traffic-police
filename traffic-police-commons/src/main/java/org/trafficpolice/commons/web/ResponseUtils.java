package org.trafficpolice.commons.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月22日 下午6:17:33
 */
public class ResponseUtils {

	public static void writeJson(HttpServletResponse response, String content) {
		write(response, content, "application/json");
	}
	
	public static void writeXml(HttpServletResponse response, String content) {
		write(response, content, "text/xml");
	}
	
	public static void writeHtml(HttpServletResponse response, String content) {
		write(response, content, "text/html");
	}
	
	public static void writeText(HttpServletResponse response, String content) {
		write(response, content, "text/plain");
	}
	
	public static void write(HttpServletResponse response, String content, String contentType) {
		try {
			response.setContentType(contentType + ";charset=utf-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			throw new RuntimeException("#response write error#", e);
		}
	}
	
}
