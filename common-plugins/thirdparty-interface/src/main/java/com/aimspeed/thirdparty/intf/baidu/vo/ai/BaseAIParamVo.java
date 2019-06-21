package com.aimspeed.thirdparty.intf.baidu.vo.ai;

/**
 * 基础的AI平台请求参数
 * @author AimSpeed
 */
public class BaseAIParamVo {
	
	/**
	 * @date 2018年8月16日 上午12:48:02
	 */ 
	private static final long serialVersionUID = 2202609740672364836L;

	/**
	 * APP ID
	 */
	private String appId;
	
	/**
	 * APP密钥
	 */
	private String appKey;
	
	/**
	 * 安全码
	 */
	private String secretKey;
	
	/**
	 * 请求数据参数的字符值
	 */
	private String reqParamValStr;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getReqParamValStr() {
		return reqParamValStr;
	}

	public void setReqParamValStr(String reqParamValStr) {
		this.reqParamValStr = reqParamValStr;
	}
	
	
	
}
