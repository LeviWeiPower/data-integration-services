package com.aimspeed.thirdparty.intf.tencent.vo.wechat.token;

/**
 * 小程序SessionKey接口的响应数据
 * @author AimSpeed
 */
public class SessionKeyAndOpenIdRespVo {
	
	/**
	 * openId：位于小程序中用户是唯一的
	 * openId或sessionKey为空，那么则是错误了。
	 */
	private String openId;
	
	/**
	 * 会话Key
	 * openId或sessionKey为空，那么则是错误了。
	 */
	private String sessionKey;

	/**
	 * 如果开发者拥有多个移动应用、网站应用、和公众帐号（包括小程序），
	 * 可通过unionid来区分用户的唯一性，因为只要是同一个微信开放平台帐号下的移动应用、网站应用和公众帐号（包括小程序），
	 * 用户的unionid是唯一的。换句话说，同一用户，对同一个微信开放平台下的不同应用，unionid是相同的。
	 * unionid：如果是一个用户有多少个，那么则加上unionid在加上openId则是唯一
	 */
	private String unionId;

	/**
	 * 错误代码：
	 * openId或sessionKey为空，那么则是错误了。
	 * 示例： {"errcode": 40029, "errmsg": "invalid code" }
	 */
	private String errCode;
		
	/**
	 * 错误提示信息：
	 * openId或sessionKey为空，那么则是错误了。
	 * 示例：{"errcode": 40029, "errmsg": "invalid code" }
	 */
	private String errMsg;

	public SessionKeyAndOpenIdRespVo() {
		super();
	}

	public SessionKeyAndOpenIdRespVo(String openId, String sessionKey, String unionId, String errCode, String errMsg) {
		super();
		this.openId = openId;
		this.sessionKey = sessionKey;
		this.unionId = unionId;
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	@Override
	public String toString() {
		return "SessionKeyAndOpenIdRespVo [openId=" + openId + ", sessionKey=" + sessionKey + ", unionId=" + unionId
				+ ", errCode=" + errCode + ", errMsg=" + errMsg + "]";
	}

	
	
	
}
