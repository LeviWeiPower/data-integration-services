package com.aimspeed.common.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * HTTP请求包工具类
 * @author AimSpeed
 */
public class HttpRequestUtils {

	/**
	 * 根据name获取String参数
	 * @author AimSpeed
	 * @param request 请求包类对象
	 * @param name Parameter的Key
	 * @return String Parameter的Value
	 */
	public static String getParameter(HttpServletRequest request,String name){
		String value = request.getParameter(name);
		return value;
	}
	
	/**
	 * 获取ip
	 * @author AimSpeed
	 * @param request 请求包类对象
	 * @return String ip
	 */
	public static String getRemortIp(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
	
	/**
	 * 获取sessionId
	 * @author AimSpeed
	 * @param request 请求包类对象
	 * @return String 结果值
	 */
	public static String getSessionId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		return sessionId;
	}
	
	
	
	
}
