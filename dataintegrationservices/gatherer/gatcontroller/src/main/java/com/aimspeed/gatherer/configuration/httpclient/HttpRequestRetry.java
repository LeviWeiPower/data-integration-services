package com.aimspeed.gatherer.configuration.httpclient;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 多次请求连接
 * @author AimSpeed
 */
@Configuration  
public class HttpRequestRetry {  
  
    @Value("${httpclient.config.retryTime}")// 此处建议采用@ConfigurationProperties(prefix="httpclient.config")方式，方便复用  
    private int retryTime;  
     
    /**
     * 请求失败的处理策略Bean
     * @author AimSpeed
     * @Title httpRequestRetryHandler 
     * @return HttpRequestRetryHandler 
     * @date 2018年3月27日
     */
    @Bean  
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
}  