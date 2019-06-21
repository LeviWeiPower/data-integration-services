package com.aimspeed.thirdparty.intf.tencent.wechat.miniapps.enums;

/**
 * 小程序SessionKey接口的枚举
 * @author AimSpeed
 */
public enum SessionKeyAndOpenIdReqParamEnum {

	/**
	 * 开发者设置中的appId 
	 */
	appId("appid"),
	
	/**
	 * 开发者设置中的appSecret 
	 */
	secret("secret"),
	
	/**
	 * 小程序调用wx.login返回的code 
	 */
	jsCode("js_code"),
	
	/**
	 * 默认参数 
	 */
	grantType("grant_type");
	
	private String value;
	
	private SessionKeyAndOpenIdReqParamEnum(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
