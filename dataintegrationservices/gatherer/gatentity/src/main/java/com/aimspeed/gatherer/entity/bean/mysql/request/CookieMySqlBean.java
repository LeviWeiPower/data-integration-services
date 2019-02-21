package com.aimspeed.gatherer.entity.bean.mysql.request;

import com.aimspeed.gatherer.entity.bean.mysql.BaseMySqlBean;

/**
 * cookie信息表
 * @author AimSpeed
 */
public class CookieMySqlBean extends BaseMySqlBean {

	/**
	 * @date 2018年6月20日 上午12:07:16
	 * @reason 修改原因
	 */ 
	private static final long serialVersionUID = 4048328044789681322L;

	/****/
	private Integer id;

	/**采集者的唯一序列号**/
	private String sequence;

	/**键**/
	private String cookieKey;

	/**值**/
	private String cookieValue;

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

	public void setCookieKey(String cookieKey){
		this.cookieKey = cookieKey;
	}

	public String getCookieKey(){
		return this.cookieKey;
	}

	public void setCookieValue(String cookieValue){
		this.cookieValue = cookieValue;
	}

	public String getCookieValue(){
		return this.cookieValue;
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
