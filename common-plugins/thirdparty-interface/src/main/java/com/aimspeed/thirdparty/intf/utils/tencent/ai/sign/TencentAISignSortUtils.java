package com.aimspeed.thirdparty.intf.utils.tencent.ai.sign;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.aimspeed.thirdparty.intf.tencent.vo.ai.BaseAIParamVo;

/**
 * 腾讯Sgn签名排序类
 * @author AimSpeed
 */
public class TencentAISignSortUtils {

	/**
	 * Sign签名生成算法-JAVA版本 通用。默认参数都为UTF-8适用
	 * @author AimSpeed
	 * @Title getSignature 
	 * @param params 请求参数集，所有参数必须已转换为字符串类型
	 * @param baseAIParamVo
	 * @return
	 * @throws IOException String  
	 */
	public static String getSignature(Map<String,String> params,BaseAIParamVo baseAIParamVo) throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<>(params);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder baseString = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            //sign参数 和 空值参数 不加入算法
            if(param.getValue()!=null && !"".equals(param.getKey().trim()) && !"sign".equals(param.getKey().trim()) && !"".equals(param.getValue().trim())) {
                baseString.append(param.getKey().trim()).append("=").append(URLEncoder.encode(param.getValue().trim(),"UTF-8")).append("&");
            }
        }
        if(baseString.length() > 0 ) {
            baseString.deleteCharAt(baseString.length()-1).append("&app_key=" + baseAIParamVo.getAppKey());
        }
        // 使用MD5对待签名串求签
        try {
        	String sign = SignMD5Utils.getMD5(baseString.toString());
        	return sign;
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }
	
	/**
	 * Sign签名生成算法-JAVA版本 针对于基本文本分析接口要求text为GBK的方法
	 * @author AimSpeed
	 * @Title getSignatureforNLP 
	 * @param params 请求参数集，所有参数必须已转换为字符串类型
	 * @param baseAIParamVo
	 * @return
	 * @throws IOException String  
	 */
	public static String getSignatureforNLP(HashMap<String,String> params,BaseAIParamVo baseAIParamVo) throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<>(params);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder baseString = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
        	
            //sign参数 和 空值参数 不加入算法
            if(param.getValue()!=null && !"".equals(param.getKey().trim()) && !"sign".equals(param.getKey().trim()) && !"".equals(param.getValue().trim())) {
            	if(param.getKey().equals("text")){
            		baseString.append(param.getKey().trim()).append("=").append(URLEncoder.encode(param.getValue().trim(),"GBK")).append("&");
            	}else{
            		baseString.append(param.getKey().trim()).append("=").append(URLEncoder.encode(param.getValue().trim(),"UTF-8")).append("&");
            		
            	}
            }
        }
        if(baseString.length() > 0 ) {
            baseString.deleteCharAt(baseString.length()-1).append("&app_key=" + baseAIParamVo.getAppKey());
        }
        // 使用MD5对待签名串求签
        try {
        	String sign = SignMD5Utils.getMD5(baseString.toString());
        	return sign;
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }
	
	/**
	 * 获取拼接的参数
	 * @author AimSpeed
	 * @Title getParams 
	 * @param params
	 * @return
	 * @throws IOException String  
	 */
	public static String getParams(HashMap<String,String> params) throws IOException {
		//  先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<>(params);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder baseString = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            //sign参数 和 空值参数 不加入算法
           baseString.append(param.getKey().trim()).append("=").append(URLEncoder.encode(param.getValue().trim(),"UTF-8")).append("&");
        }
       return baseString.toString();
    }
}

