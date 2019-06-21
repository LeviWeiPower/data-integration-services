package com.aimspeed.thirdparty.intf.tencent.vo.wechat.token;

/**
 * 小程序访问令牌请求接口的返回数据
 * @author AimSpeed
 */
public class AccessTokenRespVo {
	
	/**
	 * 获取到的凭证
	 */
	private String accessToken;
	
	/**
	 * 凭证有效时间，单位：秒
	 */
	private Integer expiresIn;

	/**
	 * 错误代码：
	 * openId或sessionKey为空，那么则是错误了。
	 * 示例：{"errcode": 40013, "errmsg": "invalid appid"}
	 */
	private String errCode;
		
	/**
	 * 错误提示信息：
	 * openId或sessionKey为空，那么则是错误了。
	 * 示例：{"errcode": 40013, "errmsg": "invalid appid"}
	 */
	private String errMsg;

	public AccessTokenRespVo() {
		super();
	}

	public AccessTokenRespVo(String accessToken, Integer expiresIn, String errCode, String errMsg) {
		super();
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
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
		return "AccessTokenRespVo [accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", errCode=" + errCode
				+ ", errMsg=" + errMsg + "]";
	}

	
	
	
}
