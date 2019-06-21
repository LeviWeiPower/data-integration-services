package com.aimspeed.gatherer.entity.bean.mysql.content;

import com.aimspeed.mysql.BaseMySqlBean;

/**
 * 执行过的URL
 * @author AimSpeed
 * @date 2019年04月11日
 */
public class ExecutedUrlMySqlBean extends BaseMySqlBean {

	/**
	 * 
	 */
	private Integer id;

	/**
	 * 采集者的唯一序列号
	 */
	private String sequence;

	/**
	 * 当前的这个URL的父URL盐值（来源）
	 */
	private String parentUrlSalting;

	/**
	 * 执行过的URL
	 */
	private String executedUrl;

	/**
	 * URL的MD5盐值
	 */
	private String urlSalting;

	/**
	 * 请求方式
	 */
	private String method;

	/**
	 * 页面盐值编码，用于识别页面数据是否改变过，如果改变了则重新抓取数据。
	 */
	private String pageSalting;

	/**
	 * 数据包中的最后更新时间
	 */
	private String lastUpdateTime;

	/**
	 * 是否删除 N非删除 Y删除
	 */
	private String isDelete;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	private java.util.Date createTime;

	/**
	 * 更新人
	 */
	private String updator;

	/**
	 * 更新时间
	 */
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

	public void setParentUrlSalting(String parentUrlSalting){
		this.parentUrlSalting = parentUrlSalting;
	}

	public String getParentUrlSalting(){
		return this.parentUrlSalting;
	}

	public void setExecutedUrl(String executedUrl){
		this.executedUrl = executedUrl;
	}

	public String getExecutedUrl(){
		return this.executedUrl;
	}

	public void setUrlSalting(String urlSalting){
		this.urlSalting = urlSalting;
	}

	public String getUrlSalting(){
		return this.urlSalting;
	}

	public void setMethod(String method){
		this.method = method;
	}

	public String getMethod(){
		return this.method;
	}

	public void setPageSalting(String pageSalting){
		this.pageSalting = pageSalting;
	}

	public String getPageSalting(){
		return this.pageSalting;
	}

	public void setLastUpdateTime(String lastUpdateTime){
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateTime(){
		return this.lastUpdateTime;
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
