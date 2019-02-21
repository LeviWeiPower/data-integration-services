package com.aimspeed.gatherer.configuration.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * 
 * @author AimSpeed
 */
@Configuration
//@ComponentScan("com.aimspeed.gatherer.repository.mongo.*")
public class MongoConfiguration {
	
	/**
	 * 注入配置信息
	 * @author AimSpeed
	 * @Title getMongoSettingProperties 
	 * @return MongoSettingProperties 
	 * @date 2018年7月30日
	 */
    @Bean
    @ConfigurationProperties( prefix = "spring.mongo")
    public MongoSettingProperties getMongoSettingProperties() {
        return new MongoSettingProperties();
    }
	
    @Bean
    public MongoDbFactory mongoDbFactory() {
    	MongoSettingProperties mongoSettingProperties = getMongoSettingProperties();
    	
        //客户端配置（连接数，副本集群验证）
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(mongoSettingProperties.getMaxConnectionsPerHost());
        builder.minConnectionsPerHost(mongoSettingProperties.getMinConnectionsPerHost());
        /*if (mongoSettingProperties.getReplicaSet() != null) {
            builder.requiredReplicaSetName(mongoSettingProperties.getReplicaSet());
        }*/
        builder.threadsAllowedToBlockForConnectionMultiplier(mongoSettingProperties.getThreadsAllowedToBlockForConnectionMultiplier());
        builder.serverSelectionTimeout(mongoSettingProperties.getServerSelectionTimeout());
        builder.maxWaitTime(mongoSettingProperties.getMaxWaitTime());
        builder.maxConnectionIdleTime(mongoSettingProperties.getMaxConnectionIdleTime());
        builder.maxConnectionLifeTime(mongoSettingProperties.getMaxConnectionLifeTime());
        builder.connectTimeout(mongoSettingProperties.getConnectTimeout());
        builder.socketTimeout(mongoSettingProperties.getSocketTimeout());
//        builder.socketKeepAlive(mongoSettingProperties.getSocketKeepAlive());
//        builder.sslEnabled(mongoSettingProperties.getSslEnabled());
//        builder.sslInvalidHostNameAllowed(mongoSettingProperties.getSslInvalidHostNameAllowed());
//        builder.alwaysUseMBeans(mongoSettingProperties.getAlwaysUseMBeans());
        builder.heartbeatFrequency(mongoSettingProperties.getHeartbeatFrequency());
        builder.minHeartbeatFrequency(mongoSettingProperties.getMinHeartbeatFrequency());
        builder.heartbeatConnectTimeout(mongoSettingProperties.getHeartbeatConnectTimeout());
        builder.heartbeatSocketTimeout(mongoSettingProperties.getHeartbeatSocketTimeout());
//        builder.localThreshold(mongoSettingProperties.getLocalThreshold());

        //MongoDB客户端
        MongoClientOptions mongoClientOptions = builder.build();
        
        // MongoDB地址列表
        List<ServerAddress> serverAddresses = new ArrayList<>();
        for (String address : mongoSettingProperties.getAddress()) {
            String[] hostAndPort = address.split(":");
            String host = hostAndPort[0];
            Integer port = Integer.parseInt(hostAndPort[1]);
            ServerAddress serverAddress = new ServerAddress(host, port);
            serverAddresses.add(serverAddress);
        }
        
        //连接认证
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(
        									mongoSettingProperties.getUsername(),
        									null != mongoSettingProperties.getAuthenticationDatabase() ? 
        											mongoSettingProperties.getAuthenticationDatabase() 
        											: 
        											mongoSettingProperties.getDatabase(),
        									mongoSettingProperties.getPassword().toCharArray());
		List<MongoCredential> mongoCredentials = new ArrayList<>();
		mongoCredentials.add(mongoCredential);

		//创建客户端和Factory   
        MongoClient mongoClient = new MongoClient(serverAddresses, mongoCredential, mongoClientOptions);
		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, mongoSettingProperties.getDatabase());
        return mongoDbFactory;
    }
    
    
    
    
    
    
    
    
    
	
}
