package com.aimspeed.gatherer.service.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aimspeed.common.datatype.StringUtils;
import com.aimspeed.common.enums.HttpRequestHeaderEnum;
import com.aimspeed.common.enums.HttpResponseEnum;
import com.aimspeed.common.enums.IsDeleteEnum;
import com.aimspeed.gatherer.common.http.RequestPacketUtil;
import com.aimspeed.gatherer.entity.bean.mysql.request.CookieMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.request.RequestHeaderMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.request.RequestParamMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.user.UserMySqlBean;
import com.aimspeed.gatherer.entity.vo.result.ResultVo;
import com.aimspeed.gatherer.entity.vo.rule.RequestPacketVo;
import com.aimspeed.mysql.BaseMySqlServiceImpl;

/** 
 * @author AimSpeed
 */
@Service
public class RequestHeaderServiceImpl extends BaseMySqlServiceImpl<RequestHeaderMySqlBean> implements RequestHeaderService {

	@Autowired
    private CookieService cookieBeanService;

	@Autowired
	private RequestParamService requestParamBeanService;

	/*
	 * 添加请求包头配置信息、Cookie信息、Session信息e
	 * @author AimSpeed
	 * @Title addRequestPacketMsg
	 * @param sysUserBean
	 * @param reqMsgVo
	 * @return
	 * @overridden @see com.aimspeed.gatherer.service.request.RequestHeaderBeanService#addRequestPacketMsg(com.aimspeed.gatherer.entity.bean.system.SysUserBean, com.aimspeed.gatherer.entity.vo.rule.RequestPacketVo)
	 * @date 2018年3月27日
	 */
	@Override
	public ResultVo addRequestPacketMsg(UserMySqlBean sysUserBean, RequestPacketVo reqMsgVo) {
		//添加Cookie
		List<CookieMySqlBean> cookieMsgs = reqMsgVo.getCookieBeans();
		if(null != cookieMsgs && cookieMsgs.size() > 0) {
			for (CookieMySqlBean cookieBean : cookieMsgs) {
				if(!StringUtils.isEmpty(cookieBean.getCookieKey(), true) && !StringUtils.isEmpty(cookieBean.getCookieValue(), true)) {
					cookieBean.setIsDelete(IsDeleteEnum.N.getValue());
					cookieBean.setCreator(sysUserBean.getAccount());
					cookieBean.setUpdator(sysUserBean.getAccount());
					cookieBeanService.insertSelective(cookieBean);
				}
			}
		}
		
		//添加请求包头
		List<RequestHeaderMySqlBean> reqHeaderMsgBeans = reqMsgVo.getRequestHeaderBeans();
		if(null != reqHeaderMsgBeans && reqHeaderMsgBeans.size() > 0) {
			for (RequestHeaderMySqlBean requestHeaderBean : reqHeaderMsgBeans) {
				if(!StringUtils.isEmpty(requestHeaderBean.getHeaderKey(), true) && !StringUtils.isEmpty(requestHeaderBean.getHeaderKey(), true)) {
					requestHeaderBean.setIsDelete(IsDeleteEnum.N.getValue());
					requestHeaderBean.setCreator(sysUserBean.getAccount());
					requestHeaderBean.setUpdator(sysUserBean.getAccount());
					this.insertSelective(requestHeaderBean);
				}
			}
		}
		
		//添加请求参数
		List<RequestParamMySqlBean> requestParamBeans = reqMsgVo.getRequestParamBeans();
		if(null != requestParamBeans && requestParamBeans.size() > 0) {
			for (RequestParamMySqlBean requestParamBean : requestParamBeans) {
				if(!StringUtils.isEmpty(requestParamBean.getParamKey(), true) && !StringUtils.isEmpty(requestParamBean.getParamValue(), true)) {
					requestParamBean.setIsDelete(IsDeleteEnum.N.getValue());
					requestParamBean.setCreator(sysUserBean.getAccount());
					requestParamBean.setUpdator(sysUserBean.getAccount());
					requestParamBeanService.insertSelective(requestParamBean);
				}
			}
		}

		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(), HttpResponseEnum.SUCCESS.getValue());
	}

	/*
	 * 根据条件进行查找，将查找到的结果的Key和Vlaue值，转换为Map对象，
	 * 如果查找结果为空那么则添加上默认的请求包头信息。
	 * 如果查找结果不为空，那么则按照结果进行转换为Map对象，并添加上一些必要的包头信息
	 * @author AimSpeed
	 * @Title headerToMap
	 * @param reqHeaderMsgBeans
	 * @return
	 * @overridden @see com.aimspeed.gatherer.service.request.RequestHeaderBeanService#headerToMap(java.util.List)
	 * @date 2018年3月27日
	 */
	@Override
	public Map<String, String> headerToMap(List<RequestHeaderMySqlBean> reqHeaderMsgBeans) {
		
		Map<String, String> result = new HashMap();
		
		//拼装数据
		if(null != reqHeaderMsgBeans && reqHeaderMsgBeans.size() > 0) {
			
			for (RequestHeaderMySqlBean reqHeaderMsg : reqHeaderMsgBeans) {
				result.put(reqHeaderMsg.getHeaderKey(), reqHeaderMsg.getHeaderValue());
			}
		
		}else {
			//添加默认数据
			result.put(HttpRequestHeaderEnum.ACCEPT.getKey(), HttpRequestHeaderEnum.ACCEPT.getValue());
			result.put(HttpRequestHeaderEnum.ACCEPT_ENCODING.getKey(), HttpRequestHeaderEnum.ACCEPT_ENCODING.getValue());
			result.put(HttpRequestHeaderEnum.ACCEPT_LANGUAGE.getKey(), HttpRequestHeaderEnum.ACCEPT_LANGUAGE.getValue());
			result.put(HttpRequestHeaderEnum.CONNECTION.getKey(), HttpRequestHeaderEnum.CONNECTION.getValue());
		}
		//改变浏览器伪装
		result.put(HttpRequestHeaderEnum.USER_AGENT.getKey(), RequestPacketUtil.getRandomBrowserVersion());
		return result;
	}



}
