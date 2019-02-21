package com.aimspeed.gatherer.service.rule;

import java.util.List;

import com.aimspeed.gatherer.entity.bean.mysql.request.CookieMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.request.RequestHeaderMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.request.RequestParamMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.rule.CrawlerMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.user.UserMySqlBean;
import com.aimspeed.gatherer.entity.vo.result.ResultVo;
import com.aimspeed.gatherer.service.httpclient.HttpClientResult;
import com.aimspeed.mysql.BaseMySqlService;

/**
 * 基础规则表
 * @author AimSpeed
 */
public interface CrawlerService extends BaseMySqlService<CrawlerMySqlBean> {
	
	/**
	 * 验证是否已存在后再保存
	 * @author AimSpeed
	 * @Title save 
	 * @param sysUserBean
	 * @param crawlerBean
	 * @return ResultVo 
	 * @date 2018年3月27日
	 */
	ResultVo save(UserMySqlBean sysUserBean,CrawlerMySqlBean crawlerBean);
	
	/**
	 * 取消添加采集者，删除所有添加过的request包的数据，采集规则数据
	 * @author AimSpeed
	 * @Title cancelAddCrawler 
	 * @param sysUserBean
	 * @param id
	 * @return ResultVo 
	 * @date 2018年3月27日
	 */
	ResultVo cancelAddCrawler(UserMySqlBean sysUserBean,Integer id);
	
	/**
	 * 通过改变状态决定是否要运行
	 * @author AimSpeed
	 * @Title runOfChangeStatus 
	 * @param loginAccount
	 * @param id
	 * @param flag
	 * @return ResultVo 
	 * @date 2018年3月27日
	 */
	ResultVo runOfChangeStatus(String loginAccount,Integer id,String flag);

	/**
	 * 根据采集的Id查询到对应的配置信息，获取访问对应URL所返回的信息
	 * @author AimSpeed
	 * @Title crawlerRuleToRequest 
	 * @param crawlerId
	 * @param requestUrl
	 * @return HttpClientResult 
	 * @date 2018年3月27日
	 */
	HttpClientResult crawlerRuleToRequest(Integer crawlerId,String requestUrl);
	
	/**
	 * 根据采集的Id查询到对应的配置信息，获取访问对应URL所返回的信息
	 * @author AimSpeed
	 * @Title crawlerRuleToRequest 
	 * @param sequence
	 * @param requestUrl
	 * @return HttpClientResult 
	 * @date 2018年3月27日
	 */
	HttpClientResult crawlerRuleToRequest(String sequence,String requestUrl);

	/**
	 * 根据采集的Id查询到对应的配置信息，获取访问对应URL所返回的信息
	 * @Title crawlerRuleToRequest 
	 * @param sequence
	 * @param requestUrl
	 * @param reqHeader
	 * @param requestParam
	 * @param cookie
	 * @return HttpClientResult  
	 * @date 2018年4月10日 下午10:55:10
	 */
	HttpClientResult crawlerRuleToRequest(String sequence,String requestUrl,Boolean reqHeader,Boolean requestParam,Boolean cookie);
	
	/**
	 * 根据配置信息，获取访问对应URL所返回的信息
	 * @author AimSpeed
	 * @Title crawlerRuleToRequest 
	 * @param requestUrl
	 * @param crawlerBean
	 * @param cookieBeans
	 * @param reqHeaderMsgBeans
	 * @return HttpClientResult 
	 * @date 2018年3月27日
	 */
	HttpClientResult crawlerRuleToRequest(String requestUrl,CrawlerMySqlBean crawlerBean,List<RequestParamMySqlBean> requestParamBeans,List<CookieMySqlBean> cookieBeans,List<RequestHeaderMySqlBean> reqHeaderMsgBeans);
	
	

	 

}