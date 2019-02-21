package com.aimspeed.gatherer.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aimspeed.common.ReflectUtils;
import com.aimspeed.common.datatype.DataTypeChangeUtils;
import com.aimspeed.common.enums.DefFieldNameEnum;
import com.aimspeed.common.enums.HttpResponseCurdEnum;
import com.aimspeed.common.enums.HttpResponseEnum;
import com.aimspeed.common.enums.IsDeleteEnum;
import com.aimspeed.gatherer.entity.bean.mysql.BaseMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.user.UserMySqlBean;
import com.aimspeed.gatherer.entity.vo.result.ResultVo;
import com.aimspeed.mysql.BaseMySqlService;
import com.aimspeed.mysql.vo.MySqlPageVo;

/**
 * 控制层基础类
 * @author AimSpeed
 */
public class BaseController<T extends BaseMySqlBean> {

	@Autowired
	private BaseMySqlService<T> baseService;
	
	/**
	 * 前缀
	 */
	private String prefixUrl;

	protected BaseController(String prefixUrl) {
		super();
		prefixUrl = "/" + prefixUrl;
		this.prefixUrl = prefixUrl;
	}

	/**
	 * 跳转列表页面
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @return String 
	 */
	@RequestMapping(value = "/getListPage", method = {RequestMethod.GET,RequestMethod.POST})
    protected String getListPage(HttpServletRequest request, HttpServletResponse response) {
        return prefixUrl + "list";
    }

	/**
	 * 跳转到添加页面
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @return String 
	 */
	@RequestMapping(value = "/getAddPage", method = {RequestMethod.GET,RequestMethod.POST})
    protected String getAddPage(HttpServletRequest request, HttpServletResponse response) {
		return prefixUrl + "add";
    }
	
	/**
	 * 跳转到编辑页面
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @return String 
	 */
	@RequestMapping(value = "/getEditPage", method = {RequestMethod.GET,RequestMethod.POST})
    protected String getEditPage(HttpServletRequest request, HttpServletResponse response) {
        return prefixUrl + "edit";
    }
	
	/**
	 * 根据对象有值的字段作为有效值进行筛选查找（单条数据[JSON数据格式]）
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/getData", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
    protected ResultVo getData(HttpServletRequest request, HttpServletResponse response,T t) {
		List<T> ts = baseService.selectSelective(t);
		if(null != ts && ts.size() > 0) {
			return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseEnum.SUCCESS.getValue(),ts.get(0));
		}
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseEnum.SUCCESS.getValue(),null);
    }

	/**
	 * 根据对象有值的字段作为有效值进行筛选查找数据是否已存在
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return Map<String,Boolean> 
	 */
	@RequestMapping(value = "/isExists", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	protected Map<String, Boolean> isExists(HttpServletRequest request, HttpServletResponse response,T t) {

		try {
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.IS_DELETE.getValue(),IsDeleteEnum.N.getValue());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		List<T> ts = baseService.selectSelective(t);
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		if(null != ts && ts.size() > 0) {
			result.put("valid", false);
			return result;
		}
		result.put("valid", true);
		return result;
	}
	
	/**
	 * 根据对象有值的字段作为有效值进行筛选查找数据是否不存在
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return Map<String,Boolean> 
	 */
	@RequestMapping(value = "/isNotExists", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	protected Map<String, Boolean> isNotExists(HttpServletRequest request, HttpServletResponse response,T t) {
		
		try {
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.IS_DELETE.getValue(),IsDeleteEnum.N.getValue());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		List<T> ts = baseService.selectSelective(t);
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		if(null != ts && ts.size() > 0) {
			result.put("valid", true);
			return result;
		}
		result.put("valid", false);
		return result;
	}
	
	/**
	 * 根据对象有值的字段作为有效值进行筛选查找（多条数据[JSON数组的数据格式]）
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/getDatas", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
    protected ResultVo getDatas(HttpServletRequest request, HttpServletResponse response,T t) {
		try {
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.IS_DELETE.getValue(),IsDeleteEnum.N.getValue());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		List<T> ts = baseService.selectSelective(t);
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseEnum.SUCCESS.getValue(),ts);
    }

	/**
	 * 查询所有记录数
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/getSumCountSize", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	protected ResultVo getSumCountSize(HttpServletRequest request, HttpServletResponse response) {
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseEnum.SUCCESS.getValue(),baseService.selectDataCountSize(null, null));
	}
	
	/**
	 * 根据条件查询记录数，绝对值匹配
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param condition
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/getCountSize", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	protected ResultVo getCountSize(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> condition) {
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseEnum.SUCCESS.getValue(),baseService.selectDataCountSize(condition, null));
	}
	
	/**
	 * 获取到分页列表数据
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param pageVo
	 * @param pageSize
	 * @param pageNumber
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/getPagingList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
    protected ResultVo getPagingList(HttpServletRequest request, HttpServletResponse response,MySqlPageVo<T> pageVo,Integer pageSize, Integer pageNumber) {
		pageVo.setPageSize(pageSize);
		pageVo.setCurPage(pageNumber);
		MySqlPageVo<T> pageVoResult = baseService.selectPageSelective(pageVo.getCurPage(),pageVo.getPageSize(),pageVo.getOrderField(),pageVo.getOrderingRule(),pageVo.getFiltrate(),pageVo.getLikeFiltrate());
		ResultVo resultVo = new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseEnum.SUCCESS.getValue(),pageVoResult);
		resultVo.setTotalCount(pageVoResult.getTotalCount());
		resultVo.setResult(pageVoResult.getPageData());
		return resultVo;
    }
	
	/**
	 * 保存
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
    protected ResultVo save(HttpServletRequest request, HttpServletResponse response,T t) {
		UserMySqlBean userBean = getCurrentUser(request);
		try {
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.CREATOR.getValue(),userBean.getAccount());
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.UPDATOR.getValue(),userBean.getAccount());
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.IS_DELETE.getValue(),IsDeleteEnum.N.getValue());
			int len = baseService.insertSelective(t);
			return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseCurdEnum.SAVE_SUCCESS.getValue(),len);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return new ResultVo(HttpResponseEnum.FAIL.getCode(),HttpResponseCurdEnum.SAVE_FAIL.getValue());
	}
	
	/**
	 * 更新默认使用id
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
    protected ResultVo update(HttpServletRequest request, HttpServletResponse response,T t) {
		UserMySqlBean userBean = getCurrentUser(request);
		try {
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.UPDATOR.getValue(),userBean.getAccount());
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.UPDATE_TIME.getValue(),new Date().toString());
			int len = baseService.updateSelectiveOfId(t);
			return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseCurdEnum.UPDATE_SUCCESS.getValue(),len);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return new ResultVo(HttpResponseEnum.FAIL.getCode(),HttpResponseCurdEnum.UPDATE_FAIL.getValue());
	}
	
	/**
	 * 根据id删除一个(逻辑删除)
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
    protected ResultVo deleteById(HttpServletRequest request, HttpServletResponse response,T t) {
		UserMySqlBean userBean = getCurrentUser(request);
		try {
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.IS_DELETE.getValue(),IsDeleteEnum.Y.getValue());
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.UPDATOR.getValue(),userBean.getAccount());
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.UPDATE_TIME.getValue(),new Date().toString());
			int len = baseService.updateSelectiveOfId(t);
			return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseCurdEnum.DELETE_SUCCESS.getValue(),len);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return new ResultVo(HttpResponseEnum.FAIL.getCode(),HttpResponseCurdEnum.DELETE_FAIL.getValue());
	}
	
	/**
	 * 根据Id删除多个，使用(,)分割，(逻辑删除)
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @param ids
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/deletes", method = RequestMethod.POST)
	@ResponseBody
    protected ResultVo deleteByIds(HttpServletRequest request, HttpServletResponse response,T t,String ids) {
		UserMySqlBean userBean = getCurrentUser(request);
		String [] idStrings = ids.split(",");
		
		try {
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.IS_DELETE.getValue(),IsDeleteEnum.Y.getValue());
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.UPDATOR.getValue(),userBean.getAccount());
			ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.UPDATE_TIME.getValue(),new Date().toString());
			int len = 0;
			if(null != idStrings && idStrings.length > 0) {
				Integer [] integers = DataTypeChangeUtils.strArrToIntArr(idStrings);
				List<Integer> idsArr = Arrays.asList(integers);
				len = baseService.batchUpdateOfIds(t, idsArr);
			}else {
				ReflectUtils.setFieldDefVal(t,DefFieldNameEnum.ID.getValue(),ids);
				len = baseService.updateSelectiveOfId(t);
			}
			return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseCurdEnum.DELETE_SUCCESS.getValue(),len);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return new ResultVo(HttpResponseEnum.FAIL.getCode(),HttpResponseCurdEnum.DELETE_FAIL.getValue());
	}

	/**
	 * 获取到当前用户
	 * @author AimSpeed
	 * @param request
	 * @return UserMySqlBean 
	 */
	protected UserMySqlBean getCurrentUser(HttpServletRequest request) {
		UserMySqlBean userBean = new UserMySqlBean();
		userBean.setAccount("system");
		return userBean;
	}
	
	
	
}
