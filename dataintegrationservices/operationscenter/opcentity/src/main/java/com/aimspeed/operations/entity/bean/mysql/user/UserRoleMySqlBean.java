package com.aimspeed.operations.entity.bean.mysql.user;

import com.aimspeed.mysql.BaseMySqlBean;

/**
 * 用户角色表
 */
public class UserRoleMySqlBean extends BaseMySqlBean {

	/**
	 * @reason 修改原因
	 */ 
	private static final long serialVersionUID = -5908662998533811129L;

	/**
	 * 
	 */
	private Integer id;

	/**
	 * 角色名
	 */
	private String name;

	/**
	 * 权限id
	 */
	private String authorityIds;

	/**
	 * 是否删除，N未删除，Y已删除 - 默认N
	 */
	private String isDelete;

	/**
	 * 创建时间
	 */
	private java.util.Date createTime;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

	/**
	 * 更新人
	 */
	private String updator;



	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setAuthorityIds(String authorityIds){
		this.authorityIds = authorityIds;
	}

	public String getAuthorityIds(){
		return this.authorityIds;
	}

	public void setIsDelete(String isDelete){
		this.isDelete = isDelete;
	}

	public String getIsDelete(){
		return this.isDelete;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setCreator(String creator){
		this.creator = creator;
	}

	public String getCreator(){
		return this.creator;
	}

	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

	public void setUpdator(String updator){
		this.updator = updator;
	}

	public String getUpdator(){
		return this.updator;
	}

}
