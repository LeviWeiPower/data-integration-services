package com.aimspeed.common.enums;
/**
 * 请求方法
 * @author AimSpeed
 */
public enum HttpRequestMethodEnum {

	/**
	 * 是
	 */
	GET("GET"),
	
	/**
	 * 否
	 */
	POST("POST");
	

	/**
	 * 值
	 */
	private String value;

	private HttpRequestMethodEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	
	
}
