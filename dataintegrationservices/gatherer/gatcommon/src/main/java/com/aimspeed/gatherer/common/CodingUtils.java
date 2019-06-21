package com.aimspeed.gatherer.common;

import com.aimspeed.gatherer.common.enums.CodingEnum;

/**
 * 编码工具类
 * @author AimSpeed
 */
public class CodingUtils {
	
	
	/**
	 * 从字符串中查找是否包含编码
	 * @author AimSpeed
	 * @param string
	 * @return String 
	 */
	public static String getCodingOfContains(String string){
		
		if(string.toLowerCase().contains(CodingEnum.GB18030.getValue())){
			return CodingEnum.GB18030.getValue();
			
		}else if(string.toLowerCase().contains(CodingEnum.GB2312.getValue())){
			return CodingEnum.GB2312.getValue();
			
		}else if(string.toLowerCase().contains(CodingEnum.GBK.getValue())){
			return CodingEnum.GBK.getValue();
			
		}else if(string.toLowerCase().contains(CodingEnum.ISO_8859_1.getValue())){
			return CodingEnum.ISO_8859_1.getValue();
		
		}else if(string.toLowerCase().contains(CodingEnum.UTF8.getValue())){
			return CodingEnum.UTF8.getValue();
			
		}else if(string.toLowerCase().contains(CodingEnum.UTF_8.getValue())){
			return CodingEnum.UTF_8.getValue();
			
		}else{
			return CodingEnum.UTF_8.getValue();
		}
		
	}
	
}
