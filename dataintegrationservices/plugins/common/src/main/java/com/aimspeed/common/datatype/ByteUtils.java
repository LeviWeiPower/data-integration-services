package com.aimspeed.common.datatype;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;

/**
 * Byte数据类型工具类 
 * @author AimSpeed
 */
public abstract class ByteUtils {
	
	/**
	 * 将Byte数据类型的Map转换为String类型的Map
	 * @author AimSpeed
	 * @param map 需要转换的Map
	 * @return Map<String,String> 转换为字符串的Map 
	 */
	public static Map<String, String> changeStringMap(Map<byte[], byte[]> map){
		Map<String, String> mapString = new HashMap<>();
		for (Entry<byte[], byte[]> entry : map.entrySet()) {
			mapString.put(new String(entry.getKey()), new String(entry.getValue()));
		}
		return mapString;
	}
	
	/**
	 * 将Byte数据类型的Map转换为，Key为类对象 - Value为String类型的Map，而需要转换的Map的Key转换为字符串必须要是对应的类的Json字符串
	 * @author AimSpeed
	 * @param map 需要转换的Map
	 * @param clazz 转换的类的类型
	 * @return Map<T,String> 转换后的Map
	 */
	public static <T> Map<T, String> changeMapKeyClassForJson(Map<byte[], byte[]> map,Class<T> clazz){
		Map<T, String> mapString = new HashMap<>();
		for (Entry<byte[], byte[]> entry : map.entrySet()) {
			mapString.put(JSON.parseObject(new String(entry.getKey()),clazz), new String(entry.getValue()));
		}
		return mapString;
	}
	
	/**
	 * 将Byte数据类型的Map转换为，Value为类对象 - Key为String类型的Map，而需要转换的Map的Value转换为字符串必须要是对应的类的Json字符串
	 * @author AimSpeed
	 * @param map 需要转换的Map
	 * @param clazz 转换的类的类型
	 * @return Map<T,String> 转换后的Map
	 */
	public static <T> Map<String, T> changeMapValueClassForJson(Map<byte[], byte[]> map,Class<T> clazz){
		Map<String, T> mapString = new HashMap<>();
		for (Entry<byte[], byte[]> entry : map.entrySet()) {
			mapString.put(new String(entry.getValue()),JSON.parseObject(new String(entry.getKey()),clazz));
		}
		return mapString;
	}
	
}
