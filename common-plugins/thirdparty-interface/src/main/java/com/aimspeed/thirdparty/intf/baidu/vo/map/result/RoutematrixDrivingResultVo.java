package com.aimspeed.thirdparty.intf.baidu.vo.map.result;

/**
 * 百度地图计算距离结果
 * @author AimSpeed
 */
public class RoutematrixDrivingResultVo {
	
	/**
	 * 线路距离的文本描述，文本描述的单位有公里
	 */
	private String distanceText;
	
	/**
	 * 线路距离的数值，米
	 */
	private String distanceValue;
	
	/**
	 * 路线耗时的文本，分钟
	 */
	private String durationText;
	
	/**
	 * 路线耗时的数值，秒
	 */
	private String durationValue;

	public String getDistanceText() {
		return distanceText;
	}

	public void setDistanceText(String distanceText) {
		this.distanceText = distanceText;
	}

	public String getDistanceValue() {
		return distanceValue;
	}

	public void setDistanceValue(String distanceValue) {
		this.distanceValue = distanceValue;
	}

	public String getDurationText() {
		return durationText;
	}

	public void setDurationText(String durationText) {
		this.durationText = durationText;
	}

	public String getDurationValue() {
		return durationValue;
	}

	public void setDurationValue(String durationValue) {
		this.durationValue = durationValue;
	}
	
		
	
	
}
