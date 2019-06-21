package com.aimspeed.common.enums;

/**
 * 默认的字段枚举类
 * @author AimSpeed
 */
public enum DefFieldNameEnum {

	
	ID("id"),
	
	/**
	 * 创建时间
	 */
	CREATE_TIME("createTime"),

	/**
	 * 创建人
	 */
	CREATOR("creator"),
	
	/**
	 * 更新时间
	 */
	UPDATE_TIME("updateTime"),

	/**
	 * 更新人
	 */
	UPDATOR("updator"),
	
	/**
	 * 是否删除
	 */
	IS_DELETE("isDelete");
		
	/**
	 * 值
	 */
	private String value;

	private DefFieldNameEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
