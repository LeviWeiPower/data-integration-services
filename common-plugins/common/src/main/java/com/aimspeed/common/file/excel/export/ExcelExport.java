package com.aimspeed.common.file.excel.export;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * Excel导出标识，只有有这个标识的才进行导出
 * @author AimSpeed
 */
@Target(ElementType.TYPE)//位置：类
@Retention(RetentionPolicy.RUNTIME) //运行时才启用
@Documented
public @interface ExcelExport {
	
	
	
}
