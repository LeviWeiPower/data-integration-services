package com.aimspeed.common.enums;


/**
 * 请求包头的字段和默认值
 * @author AimSpeed
 */
public enum HttpRequestHeaderEnum {

	/**
	 * 接收的类型资源 - 默认是所有
	 */
	ACCEPT("Accept","*/*"),
	
	/**
	 * 声明浏览器支持的编码类型的
	 */
	ACCEPT_ENCODING("Accept-Encoding","gzip, deflate"),
	
	/**
	 * 告诉服务器浏览器可以支持什么语言
	 */
	ACCEPT_LANGUAGE("Accept-Language","zh-CN,zh;q=0.9"),

	/**
	 * 链接时间 - 长链接
	 */
	CONNECTION("Connection","keep-alive"),
	
	/**
	 * 使用的浏览器版本号 
	 */
	USER_AGENT("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
	
	
	private HttpRequestHeaderEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * 请求包头的Key
	 */
	private String key;

	/**
	 * 请求包头的Value
	 */
	private String value;

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
