package com.aimspeed.gatherer.controller.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aimspeed.common.vo.ResultVo;
import com.aimspeed.gatherer.controller.BaseController;
import com.aimspeed.gatherer.entity.bean.mysql.request.RequestHeaderMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.user.UserMySqlBean;
import com.aimspeed.gatherer.entity.vo.rule.RequestPacketVo;
import com.aimspeed.gatherer.service.request.CookieService;
import com.aimspeed.gatherer.service.request.RequestHeaderService;

/**
 * 请求包
 * @author AimSpeed
 */
@Controller
@RequestMapping("/requestPacket")
public class RequestPacketController extends BaseController<RequestHeaderMySqlBean> {

	
	public RequestPacketController() {
		super("request");
	}

	@Autowired
    private CookieService cookieMsgService;

    @Autowired
    private RequestHeaderService reqHeaderMsgService;

    /**
     * 跳转到请求包信息添加页面
     * @author AimSpeed
     * @param request
     * @param response
     * @return String 
     */
	@RequestMapping(value="/addRequestPacketMsgPage",method = {RequestMethod.GET,RequestMethod.POST})
	public String addBaseRuleView(HttpServletRequest request,HttpServletResponse response){
		return "/rule/request_packet";
	}
	
	/**
	 * 添加采集规则的请求包附属信息
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param reqMsgVo
	 * @return ResultVo 
	 */
    @ResponseBody   
    @RequestMapping(value = "/addRequestPacketMsg", method = {RequestMethod.GET,RequestMethod.POST})
    public ResultVo addRequestPacketMsg(HttpServletRequest request, HttpServletResponse response,
                                              RequestPacketVo reqMsgVo) {
    	UserMySqlBean sysUserBean = getCurrentUser(request);
    	ResultVo resultVo =  reqHeaderMsgService.addRequestPacketMsg(sysUserBean, reqMsgVo);
    	return resultVo;
    }
    
    
}
