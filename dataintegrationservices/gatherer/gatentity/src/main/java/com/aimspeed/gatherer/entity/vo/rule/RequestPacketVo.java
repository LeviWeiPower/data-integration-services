package com.aimspeed.gatherer.entity.vo.rule;

import java.util.List;

import com.aimspeed.gatherer.entity.bean.mysql.request.CookieMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.request.RequestHeaderMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.request.RequestParamMySqlBean;

/**
 * 请求包的数据传递对象
 * @author AimSpeed
 */
public class RequestPacketVo {
	
	
	
	/**
	 * 请求包头信息
	 */
	private List<RequestHeaderMySqlBean> requestHeaderBeans;
	
	/**
	 * 请求参数信息
	 */
	private List<RequestParamMySqlBean> requestParamBeans;
	
	/**
	 * Cookie信息
	 */
	private List<CookieMySqlBean> cookieBeans;

	public List<RequestHeaderMySqlBean> getRequestHeaderBeans() {
		return requestHeaderBeans;
	}

	public void setRequestHeaderBeans(List<RequestHeaderMySqlBean> requestHeaderBeans) {
		this.requestHeaderBeans = requestHeaderBeans;
	}

	public List<RequestParamMySqlBean> getRequestParamBeans() {
		return requestParamBeans;
	}

	public void setRequestParamBeans(List<RequestParamMySqlBean> requestParamBeans) {
		this.requestParamBeans = requestParamBeans;
	}

	public List<CookieMySqlBean> getCookieBeans() {
		return cookieBeans;
	}

	public void setCookieBeans(List<CookieMySqlBean> cookieBeans) {
		this.cookieBeans = cookieBeans;
	}
	
	


}
