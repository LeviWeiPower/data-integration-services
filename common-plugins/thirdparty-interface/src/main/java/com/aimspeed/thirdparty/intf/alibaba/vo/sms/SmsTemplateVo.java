package com.aimspeed.thirdparty.intf.alibaba.vo.sms;

/**
 * 阿里云配置信息传输对象
 * @author AimSpeed
 */
public class SmsTemplateVo {

	/**
	 * 短信签名
	 */
	private String signName;
	
	/**
	 * 面板Code
	 */
	private String templateCode;

	
	
	public SmsTemplateVo() {
		super();
	}

	public SmsTemplateVo(String signName, String templateCode) {
		super();
		this.signName = signName;
		this.templateCode = templateCode;
	}

	public String getTemplateCode() {
		return templateCode;
	}


	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}


	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	
	
}
