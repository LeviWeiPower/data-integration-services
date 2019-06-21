package com.aimspeed.thirdparty.intf.kuaiproxy.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 快代理参数
 * @author AimSpeed
 */
public class BaseKuaiProxyParamVo extends BaseVo {
	
	/** 
	 * @author AimSpeed
	 * @date 2018年8月16日 
	 */
	private static final long serialVersionUID = -3831447182363639718L;

	/**
	 * 要访问的目标网页
	 */
	private String pageUrl; 
	
	/**
	 * 代理服务器IP
	 */
    private String proxyIp; 
	
	/**
	 * 代理服务器IP
	 */
    private String proxyPort; 
	
	/**
	 * 用户名
	 */
    private String username; 
	
	/**
	 * 密码
	 */
    private String password; 
	
	/**
	 * 请求参数
	 */
    private Map<String, String> params;
	
	/**
	 * 请求头
	 */
    private Map<String, String> headers;
	
	/**
	 * 请求代理：不需要设置
	 */
    private Map<String, String> proxySettings;

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getProxyIp() {
		return proxyIp;
	}

	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
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

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getProxySettings() {
		if(null == proxySettings) {
			proxySettings = new HashMap<>();
		}
		proxySettings.put("ip", proxyIp);
        proxySettings.put("port", proxyPort);
        proxySettings.put("username", username);
        proxySettings.put("password", password);
		return proxySettings;
	}

	/*public void setProxySettings(Map<String, String> proxySettings) {
		this.proxySettings = proxySettings;
	}
	*/
	
	
	
	
}
