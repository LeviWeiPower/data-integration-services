package com.aimspeed.operations.controller.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aimspeed.common.http.HttpRequestUtils;
import com.aimspeed.common.vo.ResultVo;
import com.aimspeed.operations.common.constants.CommonConstant;
import com.aimspeed.operations.service.user.UserAuthorityService;
import com.aimspeed.operations.service.user.UserService;
import com.aimspeed.redis.RedisMapperImpl;


@Controller
@RequestMapping("/main")
public class MainController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
	private UserService userBeanService;

	@Autowired
	private UserAuthorityService userAuthorityService;

	@Autowired
	private RedisMapperImpl redisMapper;

    /**
     * 跳转到主页面
     *
     * @param request
     * @param response
     * @return String
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getMainPage(HttpServletRequest request, HttpServletResponse response) {
        return "/common/main";
    }

    /**
     * 跳转到首页
     *
     * @param request
     * @param response
     * @return String
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndexPage(HttpServletRequest request, HttpServletResponse response) {
        return "/index/index";
    }
	
    /**
     * 跳转到登录页面
     *
     * @param request
     * @param response
     * @return String
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String getLogin(HttpServletRequest request, HttpServletResponse response) {
        return "/login/login";
    }
    
    /**
     * 登录
     *
     * @param request
     * @param response
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public ResultVo getLogin(HttpServletRequest request, HttpServletResponse response,String account,String password) {
    	ResultVo resultVo = userBeanService.login(HttpRequestUtils.getSessionId(request),account, password);
    	return resultVo;
    }

    /**
     * 获取到当前登录权限
     * @param request
     * @param response
     * @return String
     */
    /*@ResponseBody
    @RequestMapping(value = "/getCurLoginUserAuthorities", method = {RequestMethod.POST})
    public ResultVo getCurLoginUserAuthorities(HttpServletRequest request, HttpServletResponse response) {
    	return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),
    						HttpResponseCurdEnum.DELETE_SUCCESS.getValue(),
    						userAuthorityService.getCurLoginUserAuthority(HttpRequestUtils.getSessionId(request)));
    }*/

    /**
     * 登出
     *
     * @param request
     * @param response
     * @return String
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public String logout(HttpServletRequest request, HttpServletResponse response) {
    	redisMapper.del(HttpRequestUtils.getSessionId(request) + CommonConstant.REDIS_LOGIN_USER_INFO);
		redisMapper.del(HttpRequestUtils.getSessionId(request) + CommonConstant.REDIS_LOGIN_USER_ROLE_INFO);
		return "/login/login";
    }

}
