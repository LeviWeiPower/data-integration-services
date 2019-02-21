package com.aimspeed.gatherer.controller.request;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aimspeed.gatherer.controller.BaseController;
import com.aimspeed.gatherer.entity.bean.mysql.request.RequestHeaderMySqlBean;

/**
 * 执行过的URL控制层
 * @author AimSpeed
 */
@Controller
@RequestMapping("/req/header")
public class RequestHeaderController extends BaseController<RequestHeaderMySqlBean> {

	
	public RequestHeaderController() {
		super("request");
	}

}
