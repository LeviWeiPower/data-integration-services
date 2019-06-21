package com.aimspeed.operations.entity.vo.api;

import java.util.Map;

/**
 * 
 * @author AimSpeed
 */
public class SSOApiBaseParamVo {
	
	/**
	 * sequenceCode+pubKey组合加密后的字符串
	 */
	private String sequenceCode;
	
	/**
	 * 运营系统位于单点登录系统的pubKey
	 */
	private String pubKey;

	/**
	 * 运营系统位于单点登录系统的序列码
	 * 原始的sequenceCode
	 */
	private String sourceSequenceCode;
	
	/**
	 * 私钥
	 */
	private String priKey;

	/**
	 * 多个参数
	 */
	private Map<String, Object> params;

	public SSOApiBaseParamVo() {
		super();
	}
	
	public String getSequenceCode() {
		return sequenceCode;
	}

	public void setSequenceCode(String sequenceCode) {
		this.sequenceCode = sequenceCode;
	}

	public String getPubKey() {
		return pubKey;
	}

	public void setPubKey(String pubKey) {
		this.pubKey = pubKey;
	}

	public String getSourceSequenceCode() {
		return sourceSequenceCode;
	}

	public void setSourceSequenceCode(String sourceSequenceCode) {
		this.sourceSequenceCode = sourceSequenceCode;
	}

	public String getPriKey() {
		return priKey;
	}

	public void setPriKey(String priKey) {
		this.priKey = priKey;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	
	
}
