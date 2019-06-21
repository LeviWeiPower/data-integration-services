package com.aimspeed.thirdparty.intf.expressage.vo;

/**
 * 快递单号代码类
 * @author AimSpeed
 */
public class CodeDiscernVo {
	
	/**
	 * 订单号
	 */
	private String logisticCode;
	
	/**
	 * 业务ID，忽略
	 */
	private String eBusinessID;

	/**
	 * 响应码：100为成功
	 */
	private String code;
	
	/**
	 * 快递英文名
	 */
	private String shipperNameEn;
	
	/**
	 * 快递英文编码：用于后续的即时查询快递编码
	 */
	private String shipperCodeEn;

	/**
	 * 快递中文名
	 */
	private String shipperNameChinese;

	/**
	 * 快递中文编码
	 */
	private String shipperCodeChinese;

	public String getLogisticCode() {
		return logisticCode;
	}

	public void setLogisticCode(String logisticCode) {
		this.logisticCode = logisticCode;
	}

	public String geteBusinessID() {
		return eBusinessID;
	}

	public void seteBusinessID(String eBusinessID) {
		this.eBusinessID = eBusinessID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShipperNameEn() {
		return shipperNameEn;
	}

	public void setShipperNameEn(String shipperNameEn) {
		this.shipperNameEn = shipperNameEn;
	}

	public String getShipperCodeEn() {
		return shipperCodeEn;
	}

	public void setShipperCodeEn(String shipperCodeEn) {
		this.shipperCodeEn = shipperCodeEn;
	}

	public String getShipperNameChinese() {
		return shipperNameChinese;
	}

	public void setShipperNameChinese(String shipperNameChinese) {
		this.shipperNameChinese = shipperNameChinese;
	}

	public String getShipperCodeChinese() {
		return shipperCodeChinese;
	}

	public void setShipperCodeChinese(String shipperCodeChinese) {
		this.shipperCodeChinese = shipperCodeChinese;
	}
	
	
	
}
