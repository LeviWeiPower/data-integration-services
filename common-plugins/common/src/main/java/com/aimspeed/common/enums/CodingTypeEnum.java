package com.aimspeed.common.enums;

/** 
 * 编码类型
 * @author AimSpeed
 */
public enum CodingTypeEnum {
	
	/**
	 * UTF-8
	 */
	UTF_8("utf-8"),
	
	/**
	 * UTF8
	 */
	UTF8("utf8"),
	
	/**
	 * GBK
	 */
	GBK("gbk"),
	
	/**
	 * GB2312
	 */
	GB2312("gb2312"),
	
	/**
	 * GB18030
	 */
	GB18030("gb18030"),
	
	/**
	 * ISO-8859-1
	 */
	ISO_8859_1("iso-8859-1");

	private String value;
	
	private CodingTypeEnum(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
