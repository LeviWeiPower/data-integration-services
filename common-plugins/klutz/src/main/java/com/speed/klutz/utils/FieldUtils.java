package com.speed.klutz.utils;

/**
 * 字段处理工具类
 * @author AimSpeed
 */
public abstract class FieldUtils {
	
	/**
	 * 驼峰规则进行切割处理数据库字段信息，将其转换为Java规则
	 * @author AimSpeed
	 * @param field
	 * @return String 
	 */
	public static String ddatabaseFieldToJava( String field ) {
        StringBuffer sb = new StringBuffer(field.length());
        //field = field.toLowerCase();
        String[] fields = field.split("_");//根据驼峰规则进行切割
        String temp = null;
        sb.append(fields[0]);
        for ( int i = 1 ; i < fields.length ; i++ ) {
            temp = fields[i].trim();
            //基于驼峰规则：首字母大写
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        return sb.toString();
    }
	
}
