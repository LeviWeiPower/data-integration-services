package com.aimspeed.common.enums;

/** 
 * 时间类型
 * @author AimSpeed
 */
public enum TimeTypeEnum {
	
	/**
	 * 分钟
	 */
	MINUTE("minute"),
	
	/**
	 * 月
	 */
	MONTH("month"),

	/**
	 * 日
	 */
	YEAR("year");
	
	private TimeTypeEnum(String value){
		this.value = value;
	}
	
	private String value;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
