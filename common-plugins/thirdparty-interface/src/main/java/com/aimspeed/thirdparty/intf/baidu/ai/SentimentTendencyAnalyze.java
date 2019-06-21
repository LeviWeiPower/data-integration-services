package com.aimspeed.thirdparty.intf.baidu.ai;

import java.util.HashMap;

import org.json.JSONObject;

import com.aimspeed.thirdparty.intf.baidu.vo.ai.BaseAIParamVo;
import com.aimspeed.thirdparty.intf.baidu.vo.ai.sentiment.SentimentTendencyResultVo;
import com.baidu.aip.nlp.AipNlp;

/**
 * 情感倾向分析
 * @author AimSpeed
 */
public class SentimentTendencyAnalyze {
	
	public static void main(String[] args) {
		BaseAIParamVo baseAIParamVo = new BaseAIParamVo();
		baseAIParamVo.setAppId("222222222");
		baseAIParamVo.setAppKey("1111111111111111");
		baseAIParamVo.setSecretKey("2222222222222222222");
		baseAIParamVo.setReqParamValStr("33333333333333333333");
		System.out.println(new SentimentTendencyAnalyze().analyze(baseAIParamVo));
	}
	
	
	/**
	 * 情感倾向分析
	 * @author AimSpeed
	 * @param baseAIParamVo
	 * @return SentimentTendencyResultVo
	 */
	public SentimentTendencyResultVo analyze(BaseAIParamVo baseAIParamVo) {
		return analyze(baseAIParamVo,null);
	}
	
	/**
	 * analyze
	 * @author AimSpeed
	 * @param baseAIParamVo
	 * @param reqBody
	 * @return SentimentTendencyResultVo
	 */
	public SentimentTendencyResultVo analyze(BaseAIParamVo baseAIParamVo,HashMap<String, Object> reqBody) {
		//创建Nlp处理Api类
		AipNlp client = new AipNlp(baseAIParamVo.getAppId(), baseAIParamVo.getAppKey(), baseAIParamVo.getSecretKey());

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        
        // 情感倾向分析
        JSONObject res = client.sentimentClassify(baseAIParamVo.getReqParamValStr(), reqBody);
        
        if(null != res) {
        	/*
        	 * {
				  "log_id": 6388714866494327381,
				  "text": "去吃饭",
				  "items": [{
				    "positive_prob": 0.526864,
				    "sentiment": 1,
				    "confidence": 0.462721,
				    "negative_prob": 0.473136
				  }]
				}
        	 */
        	SentimentTendencyResultVo sentimentTendencyResultVo = new SentimentTendencyResultVo();
        	sentimentTendencyResultVo.setText(res.getString("text"));
        	sentimentTendencyResultVo.setLogId(res.getLong("log_id"));
        	sentimentTendencyResultVo.setSentiment(res.getJSONArray("items").getJSONObject(0).getInt("sentiment"));
        	sentimentTendencyResultVo.setConfidence(res.getJSONArray("items").getJSONObject(0).getDouble("confidence"));
        	sentimentTendencyResultVo.setPositive(res.getJSONArray("items").getJSONObject(0).getDouble("positive_prob"));
        	sentimentTendencyResultVo.setNegative(res.getJSONArray("items").getJSONObject(0).getDouble("negative_prob"));
        	sentimentTendencyResultVo.setRawJson(res.toString());
        	sentimentTendencyResultVo.setTendencyResult(sentimentTendencyResultVo.getPositive() - sentimentTendencyResultVo.getNegative());
        	return sentimentTendencyResultVo;
        }
        
		return null;
	}

	
}
