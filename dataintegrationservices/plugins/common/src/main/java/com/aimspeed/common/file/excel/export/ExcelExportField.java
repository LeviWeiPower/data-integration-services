package com.aimspeed.common.file.excel.export;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 要导出的Excel字段，可以配置对应的字段导出的位置和标题，如果没有配置导出的位置和标题，那么则按照读取的类的字段顺序导出
 * @author AimSpeed
 */
@Target(ElementType.FIELD)//只用在参数
@Retention(RetentionPolicy.RUNTIME) //运行时才启用
@Documented
public @interface ExcelExportField {
	
	/**
	 * 设置导出之后的标题名
	 * @author AimSpeed
	 * @return String 
	 */
	String headlineName() default "";
	
	/**
	 * 将数据导出到对应的列中，要么就全部row都不设置，要么涉及到的都设置，从1开始
	 * @author AimSpeed
	 * @return int 
	 */
	int row() default 0;
	
	
}
