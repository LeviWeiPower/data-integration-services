package com.aimspeed.thirdparty.intf.tencent.ai.sentiment;

import java.util.Map;

import com.aimspeed.thirdparty.intf.tencent.ai.AbstractTencentAI;
import com.aimspeed.thirdparty.intf.tencent.constatns.AIReqUrlConstans;
import com.aimspeed.thirdparty.intf.tencent.vo.ai.BaseAIParamVo;
import com.aimspeed.thirdparty.intf.tencent.vo.ai.sentiment.SentimentTendencyResult;
import com.aimspeed.thirdparty.intf.utils.UrlUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 情感倾向分析
 * @author AimSpeed
 */
public class SentimentTendencyAnalyze extends AbstractTencentAI {
	
	public static void main(String[] args) {
		BaseAIParamVo baseAIParamVo = new BaseAIParamVo();
		baseAIParamVo.setAppId("3333333333333");
		baseAIParamVo.setAppKey("3333333333343211");
		baseAIParamVo.setReqParamValStr("今天去公司玩的挺开心");
		SentimentTendencyResult sentimentTendencyResult = new SentimentTendencyAnalyze().analyze(baseAIParamVo);
		System.out.println(sentimentTendencyResult);
	}
	
	public SentimentTendencyResult analyze(BaseAIParamVo baseAIParamVo) {
		Map<String, String> params = baseAIParamToMap(baseAIParamVo);
		params.put("text", baseAIParamVo.getReqParamValStr());
		params = analyzePre(baseAIParamVo, params);

		//调用结果
		JSONObject jsonObject = JSON.parseObject(UrlUtils.sendPost(AIReqUrlConstans.SENTIMENT, params));
		
//		System.out.println(jsonObject);
		
		//{"ret":0,"msg":"ok","data":{"polar":1,"confd":1,"text":"开心"}}
		//组装结果数据
		SentimentTendencyResult sentimentTendencyResult = new SentimentTendencyResult();
		
		//提取出结果数据
		Integer ret = jsonObject.getInteger("ret");
		if(null != ret) {
			String msg = jsonObject.getString("msg");
			String data = jsonObject.getString("data");
			if(null != data && !"".equals(data.trim())) {
				JSONObject dataJsonObject = JSON.parseObject(data);
				Integer polar = dataJsonObject.getInteger("polar");
				Double confd = dataJsonObject.getDouble("confd");
				String text = dataJsonObject.getString("text");
				
				sentimentTendencyResult.setPolar(polar);
				sentimentTendencyResult.setConfd(confd);
				sentimentTendencyResult.setText(text);
				
				if(polar < 0) {
					sentimentTendencyResult.setTendency((confd - confd - confd));
				}else {
					sentimentTendencyResult.setTendency(confd);
				}
			}
			sentimentTendencyResult.setRet(ret);
			sentimentTendencyResult.setMsg(msg);
		}
		return sentimentTendencyResult;
	}
	
	
	
	
	
	
	
}
