package com.aimspeed.server.eureka;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/** 
 * 
 * @author AimSpeed
 */
@EnableEurekaServer 
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

