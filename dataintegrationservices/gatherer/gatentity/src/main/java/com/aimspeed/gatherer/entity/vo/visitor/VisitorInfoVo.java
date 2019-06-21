package com.aimspeed.gatherer.entity.vo.visitor;

/**
 * 访问者的信息
 * @author AimSpeed
 */
public class VisitorInfoVo {
	
	/****/
	private Integer id;

	/**账号**/
	private String account;

	/**访问者的令牌，系统内用户则不用，RPC调用的则会有令牌**/
	private String token;
	
	/**名称**/
	private String name;

	/**邮箱**/
	private String email;

	/**手机号**/
	private String phone;

	/** 最近登录时间 **/
	private java.util.Date lastLoginTime;
	
	/** 最近登录ip **/
	private String lastLoginIp;

	/** 状态 **/
	private Integer status;

	/**更新时间**/
	private java.util.Date updateTime;

	/**更新人**/
	private String updator;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}
	
	
	
	
}
