package com.aimspeed.operations.entity.bean.mysql.user;

import com.aimspeed.mysql.BaseMySqlBean;

/**
 * 用户表
 */
public class UserMySqlBean extends BaseMySqlBean {

	/** 
	 * @author AimSpeed
	 */
	private static final long serialVersionUID = 3841757144592146315L;

	/**
	 * 
	 */
	private Integer id;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 账号
	 */
	private String account;

	/**
	 * 密码（md5加密）
	 */
	private String password;

	/**
	 * 角色id
	 */
	private Integer roleId;

	/**
	 * 最近登录时间
	 */
	private java.util.Date lastLoginTime;

	/**
	 * 最近登录ip
	 */
	private String lastLoginIp;

	/**
	 * 账户状态（0-正常、1-禁用）
	 */
	private Integer status;

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

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	public String getNickname(){
		return this.nickname;
	}

	public void setAccount(String account){
		this.account = account;
	}

	public String getAccount(){
		return this.account;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setRoleId(Integer roleId){
		this.roleId = roleId;
	}

	public Integer getRoleId(){
		return this.roleId;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}

	public java.util.Date getLastLoginTime(){
		return this.lastLoginTime;
	}

	public void setLastLoginIp(String lastLoginIp){
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginIp(){
		return this.lastLoginIp;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
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
