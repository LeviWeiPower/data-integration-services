package com.aimspeed.gatherer.controller.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aimspeed.gatherer.entity.bean.mysql.rule.CrawlerMySqlBean;


/**
 * main.jsp对应的控制层
 * @author AimSpeed
 */
@Controller()
@RequestMapping("/main")
public class MainController {

	/**
	 * 跳转首页
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param baseRule
	 * @return String 
	 */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getMainPage(HttpServletRequest request, HttpServletResponse response, CrawlerMySqlBean baseRule) {
        return "/common/main";
    }
}
