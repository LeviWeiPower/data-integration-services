package com.aimspeed.gatherer.core.configuration.httpclient;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLException;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HttpClient配置
 * @author AimSpeed
 */
@Configuration
public class HttpClientConfig {
	
	/** 
     * 连接池最大连接数 
     */  
    @Value("${httpclient.config.connMaxTotal}")  
    private int connMaxTotal;  
      
    /** 
     *  
     */  
    @Value("${httpclient.config.maxPerRoute}")  
	private int maxPerRoute;  
  
        /** 
     * 连接存活时间，单位为s 
     */  
	@Value("${httpclient.config.timeToLive}")  
	private int timeToLive;  
	
    /**
     * 连接超时或异常重试次数  
     */
	@Value("${httpclient.config.retryTime}")
    private int retryTime;  
    
	/**
	 * 存活时间
	 */
	@Value("${httpclient.config.keepAliveTime}")  
    private int keepAliveTime;
	
	/**
	 * 连接超时时间，单位ms  
	 */
	@Value("${httpclient.config.connectTimeout}")  
    private int connectTimeout;  

	/**
	 * 请求超时时间  
	 */
    @Value("${httpclient.config.connectRequestTimeout}")  
    private int connectRequestTimeout;  

	/**
	 * sock超时时间  
	 */
    @Value("${httpclient.config.socketTimeout}")  
    private int socketTimeout;
	
    /**
     * 设置连接池
     * @author AimSpeed
     * @return PoolingHttpClientConnectionManager
     */
	@Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager poolingClientConnectionManager() {  
    	PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(60, TimeUnit.SECONDS);
   	 	// 最大连接数  
   	 	poolingHttpClientConnectionManager.setMaxTotal(this.connMaxTotal);  
   	 	// 路由基数，并发
   	 	poolingHttpClientConnectionManager.setDefaultMaxPerRoute(this.maxPerRoute);  
   	 	return poolingHttpClientConnectionManager;  
    }
    
	/**
	 * 实例化连接池，设置连接池管理器。
     * 这里需要以参数形式注入上面实例化的连接池管理器
	 * @author AimSpeed
	 * @param httpClientConnectionManager
	 * @return HttpClientBuilder
	 */
    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder(@Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager){
        //HttpClientBuilder中的构造方法被protected修饰，所以这里不能直接使用new来实例化一个HttpClientBuilder，
    	//可以使用HttpClientBuilder提供的静态方法create()来获取HttpClientBuilder对象
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);
        return httpClientBuilder;
    }
	
    /**
     * 定义连接策略
     * @author AimSpeed
     * @return ConnectionKeepAliveStrategy
     */
    @Bean("connectionKeepAliveStrategy")  
    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {  
        return new ConnectionKeepAliveStrategy() {  
			@Override
			public long getKeepAliveDuration(HttpResponse response, org.apache.http.protocol.HttpContext context) {
				// Honor 'keep-alive' header  
                HeaderElementIterator it = new BasicHeaderElementIterator(  
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));  
                while (it.hasNext()) {  
                    HeaderElement he = it.nextElement();  
                    String param = he.getName();  
                    String value = he.getValue();  
                    if (value != null && param.equalsIgnoreCase("timeout")) {  
                        try {  
                            return Long.parseLong(value) * 1000;  
                        } catch (NumberFormatException ignore) {  
                        }  
                    }  
                }  
                return 30 * 1000;  
			}  
        };  
    }
    
    /**
     * 多次链接
     * 请求失败的处理策略Bean
     * @author AimSpeed
     * @return HttpRequestRetryHandler
     */
    @Bean("httpRequestRetryHandler")  
    public HttpRequestRetryHandler httpRequestRetryHandler() {  
        // 请求重试  
        final int retryTime = this.retryTime;  
        return new HttpRequestRetryHandler() {  
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {  
                // Do not retry if over max retry count,如果重试次数超过了retryTime,则不再重试请求  
                if (executionCount >= retryTime) {  
                    return false;  
                }  
                // 服务端断掉客户端的连接异常  
                if (exception instanceof NoHttpResponseException) {  
                    return true;  
                }  
                // time out 超时重试  
                if (exception instanceof InterruptedIOException) {  
                    return true;  
                }  
                // Unknown host  
                if (exception instanceof UnknownHostException) {  
                    return false;  
                }  
                // Connection refused  
                if (exception instanceof ConnectTimeoutException) {  
                    return false;  
                }  
                // SSL handshake exception  
                if (exception instanceof SSLException) {  
                    return false;  
                }  
                HttpClientContext clientContext = HttpClientContext.adapt(context);  
                HttpRequest request = clientContext.getRequest();  
                if (!(request instanceof HttpEntityEnclosingRequest)) {  
                    return true;  
                }  
                return false;  
            }  
        };  
    }  
	
    /**
     * 定义请求配置Bean
     * @author AimSpeed
     * @return RequestConfig
     */
    @Bean(name = "requestConfig") 
    public RequestConfig config(){  
    	return RequestConfig.custom()  
                .setConnectionRequestTimeout(this.connectRequestTimeout)  
                .setConnectTimeout(this.connectTimeout)  
                .setSocketTimeout(this.socketTimeout)  
                .build();  
    }

    /**
     * 注入连接池，用于获取httpClient
     * 最终使用：
     * 	@Autowired
     * 	private CloseableHttpClient client;
     * 
     * @author AimSpeed
     * @param httpClientConnectionManager
     * @param httpRequestRetryHandler
     * @param connectionKeepAliveStrategy
     * @param requestConfig
     * @return CloseableHttpClient
     */
    @Bean
    public CloseableHttpClient getCloseableHttpClient(@Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager,
    		@Qualifier("httpRequestRetryHandler") HttpRequestRetryHandler httpRequestRetryHandler,
    		@Qualifier("connectionKeepAliveStrategy") ConnectionKeepAliveStrategy connectionKeepAliveStrategy,
    		@Qualifier("requestConfig") RequestConfig requestConfig){ 
    	return HttpClients.custom().setConnectionManager(httpClientConnectionManager)  
							        .setRetryHandler(httpRequestRetryHandler)  
							        .setKeepAliveStrategy(connectionKeepAliveStrategy)  
							        .setDefaultRequestConfig(requestConfig)  
							        .build();
    }
	
	
}
