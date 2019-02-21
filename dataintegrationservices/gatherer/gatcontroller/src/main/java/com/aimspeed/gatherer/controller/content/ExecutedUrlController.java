package com.aimspeed.gatherer.controller.content;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aimspeed.gatherer.controller.BaseController;
import com.aimspeed.gatherer.entity.bean.mysql.content.ExecutedUrlMySqlBean;

/**
 * 执行过的URL控制层
 * @author AimSpeed
 */
@Controller
@RequestMapping("/executed/url")
public class ExecutedUrlController extends BaseController<ExecutedUrlMySqlBean> {

	
	public ExecutedUrlController() {
		super("execute");
	}

}
