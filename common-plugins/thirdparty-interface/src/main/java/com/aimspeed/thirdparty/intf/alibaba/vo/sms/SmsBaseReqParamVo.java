package com.aimspeed.thirdparty.intf.alibaba.vo.sms;

/**
 * 阿里云短信配置信息
 * @author AimSpeed
 */
public class SmsBaseReqParamVo {

	
	/**
	 * 产品名称:云通信短信API产品,无需替换
	 */
	private String product;
	
	/**
	 * 产品域名,无需替换
	 */
	private String domain;
	
	/**
	 * 访问的KeyId
	 */
	private String accessKeyId;
	
	/**
	 * 访问的Key密码
	 */
	private String accessKeySecret;
	
	/**
	 * 默认的链接时间
	 */
	private Integer defaultConnectTimeout;
	
	/**
	 * 默认的读取时间
	 */
	private Integer defaultReadTimeout;
	

	
	
	public SmsBaseReqParamVo() {
		super();
	}

	public SmsBaseReqParamVo(String product, String domain, String accessKeyId, String accessKeySecret,
			Integer defaultConnectTimeout, Integer defaultReadTimeout) {
		super();
		this.product = product;
		this.domain = domain;
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.defaultConnectTimeout = defaultConnectTimeout;
		this.defaultReadTimeout = defaultReadTimeout;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public Integer getDefaultConnectTimeout() {
		return defaultConnectTimeout;
	}

	public void setDefaultConnectTimeout(Integer defaultConnectTimeout) {
		this.defaultConnectTimeout = defaultConnectTimeout;
	}

	public Integer getDefaultReadTimeout() {
		return defaultReadTimeout;
	}

	public void setDefaultReadTimeout(Integer defaultReadTimeout) {
		this.defaultReadTimeout = defaultReadTimeout;
	}
	
}
