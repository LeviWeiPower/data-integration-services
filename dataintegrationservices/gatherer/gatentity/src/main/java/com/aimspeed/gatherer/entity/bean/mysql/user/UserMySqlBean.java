package com.aimspeed.gatherer.entity.bean.mysql.user;
/**
 * 
 * @author AimSpeed
 */
public class UserMySqlBean {
	/****/
	private Integer id;

	/**账号**/
	private String account;

	/**用户名**/
	private String name;

	/**邮箱**/
	private String email;

	/**手机号**/
	private String phone;

	/**密码（md5加密）**/
	private String password;

	/**角色id**/
	private Integer roleId;

	/**角色名称**/
	private String roleName;

	/**权限所有url(使用,分割)**/
	private String authorityAllUrl;

	/**最近登录时间**/
	private java.util.Date lastLoginTime;

	/**最近登录ip**/
	private String lastLoginIp;

	/**状态（1-禁用、0-启用）**/
	private Integer status;

	/**创建时间**/
	private java.util.Date createTime;

	/**创建人**/
	private String creator;

	/**更新时间**/
	private java.util.Date updateTime;

	/**更新人**/
	private String updator;

	/**是否删除，N未删除，Y已删除默认N**/
	private String isDelete;



	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setAccount(String account){
		this.account = account;
	}

	public String getAccount(){
		return this.account;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
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

	public void setRoleName(String roleName){
		this.roleName = roleName;
	}

	public String getRoleName(){
		return this.roleName;
	}

	public void setAuthorityAllUrl(String authorityAllUrl){
		this.authorityAllUrl = authorityAllUrl;
	}

	public String getAuthorityAllUrl(){
		return this.authorityAllUrl;
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

	public void setIsDelete(String isDelete){
		this.isDelete = isDelete;
	}

	public String getIsDelete(){
		return this.isDelete;
	}

}
