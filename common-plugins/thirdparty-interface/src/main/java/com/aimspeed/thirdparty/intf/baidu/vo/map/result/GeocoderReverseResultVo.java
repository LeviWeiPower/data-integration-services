package com.aimspeed.thirdparty.intf.baidu.vo.map.result;

/**
 * 逆向查询位置
 * @author AimSpeed
 */
public class GeocoderReverseResultVo {
	
	/**
	 * 结果状态
	 */
	private String status;

	/**
	 * 经度
	 */
	private String longitude;

	/**
	 * 纬度
	 */
	private String latitude;

	/**
	 * 结构化地址信息
	 */
	private String formattedAddress;

	/**
	 * 可信度，描述打点准确度。[0,100]，大于80表示误差低于100m
	 */
	private String confidence;

	/**
	 * 坐标所在商圈信息，如 "人民大学,中关村,苏州街"。最多返回3个。
	 */
	private String business;

	/**
	 * 国家
	 */
	private String country;

	/**
	 * 国家代码
	 */
	private String countryCode;

	/**
	 * 国际标准国家3位编码（ISO 3166-1 alpha-3）
	 */
	private String countryCodeIso;

	/**
	 * 国际标准国家2位编码（ISO 3166-1 alpha-2）
	 */
	private String countryCodeIsoTwo;

	/**
	 * 省
	 */
	private String province;

	/**
	 * 市
	 */
	private String city;

	/**
	 * 区
	 */
	private String district;

	/**
	 * 乡镇
	 */
	private String town;

	/**
	 * 街道
	 */
	private String street;
	
	/**
	 * 街道门牌号
	 */
	private String streetNumber;
	
	/**
	 * 相对当前坐标点的方向，当有门牌号的时候返回数据
	 */
	private String direction;
	
	/**
	 * 相对当前坐标点的距离，当有门牌号的时候返回数据
	 */
	private String distance;
	
	/**
	 * 当前位置结合POI的语义化结果描述。
	 */
	private String sematicDescription;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public String getConfidence() {
		return confidence;
	}

	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryCodeIso() {
		return countryCodeIso;
	}

	public void setCountryCodeIso(String countryCodeIso) {
		this.countryCodeIso = countryCodeIso;
	}

	public String getCountryCodeIsoTwo() {
		return countryCodeIsoTwo;
	}

	public void setCountryCodeIsoTwo(String countryCodeIsoTwo) {
		this.countryCodeIsoTwo = countryCodeIsoTwo;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
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

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getSematicDescription() {
		return sematicDescription;
	}

	public void setSematicDescription(String sematicDescription) {
		this.sematicDescription = sematicDescription;
	}
	
	
	
	
	
}
