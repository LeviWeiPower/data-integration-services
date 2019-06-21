package com.aimspeed.gatherer.controller.content;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aimspeed.gatherer.controller.BaseController;
import com.aimspeed.gatherer.entity.bean.mysql.content.ContentDataMySqlBean;

/**
 * 内容数据
 * @author AimSpeed
 */
@Controller
@RequestMapping("/content/data")
public class ContentDataController extends BaseController<ContentDataMySqlBean> {

	
	public ContentDataController() {
		super("data/content_");
	}
	
	
}
