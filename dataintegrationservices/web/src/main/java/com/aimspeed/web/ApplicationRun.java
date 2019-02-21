package com.aimspeed.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * 启动项目控制类
 *
 * @author AimSpeed
 */
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

