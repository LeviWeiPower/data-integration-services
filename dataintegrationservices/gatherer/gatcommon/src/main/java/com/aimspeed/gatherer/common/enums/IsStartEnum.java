package com.aimspeed.gatherer.common.enums;


/**
 * 是否开启任务标识
 * @author AimSpeed
 */
public enum IsStartEnum {

	/**
	 * 是
	 */
	Y("Y"),
	
	/**
	 * 否
	 */
	N("N");
	
	/**
	 * 请求包头的Value
	 */
	private String value;

	private IsStartEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	
	
}
