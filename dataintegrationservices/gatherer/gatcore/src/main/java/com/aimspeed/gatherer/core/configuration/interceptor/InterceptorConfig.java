package com.aimspeed.gatherer.core.configuration.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aimspeed.gatherer.core.interceptor.ApiInterceptor;
import com.aimspeed.gatherer.core.interceptor.LogInterceptor;

/** 
 * 拦截器配置
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Bean
	public  LogInterceptor logInterceptor() {
		return new LogInterceptor();
	}
	
	@Bean
	public  ApiInterceptor apiInterceptor() {
		return new ApiInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//添加拦截器
//		registry.addInterceptor(logInterceptor()).addPathPatterns("/**"); //日志拦截器
//		registry.addInterceptor(apiInterceptor()).addPathPatterns("/api/**"); //api拦截器
	}
	
	
	
}
