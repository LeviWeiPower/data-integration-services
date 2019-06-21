package com.aimspeed.gatherer.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/** 
 * 访问api的话，则需要携带token，主要是拦截Api的访问
 * 内部访问的话，则需要登录，携带登录信息。
 * 如果是访问Api的，则不需要经过登录拦截器
 */
public class ApiInterceptor implements HandlerInterceptor {
	
	Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);
	
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
		
		/*String url = request.getRequestURL().toString();
		
		if(url.contains("?")) {
			url = url.substring(0, url.indexOf("?"));
		}*/
		
		
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
