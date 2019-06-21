package com.aimspeed.operations.controller.gatherer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aimspeed.common.vo.ResultVo;
import com.aimspeed.operations.api.gatherer.rule.CrawlerApi;


/**
 * 采集系统控制层
 * @author AimSpeed
 */
@Controller
@RequestMapping("/gatherer")
public class GathererController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CrawlerApi crawlerApi;
	
    /**
     * 跳转到采集系统列表页面
     *
     * @param request
     * @param response
     * @return String
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getMainPage(HttpServletRequest request, HttpServletResponse response) {
    	ResultVo sum = crawlerApi.sum(1);
        return "/gatherer/gatherer_list";
    }

    /**
     * 跳转到采集日志页面
     *
     * @param request
     * @param response
     * @return String
     */
    @RequestMapping(value = "/log/list", method = RequestMethod.GET)
    public String getIndexPage(HttpServletRequest request, HttpServletResponse response) {
        return "/gatherer/gatherer_log_list";
    }
	

}
