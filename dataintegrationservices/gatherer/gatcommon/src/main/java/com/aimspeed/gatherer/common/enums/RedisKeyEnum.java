package com.aimspeed.gatherer.common.enums;
/**
 * RedisKey的常量
 * @author AimSpeed
 */
public enum RedisKeyEnum {

	/**
	 * 采集任务位于缓存的前缀
	 */
	gatherer_cache_perfix("gathererTask_");
		
	/**
	 * 值
	 */
	private String value;

	private RedisKeyEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
