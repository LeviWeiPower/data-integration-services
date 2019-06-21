package com.aimspeed.thirdparty.intf.baidu.map;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.aimspeed.thirdparty.intf.baidu.constants.MapReqUrlConstans;
import com.aimspeed.thirdparty.intf.baidu.vo.map.param.GeocoderTowardsReqParamVo;
import com.aimspeed.thirdparty.intf.baidu.vo.map.param.IpReqParamVo;
import com.aimspeed.thirdparty.intf.baidu.vo.map.result.GeocoderTowardsResultVo;
import com.aimspeed.thirdparty.intf.baidu.vo.map.result.IpResultVo;
import com.aimspeed.thirdparty.intf.utils.UrlUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 百度地图定位
 * @author AimSpeed
 */
public class BaiDuMapLocation {
	
	public static void main(String[] args) {
		//IpResultVo [address=CN|广东|None|None|ALIBABA|0|0, shortAddress=广东省, city=, cityCode=7, district=, province=广东省, street=, streetNumber=, longitude=12623190.7, latitude=2664306.83, status=0]
		IpReqParamVo ipLocationReqParam = new IpReqParamVo();
		ipLocationReqParam.setAk("22222222222222");
		ipLocationReqParam.setIp("333333333333333");
		System.out.println(new BaiDuMapLocation().ipLocation(ipLocationReqParam));
		
		//GeocoderTowardsResultVo [status=null, level=道路, precise=0, confidence=30, longitude=113.27566333447861, latitude=23.12679107720343]
		GeocoderTowardsReqParamVo geocoderTowardsReqParamVo = new GeocoderTowardsReqParamVo();
		geocoderTowardsReqParamVo.setAk("4444444444444444444");
		geocoderTowardsReqParamVo.setAddress("广东省");
		System.out.println(new BaiDuMapLocation().geocoderTowards(geocoderTowardsReqParamVo));
		
	}
	
	/**
	 * 根据Ip进行定位
	 * @author AimSpeed
	 * @param ipLocationReqParam
	 * @return IpResultVo
	 */
	public IpResultVo ipLocation(IpReqParamVo ipLocationReqParam) {
		Map<String, String> param = new LinkedHashMap<String, String>();
		if(null != ipLocationReqParam.getIp()) {
			param.put("ip", ipLocationReqParam.getIp());
		}
		if(null != ipLocationReqParam.getCoor()) {
			param.put("coor", ipLocationReqParam.getCoor());
		}

		param.put("ak", ipLocationReqParam.getAk());
		
		//访问地址
		String reqPath = MapReqUrlConstans.MAP_URI + MapReqUrlConstans.IP_URL;
		
		reqPath = doGetUrlJoint(reqPath,param);
		
		try {
			JSONObject jsonObject = JSON.parseObject(UrlUtils.sendGet(reqPath, null, null));
			String status = jsonObject.getString("status");
			
			if("0".equals(status)) {
				String address = jsonObject.getString("address");
				//内容信息
				JSONObject contentJSONObject = JSON.parseObject(jsonObject.getString("content"));
				String shortAddress = contentJSONObject.getString("address");
				
				//信息地址信息
				JSONObject addressDetailJSONObject = JSON.parseObject(contentJSONObject.getString("address_detail"));

				String province = addressDetailJSONObject.getString("province");
				String city = addressDetailJSONObject.getString("city");
				String cityCode = addressDetailJSONObject.getString("city_code");
				String district = addressDetailJSONObject.getString("district");
				String street = addressDetailJSONObject.getString("street");
				String streetNumber = addressDetailJSONObject.getString("street_number");
				
				//定位信息
				JSONObject pointJSONObject = JSON.parseObject(contentJSONObject.getString("point"));
				String longitude = pointJSONObject.getString("x");
				String latitude = pointJSONObject.getString("y");
				
				IpResultVo ipResultVo = new IpResultVo();
				ipResultVo.setAddress(address);
				ipResultVo.setShortAddress(shortAddress);
				ipResultVo.setProvince(province);
				ipResultVo.setCity(city);
				ipResultVo.setCityCode(cityCode);
				ipResultVo.setDistrict(district);
				ipResultVo.setStreet(street);
				ipResultVo.setStreetNumber(streetNumber);
				ipResultVo.setLongitude(longitude);
				ipResultVo.setLatitude(latitude);
				ipResultVo.setStatus(status);
				
				return ipResultVo;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 正向地理编码服务提供将结构化地址数据（如：北京市海淀区上地十街十号）转换为对应坐标点（经纬度）功能；
	 * @author AimSpeed
	 * @param geocoderTowardsReqParamVo
	 * @return GeocoderTowardsResultVo
	 */
	public GeocoderTowardsResultVo geocoderTowards(GeocoderTowardsReqParamVo geocoderTowardsReqParamVo) {
		
		Map<String, String> param = new LinkedHashMap<>();
		param.put("address", geocoderTowardsReqParamVo.getAddress());
		
		if(null != geocoderTowardsReqParamVo.getCity()) {
			param.put("city", geocoderTowardsReqParamVo.getCity());
		}
		
		if(null != geocoderTowardsReqParamVo.getRetCoordType()) {
			param.put("ret_coordtype", geocoderTowardsReqParamVo.getRetCoordType());
		}
		//返回数据类型，默认是Xml
		param.put("output", "json");
		
		//生成SNCode
		/*String snCode = snCode(PropertiesUtils.getProperty(ServicePropertiesConstants.COMMON_FILE_PATH, 
								ServicePropertiesConstants.BAIDU_MAP_GEOCODER_URL),param);*/
		
		param.put("ak", geocoderTowardsReqParamVo.getAk());
//		param.put("sn", snCode);
		
		//访问地址
		String reqPath = MapReqUrlConstans.MAP_URI + MapReqUrlConstans.GEOCODER_URL;
		
		
		reqPath = doGetUrlJoint(reqPath,param);
		
		try {
			/*HttpClientResult result = HttpClientService.doGet(reqPath, "UTF-8", null, null);
			String resultStr = result.getResponseBody();
			
			JSONObject jsonObject = JSON.parseObject(resultStr);*/
			JSONObject jsonObject = JSON.parseObject(UrlUtils.sendGet(reqPath, null, null));
			String status = jsonObject.getString("status");
			
			if("0".equals(status)) {
				jsonObject = JSON.parseObject(jsonObject.getString("result"));
				
				//定位信息
				JSONObject pointJSONObject = JSON.parseObject(jsonObject.getString("location"));
				String longitude = pointJSONObject.getString("lng");
				String latitude = pointJSONObject.getString("lat");
				
				String precise = jsonObject.getString("precise");
				String confidence = jsonObject.getString("confidence");
				String level = jsonObject.getString("level");
				
				GeocoderTowardsResultVo baiDuGeocoderTowardsResultVo = new GeocoderTowardsResultVo();
				baiDuGeocoderTowardsResultVo.setConfidence(confidence);
				baiDuGeocoderTowardsResultVo.setLatitude(latitude);
				baiDuGeocoderTowardsResultVo.setLongitude(longitude);
				baiDuGeocoderTowardsResultVo.setPrecise(precise);
				baiDuGeocoderTowardsResultVo.setLevel(level);
				return baiDuGeocoderTowardsResultVo;
				
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	/**
	 * get的Url的拼接
	 * @author AimSpeed
	 * @param reqPath
	 * @param param
	 * @return String
	 */
	private String doGetUrlJoint(String reqPath,Map<String, String> param) {
		reqPath += "?";
		
		int len = 0;
		for (Entry<String, String> entry : param.entrySet()) {

			len++;
			
			String key = entry.getKey();
			String value = entry.getValue().toString();
			
			if(len != param.size()) {
				reqPath += key + "=" + value + "&";
			}else {
				reqPath += key + "=" + value;
			}
		}
		return reqPath;
	}
	
	
	
}
