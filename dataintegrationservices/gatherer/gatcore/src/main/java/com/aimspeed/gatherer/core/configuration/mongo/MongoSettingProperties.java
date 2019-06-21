package com.aimspeed.gatherer.core.configuration.mongo;

import java.util.List;

/**
 * MongDB配置
 * @author AimSpeed
 */
public class MongoSettingProperties {
	
	private String authenticationDatabase;
    private String database;
    private List<String> address;
//    private String replicaSet;
    private String username;
    private String password;
    private Integer minConnectionsPerHost;
    private Integer maxConnectionsPerHost;
    private Integer threadsAllowedToBlockForConnectionMultiplier;
    private Integer serverSelectionTimeout;
    private Integer maxWaitTime;
    private Integer maxConnectionIdleTime;
    private Integer maxConnectionLifeTime;
    private Integer connectTimeout;
    private Integer socketTimeout;
    private Boolean socketKeepAlive;
    private Boolean sslEnabled;
    private Boolean sslInvalidHostNameAllowed;
    private Boolean alwaysUseMBeans;
    private Integer heartbeatFrequency;
    private Integer minHeartbeatFrequency;
    private Integer heartbeatConnectTimeout;
    private Integer heartbeatSocketTimeout;
    private Integer localThreshold;
    
	public MongoSettingProperties() {
		super();
	}
	public String getAuthenticationDatabase() {
		return authenticationDatabase;
	}
	public void setAuthenticationDatabase(String authenticationDatabase) {
		this.authenticationDatabase = authenticationDatabase;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public List<String> getAddress() {
		return address;
	}
	public void setAddress(List<String> address) {
		this.address = address;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getMinConnectionsPerHost() {
		return minConnectionsPerHost;
	}
	public void setMinConnectionsPerHost(Integer minConnectionsPerHost) {
		this.minConnectionsPerHost = minConnectionsPerHost;
	}
	public Integer getMaxConnectionsPerHost() {
		return maxConnectionsPerHost;
	}
	public void setMaxConnectionsPerHost(Integer maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}
	public Integer getThreadsAllowedToBlockForConnectionMultiplier() {
		return threadsAllowedToBlockForConnectionMultiplier;
	}
	public void setThreadsAllowedToBlockForConnectionMultiplier(Integer threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}
	public Integer getServerSelectionTimeout() {
		return serverSelectionTimeout;
	}
	public void setServerSelectionTimeout(Integer serverSelectionTimeout) {
		this.serverSelectionTimeout = serverSelectionTimeout;
	}
	public Integer getMaxWaitTime() {
		return maxWaitTime;
	}
	public void setMaxWaitTime(Integer maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}
	public Integer getMaxConnectionIdleTime() {
		return maxConnectionIdleTime;
	}
	public void setMaxConnectionIdleTime(Integer maxConnectionIdleTime) {
		this.maxConnectionIdleTime = maxConnectionIdleTime;
	}
	public Integer getMaxConnectionLifeTime() {
		return maxConnectionLifeTime;
	}
	public void setMaxConnectionLifeTime(Integer maxConnectionLifeTime) {
		this.maxConnectionLifeTime = maxConnectionLifeTime;
	}
	public Integer getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public Integer getSocketTimeout() {
		return socketTimeout;
	}
	public void setSocketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	public Boolean getSocketKeepAlive() {
		return socketKeepAlive;
	}
	public void setSocketKeepAlive(Boolean socketKeepAlive) {
		this.socketKeepAlive = socketKeepAlive;
	}
	public Boolean getSslEnabled() {
		return sslEnabled;
	}
	public void setSslEnabled(Boolean sslEnabled) {
		this.sslEnabled = sslEnabled;
	}
	public Boolean getSslInvalidHostNameAllowed() {
		return sslInvalidHostNameAllowed;
	}
	public void setSslInvalidHostNameAllowed(Boolean sslInvalidHostNameAllowed) {
		this.sslInvalidHostNameAllowed = sslInvalidHostNameAllowed;
	}
	public Boolean getAlwaysUseMBeans() {
		return alwaysUseMBeans;
	}
	public void setAlwaysUseMBeans(Boolean alwaysUseMBeans) {
		this.alwaysUseMBeans = alwaysUseMBeans;
	}
	public Integer getHeartbeatFrequency() {
		return heartbeatFrequency;
	}
	public void setHeartbeatFrequency(Integer heartbeatFrequency) {
		this.heartbeatFrequency = heartbeatFrequency;
	}
	public Integer getMinHeartbeatFrequency() {
		return minHeartbeatFrequency;
	}
	public void setMinHeartbeatFrequency(Integer minHeartbeatFrequency) {
		this.minHeartbeatFrequency = minHeartbeatFrequency;
	}
	public Integer getHeartbeatConnectTimeout() {
		return heartbeatConnectTimeout;
	}
	public void setHeartbeatConnectTimeout(Integer heartbeatConnectTimeout) {
		this.heartbeatConnectTimeout = heartbeatConnectTimeout;
	}
	public Integer getHeartbeatSocketTimeout() {
		return heartbeatSocketTimeout;
	}
	public void setHeartbeatSocketTimeout(Integer heartbeatSocketTimeout) {
		this.heartbeatSocketTimeout = heartbeatSocketTimeout;
	}
	public Integer getLocalThreshold() {
		return localThreshold;
	}
	public void setLocalThreshold(Integer localThreshold) {
		this.localThreshold = localThreshold;
	}
	
	
}
