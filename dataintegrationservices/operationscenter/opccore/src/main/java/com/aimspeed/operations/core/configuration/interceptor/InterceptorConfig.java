package com.aimspeed.operations.core.configuration.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aimspeed.common.file.PropertiesUtils;
import com.aimspeed.operations.common.constants.CommonPropertiesConstant;
import com.aimspeed.operations.core.interceptor.AuthorityInterceptor;
import com.aimspeed.operations.core.interceptor.LoginInterceptor;

/** 
 * 拦截器配置
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Bean
	public  LoginInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}
	
	@Bean
	public  AuthorityInterceptor authorityInterceptor() {
		return new AuthorityInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//---------- 登录拦截器
		List<String> excludeUrls = new ArrayList();
     		excludeUrls.add("/resource/**");
     		
		//获取到所有可以不需要登录就通过的url
		String passUrlStr = PropertiesUtils.getProperty(CommonPropertiesConstant.COMMON_FILE_PATH, CommonPropertiesConstant.LOGIN_PASS_URL);
		if(null != passUrlStr) {
			if(passUrlStr.contains(",")) {
				String [] passUrls = passUrlStr.split(",");
				excludeUrls.addAll(Arrays.asList(passUrls));
			}
		}
		
		//添加拦截器
		registry.addInterceptor(loginInterceptor()).excludePathPatterns(excludeUrls).addPathPatterns("/**");
		registry.addInterceptor(authorityInterceptor()).excludePathPatterns(excludeUrls).addPathPatterns("/**");
	}

}
