package com.aimspeed.operations.controller.user;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aimspeed.operations.controller.BaseController;
import com.aimspeed.operations.entity.bean.mysql.user.UserRoleMySqlBean;


/**
 * 
 * 用户角色表
 * 
 */
@Controller
@RequestMapping("/user/role")
public class UserRoleController extends BaseController<UserRoleMySqlBean> {


	public UserRoleController() {
		super("user/role_");
	}

}

