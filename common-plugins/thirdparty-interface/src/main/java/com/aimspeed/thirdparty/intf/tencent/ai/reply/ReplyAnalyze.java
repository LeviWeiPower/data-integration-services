package com.aimspeed.thirdparty.intf.tencent.ai.reply;

import java.util.Map;

import com.aimspeed.thirdparty.intf.tencent.ai.AbstractTencentAI;
import com.aimspeed.thirdparty.intf.tencent.constatns.AIReqUrlConstans;
import com.aimspeed.thirdparty.intf.tencent.vo.ai.BaseAIParamVo;
import com.aimspeed.thirdparty.intf.tencent.vo.ai.reply.ReplyAnalyzeResult;
import com.aimspeed.thirdparty.intf.utils.UrlUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 智能分析回复
 * @author AimSpeed
 */
public class ReplyAnalyze  extends AbstractTencentAI {
	
	public static void main(String[] args) {
		BaseAIParamVo baseAIParamVo = new BaseAIParamVo();
		baseAIParamVo.setAppId("11111111111");
		baseAIParamVo.setAppKey("2222222222222");
		baseAIParamVo.setReqParamValStr("你是谁啊");
		ReplyAnalyzeResult replyAnalyzeResult = new ReplyAnalyze().analyze(baseAIParamVo);
		System.out.println(replyAnalyzeResult);
	}
	
	public ReplyAnalyzeResult analyze(BaseAIParamVo baseAIParamVo) {
		Map<String, String> params = baseAIParamToMap(baseAIParamVo);
		params.put("session", "101122");  //默认参数 
		params.put("question", baseAIParamVo.getReqParamValStr());  //默认参数 
		params = analyzePre(baseAIParamVo, params);
		
		//调用结果
		JSONObject jsonObject = JSON.parseObject(UrlUtils.sendPost(AIReqUrlConstans.REPLY, params));
		
//		System.out.println(jsonObject);
		
		//{"ret":0,"msg":"ok","data":{"answer":"大丈夫行不改名坐不改姓，小A砖家是也。","session":"101122"}}
		
		
		//组装结果数据
		ReplyAnalyzeResult replyAnalyzeResult = new ReplyAnalyzeResult();
		
		//提取出结果数据
		Integer ret = jsonObject.getInteger("ret");
		if(null != ret) {
			String msg = jsonObject.getString("msg");
			String data = jsonObject.getString("data");
			if(null != data && !"".equals(data.trim())) {
				JSONObject dataJsonObject = JSON.parseObject(data);
				String answer = dataJsonObject.getString("answer");
				String sessionId = dataJsonObject.getString("session");
				
				replyAnalyzeResult.setAnswer(answer);
				replyAnalyzeResult.setSessionId(sessionId);
			}
			replyAnalyzeResult.setRet(ret);
			replyAnalyzeResult.setMsg(msg);
		}
		return replyAnalyzeResult;
	}
	

}
