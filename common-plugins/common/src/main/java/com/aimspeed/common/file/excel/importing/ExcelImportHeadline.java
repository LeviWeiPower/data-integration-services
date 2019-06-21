package com.aimspeed.common.file.excel.importing;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel解析标识的类参数对应的行
 * @author AimSpeed
 */
@Target(ElementType.FIELD)//只用在参数
@Retention(RetentionPolicy.RUNTIME) //运行时才启用
@Documented
public @interface ExcelImportHeadline {
	
	/**
	 * 标题名
	 * @author AimSpeed
	 * @return String 
	 */
	String headlineName() default "";
	
	/**
	 * 索引位置，从0算起
	 * @author AimSpeed
	 * @return int 
	 */
	int row() default 0;
	
	
	
}
