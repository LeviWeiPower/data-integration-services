package com.aimspeed.gatherer.configuration.httpclient;
import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;  

/**
 * 请求配置
 * @author AimSpeed
 */
@Configuration  
public class HttpRequestConfig {  

	@Value("${httpclient.config.connectTimeout}")  
    private int connectTimeout = 2000;  
      
    @Value("${httpclient.config.connectRequestTimeout}")  
    private int connectRequestTimeout = 2000;  
      
    @Value("${httpclient.config.socketTimeout}")  
    private int socketTimeout = 2000;  
    
    /**
     * 定义请求配置Bean
     * @author AimSpeed
     * @Title config 
     * @return RequestConfig 
     * @date 2018年3月27日
     */
    @Bean  
    public RequestConfig config(){  
    	return RequestConfig.custom()  
                .setConnectionRequestTimeout(this.connectRequestTimeout)  
                .setConnectTimeout(this.connectTimeout)  
                //.setSocketTimeout(this.socketTimeout)  
                .build();  
    }  
    
}  