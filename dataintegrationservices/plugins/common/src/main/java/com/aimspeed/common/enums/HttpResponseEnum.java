package com.aimspeed.common.enums;

/**
 * 系统响应码
 * @author AimSpeed
 */
public enum HttpResponseEnum {
	
	/**
	 * 成功
	 */
	SUCCESS(2000,"操作成功"),
	
	/**
	 * 系统错误
	 */
	FAIL(5000,"系统错误，请联系管理员!"),
	
	/**
	 * 认证失败 
	 */
	UNAUT(6000,"认证失败，请联系管理员!"),
	
	/**
	 * 重定向
	 */
	REDIRECT(6001,"重定向"),
	
	/**
	 * 参数错误
	 */
	PARAM_ERROR(3001,"参数错误，请检查!"),
	
	/**
	 * 业务逻辑错误
	 */
	BUSINESS_ERROR(3002,"业务出错，请联系管理员!");
	

	/**
	 * 响应吗
	 */
	private Integer code;
	
	/**
	 * 默认响应信息
	 */
	private String value;

	private HttpResponseEnum(Integer code, String value) {
		this.code = code;
		this.value = value;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	

	
}
