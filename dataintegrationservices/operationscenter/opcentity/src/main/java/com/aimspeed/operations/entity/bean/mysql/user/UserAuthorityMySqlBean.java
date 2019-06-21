package com.aimspeed.operations.entity.bean.mysql.user;

import com.aimspeed.mysql.BaseMySqlBean;

/**
 * 用户权限表
 */
public class UserAuthorityMySqlBean extends BaseMySqlBean {

	/**
	 * @reason 修改原因
	 */ 
	private static final long serialVersionUID = -3709511152529923380L;

	/**
	 * 
	 */
	private Integer id;

	/**
	 * 页面展示的时候的id
	 */
	private String pageId;

	/**
	 * 权限名称
	 */
	private String name;

	/**
	 * 权限url
	 */
	private String uri;

	/**
	 * 权限级别，系统模块权限级别为0
	 */
	private Integer level;

	/**
	 * 父级权限
	 */
	private Integer pid;

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

	public void setPageId(String pageId){
		this.pageId = pageId;
	}

	public String getPageId(){
		return this.pageId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}


	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setLevel(Integer level){
		this.level = level;
	}

	public Integer getLevel(){
		return this.level;
	}

	public void setPid(Integer pid){
		this.pid = pid;
	}

	public Integer getPid(){
		return this.pid;
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
