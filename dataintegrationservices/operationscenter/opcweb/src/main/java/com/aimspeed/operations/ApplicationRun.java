package com.aimspeed.operations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/** 
 * 项目启动类
 */
@EnableFeignClients
//只能应用在Eureka注册中心，并表单数据(@RequestBody Bean对象)传输到服务端需要单独的对相应的数据包类做处理，
//否则服务端无法接受数据，只能接受@RequestParam数据
//@EnableEurekaClient 
//基于spring-cloud-commons依赖，并且在classpath中实现； 自动适应注册中心实现注册，
//数据传输方式按照SpringMVC的方式，表单数据只需要加上@RequestBody
@EnableDiscoveryClient 
@SpringBootApplication
public class ApplicationRun extends SpringBootServletInitializer {  
    
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApplicationRun.class);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class, args);
    }
    
}

