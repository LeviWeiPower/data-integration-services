package com.aimspeed.thirdparty.intf.tencent.vo.wechat.token;

/**
 * 小程序访问令牌请求接口的参数
 * @author AimSpeed
 */
public class AccessTokenReqParamVo {
	
	/**
	 * 小程序唯一标识
	 */
	private String appId;
	
	/**
	 * 小程序的 app secret
	 */
	private String secret;
	
	/**
	 * 填写为 authorization_code
	 */
	private String grantType;

	public AccessTokenReqParamVo() {
		super();
	}

	public AccessTokenReqParamVo(String appId, String secret, String grantType) {
		super();
		this.appId = appId;
		this.secret = secret;
		this.grantType = grantType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}


	
	
	
}
