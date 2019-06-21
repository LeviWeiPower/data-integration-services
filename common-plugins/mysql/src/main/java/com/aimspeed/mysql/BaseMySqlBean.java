package com.aimspeed.mysql;

import java.io.Serializable;

/**
 * 基础的Mysql类
 * @author AimSpeed
 */
public class BaseMySqlBean implements Serializable {
	
	/**
	 */ 
	private static final long serialVersionUID = -3047971221154628028L;

	/**是否删除 N非删除 Y删除**/
	private String isDelete;

	/**更新时间**/
	private java.util.Date updateTime;

	/**创建人**/
	private String creator;

	/**创建时间**/
	private java.util.Date createTime;

	/**更新人**/
	private String updator;

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}
	
	
	
}
