package com.aimspeed.thirdparty.intf.tencent.vo.wechat.token;

/**
 * 小程序SessionKey接口的参数
 * @author AimSpeed
 */
public class SessionKeyAndOpenIdReqParamVo {

	/**
	 * 小程序唯一标识
	 */
	private String appId;
	
	/**
	 * 小程序的 app secret
	 */
	private String secret;

	/**
	 * 登录时获取的 code
	 */
	private String jsCode;
	
	/**
	 * 填写为 authorization_code
	 */
	private String grantType;

	public SessionKeyAndOpenIdReqParamVo() {
		super();
	}

	

	public SessionKeyAndOpenIdReqParamVo(String appId, String secret, String jsCode, String grantType) {
		super();
		this.appId = appId;
		this.secret = secret;
		this.jsCode = jsCode;
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

	public String getJsCode() {
		return jsCode;
	}

	public void setJsCode(String jsCode) {
		this.jsCode = jsCode;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	
	
	
	
}
