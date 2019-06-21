package com.aimspeed.thirdparty.intf.utils.tencent.ai.sign;
import java.net.URLEncoder;

import com.aimspeed.thirdparty.intf.tencent.vo.ai.BaseAIParamVo;

/**
 * 腾讯AI平台Sign
 * @author AimSpeed
 */
public class TencentAISignUtils {
	
	/**
	 * 腾讯AI获取图片检测的Sign
	 * @author AimSpeed
	 * @Title appSignAI4FaceDetect 
	 * @param image
	 * @param mode 检测模式，0-正常，1-大脸模式（默认1）
	 * @param baseAIParamVo
	 * @return
	 * @throws Exception String  
	 */
	public static String appSignAI4FaceDetect(String image,Integer mode,BaseAIParamVo baseAIParamVo) throws Exception {
		return appSignBaseAI4FaceDetect(image,mode,baseAIParamVo);
	}
	
	/**
	 * 计算图片检测的Sign
	 * @author AimSpeed
	 * @Title appSignBaseAI4FaceDetect 
	 * @param image
	 * @param mode
	 * @param baseAIParamVo
	 * @return
	 * @throws Exception String  
	 */
	private static String appSignBaseAI4FaceDetect(String image,Integer mode,BaseAIParamVo baseAIParamVo) throws Exception {
		String time_stamp = System.currentTimeMillis()/1000+"";    
		String plain_text = "app_id=" + URLEncoder.encode(baseAIParamVo.getAppId(),"UTF-8") 
				+ "&image=" + URLEncoder.encode(image,"UTF-8")
				+"&nonce_str=" + URLEncoder.encode(baseAIParamVo.getNonceStr(),"UTF-8") 
				+"&mode=" + URLEncoder.encode(mode.toString(),"UTF-8")
				+"&time_stamp=" + URLEncoder.encode(time_stamp,"UTF-8");
        String plain_text_encode = plain_text+"&app_key="+ baseAIParamVo.getAppKey();
        String sign = SignMD5Utils.getMD5(plain_text_encode);
		return sign;
	}
	
	/**
	 * 计算Sign
	 * @author AimSpeed
	 * @Title appSignBaseAI4PersionDetect 
	 * @param image
	 * @param card_type
	 * @param baseAIParamVo
	 * @return
	 * @throws Exception String  
	 */
	public static String appSignBaseAI4PersionDetect(String image,Integer card_type,BaseAIParamVo baseAIParamVo) throws Exception {
		String time_stamp = System.currentTimeMillis()/1000+"";    
		String plain_text = "app_id=" + URLEncoder.encode(baseAIParamVo.getAppId(),"UTF-8") 
							+"&card_type=" + URLEncoder.encode(card_type.toString(),"UTF-8") 
							+"&nonce_str=" + URLEncoder.encode(baseAIParamVo.getNonceStr(),"UTF-8") 
							+"&time_stamp=" + URLEncoder.encode(time_stamp,"UTF-8")
							+"&image="+URLEncoder.encode(image,"UTF-8");
        String plain_text_encode = plain_text+"&app_key="+baseAIParamVo.getAppKey();
        String sign = SignMD5Utils.getMD5(plain_text_encode);
		return sign;
	}
	
}
