package com.aimspeed.gatherer.core.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.aimspeed.common.http.HttpRequestUtils;
import com.alibaba.fastjson.JSON;


/** 
 * 日志拦截器，记录下客户端访问的信息，和服务端的某些处理信息
 */
public class LogInterceptor implements HandlerInterceptor {
	
	Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
	
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
		logger.info("----------------- 请求前的信息 -----------------");
		logger.info("URL：" + request.getRequestURL().toString());
		logger.info("访问的IP：" + HttpRequestUtils.getRemortIp(request));
		logger.info("访问的端口：" + request.getRemotePort()+"");
		logger.info("访问的主机：" + request.getRemoteHost());
		logger.info("访问的用户：" + request.getRemoteUser());
		logger.info("访问协议：" + request.getProtocol());
		logger.info("Http请求的方法：" + request.getMethod());
		logger.info("访问时间：" + new Date()+"");
		logger.info("上下文路径：" + request.getContextPath());
		logger.info("内容长度：" + request.getContentLengthLong());
		logger.info("内容类型：" + request.getContentType());
		logger.info("路径信息：" + request.getPathInfo());
		logger.info("查询信息：" + request.getQueryString());
		logger.info("请求类型：" + request.getAuthType());
		logger.info("字符编码：" + request.getCharacterEncoding());
		if(null != request.getParameterMap()) {
			logger.info("请求的参数：" + JSON.toJSONString(request.getParameterMap()));
		}
		
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
		logger.info("----------------- 内部处理后的信息 -----------------");
		if(null != handler) {
			logger.info("处理类的名字：" + handler.getClass().getSimpleName());
		}
		
		if(null != response) {
			logger.info("响应数据的编码：" + response.getCharacterEncoding());
		}
		
		if(null != modelAndView) {
			logger.info("页面名称：" + modelAndView.getViewName());
			logger.info("响应模型信息：" + JSON.toJSONString(modelAndView.getModel()));
			logger.info("响应码：" + modelAndView.getStatus());
		}
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
		logger.info("----------------- 视图渲染后的信息 -----------------");
		if(null != ex) {
			logger.info("异常信息：" + ex.getMessage());
		}	
		
	}
	
	
}
