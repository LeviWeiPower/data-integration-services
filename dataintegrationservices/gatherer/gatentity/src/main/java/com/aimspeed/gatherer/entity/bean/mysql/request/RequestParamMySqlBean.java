package com.aimspeed.gatherer.entity.bean.mysql.request;

import com.aimspeed.gatherer.entity.bean.mysql.BaseMySqlBean;

/**
 * 
 * 启用搜索的参数
 * 
 */
public class RequestParamMySqlBean extends BaseMySqlBean {

	/**
	 * @reason 修改原因
	 */ 
	private static final long serialVersionUID = 8325380150555740451L;

	/****/
	private Integer id;

	/**采集者的唯一序列号**/
	private String sequence;

	/**键**/
	private String paramKey;

	/**值**/
	private String paramValue;

	/**是否删除 N非删除 Y删除**/
	private String isDelete;

	/**创建人**/
	private String creator;

	/**创建时间**/
	private java.util.Date createTime;

	/**更新人**/
	private String updator;

	/**更新时间**/
	private java.util.Date updateTime;



	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setSequence(String sequence){
		this.sequence = sequence;
	}

	public String getSequence(){
		return this.sequence;
	}

	public void setParamKey(String paramKey){
		this.paramKey = paramKey;
	}

	public String getParamKey(){
		return this.paramKey;
	}

	public void setParamValue(String paramValue){
		this.paramValue = paramValue;
	}

	public String getParamValue(){
		return this.paramValue;
	}

	public void setIsDelete(String isDelete){
		this.isDelete = isDelete;
	}

	public String getIsDelete(){
		return this.isDelete;
	}

	public void setCreator(String creator){
		this.creator = creator;
	}

	public String getCreator(){
		return this.creator;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setUpdator(String updator){
		this.updator = updator;
	}

	public String getUpdator(){
		return this.updator;
	}

	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

}
