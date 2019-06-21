package com.aimspeed.thirdparty.intf.baidu.vo.map.result;

/** 
 * 百度Ip调用方式的Vo
 * @author AimSpeed
 */
public class IpResultVo {
	
	/**
	 * 详细地址
	 */
	private String address;

	/**
	 * 简要地址信息
	 */
	private String shortAddress;

	/**
	 * 城市
	 */
	private String city;
	
	
	/**
	 * 城市代码
	 */
	private String cityCode;
	
	/**
	 * 区县。
	 */
	private String district;
	
	/**
	 * 省份。
	 */
	private String province;
	
	/**
	 * 街道。
	 */
	private String street;
	
	/**
	 * 门牌号。
	 */
	private String streetNumber;

	/**
	 * 经度
	 */
	private String longitude;

	/**
	 * 纬度
	 */
	private String latitude;

	/**
	 * 结果状态返回码
	 */
	private String status;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShortAddress() {
		return shortAddress;
	}

	public void setShortAddress(String shortAddress) {
		this.shortAddress = shortAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "IpResultVo [address=" + address + ", shortAddress=" + shortAddress + ", city=" + city + ", cityCode="
				+ cityCode + ", district=" + district + ", province=" + province + ", street=" + street
				+ ", streetNumber=" + streetNumber + ", longitude=" + longitude + ", latitude=" + latitude + ", status="
				+ status + "]";
	}
	

	
	
}
