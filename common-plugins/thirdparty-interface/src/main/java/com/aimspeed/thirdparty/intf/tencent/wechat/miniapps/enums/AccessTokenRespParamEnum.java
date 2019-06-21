package com.aimspeed.thirdparty.intf.tencent.wechat.miniapps.enums;

/** 
 * 小程序访问令牌请求参数
 * @author AimSpeed
 */
public enum AccessTokenRespParamEnum {

	/**
	 * 成功访问的Token
	 */
	accessToken("access_token"),
	
	/**
	 * 有效时间 
	 */
	expiresIn("expires_in"),

	/**
	 * 错误编码
	 */
	errCode("errcode"),
	
	/**
	 * 错误响应信息
	 */
	errMsg("errmsg");
	
	private String value;
	
	private AccessTokenRespParamEnum(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
}
