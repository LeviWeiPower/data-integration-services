package com.aimspeed.gatherer.controller.rule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aimspeed.common.vo.ResultVo;
import com.aimspeed.gatherer.controller.BaseController;
import com.aimspeed.gatherer.entity.bean.mysql.rule.CrawlerMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.user.UserMySqlBean;
import com.aimspeed.gatherer.service.rule.CrawlerService;

/**
 * 采集者控制层
 * @author AimSpeed
 */
@Controller
@RequestMapping("/crawler")
public class CrawlerController extends BaseController<CrawlerMySqlBean> {
	
	public CrawlerController() {
		super("crawler");
	}
	
	@Autowired
	private CrawlerService crawlerBeanService;
	
	/**
	 * 用于测试
	 * @author AimSpeed
	 * @param id
	 * @return ResultVo
	 */
	@RequestMapping(value="/sum",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultVo sum(@RequestParam("id") Integer id){
		return new ResultVo("11111111111111");
	}
	
	/**
	 * 跳转到列表首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/index",method = {RequestMethod.GET,RequestMethod.POST})
	public String index(HttpServletRequest request,HttpServletResponse response){
		return "/data/crawler_list";
	}
	
	/**
	 * 跳转到添加规则配置
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/addCrawlerPage",method = {RequestMethod.GET,RequestMethod.POST})
	public String addBaseRuleView(HttpServletRequest request,HttpServletResponse response){
		return "/rule/crawler";
	}

	/**
	 * 跳转到是否异步配置页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/isAsyncPage",method = {RequestMethod.GET,RequestMethod.POST})
	public String isAsyncView(HttpServletRequest request,HttpServletResponse response){
		return "/rule/is_async";
	}
	
	/**
	 * 运行采集任务
	 * @param request
	 * @param response
	 * @param id
	 * @param flag
	 * @return ResultVo  
	 */
	@RequestMapping(value = "/run", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
    public ResultVo runOfChangeStatus(HttpServletRequest request, HttpServletResponse response,Integer id,String flag) {
		UserMySqlBean userBean = getCurrentUser(request);
		
		ResultVo resultVo = crawlerBeanService.runOfChangeStatus(userBean.getAccount(),id,flag);
		return resultVo;
	}
	
	/*
	 * 添加爬虫规则，添加成功后返回添加的id
	 * @param request
	 * @param response
	 * @param t
	 * @return
	 * @overridden @see com.aimspeed.gatherer.controller.BaseController#save(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	public ResultVo save(HttpServletRequest request, HttpServletResponse response,CrawlerMySqlBean t) {
		UserMySqlBean userBean = getCurrentUser(request);
		ResultVo resultVo = crawlerBeanService.save(userBean, t);
		return resultVo; 
	}
	
	/**
	 * 取消添加采集者，删除所有添加过的request包的数据，采集规则数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/cancelAddCrawler/{id}",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultVo cancelAddCrawler(HttpServletRequest request,HttpServletResponse response,@PathVariable("id") Integer id){
		UserMySqlBean userBean = getCurrentUser(request);
		ResultVo resultVo = crawlerBeanService.cancelAddCrawler(userBean, id);
		return resultVo;
	}
	
	/**
	 * 页面是否是异步
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param sequence
	 * @param templateUrl
	 * @return ResultVo
	 */
	/*@RequestMapping(value="/isAsync",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultVo isAsync(HttpServletRequest request,HttpServletResponse response,
										String sequence,String isAsync){
		ResultVo resultVo = crawlerBeanService.isAsync(sequence, isAsync);
		return resultVo;
	}*/
	
}
