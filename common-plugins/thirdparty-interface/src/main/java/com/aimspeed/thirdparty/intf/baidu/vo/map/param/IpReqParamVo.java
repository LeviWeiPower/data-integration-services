package com.aimspeed.thirdparty.intf.baidu.vo.map.param;

/**
 * ip定位请求参数
 * @author AimSpeed
 */
public class IpReqParamVo {
	/**
	 * ip
	 */
	private String ip;
	
	/**
	 * coor不出现、或为空：百度墨卡托坐标，即百度米制坐标；
	 * coor = bd09ll：百度经纬度坐标，在国测局坐标基础之上二次加密而来；
	 * coor = gcj02：国测局02坐标，在原始GPS坐标基础上，按照国家测绘行业统一要求，加密后的坐标；
	 * 注：百度地图的坐标类型为bd09ll，如果结合百度地图使用，请注意坐标选择。
	 */
	private String coor;
	
	/**
	 * ak码
	 */
	private String ak;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCoor() {
		return coor;
	}

	public void setCoor(String coor) {
		this.coor = coor;
	}

	public String getAk() {
		return ak;
	}

	public void setAk(String ak) {
		this.ak = ak;
	}
	
	
}
