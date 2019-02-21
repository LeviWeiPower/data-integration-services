package com.aimspeed.gatherer.service.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aimspeed.gatherer.entity.bean.mysql.request.RequestParamMySqlBean;
import com.aimspeed.mysql.BaseMySqlServiceImpl;

/**
 * 请求参数
 * @author AimSpeed
 */
@Service
public class RequestParamServiceImpl extends BaseMySqlServiceImpl<RequestParamMySqlBean> implements RequestParamService {

	@Override
	public Map<String, String> paramToMap(List<RequestParamMySqlBean> requestParamBeans) {

		Map<String, String> result = new HashMap();
		
		//拼装数据
		if(null != requestParamBeans && requestParamBeans.size() > 0) {
			
			for (RequestParamMySqlBean requestParamBean : requestParamBeans) {
				result.put(requestParamBean.getParamKey(), requestParamBean.getParamValue());
			}
		
		}
		return result;
	}
	
		

}
