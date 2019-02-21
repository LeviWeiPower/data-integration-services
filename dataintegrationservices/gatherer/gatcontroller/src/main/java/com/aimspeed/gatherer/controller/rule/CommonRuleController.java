package com.aimspeed.gatherer.controller.rule;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aimspeed.gatherer.controller.BaseController;
import com.aimspeed.gatherer.entity.bean.mysql.rule.CommonRuleMySqlBean;

/**
 * 执行过的URL控制层
 * @author AimSpeed
 */
@Controller
@RequestMapping("/commonRule")
public class CommonRuleController extends BaseController<CommonRuleMySqlBean> {

	
	public CommonRuleController() {
		super("");
	}

}
