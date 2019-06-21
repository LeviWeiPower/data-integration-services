package com.aimspeed.common.datatype;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON操作工具类（转换...）
 * @author AimSpeed
 */
public class JsonUtils {

	private static Gson gson = null;
	
	/**
	 * 设置时间戳，创建对象
	 */
	static {
		if (gson == null) {
			gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		}
	}

	/**
	 * JSON转换为Map对象
	 * @author AimSpeed
	 * @param jsonStr JSON字符串
	 * @return Map<String,Object> 结果数据 
	 */
	public static Map<String, Object> jsonToMap(String jsonStr) {
		Map<String, Object> objMap = null;
		if (gson != null) {
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<String, Object>>() {
			}.getType();
			objMap = gson.fromJson(jsonStr, type);
		}
		return objMap;
	}
	
	/**
	 * JSON转换为Map对象
	 * @author AimSpeed
	 * @param jsonStr JSON字符串
	 * @return Map<String,String> 结果数据 
	 */
	public static Map<String, String> strJsonToMap(String jsonStr) {
		Map<String, String> objMap = null;
		if (gson != null) {
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<String, String>>() {
			}.getType();
			objMap = gson.fromJson(jsonStr, type);
		}
		return objMap;
	}

	
}
