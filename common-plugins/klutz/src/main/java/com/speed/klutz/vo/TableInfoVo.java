package com.speed.klutz.vo;

/**
 * 表信息数据传输类
 * @author AimSpeed
 */
public class TableInfoVo {
	
	/** 表名 **/
	private String tableName;
	
	/** 实体名 **/
	private String beanName;
	
	/** 包路径信息 **/
	private String beanPackage;

	/** Mapper文件名称 **/
	private String mapperName;
	
	/** Mapper文件名称 **/
	private String mapperPackage;
	
	/** 表注释 **/
	private String tableComment;
	
	/** 分页 **/
	private String pagePackaging;
	
	public TableInfoVo() {
		super();
	}

	public TableInfoVo(String tableName, String beanName, String mapperName) {
		super();
		this.tableName = tableName;
		this.beanName = beanName;
		this.mapperName = mapperName;
	}

	public TableInfoVo(String tableName, String beanName, String mapperName, String tableComment) {
		super();
		this.tableName = tableName;
		this.beanName = beanName;
		this.mapperName = mapperName;
		this.tableComment = tableComment;
	}

	public TableInfoVo(String tableName, String beanName, String mapperName, String tableComment, String beanPackage) {
		super();
		this.tableName = tableName;
		this.beanName = beanName;
		this.mapperName = mapperName;
		this.tableComment = tableComment;
		this.beanPackage = beanPackage;
	}

	public TableInfoVo(String tableName, String beanName, String beanPackage, String mapperName, String mapperPackage,
			String tableComment) {
		super();
		this.tableName = tableName;
		this.beanName = beanName;
		this.beanPackage = beanPackage;
		this.mapperName = mapperName;
		this.mapperPackage = mapperPackage;
		this.tableComment = tableComment;
	}

	public TableInfoVo(String tableName, String beanName, String beanPackage, String mapperName, String mapperPackage,
			String tableComment, String pagePackaging) {
		super();
		this.tableName = tableName;
		this.beanName = beanName;
		this.beanPackage = beanPackage;
		this.mapperName = mapperName;
		this.mapperPackage = mapperPackage;
		this.tableComment = tableComment;
		this.pagePackaging = pagePackaging;
	}

	public String getPagePackaging() {
		return pagePackaging;
	}

	public void setPagePackaging(String pagePackaging) {
		this.pagePackaging = pagePackaging;
	}

	public String getMapperPackage() {
		return mapperPackage;
	}

	public void setMapperPackage(String mapperPackage) {
		this.mapperPackage = mapperPackage;
	}

	public String getBeanPackage() {
		return beanPackage;
	}

	public void setBeanPackage(String beanPackage) {
		this.beanPackage = beanPackage;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMapperName() {
		return mapperName;
	}

	public void setMapperName(String mapperName) {
		this.mapperName = mapperName;
	}
	
	
}
