package com.aimspeed.thirdparty.intf.tencent.ai;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.aimspeed.thirdparty.intf.AI;
import com.aimspeed.thirdparty.intf.tencent.vo.ai.BaseAIParamVo;
import com.aimspeed.thirdparty.intf.utils.tencent.ai.sign.SignMD5Utils;

/**
 * 基础的AI抽象类
 * @author AimSpeed
 */
public abstract class AbstractTencentAI implements AI {
	
	/**
	 * 分析前的数据转换
	 * @author AimSpeed
	 * @param baseAIParamVo
	 * @param params
	 * @return Map<String,String>
	 */
	public Map<String, String> analyzePre(BaseAIParamVo baseAIParamVo,Map<String, String> params) {
        //拼接数据
		Set<String> keySet = params.keySet();
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = keySet.iterator();
        try {
	        while (iterator.hasNext()) {
	            String key = iterator.next();
	            String value = params.get(key);
				sb.append("&").append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
	        }
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        sb.deleteCharAt(0);
        sb.append("&app_key=").append("22222222222");
        
        //加密后获取到鉴权码
        String sign = SignMD5Utils.getMD5(sb.toString());
        
        params.put("sign", sign);
        baseAIParamVo.setSign(sign);
		return params;
	}
	
	/**
	 * 将基础的三个数据（app_id、nonce_str、time_stamp）转换为map
	 * @author AimSpeed
	 * @param baseAIParamVo
	 * @return Map<String,String>
	 */
	public Map<String, String> baseAIParamToMap(BaseAIParamVo baseAIParamVo) {
		TreeMap<String, String> mParams = new TreeMap<>();
        //将通用参数设置进map中
        mParams.put("app_id", baseAIParamVo.getAppId());
        mParams.put("nonce_str", baseAIParamVo.getNonceStr());
        mParams.put("time_stamp", baseAIParamVo.getTimeStamp());
		return mParams;
	}
	
}
