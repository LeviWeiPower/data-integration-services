package com.aimspeed.gatherer.configuration.httpclient;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 连接策略
 * @author AimSpeed
 */
@Configuration
public class ConnectionStrategy {
	
	@Value("${httpclient.config.keepAliveTime}")  
    private int keepAliveTime = 30;  
     
	/**
	 * 定义连接策略
	 * @author AimSpeed
	 * @Title connectionKeepAliveStrategy 
	 * @return ConnectionKeepAliveStrategy 
	 * @date 2018年3月27日
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
	
}
