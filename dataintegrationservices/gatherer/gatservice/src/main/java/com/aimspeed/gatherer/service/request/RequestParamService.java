package com.aimspeed.gatherer.service.request;

import java.util.List;
import java.util.Map;

import com.aimspeed.gatherer.entity.bean.mysql.request.RequestParamMySqlBean;
import com.aimspeed.mysql.BaseMySqlService;

/**
 * 请求参数
 * @author AimSpeed
 */
public interface RequestParamService extends BaseMySqlService<RequestParamMySqlBean> {

	/**
	 * 根据条件进行查找，将查找到的结果的Key和Vlaue值，转换为Map对象，
	 * @author AimSpeed
	 * @param requestParamBeans
	 * @return Map<String,String> 
	 */
    Map<String, String> paramToMap(List<RequestParamMySqlBean> requestParamBeans);
	
}
