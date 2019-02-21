package com.aimspeed.common.enums;
/**
 * 系统默认响应信息
 * @author AimSpeed
 */
public enum HttpResponseCurdEnum {
	
	/**
	 * 保存成功
	 */
	SAVE_SUCCESS("保存成功!"),
	
	/**
	 * 保存失败
	 */
	SAVE_FAIL("保存失败!"),
	
	/**
	 * 删除成功
	 */
	DELETE_SUCCESS("删除成功!"),
	
	/**
	 * 删除失败
	 */
	DELETE_FAIL("删除失败!"),
	
	/**
	 * 更新成功
	 */
	UPDATE_SUCCESS("更新成功!"),
	
	/**
	 * 更新失败
	 */
	UPDATE_FAIL("更新失败!"),
	
	/**
	 * 数据已存在
	 */
	LOG_EXIST("记录已存在!"),
	
	/**
	 * 数据不存在
	 */
	LOG_NOT_EXIST("记录不存在!"),
	
	/**
	 * 数据已存在
	 */
	VERIFY_SUCCESS("验证成功!"),
	
	/**
	 * 数据不存在
	 */
	VERIFY_FAIL("验证失败!");
	
	/**
	 * 值
	 */
	private String value;


	private HttpResponseCurdEnum(String value) {
		this.value = value;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	

	
}
