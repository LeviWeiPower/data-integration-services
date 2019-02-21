package com.aimspeed.gatherer.entity.bean.mongo.content;

import java.util.Map;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.aimspeed.gatherer.entity.bean.mongo.BaseMongoBean;

/**
 *  内容数据
 * @author AimSpeed
 */
@Document(collection="content")
/*@CompoundIndexes({
    @CompoundIndex(name = "age_idx", def = "{'name': 1, 'age': -1}")
})*/
public class ContentDataMongoBean extends BaseMongoBean {
	
	/** 
	 * @author AimSpeed
	 * @date 2018年8月3日 
	 */
	private static final long serialVersionUID = -5358330146635852806L;

	/****/
	@Id
	private String id;

	/**采集者的唯一序列号**/
	private String sequence;

	/**爬虫人物名**/
	private String crawlerName;

	/**数据分类（避免数据混乱）**/
	private String dataClassify;

	/**内容地址**/
	private String contentUrl;

	/**内容数据**/
	private Map<String,Object> content;

	/**数据盐值**/
	private String contentSaltingVal;
	
	/**数据uuid**/
	private String dataUuid;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getCrawlerName() {
		return crawlerName;
	}

	public void setCrawlerName(String crawlerName) {
		this.crawlerName = crawlerName;
	}

	public String getDataClassify() {
		return dataClassify;
	}

	public void setDataClassify(String dataClassify) {
		this.dataClassify = dataClassify;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public Map<String, Object> getContent() {
		return content;
	}

	public void setContent(Map<String, Object> content) {
		this.content = content;
	}

	public String getDataUuid() {
		return dataUuid;
	}

	public void setDataUuid(String dataUuid) {
		this.dataUuid = dataUuid;
	}

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
