package com.aimspeed.gatherer.common.enums;
/**
 * 编码常量
 * @author AimSpeed
 */
public enum CodingEnum {

	UTF_8("utf-8"),
	UTF8("utf8"),
	GBK("gbk"),
	GB2312("gb2312"),
	GB18030("gb18030"),
	ISO_8859_1("iso-8859-1");
		
	/**
	 * 值
	 */
	private String value;

	private CodingEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
