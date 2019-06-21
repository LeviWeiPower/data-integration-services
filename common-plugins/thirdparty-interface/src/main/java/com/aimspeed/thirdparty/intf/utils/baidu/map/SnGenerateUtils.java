package com.aimspeed.thirdparty.intf.utils.baidu.map;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 百度地图的SN码生成
 * @author AimSpeed
 */
public class SnGenerateUtils {
	
	/**
	 * 生成SN验证码
	 * @author AimSpeed
	 * @Title generateSn 
	 * @param reqSufUrl 访问地址：https://www.baidu.com/location/ip 后缀： /location/ip
	 * @param queryMap
	 * @param ak
	 * @param sk
	 * @return String
	 * @throws UnsupportedEncodingException String 
	 */
	public static String generateSn(String reqSufUrl,Map<String, String> queryMap,String ak,String sk) throws UnsupportedEncodingException {
		
		/*
		 * 计算sn跟参数对出现顺序有关，get请求请使用LinkedHashMap保存<key,value>，
		 * 该方法根据key的插入顺序排序；post请使用TreeMap保存<key,value>，该方法会自动将key按照字母a-z顺序排序。
		 * 所以get请求可自定义参数顺序（sn参数必须在最后）发送请求，但是post请求必须按照字母a-z顺序填充body（sn参数必须在最后）。
		 * 以get请求为例：http://api.map.baidu.com/geocoder/v2/?address=百度大厦&output=json&ak=yourak，
		 * paramsMap中先放入address，再放output，然后放ak，放入顺序必须跟get请求中对应参数的出现顺序保持一致。
		 */
		if(null != queryMap && queryMap.size() > 0) {
			queryMap.put("ak", ak);
		}else {
			queryMap = new LinkedHashMap<String, String>();
			queryMap.put("ak", ak);
		}
		
		/*
		 * 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，
		 * 拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
		 */
		String paramsStr = toQueryString(queryMap);
		
		String wholeStr = new String(reqSufUrl + "?" + paramsStr + sk);
		

		// 对上面wholeStr再作utf8编码
		String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
		
		// 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
		return stackOverFlowMD5(tempStr);
		
	}
	
	/**
	 * 对Map内所有value作utf8编码，拼接返回结果
	 * @author AimSpeed
	 * @Title toQueryString 
	 * @param data
	 * @return String
	 * @throws UnsupportedEncodingException String 
	 */
	public static String toQueryString(Map<?, ?> data) throws UnsupportedEncodingException {
		StringBuffer queryString = new StringBuffer();
		
		for (Entry<?, ?> pair : data.entrySet()) {
			if(null == pair.getValue() || "".equals(pair.getValue().toString())) {
				continue;
			}
		    queryString.append(pair.getKey() + "=");
		    queryString.append((String) pair.getValue()+"&");
		}
		
		if (queryString.length() > 0) {
		    queryString.deleteCharAt(queryString.length() - 1);
		}
		
		return queryString.toString();
	}
	
	/**
	 * 来自的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
	 * @author AimSpeed
	 * @Title stackOverFlowMD5 
	 * @param md5
	 * @return String 
	 */
    public static String stackOverFlowMD5(String md5) {
		try {
		    java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		    byte[] array = md.digest(md5.getBytes());
		    
		    StringBuffer sb = new StringBuffer();
		    
		    for (int i = 0; i < array.length; ++i) {
		            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
		    }
		    return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		
		}
		return null;
	}
	
	
}
