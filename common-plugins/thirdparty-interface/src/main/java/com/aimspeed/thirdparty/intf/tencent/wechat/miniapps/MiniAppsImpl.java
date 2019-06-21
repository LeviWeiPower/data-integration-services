package com.aimspeed.thirdparty.intf.tencent.wechat.miniapps;

import java.util.HashMap;
import java.util.Map;

import com.aimspeed.thirdparty.intf.tencent.constatns.MiniAppsGrantTypeConstans;
import com.aimspeed.thirdparty.intf.tencent.constatns.MiniAppsReqUrlConstans;
import com.aimspeed.thirdparty.intf.tencent.vo.wechat.token.AccessTokenReqParamVo;
import com.aimspeed.thirdparty.intf.tencent.vo.wechat.token.AccessTokenRespVo;
import com.aimspeed.thirdparty.intf.tencent.vo.wechat.token.SessionKeyAndOpenIdReqParamVo;
import com.aimspeed.thirdparty.intf.tencent.vo.wechat.token.SessionKeyAndOpenIdRespVo;
import com.aimspeed.thirdparty.intf.tencent.wechat.miniapps.enums.AccessTokenReqParamEnum;
import com.aimspeed.thirdparty.intf.tencent.wechat.miniapps.enums.AccessTokenRespParamEnum;
import com.aimspeed.thirdparty.intf.tencent.wechat.miniapps.enums.SessionKeyAndOpenIdReqParamEnum;
import com.aimspeed.thirdparty.intf.tencent.wechat.miniapps.enums.SessionKeyAndOpenIdRespParamEnum;
import com.aimspeed.thirdparty.intf.utils.UrlUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 小程序
 * @author AimSpeed
 */
public class MiniAppsImpl implements MiniApps {
	
	/*
	 * 获取到SessionKey和OpenId
	 * 成功示例：{"openid": "OPENID","session_key": "SESSIONKEY", "unionid": "UNIONID"}
	 * 失败示例：{"errcode":40013,"errmsg":"invalid appid, hints: [ req_id: 9.UyXa05352059 ]"}
	 * @author AimSpeed
	 * @Title getSessionKeyAndOpenId 
	 * @param sessionKeyReqParamVo
	 * @return
	 * @overridden @see com.aimspeed.wechat.miniapps.IMiniApps#getSessionKeyAndOpenId(com.aimspeed.wechat.vo.token.SessionKeyAndOpenIdReqParamVo)
	 */
	@Override
	public SessionKeyAndOpenIdRespVo getSessionKeyAndOpenId(SessionKeyAndOpenIdReqParamVo sessionKeyReqParamVo) {
	    Map<String,String> requestUrlParam = new HashMap<String,String>(); 
	    requestUrlParam.put(SessionKeyAndOpenIdReqParamEnum.appId.getValue(), sessionKeyReqParamVo.getAppId()); //开发者设置中的appId 
	    requestUrlParam.put(SessionKeyAndOpenIdReqParamEnum.secret.getValue(), sessionKeyReqParamVo.getSecret()); //开发者设置中的appSecret 
	    requestUrlParam.put(SessionKeyAndOpenIdReqParamEnum.jsCode.getValue(), sessionKeyReqParamVo.getJsCode()); //小程序调用wx.login返回的code 
	    requestUrlParam.put(SessionKeyAndOpenIdReqParamEnum.grantType.getValue(), MiniAppsGrantTypeConstans.SESSIONKEY_AND_OPENID);  //默认参数 
	      
	    //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识 
	    JSONObject jsonObject = JSON.parseObject(UrlUtils.sendPost(MiniAppsReqUrlConstans.SESSIONKEY_AND_OPENID, requestUrlParam)); 
	    String openId = jsonObject.getString(SessionKeyAndOpenIdRespParamEnum.openId.getValue());
	    String sessionKey = jsonObject.getString(SessionKeyAndOpenIdRespParamEnum.sessionKey.getValue());
	    String unionId = jsonObject.getString(SessionKeyAndOpenIdRespParamEnum.unionid.getValue());
	    String errCode = jsonObject.getString(SessionKeyAndOpenIdRespParamEnum.errCode.getValue());
	    String errMsg = jsonObject.getString(SessionKeyAndOpenIdRespParamEnum.errMsg.getValue());
	    
	    //组装数据
	    SessionKeyAndOpenIdRespVo sessionKeyAndOpenIdRespVo = new SessionKeyAndOpenIdRespVo();
	    sessionKeyAndOpenIdRespVo.setOpenId(openId);
	    sessionKeyAndOpenIdRespVo.setSessionKey(sessionKey);
	    sessionKeyAndOpenIdRespVo.setUnionId(unionId);
	    sessionKeyAndOpenIdRespVo.setErrCode(errCode);
	    sessionKeyAndOpenIdRespVo.setErrMsg(errMsg);
	    
	    return sessionKeyAndOpenIdRespVo; 
	}

	/*
	 * 获取访问令牌
	 * 成功示例：{"access_token":"11_v-GA4YgR4eAy6b-HpU4k0x8Hvjx7lreFAJh09jgL2iQ_x4fExa6lqyxSFAejmPs-1UoZidOJQTtU37GaH9opDec4f9DFB7S9QtPQk-6kWLjSe2wihCRh0hvDdArG_GKQlCPaGsbvD7OyDLrmJPTbABATZD","expires_in":7200}
	 * 失败示例：{"errcode":40125,"errmsg":"invalid appsecret, view more at http://t.cn/RAEkdVq hint: [1fUWsA04483054]"}
	 * @author AimSpeed
	 * @Title getAccessToken 
	 * @param accessTokenReqParamVo
	 * @return
	 * @overridden @see com.aimspeed.wechat.miniapps.IMiniApps#getAccessToken(com.aimspeed.wechat.miniapps.vo.token.AccessTokenReqParamVo)
	 */
	@Override
	public AccessTokenRespVo getAccessToken(AccessTokenReqParamVo accessTokenReqParamVo) {
	    Map<String,String> requestUrlParam = new HashMap<String,String>(); 
	    requestUrlParam.put(AccessTokenReqParamEnum.appId.getValue(), accessTokenReqParamVo.getAppId()); //开发者设置中的appId 
	    requestUrlParam.put(AccessTokenReqParamEnum.secret.getValue(), accessTokenReqParamVo.getSecret()); //开发者设置中的appSecret 
	    requestUrlParam.put(AccessTokenReqParamEnum.grantType.getValue(), MiniAppsGrantTypeConstans.ACCESS_TOKEN);  //默认参数 
	    
	    //处理响应信息
	    JSONObject jsonObject = JSON.parseObject(UrlUtils.sendPost(MiniAppsReqUrlConstans.ACCESS_TOKEN, requestUrlParam)); 
	    String accessToken = jsonObject.getString(AccessTokenRespParamEnum.accessToken.getValue());
	    Integer expiresIn = jsonObject.getInteger(AccessTokenRespParamEnum.expiresIn.getValue());
	    String errCode = jsonObject.getString(AccessTokenRespParamEnum.errCode.getValue());
	    String errMsg = jsonObject.getString(AccessTokenRespParamEnum.errMsg.getValue());
	     
	    //组装数据
	    AccessTokenRespVo accessTokenRespVo = new AccessTokenRespVo();
	    accessTokenRespVo.setAccessToken(accessToken);
	    accessTokenRespVo.setExpiresIn(expiresIn);
	    accessTokenRespVo.setErrCode(errCode);
	    accessTokenRespVo.setErrMsg(errMsg);
	    return accessTokenRespVo; 
	}

}
