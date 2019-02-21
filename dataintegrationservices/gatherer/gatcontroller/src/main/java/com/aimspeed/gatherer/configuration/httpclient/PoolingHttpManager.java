package com.aimspeed.gatherer.configuration.httpclient;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;  

/**
 * 连接池，并且可以为多个线程的请求提供服务
 * @author AimSpeed
 */
@Configuration  
public class PoolingHttpManager { 
	
	@Autowired
	private PoolingHttpClientConnectionManager poolHttpcConnManager;

    /** 
     * 连接池最大连接数 
     */  
    @Value("${httpclient.config.connMaxTotal}")  
    private int connMaxTotal = 20;  
      
    /** 
     *  
     */  
    @Value("${httpclient.config.maxPerRoute}")  
    private int maxPerRoute = 20;  
  
        /** 
     * 连接存活时间，单位为s 
     */  
     @Value("${httpclient.config.timeToLive}")  
     private int timeToLive = 60;  
  
     public PoolingHttpManager() {
 		super();
 	}
    
     /**
      * 设置连接池
      * @author AimSpeed
      * @Title poolingClientConnectionManager 
      * @return PoolingHttpClientConnectionManager 
      * @date 2018年3月27日
      */
     @Bean  
     public PoolingHttpClientConnectionManager poolingClientConnectionManager(){  
    	 poolHttpcConnManager = new PoolingHttpClientConnectionManager(60, TimeUnit.SECONDS);  
    	 // 最大连接数  
    	 poolHttpcConnManager.setMaxTotal(this.connMaxTotal);  
    	 // 路由基数，并发
    	 poolHttpcConnManager.setDefaultMaxPerRoute(this.maxPerRoute);  
    	 return poolHttpcConnManager;  
     }  
     
     /**
      * 关闭策略
      * @author AimSpeed
      * @Title httpClientBuilder 
      * @return CloseableHttpClient 
      * @date 2018年3月27日
      */
     @Bean  
     public CloseableHttpClient httpClientBuilder(){  
    	 HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
    	 httpClientBuilder.setConnectionManager(poolHttpcConnManager);
    	 return httpClientBuilder.build();  
     }     
    
}  