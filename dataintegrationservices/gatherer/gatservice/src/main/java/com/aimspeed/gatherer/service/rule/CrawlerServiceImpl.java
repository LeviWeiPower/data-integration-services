package com.aimspeed.gatherer.service.rule;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.aimspeed.common.ThreadUtils;
import com.aimspeed.common.cryptology.irreversible.MD5Arithmetic;
import com.aimspeed.common.enums.HttpResponseCurdEnum;
import com.aimspeed.common.enums.HttpResponseEnum;
import com.aimspeed.common.enums.IsDeleteEnum;
import com.aimspeed.common.http.HttpRequestUtils;
import com.aimspeed.common.vo.ResultVo;
import com.aimspeed.gatherer.common.enums.IsStartEnum;
import com.aimspeed.gatherer.common.enums.RedisKeyEnum;
import com.aimspeed.gatherer.entity.bean.mysql.request.CookieMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.request.RequestHeaderMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.request.RequestParamMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.rule.CrawlerMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.rule.ExtractRuleMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.user.UserMySqlBean;
import com.aimspeed.gatherer.service.action.GathererActionService;
import com.aimspeed.gatherer.service.httpclient.HttpClientResult;
import com.aimspeed.gatherer.service.httpclient.HttpClientService;
import com.aimspeed.gatherer.service.request.CookieService;
import com.aimspeed.gatherer.service.request.RequestHeaderService;
import com.aimspeed.gatherer.service.request.RequestParamService;
import com.aimspeed.mysql.BaseMySqlServiceImpl;
import com.aimspeed.redis.RedisMapperImpl;

/**
 * @author AimSpeed
 */
@Service
public class CrawlerServiceImpl extends BaseMySqlServiceImpl<CrawlerMySqlBean> implements CrawlerService {

	private final Logger log = LoggerFactory.getLogger(CrawlerServiceImpl.class);
   
   	@Autowired
   	private HttpClientService httpClientService;

   	@Autowired
   	private ExtractRuleService extractRuleMapper;

   	@Autowired
   	private CookieService cookieBeanService;

   	@Autowired
   	private RequestHeaderService requestHeaderBeanService;
    
   	@Autowired
   	private CrawlerService crawlerBeanService;

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private RedisMapperImpl redisMapper;

	@Autowired
	private RequestParamService requestParamBeanService;

	/*
	 * 保存爬虫配置
	 * @author AimSpeed
	 * @param sysUserBean
	 * @param crawlerBean
	 * @return
	 * @overridden @see com.aimspeed.gatherer.service.rule.CrawlerBeanService#save(com.aimspeed.gatherer.entity.bean.system.SysUserBean, com.aimspeed.gatherer.entity.bean.rule.CrawlerBean)
	 */
	@Override
	public ResultVo save(UserMySqlBean sysUserBean,CrawlerMySqlBean crawlerBean) {
		
		//使用这几个唯一值，判断是否存在
		CrawlerMySqlBean baseRuleStr = new CrawlerMySqlBean();
        baseRuleStr.setDataClassify(crawlerBean.getDataClassify());
        baseRuleStr.setName(crawlerBean.getName());
        baseRuleStr.setHierarchy(crawlerBean.getHierarchy());
        baseRuleStr.setUrl(crawlerBean.getUrl());
        baseRuleStr.setIsDelete(IsDeleteEnum.N.getValue());
        boolean isExists = isExists(baseRuleStr);
		if(isExists) {
			return new ResultVo(HttpResponseEnum.SYSTEM_ERROR.getCode(), HttpResponseCurdEnum.LOG_EXIST.getValue());
		}
		
		//提取主网址
		String mainUrl = HttpRequestUtils.parseUrl2MainUrl(crawlerBean.getUrl());
		
		
		//将名称、url、数据分类名称、层级组合在一起加密后形成序列号
		String sequence = null;
		try {
			sequence = new MD5Arithmetic().encrypt32bit(crawlerBean.getName() + crawlerBean.getUrl() + crawlerBean.getDataClassify() + crawlerBean.getHierarchy());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//保存数据
		crawlerBean.setSequence(sequence);
		crawlerBean.setMainUrl(mainUrl);
//		crawlerBean.setIsSearch(IsSearchEnum.no.getValue());
		crawlerBean.setIsStart(IsStartEnum.N.getValue());
		crawlerBean.setIsDelete(IsDeleteEnum.N.getValue());
		crawlerBean.setCreator(sysUserBean.getAccount());
		crawlerBean.setUpdator(sysUserBean.getAccount());
		this.insertSelective(crawlerBean);
		
		return new ResultVo(crawlerBean);
	}

	/*
	 * 取消添加采集者，删除所有添加过的request包的数据，采集规则数据
	 * @author AimSpeed
	 * @param sysUserBean
	 * @param id
	 * @return
	 * @overridden @see com.aimspeed.gatherer.service.rule.CrawlerBeanService#cancelAddCrawler(com.aimspeed.gatherer.entity.bean.system.SysUserBean, java.lang.Integer)
	 */
	@Override
	public ResultVo cancelAddCrawler(UserMySqlBean sysUserBean, Integer id) {
		
		CrawlerMySqlBean baseRule = this.selectOfId(id);
		if(null == baseRule) {
			return new ResultVo();
		}
		crawlerBeanService.logicDelete(baseRule);
		
        CookieMySqlBean cookieMsg = new CookieMySqlBean();
        cookieMsg.setSequence(baseRule.getSequence());
        cookieBeanService.logicDelete(cookieMsg);
        
        ExtractRuleMySqlBean extractRule = new ExtractRuleMySqlBean();
        extractRule.setSequence(baseRule.getSequence());
        extractRuleMapper.logicDelete(extractRule);
		
        return new ResultVo();
	}

	/*
	 * 通过改变状态决定是否要运行
	 * @author AimSpeed
	 * @param loginAccount
	 * @param id
	 * @param flag
	 * @return
	 * @overridden @see com.aimspeed.gatherer.service.rule.CrawlerBeanService#runOfChangeStatus(java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Override
	public ResultVo runOfChangeStatus(String loginAccount, Integer id, String flag) {
		//先通过标识判断是否要运行，如果要运行则调用对应的线程进行运行
		if(IsStartEnum.Y.getValue().equalsIgnoreCase(flag)) {
			GathererActionService gathererBeanService = applicationContext.getBean(GathererActionService.class);
			
			//开启线程，执行
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						gathererBeanService.startCrawlingData(loginAccount, id);
					} catch (Exception e) {
						Thread.currentThread().interrupt();
					}
				}

			});
			thread.start();
			
			log.info("========= 启动采集任务，id：" + id);
			
			//获取到线程id
			long threadId = thread.getId();
			
			boolean cache = redisMapper.set(RedisKeyEnum.gatherer_cache_perfix.getValue() + id, threadId+"");
			//缓存失败则结束线程
			if(!cache) {
				ThreadUtils.interruptThread(thread);
				return new ResultVo(HttpResponseEnum.SYSTEM_ERROR.getCode(),"缓存失败，请检查Redis缓存");
			}
			
		}else {//结束线程运行
			Long threadId = redisMapper.get(RedisKeyEnum.gatherer_cache_perfix.getValue() + id, Long.class);

			log.info("========= 结束采集任务，线程Id：" + threadId);
			
			if(null != threadId) {
				//获取到线程
				Thread thread = ThreadUtils.getThreadById(threadId);
				if(null != thread) {
					//结束线程
					ThreadUtils.interruptThread(thread);
				}
				
				//在缓存中删除
				redisMapper.del(threadId + "");
			}
		}
		
		CrawlerMySqlBean conditionRecord = new CrawlerMySqlBean();
		conditionRecord.setId(id);
		
		CrawlerMySqlBean updateRecord = new CrawlerMySqlBean();
		updateRecord.setIsStart(flag);
		updateRecord.setUpdator(loginAccount);
		updateRecord.setUpdateTime(new Date());
		
		Integer len = crawlerBeanService.updateSelective(updateRecord, conditionRecord);
		return new ResultVo(len);
	}
	
	/*
	 * 根据采集的Id查询到对应的配置信息，获取访问对应URL所返回的信息
	 * @author AimSpeed
	 * @param crawlerId
	 * @param requestUrl
	 * @return
	 * @overridden @see com.aimspeed.gatherer.service.rule.CrawlerBeanService#crawlerRuleToRequest(java.lang.Integer, java.lang.String)
	 */
	@Override
	public HttpClientResult crawlerRuleToRequest(Integer crawlerId, String requestUrl) {
		//查询配置信息
		CrawlerMySqlBean crawlerBean = crawlerBeanService.selectOfId(crawlerId);
		return crawlerRuleToRequest(crawlerBean.getSequence(),requestUrl,true,true,true);
	}

	/*
	 * 根据采集的Id查询到对应的配置信息，获取访问对应URL所返回的信息
	 * @author AimSpeed
	 * @param sequence
	 * @param requestUrl
	 * @return
	 * @overridden @see com.aimspeed.gatherer.service.rule.CrawlerBeanService#crawlerRuleToRequest(java.lang.String, java.lang.String)
	 */
	@Override
	public HttpClientResult crawlerRuleToRequest(String sequence, String requestUrl) {
		return crawlerRuleToRequest(sequence,requestUrl,true,true,true);
	}

	/*
	 * 根据采集的Id查询到对应的配置信息，获取访问对应URL所返回的信息
	 * @param sequence
	 * @param requestUrl
	 * @param reqHeader
	 * @param requestParam
	 * @param cookie
	 * @return
	 * @overridden @see com.aimspeed.gatherer.service.rule.CrawlerBeanService#crawlerRuleToRequest(java.lang.String, java.lang.String, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean)
	 */
	@Override
	public HttpClientResult crawlerRuleToRequest(String sequence, String requestUrl, Boolean reqHeader,
			Boolean requestParam, Boolean cookie) {
		//查询配置信息
		CrawlerMySqlBean crawlerBean = new CrawlerMySqlBean();
		crawlerBean.setSequence(sequence);
		crawlerBean = crawlerBeanService.selectOnlyOfSelective(crawlerBean);

		//查询请求包头的规则信息，请求的时候添加上包头的规则
		List<RequestHeaderMySqlBean> reqHeaderMsgBeans = null;
		if(reqHeader) {
			RequestHeaderMySqlBean reqHeaderMsgBean = new RequestHeaderMySqlBean();
			reqHeaderMsgBean.setSequence(crawlerBean.getSequence());
			reqHeaderMsgBeans = requestHeaderBeanService.selectSelective(reqHeaderMsgBean);
		
		}
		
		//查询请求参数
		List<RequestParamMySqlBean> requestParamBeans = null;
		if(requestParam) {
			RequestParamMySqlBean requestParamBean = new RequestParamMySqlBean();
			requestParamBean.setSequence(crawlerBean.getSequence());
			requestParamBeans = requestParamBeanService.selectSelective(requestParamBean);
			
		}
		
		//查询Cookie的规则信息，请求的时候添加上包头的规则
		List<CookieMySqlBean> cookieBeans = null;
		if(cookie) {
			CookieMySqlBean cookieBean = new CookieMySqlBean();
			cookieBean.setSequence(crawlerBean.getSequence());
			cookieBeans = cookieBeanService.selectSelective(cookieBean);
		}
		
		//根据请求的信息，获取到页面信息
		HttpClientResult result = null;
		try {
			result = httpClientService.getResultOfMethod(requestUrl, crawlerBean.getCoding(), crawlerBean.getMethod(), 
									requestParamBeanService.paramToMap(requestParamBeans), 
									requestHeaderBeanService.headerToMap(reqHeaderMsgBeans),
									cookieBeanService.cookieToMap(cookieBeans), null);
		} catch (IllegalArgumentException | URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * 根据配置信息，获取访问对应URL所返回的信息
	 * @author AimSpeed
	 * @param requestUrl
	 * @param crawlerBean
	 * @param cookieBeans
	 * @param reqHeaderMsgBeans
	 * @return
	 * @overridden @see com.aimspeed.gatherer.service.rule.CrawlerBeanService#crawlerRuleToRequest(java.lang.String, com.aimspeed.gatherer.entity.bean.rule.CrawlerBean, java.util.List, java.util.List)
	 */
	@Override
	public HttpClientResult crawlerRuleToRequest(String requestUrl,CrawlerMySqlBean crawlerBean,List<RequestParamMySqlBean> requestParamBeans, List<CookieMySqlBean> cookieBeans,List<RequestHeaderMySqlBean> reqHeaderMsgBeans) {
		
		//根据请求的信息，获取到页面信息
		HttpClientResult result = null;
		try {
			result = httpClientService.getResultOfMethod(requestUrl, crawlerBean.getCoding(), crawlerBean.getMethod(), 
							requestParamBeanService.paramToMap(requestParamBeans), 
							requestHeaderBeanService.headerToMap(reqHeaderMsgBeans),
							cookieBeanService.cookieToMap(cookieBeans), null);
		} catch (IllegalArgumentException | URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/*
	 * 判断是否是异步页面
	 * @author AimSpeed
	 * @param sequence
	 * @param isAsync
	 * @return
	 * @overridden @see com.aimspeed.gatherer.service.rule.CrawlerService#isAsync(java.lang.String, java.lang.String)
	 */
	/*@Override
	public ResultVo isAsync(String sequence, String isAsync) {
		CrawlerMySqlBean conditionRecord = new CrawlerMySqlBean();
		conditionRecord.setSequence(sequence);
		
		CrawlerMySqlBean updateRecord = new CrawlerMySqlBean();
//		updateRecord.setIsAsync(isAsync);
		
		Integer len = crawlerBeanService.updateSelective(updateRecord, conditionRecord);
		return new ResultVo();
	}*/

}
