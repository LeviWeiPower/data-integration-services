package com.aimspeed.common.datatype;

import java.util.Date;

/**
 * 数据类型转换类
 * @author AimSpeed
 */
public class DataTypeChangeUtils {
	/*public static void main(String[] args) {
		String time  = new Date().toString();
		System.out.println(time);
		System.out.println(new Date(System.currentTimeMillis()));
		
	}*/

	/**
	 * 类型转换
	 * @param javaTypeSimpleName 要转换的类型标识
	 * @author AimSpeed
	 * @param value 需要转换的值
	 * @return Object 转换后的对象
	 */
	public static Object changeType(String javaTypeSimpleName, String value){
		if(javaTypeSimpleName.equalsIgnoreCase("Integer")){
			value = !value.contains(".")? value : value.substring(0,value.indexOf("."));
			return Integer.parseInt(value);
			
		}else if(javaTypeSimpleName.equalsIgnoreCase("Double")){
			value = value.contains(".")? value : value+".00";
			return Double.parseDouble(value);
			
		}else if(javaTypeSimpleName.equalsIgnoreCase("Byte")){
			return Byte.parseByte(value);
			
		}else if(javaTypeSimpleName.equalsIgnoreCase("Short")){
			return Short.parseShort(value);
		
		}else if(javaTypeSimpleName.equalsIgnoreCase("Long")){
			return Long.parseLong(value);
			
		}else if(javaTypeSimpleName.equalsIgnoreCase("Boolean")){
			return Boolean.parseBoolean(value);
		
		}else if(javaTypeSimpleName.equalsIgnoreCase("BigDecimal")){
			value = value.contains(".")? value : value+".00";
			return java.math.BigDecimal.valueOf(Double.parseDouble(value));
		
		}else if(javaTypeSimpleName.equalsIgnoreCase("Float")){
			value = value.contains(".")? value : value+".00";
			return Float.parseFloat(value);
		
		}else if(javaTypeSimpleName.equalsIgnoreCase("char")){
			return value.charAt(0);
		
		}else if(javaTypeSimpleName.equalsIgnoreCase("Date")){
			return new Date(value);
		
		}else{
			return value;
		} 
	}
	
	/**
	 * 将字符串数组类型转为数值类型
	 * @author AimSpeed
	 * @param strArr 需要转换的字符串数组
	 * @return Integer[] 转换后的数值数组
	 */
	public static Integer [] strArrToIntArr(String [] strArr) {
		Integer [] integers = new Integer[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			integers[i] = Integer.parseInt(strArr[i]);
		}
		return integers;
	}
	

	/**
	 * 转换为Date类型
	 * @author AimSpeed
	 * @param obj 需要转换的对象
	 * @return Date 转换后的时间对象
	 */
	public static Date toDate(Object obj) {
		if (obj == null) {
			return null;// null默认返回值
		}

		if (obj instanceof Date) {
			return (Date) obj;
		}

		// 不支持的类型转换
		return null;
	}
	
}
