package com.aimspeed.operations.core.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.aimspeed.common.constants.RedisTimeOutConstants;
import com.aimspeed.common.http.HttpRequestUtils;
import com.aimspeed.operations.common.constants.CommonConstant;
import com.aimspeed.redis.RedisMapperImpl;


/** 
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Autowired
	private RedisMapperImpl redisMapper;
	
	/*
	 * 处理请求之前调用
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @overridden @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String url = request.getRequestURL().toString();

		//记录下必要的信息到日志中
		logger.info(url);
		logger.info(HttpRequestUtils.getRemortIp(request));
		logger.info(request.getRemotePort()+"");
		logger.info(request.getProtocol());
		logger.info(request.getMethod());
		logger.info(new Date()+"");
		logger.info(request.getContextPath());
		logger.info(request.getPathInfo());
		logger.info(request.getQueryString());
		logger.info(request.getRemoteUser());
		logger.info(request.getRequestedSessionId());
		
		if(url.contains("?")) {
			url = url.substring(0, url.indexOf("?"));
		}
		
		//获取到所有可以不需要登录就通过的url
		/*String passUrlStr = PropertiesUtils.getProperty(CommonPropertiesConstant.COMMON_FILE_PATH, CommonPropertiesConstant.LOGIN_PASS_URL);
		if(null != passUrlStr) {
			if(passUrlStr.contains(",")) {
				String [] passUrls = passUrlStr.split(",");
				for (String passUrl : passUrls) {
					if(url.contains(passUrl)) {
						return true;
					}
				}
			}else {
				if(url.contains(passUrlStr)) {
					return true;
				}
			}
		}*/

		//判断用户是否已登录，
		String sessionId = HttpRequestUtils.getSessionId(request);
		
		if(redisMapper.exists(sessionId + CommonConstant.REDIS_LOGIN_USER_INFO)) {
			redisMapper.refresh(sessionId + CommonConstant.REDIS_LOGIN_USER_INFO, RedisTimeOutConstants.HALF_HOUR);
			return true;
		}
		
		//到登录页面
		response.sendRedirect("/main/login");
		
		return true;
	}

	/*
	 * 处理请求之后调用，未渲染视图
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 * @overridden @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//		System.out.println("调用方法之后的拦截器");
		
	}

	/*
	 * 处理完请求后调用，渲染视图后
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 * @overridden @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		System.out.println("调用方法之后的拦截器");
	}
	
	
}
