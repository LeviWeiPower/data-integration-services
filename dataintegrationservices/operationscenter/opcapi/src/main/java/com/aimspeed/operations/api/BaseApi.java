package com.aimspeed.operations.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aimspeed.common.vo.ResultVo;
import com.aimspeed.mysql.vo.PageVo;

/**
 * 控制层基础类，以下的接口不能重写，如果要重写名称不能一致
 * @author AimSpeed
 */
public interface BaseApi<T> {
		
	/**
	 * 根据对象有值的字段作为有效值进行筛选查找（单条数据[JSON数据格式]）
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/getData", method = RequestMethod.GET)
	@ResponseBody
    public ResultVo getData(@RequestBody T t);

	/**
	 * 根据对象有值的字段作为有效值进行筛选查找数据是否已存在
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return Map<String,Boolean> 
	 */
	@RequestMapping(value = "/isExists", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Boolean> isExists(@RequestBody T t);
	
	/**
	 * 根据对象有值的字段作为有效值进行筛选查找数据是否不存在
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return Map<String,Boolean> 
	 */
	@RequestMapping(value = "/isNotExists", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Boolean> isNotExists(@RequestBody T t);
	
	/**
	 * 根据对象有值的字段作为有效值进行筛选查找（多条数据[JSON数组的数据格式]）
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/getDatas", method = RequestMethod.GET)
	@ResponseBody
    public ResultVo getDatas(@RequestBody T t);

	/**
	 * 查询所有记录数
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/getSumCountSize", method = RequestMethod.GET)
	@ResponseBody
	public ResultVo getSumCountSize();
	
	/**
	 * 根据条件查询记录数，绝对值匹配
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param condition
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/getCountSize", method = RequestMethod.GET)
	@ResponseBody
	public ResultVo getCountSize(@RequestParam("condition") Map<String, Object> condition);
	
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
	@RequestMapping(value = "/getPagingList", method = RequestMethod.GET)
	@ResponseBody
    public ResultVo getPagingList(@RequestBody PageVo<T> pageVo,@RequestParam("pageSize") Integer pageSize, @RequestParam("pageNumber") Integer pageNumber);
	
	/**
	 * 保存
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
    public ResultVo save(@RequestBody T t);
	
	/**
	 * 更新默认使用id
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
    public ResultVo update(@RequestBody T t);
	
	/**
	 * 根据id删除一个(逻辑删除)
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@ResponseBody
    public ResultVo deleteById(@RequestBody T t);
	
	/**
	 * 根据Id删除多个，使用(,)分割，(逻辑删除)
	 * @author AimSpeed
	 * @param request
	 * @param response
	 * @param t
	 * @param ids
	 * @return ResultVo 
	 */
	@RequestMapping(value = "/deletes", method = RequestMethod.DELETE)
	@ResponseBody
    public ResultVo deleteByIds(@RequestBody T t,@RequestParam("ids") String ids);

	
	
}
