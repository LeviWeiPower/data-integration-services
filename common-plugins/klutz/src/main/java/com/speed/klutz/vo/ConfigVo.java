package com.speed.klutz.vo;

/**
 * 配置文件数据传输类
 * @author AimSpeed
 */
public class ConfigVo {
	
	/**
	 * 控制层的包路径
	 */
	private String controllerPackage;

	/**
	 * service层的包路径
	 */
	private String servicePackage;

	/**
	 * mapperXml文件的扫描路径
	 */
	private String mapperXmlClasspath;
	 
	/**
	 * 实体存放的包路径
	 */
	private String beanPackage;

	/**
	 * Mapper文件的包路径
	 */
	private String mapperPackage;

	public ConfigVo() {
		super();
	}

	public ConfigVo(String controllerPackage, String servicePackage, String mapperXmlClasspath, String beanPackage,
			String mapperPackage) {
		super();
		this.controllerPackage = controllerPackage;
		this.servicePackage = servicePackage;
		this.mapperXmlClasspath = mapperXmlClasspath;
		this.beanPackage = beanPackage;
		this.mapperPackage = mapperPackage;
	}

	public ConfigVo(String controllerPackage, String servicePackage, String beanPackage) {
		super();
		this.controllerPackage = controllerPackage;
		this.servicePackage = servicePackage;
		this.beanPackage = beanPackage;
	}

	public String getControllerPackage() {
		return controllerPackage;
	}

	public void setControllerPackage(String controllerPackage) {
		this.controllerPackage = controllerPackage;
	}

	public String getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	public String getMapperXmlClasspath() {
		return mapperXmlClasspath;
	}

	public void setMapperXmlClasspath(String mapperXmlClasspath) {
		this.mapperXmlClasspath = mapperXmlClasspath;
	}

	public String getBeanPackage() {
		return beanPackage;
	}

	public void setBeanPackage(String beanPackage) {
		this.beanPackage = beanPackage;
	}

	public String getMapperPackage() {
		return mapperPackage;
	}

	public void setMapperPackage(String mapperPackage) {
		this.mapperPackage = mapperPackage;
	}
	
	
	
	 
	 
	
}
