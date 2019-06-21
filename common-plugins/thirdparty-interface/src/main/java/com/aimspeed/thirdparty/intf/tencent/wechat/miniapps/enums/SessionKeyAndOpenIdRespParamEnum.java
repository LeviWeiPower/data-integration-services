package com.aimspeed.thirdparty.intf.tencent.wechat.miniapps.enums;

/**
 * 小程序SessionKey接口的枚举
 * @author AimSpeed
 */
public enum SessionKeyAndOpenIdRespParamEnum {
	/**
	 * 开发者设置中的appId 
	 */
	openId("openid"),
	
	/**
	 * 开发者设置中的appSecret 
	 */
	sessionKey("session_key"),
	
	/**
	 * 如果开发者拥有多个移动应用、网站应用、和公众帐号（包括小程序），
	 * 可通过unionid来区分用户的唯一性，因为只要是同一个微信开放平台帐号下的移动应用、网站应用和公众帐号（包括小程序），
	 * 用户的unionid是唯一的。换句话说，同一用户，对同一个微信开放平台下的不同应用，unionid是相同的。
	 * unionid：如果是一个用户有多少个，那么则加上unionid在加上openId则是唯一
	 */
	unionid("unionid"),

	/**
	 * 错误编码
	 */
	errCode("errcode"),
	
	/**
	 * 错误响应信息
	 */
	errMsg("errmsg");
	
	private String value;
	
	private SessionKeyAndOpenIdRespParamEnum(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
