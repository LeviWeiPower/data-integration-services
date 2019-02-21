package com.aimspeed.gatherer.service.request;

import java.util.List;
import java.util.Map;

import com.aimspeed.gatherer.entity.bean.mysql.request.CookieMySqlBean;
import com.aimspeed.mysql.BaseMySqlService;

/**
 * cookie信息
 * @author AimSpeed
 */
public interface CookieService extends BaseMySqlService<CookieMySqlBean> {
    
	/**
	 * 根据条件进行查找，将查找到的结果的Key和Vlaue值，转换为Map对象，
	 * @author AimSpeed
	 * @Title cookieToMap 
	 * @param cookieBeans
	 * @return Map<String,String> 
	 * @date 2018年3月27日
	 */
    Map<String, String> cookieToMap(List<CookieMySqlBean> cookieBeans);

}