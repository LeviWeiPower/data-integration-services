package com.aimspeed.thirdparty.intf.baidu.vo.map.param;

/**
 * ip定位请求参数
 * @author AimSpeed
 */
public class GeocoderTowardsReqParamVo {
	
	/** 
	 * @author AimSpeed
	 * @date 2018年8月16日 
	 */
	private static final long serialVersionUID = -459573669297298176L;

	/**
	 * 待解析的地址。最多支持84个字节。
	 * 	可以输入三种样式的值，分别是： 
	 * 	1、标准的结构化地址信息，如北京市海淀区上地十街十号 【推荐，地址结构越完整，解析精度越高】 
	 * 	2、支持“*路与*路交叉口”描述方式，如北一环路和阜阳路的交叉路口 
	 * 	第二种方式并不总是有返回结果，只有当地址库中存在该地址描述时才有返回。	string	北京市海淀区上地十街10号	无	是 
	 */
	private String address;

	/**
	 * 地址所在的城市名。
	 * 用于指定上述地址所在的城市，当多个城市都有上述地址时，该参数起到过滤作用，但不限制坐标召回城市。
	 */
	private String city;
	
	/**
	 * 可选参数，添加后返回国测局经纬度坐标或百度米制坐标 坐标系说明	
	 * string	gcj02ll（国测局坐标）、bd09mc（百度墨卡托坐标）	bd09ll（百度经纬度坐标）	否
	 */
	private String retCoordType;
	
	/**
	 * 输出格式为json或者xml，默认json
	 */
	private String outPut;
	
	/**
	 * ak码
	 */
	private String ak;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRetCoordType() {
		return retCoordType;
	}

	public void setRetCoordType(String retCoordType) {
		this.retCoordType = retCoordType;
	}

	public String getOutPut() {
		return outPut;
	}

	public void setOutPut(String outPut) {
		this.outPut = outPut;
	}

	public String getAk() {
		return ak;
	}

	public void setAk(String ak) {
		this.ak = ak;
	}

	
	
}
