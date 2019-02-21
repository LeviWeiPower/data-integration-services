package com.aimspeed.gatherer.entity.vo.result;

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

	public ResultVo(Integer status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public ResultVo(Integer status, String msg, Object result) {
		super();
		this.status = status;
		this.msg = msg;
		this.result = result;
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
	
	
}
