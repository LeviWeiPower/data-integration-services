package com.aimspeed.thirdparty.intf.tencent.vo.ai;

import com.aimspeed.thirdparty.intf.utils.GenerateRandomStrUtils;

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
	 * 请求时间戳（秒级）
	 */
	private String timeStamp;
	
	/**
	 * 随机字符串
	 */
	private String nonceStr;
	
	/**
	 * 签名信息
	 */
	private String sign;
	
	/**
	 * 请求数据参数的字符值
	 */
	private String reqParamValStr;
	
	public String getReqParamValStr() {
		return reqParamValStr;
	}

	public void setReqParamValStr(String reqParamValStr) {
		this.reqParamValStr = reqParamValStr;
	}

	public String getNonceStr() {
		return null == nonceStr ? GenerateRandomStrUtils.createRandomStr(28) : nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

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

	public String getTimeStamp() {
		return null == timeStamp ? System.currentTimeMillis() / 1000 + "" : timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	
	
	
}
