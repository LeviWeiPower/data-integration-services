package com.aimspeed.gatherer.entity.bean.mysql.content;

import com.aimspeed.gatherer.entity.bean.mysql.BaseMySqlBean;

/**
 * 
 * 内容数据
 * 
 */
public class ContentDataMySqlBean extends BaseMySqlBean {

	/**
	 * @reason 修改原因
	 */ 
	private static final long serialVersionUID = -593799738139977702L;

	/****/
	private Integer id;

	/**采集者的唯一序列号**/
	private String sequence;

	/**爬虫人物名**/
	private String crawlerName;

	/**数据分类（避免数据混乱）**/
	private String dataClassify;

	/**内容类别**/
	private String contentClassify;

	/**内容地址**/
	private String contentUrl;

	/**内容数据**/
	private String content;

	/**数据uuid**/
	private String dataUuid;

	/**数据链uuid，有关联的数据**/
	private String dataChainUuid;

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

	public void setCrawlerName(String crawlerName){
		this.crawlerName = crawlerName;
	}

	public String getCrawlerName(){
		return this.crawlerName;
	}

	public void setDataClassify(String dataClassify){
		this.dataClassify = dataClassify;
	}

	public String getDataClassify(){
		return this.dataClassify;
	}

	public void setContentClassify(String contentClassify){
		this.contentClassify = contentClassify;
	}

	public String getContentClassify(){
		return this.contentClassify;
	}

	public void setContentUrl(String contentUrl){
		this.contentUrl = contentUrl;
	}

	public String getContentUrl(){
		return this.contentUrl;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setDataUuid(String dataUuid){
		this.dataUuid = dataUuid;
	}

	public String getDataUuid(){
		return this.dataUuid;
	}

	public void setDataChainUuid(String dataChainUuid){
		this.dataChainUuid = dataChainUuid;
	}

	public String getDataChainUuid(){
		return this.dataChainUuid;
	}

	public void setIsDelete(String isDelete){
		this.isDelete = isDelete;
	}

	public String getIsDelete(){
		return this.isDelete;
	}

	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime(){
		return this.updateTime;
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

}
