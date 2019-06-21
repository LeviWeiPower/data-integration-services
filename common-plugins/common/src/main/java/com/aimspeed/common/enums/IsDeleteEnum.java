package com.aimspeed.common.enums;

/**
 * 是否删除标记
 * @author AimSpeed
 */
public enum IsDeleteEnum {
	

	/**
	 * 是
	 */
	Y("Y"),
	
	/**
	 * 否
	 */
	N("N");
	

	/**
	 * 值
	 */
	private String value;

	private IsDeleteEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	
	
}
