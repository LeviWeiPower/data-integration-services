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
import com.aimspeed.common.enums.HttpResponseCurdEnum;
import com.aimspeed.common.enums.HttpResponseEnum;
import com.aimspeed.common.enums.IsDeleteEnum;
import com.aimspeed.common.vo.ResultVo;
import com.aimspeed.operations.controller.BaseController;
import com.aimspeed.operations.entity.bean.mysql.user.UserAuthorityMySqlBean;
import com.aimspeed.operations.entity.bean.mysql.user.UserMySqlBean;
import com.aimspeed.operations.service.user.UserAuthorityService;



/**
 * 
 * 用户权限表
 * 
 */
@Controller
@RequestMapping("/user/authority")
public class UserAuthorityController extends BaseController<UserAuthorityMySqlBean> {

	public UserAuthorityController() {
		super("user/authority_");
	}

	@Autowired
	private UserAuthorityService userAuthorityBeanService;

	/**
	 * 获取到
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @return Map<String,Object> 
	 */
	@RequestMapping(value = "/getAuthorityInfo", method = RequestMethod.GET)
	@ResponseBody
	protected ResultVo getAuthorityInfo(HttpServletRequest request, HttpServletResponse response){
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseEnum.SUCCESS.getValue(),userAuthorityBeanService.selectSelectiveOfIsDeleteN(new UserAuthorityMySqlBean()));
	}
	
	
	/**
	 * 删除权限及其子权限
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @return Map<String,Object> 
	 */
	@RequestMapping(value = "/removeAuthorityInfo", method = RequestMethod.GET)
	@ResponseBody
	protected ResultVo removeAuthorityInfo(HttpServletRequest request, HttpServletResponse response,Integer id){
		Integer delCount= userAuthorityBeanService.removeAuthorityInfo(id);
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseEnum.SUCCESS.getValue(),delCount);
	}

	/**
	 * 在添加之前进行验证
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/validateBeforeAdd", method = RequestMethod.POST)
	@ResponseBody
    protected ResultVo validateBeforeAdd(HttpServletRequest request, 
    										HttpServletResponse response,
    										UserAuthorityMySqlBean userAuthority) {
		//查找该节点下是否有这个节点
		if(null != userAuthority.getUri() && !"".equals(userAuthority.getUri().trim())) {
			UserAuthorityMySqlBean selectRecord = new UserAuthorityMySqlBean();
			selectRecord.setPid(userAuthority.getPid());
			selectRecord.setUri(userAuthority.getUri());
			selectRecord.setIsDelete(IsDeleteEnum.N.getValue());
			UserAuthorityMySqlBean selectiveResult = userAuthorityBeanService.selectOnlyOfSelective(selectRecord);
			if(null != selectiveResult){
				return new ResultVo(HttpResponseEnum.SYSTEM_ERROR.getCode(),"uri在该节点下以存在");
			}
		}
		
		//校验名称的重复性
		UserAuthorityMySqlBean selectRecord = new UserAuthorityMySqlBean();
		selectRecord.setPid(userAuthority.getPid());
		selectRecord.setName(userAuthority.getName());
		selectRecord.setIsDelete(IsDeleteEnum.N.getValue());
		UserAuthorityMySqlBean selectiveResult = userAuthorityBeanService.selectOnlyOfSelective(selectRecord);
		if(null != selectiveResult){
			return new ResultVo(HttpResponseEnum.SYSTEM_ERROR.getCode(),"名称在该节点下已存在");
		}
		
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),"验证成功");
	}
	
	/**
	 * 保存用户权限信息
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @return Map<String,Object> 
	 */
	@RequestMapping(value = "/saveUserAuthority", method = RequestMethod.POST)
	@ResponseBody
	protected ResultVo saveUserAuthority(HttpServletRequest request, HttpServletResponse response,UserAuthorityMySqlBean userAuthority){
		UserMySqlBean userBean = getCurrentUser(request);
		try {
			ReflectUtils.setFieldDefVal(userAuthority,DefFieldNameEnum.CREATOR.getValue(),userBean.getAccount());
			ReflectUtils.setFieldDefVal(userAuthority,DefFieldNameEnum.UPDATOR.getValue(),userBean.getAccount());
			ReflectUtils.setFieldDefVal(userAuthority,DefFieldNameEnum.IS_DELETE.getValue(),IsDeleteEnum.N.getValue());
			
			int id = userAuthorityBeanService.saveUserAuthority(userAuthority);
			return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseCurdEnum.SAVE_SUCCESS.getValue(),id);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return new ResultVo(HttpResponseEnum.SYSTEM_ERROR.getCode(),HttpResponseCurdEnum.SAVE_FAIL.getValue());
	}
	
	
	/**
	 * 更新用户权限信息
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @return Map<String,Object> 
	 */
	@RequestMapping(value = "/updateUserAuthority", method = RequestMethod.POST)
	@ResponseBody
	protected ResultVo updateUserAuthority(HttpServletRequest request, HttpServletResponse response,UserAuthorityMySqlBean userAuthority){
		UserMySqlBean userBean = getCurrentUser(request);
		try {
			ReflectUtils.setFieldDefVal(userAuthority,DefFieldNameEnum.UPDATOR.getValue(),userBean.getAccount());
			ReflectUtils.setFieldDefVal(userAuthority,DefFieldNameEnum.UPDATE_TIME.getValue(),new Date().toString());
			int id = userAuthorityBeanService.updateUserAuthority(userAuthority);
			
			return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseCurdEnum.SAVE_SUCCESS.getValue(),id);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return new ResultVo(HttpResponseEnum.SYSTEM_ERROR.getCode(),HttpResponseCurdEnum.SAVE_FAIL.getValue());
	}
	

}

