package com.aimspeed.gatherer.service.request;

import java.util.List;
import java.util.Map;

import com.aimspeed.common.vo.ResultVo;
import com.aimspeed.gatherer.entity.bean.mysql.request.RequestHeaderMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.user.UserMySqlBean;
import com.aimspeed.gatherer.entity.vo.rule.RequestPacketVo;
import com.aimspeed.mysql.BaseMySqlService;

/**
 * 请求包头信息
 * @author AimSpeed
 */
public interface RequestHeaderService extends BaseMySqlService<RequestHeaderMySqlBean>{
	
	/**
	 * 添加请求包头配置信息、Cookie信息、Session信息
	 * @author AimSpeed
	 * @param sysUserBean
	 * @param reqMsgVo
	 * @return ResultVo 
	 */
	ResultVo addRequestPacketMsg(UserMySqlBean sysUserBean,RequestPacketVo reqMsgVo);
	
	/**
	 * 根据条件进行查找，将查找到的结果的Key和Vlaue值，转换为Map对象，
	 * 如果查找结果为空那么则添加上默认的请求包头信息。
	 * 如果查找结果不为空，那么则按照结果进行转换为Map对象，并添加上一些必要的包头信息
	 * @author AimSpeed
	 * @param reqHeaderMsgBeans
	 * @return Map<String,String> 
	 */
	Map<String,String> headerToMap(List<RequestHeaderMySqlBean> reqHeaderMsgBeans);
	
}
