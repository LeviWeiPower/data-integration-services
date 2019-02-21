package com.aimspeed.gatherer.configuration.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.aimspeed.redis.RedisMapper;

import redis.clients.jedis.JedisPoolConfig;
/** 
 * @author AimSpeed
 */
@Configuration  
@EnableAutoConfiguration  
public class RedisConfig {  
  
    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);  
      
    @Bean  
    @ConfigurationProperties(prefix="spring.redis")  
    public JedisPoolConfig getRedisConfig(){  
        JedisPoolConfig config = new JedisPoolConfig();  
        return config;  
    }  
      
    @Bean  
    @ConfigurationProperties(prefix="spring.redis")  
    public JedisConnectionFactory getConnectionFactory(){  
        JedisConnectionFactory factory = new JedisConnectionFactory();  
        JedisPoolConfig config = getRedisConfig();  
        factory.setPoolConfig(config);  
        logger.info("JedisConnectionFactory bean init success.");  
        return factory;  
    }  
    
    /**
     * Reids 操作模板
     * @author AimSpeed
     * @Title getRedisTemplate 
     * @return RedisTemplate<?,?>  
     * @date 2018年8月19日 下午11:22:04
     */
    @Bean  
    public RedisTemplate<?, ?> getRedisTemplate(){  
        RedisTemplate<?,?> template = new StringRedisTemplate(getConnectionFactory());  
        return template;  
    }  
    
    /**
     * Redis 仓库操作
     * @author AimSpeed
     * @Title getRedisRepository 
     * @return RedisRepository  
     * @date 2018年8月19日 下午11:22:13
     */
    @Bean  
    public RedisMapper getRedisRepository(){ 
    	return new RedisMapper();  
    }  
    
    
    
}  
