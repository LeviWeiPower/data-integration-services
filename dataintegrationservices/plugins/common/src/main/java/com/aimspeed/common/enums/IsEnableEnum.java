package com.aimspeed.common.enums;

/**
 * 是否删除标记
 * @author AimSpeed
 */
public enum IsEnableEnum {
	

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

	private IsEnableEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	
	
}
