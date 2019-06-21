package com.aimspeed.operations.api.gatherer.rule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aimspeed.common.vo.ResultVo;
import com.aimspeed.operations.api.BaseApi;
import com.aimspeed.operations.api.gatherer.vo.CrawlerVo;

/**
 * 执行过的URL控制层
 * @author AimSpeed
 */
@FeignClient(value = "${eureka.gatherer.server.name}",path="/crawler") // path 是远程服务的访问前缀
@Controller
@RequestMapping(value = "/crawler") //本地访问的前缀，直接通过浏览器访问
public interface CrawlerApi extends BaseApi<CrawlerVo> {
	

    @RequestMapping(value = "/sum", method = RequestMethod.GET)
	@ResponseBody
	public ResultVo sum(@RequestParam("id") Integer id);
	
	/**
	 * 运行采集任务
	 * @param request
	 * @param response
	 * @param id
	 * @param flag
	 * @return ResultVo  
	 */
    @RequestMapping(value = "/run", method = RequestMethod.GET)
	@ResponseBody
    public ResultVo runOfChangeStatus(@RequestParam("id") Integer id,@RequestParam("flag") String flag);
	
	/**
	 * 取消添加采集者，删除所有添加过的request包的数据，采集规则数据
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value="/cancelAddCrawler/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResultVo cancelAddCrawler(@PathVariable("id") Integer id);
	
	
}
