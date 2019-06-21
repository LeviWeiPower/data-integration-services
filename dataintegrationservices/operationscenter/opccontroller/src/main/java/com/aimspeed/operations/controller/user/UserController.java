package com.aimspeed.operations.controller.user;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aimspeed.common.ReflectUtils;
import com.aimspeed.common.enums.DefFieldNameEnum;
import com.aimspeed.common.enums.HttpResponseEnum;
import com.aimspeed.common.enums.IsDeleteEnum;
import com.aimspeed.common.vo.ResultVo;
import com.aimspeed.operations.controller.BaseController;
import com.aimspeed.operations.entity.bean.mysql.user.UserMySqlBean;
import com.aimspeed.operations.service.user.UserService;



/**
 * 
 * 用户表
 * 
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController<UserMySqlBean> {

	public UserController() {
		super("user/user_");
	}
	
	@Autowired
	private UserService userBeanService;
	
	/*
	 * 保存
	 * @param request
	 * @param response
	 * @param t
	 * @return
	 * @overridden @see com.aimspeed.operations.controller.BaseController#save(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	protected ResultVo save(HttpServletRequest request, HttpServletResponse response, UserMySqlBean t) {
		UserMySqlBean userBean = getCurrentUser(request);
		return userBeanService.saveSelective(t,userBean.getAccount());
	}
	
	/**
	 * 更新，会修改密码
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return ResultVo  
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo updateUser(HttpServletRequest request, HttpServletResponse response, UserMySqlBean t) {
		UserMySqlBean userBean = getCurrentUser(request);
		return userBeanService.updateUserAccount(t,userBean.getAccount());
	}
	
	/*
	 * 删除
	 * @param request
	 * @param response
	 * @param t
	 * @param ids
	 * @return
	 * @overridden @see com.aimspeed.operations.controller.BaseController#deleteByIds(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.io.Serializable, java.lang.String)
	 */
	@Override
	protected ResultVo deleteByIds(HttpServletRequest request, HttpServletResponse response, UserMySqlBean t,
			String ids) {
		UserMySqlBean userBean = getCurrentUser(request);
		try {
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.IS_DELETE.getValue(),IsDeleteEnum.Y.getValue());
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.UPDATOR.getValue(),userBean.getAccount());
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.UPDATE_TIME.getValue(),new Date().toString());
			return userBeanService.deleteUserByIds(t, ids);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return new ResultVo(HttpResponseEnum.SYSTEM_ERROR.getCode(),HttpResponseEnum.SYSTEM_ERROR.getValue());
		}
	}
	
}

