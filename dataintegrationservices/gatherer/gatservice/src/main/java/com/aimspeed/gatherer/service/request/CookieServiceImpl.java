package com.aimspeed.gatherer.service.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aimspeed.gatherer.entity.bean.mysql.request.CookieMySqlBean;
import com.aimspeed.mysql.BaseMySqlServiceImpl;

/**
 * Cookie信息
 * @author AimSpeed
 */
@Service
public class CookieServiceImpl extends BaseMySqlServiceImpl<CookieMySqlBean> implements CookieService {

	/*
	 * 根据条件进行查找，将查找到的结果的Key和Vlaue值，转换为Map对象
	 * @author AimSpeed
	 * @param cookieBeans
	 * @return
	 * @overridden @see com.aimspeed.gatherer.service.request.CookieBeanService#cookieToMap(java.util.List)
	 */
	@Override
	public Map<String, String> cookieToMap(List<CookieMySqlBean> cookieBeans) {
		
		Map<String, String> result = new HashMap();
		
		//拼装数据
		if(null != cookieBeans && cookieBeans.size() > 0) {
			
			for (CookieMySqlBean cookieBean : cookieBeans) {
				result.put(cookieBean.getCookieKey(), cookieBean.getCookieValue());
			}
		
		}
		return result;
	}
}
