package com.aimspeed.common.vo;

import com.aimspeed.common.enums.HttpResponseEnum;

/** 
 * 访问结果
 */
public class ResultVo {
	
	/** 状态 **/
	private Integer status;
	
	/** 消息*****/
	private String msg;
	
	/** 对象**/
	private Object result;
	
	/** 数据数量 **/
	private Integer totalCount;
	
	/**
	 * 默认为成功
	 * @author AimSpeed
	 */
	public ResultVo() {
		super();
		this.status = HttpResponseEnum.SUCCESS.getCode();
		this.msg = HttpResponseEnum.SUCCESS.getValue();
	}

	public ResultVo(HttpResponseEnum httpResponseEnum) {
		super();
		this.status = httpResponseEnum.getCode();
		this.msg = httpResponseEnum.getValue();
	}
	
	public ResultVo(HttpResponseEnum httpResponseEnum,Object result) {
		super();
		this.status = httpResponseEnum.getCode();
		this.msg = httpResponseEnum.getValue();
		this.result = result;
	}
	
	public ResultVo(HttpResponseEnum httpResponseEnum,Object result,Integer totalCount) {
		super();
		this.status = httpResponseEnum.getCode();
		this.msg = httpResponseEnum.getValue();
		this.result = result;
		this.totalCount = totalCount;
	}
	
	/**
	 * 默认为成功
	 * @author AimSpeed
	 * @param msg 成功的提示语
	 */
	public ResultVo(Object result) {
		super();
		this.status = HttpResponseEnum.SUCCESS.getCode();
		this.msg = HttpResponseEnum.SUCCESS.getValue();
		this.result = result;
	}
	
	/**
	 * 构建对象
	 * @author AimSpeed
	 * @param status 状态码
	 * @param msg 提示消息
	 */
	public ResultVo(Integer status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	/**
	 * 构建对象
	 * @author AimSpeed
	 * @param status 状态码
	 * @param msg 提示消息
	 * @param result 结果数据
	 */
	public ResultVo(Integer status, String msg, Object result) {
		super();
		this.status = status;
		this.msg = msg;
		this.result = result;
	}
	
	/**
	 * 构建对象
	 * @author AimSpeed
	 * @param status 状态码
	 * @param msg 提示消息
	 * @param result 结果数据
	 * @param totalCount 结果总条数
	 */
	public ResultVo(Integer status, String msg, Object result, Integer totalCount) {
		super();
		this.status = status;
		this.msg = msg;
		this.result = result;
		this.totalCount = totalCount;
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public ResultVo(Integer status) {
		super();
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	/**
	 * 默认为成功的提示
	 * @author AimSpeed
	 * @return ResultVo
	 */
	public static ResultVo getSuccess() {
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(), HttpResponseEnum.SUCCESS.getValue());
	}
	
	/**
	 * 默认为成功的提示
	 * @author AimSpeed
	 * @param msg 消息
	 * @return ResultVo
	 */
	public static ResultVo getSuccess(String msg) {
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(), msg);
	}

	/**
	 * 默认为成功的提示
	 * @author AimSpeed
	 * @param msg 消息
	 * @param totalCount 数据量
	 * @return ResultVo
	 */
	public static ResultVo getSuccess(String msg,Object result) {
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(), msg,result);
	}
	
	/**
	 * 默认为成功的提示
	 * @author AimSpeed
	 * @param msg 消息
	 * @param totalCount 数据量
	 * @return ResultVo
	 */
	public static ResultVo getSuccess(String msg,Integer totalCount) {
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(), msg,totalCount);
	}
	
	/**
	 * 系统错误的提示
	 * @author AimSpeed
	 * @return ResultVo
	 */
	public static ResultVo getSystemError() {
		return new ResultVo(HttpResponseEnum.SYSTEM_ERROR.getCode(), HttpResponseEnum.SYSTEM_ERROR.getValue());
	}
	
	/**
	 * 系统错误的提示
	 * @author AimSpeed
	 * @param msg 消息
	 * @return ResultVo
	 */
	public static ResultVo getSystemError(String msg) {
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(), msg);
	}
	
	/**
	 * 参数错误的提示
	 * @author AimSpeed
	 * @return ResultVo
	 */
	public static ResultVo getParamError() {
		return new ResultVo(HttpResponseEnum.PARAM_ERROR.getCode(), HttpResponseEnum.PARAM_ERROR.getValue());
	}

	/**
	 * 参数错误的提示
	 * @author AimSpeed
	 * @param msg 消息
	 * @return ResultVo
	 */
	public static ResultVo getParamError(String msg) {
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(), msg);
	}
	
}
