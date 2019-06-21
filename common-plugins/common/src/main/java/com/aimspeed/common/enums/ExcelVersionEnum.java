package com.aimspeed.common.enums;

/**
 * Excel版本常量
 * @author AimSpeed
 */
public enum ExcelVersionEnum {
	

	/**
	 * 导出的版本 - 97
	 */
	XLS(97),
	
	/**
	 * 导出的版本 - 2007或以上
	 */
	XLSX(2007);

	private Integer value;
	
	private ExcelVersionEnum(Integer value){
		this.value = value;
	}
	
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
	
	
	
}
