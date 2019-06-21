package com.aimspeed.common.file.excel.importing;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel数据位置
 * @author AimSpeed
 */
@Target(ElementType.TYPE)//位置：类
@Retention(RetentionPolicy.RUNTIME) //运行时才启用
@Documented
public @interface ExcelImportPosition {
	
	/**
	 * 是否按照标题进行读取，默认为false不按照标题读取
	 * @author AimSpeed
	 * @return boolean 
	 */
	boolean position() default false;
	
	
}
