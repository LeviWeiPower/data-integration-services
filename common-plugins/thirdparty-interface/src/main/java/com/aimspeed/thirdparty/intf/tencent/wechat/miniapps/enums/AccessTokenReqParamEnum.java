package com.aimspeed.thirdparty.intf.tencent.wechat.miniapps.enums;

/** 
 * 小程序访问令牌请求参数
 * @author AimSpeed
 */
public enum AccessTokenReqParamEnum {
	
	/**
	 * 开发者设置中的appId 
	 */
	appId("appid"),
	
	/**
	 * 开发者设置中的appSecret 
	 */
	secret("secret"),
	
	/**
	 * 默认参数 
	 */
	grantType("grant_type");
	
	private String value;
	
	private AccessTokenReqParamEnum(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
}
