package com.aimspeed.gatherer.entity.bean.mysql.rule;

import com.aimspeed.mysql.BaseMySqlBean;

/**
 * 基础规则表
 * @author AimSpeed
 * @date 2019年04月11日
 */
public class CrawlerMySqlBean extends BaseMySqlBean {

	/**
	 * 
	 */
	private Integer id;

	/**
	 * 采集者的唯一序列号
	 */
	private String sequence;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 地址
	 */
	private String url;

	/**
	 * 域名
	 */
	private String mainUrl;

	/**
	 * 数据分类（避免数据混乱）
	 */
	private String dataClassify;

	/**
	 * 层级，从主链接页开始算，从0开始，没有填默认为0
	 */
	private Integer hierarchy;

	/**
	 * 请求方式，没有则为get
	 */
	private String method;

	/**
	 * 编码，默认utf8
	 */
	private String coding;

	/**
	 * 每分钟请求次数，默认为60次
	 */
	private Integer requestNum;

	/**
	 * 是否开启运行中，'N' 否，'Y'是，默认为'N'
	 */
	private String isStart;

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

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return this.url;
	}

	public void setMainUrl(String mainUrl){
		this.mainUrl = mainUrl;
	}

	public String getMainUrl(){
		return this.mainUrl;
	}

	public void setDataClassify(String dataClassify){
		this.dataClassify = dataClassify;
	}

	public String getDataClassify(){
		return this.dataClassify;
	}

	public void setHierarchy(Integer hierarchy){
		this.hierarchy = hierarchy;
	}

	public Integer getHierarchy(){
		return this.hierarchy;
	}

	public void setMethod(String method){
		this.method = method;
	}

	public String getMethod(){
		return this.method;
	}

	public void setCoding(String coding){
		this.coding = coding;
	}

	public String getCoding(){
		return this.coding;
	}

	public void setRequestNum(Integer requestNum){
		this.requestNum = requestNum;
	}

	public Integer getRequestNum(){
		return this.requestNum;
	}

	public void setIsStart(String isStart){
		this.isStart = isStart;
	}

	public String getIsStart(){
		return this.isStart;
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
