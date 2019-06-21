package com.aimspeed.thirdparty.intf.tencent.wechat.miniapps;

import com.aimspeed.thirdparty.intf.tencent.vo.wechat.token.AccessTokenReqParamVo;
import com.aimspeed.thirdparty.intf.tencent.vo.wechat.token.AccessTokenRespVo;
import com.aimspeed.thirdparty.intf.tencent.vo.wechat.token.SessionKeyAndOpenIdReqParamVo;
import com.aimspeed.thirdparty.intf.tencent.vo.wechat.token.SessionKeyAndOpenIdRespVo;
import com.aimspeed.thirdparty.intf.tencent.wechat.WeChat;

/**
 * 小程序接口 
 * @author AimSpeed
 */
public interface MiniApps extends WeChat {
	
	/**
	 * 获取到SessionKey和OpenId
	 * 成功示例：{"openid": "OPENID","session_key": "SESSIONKEY", "unionid": "UNIONID"}
	 * 失败示例：{"errcode":40013,"errmsg":"invalid appid, hints: [ req_id: 9.UyXa05352059 ]"}
	 * @author AimSpeed
	 * @param sessionKeyReqParamVo
	 * @return SessionKeyAndOpenIdRespVo 返回的openId参数为空的话，那么就是出错了
	 */
	SessionKeyAndOpenIdRespVo getSessionKeyAndOpenId(SessionKeyAndOpenIdReqParamVo sessionKeyReqParamVo);
	
	/**
	 * 获取访问令牌
	 * 成功示例：{"access_token":"11_v-GA4YgR4eAy6b-HpU4k0x8Hvjx7lreFAJh09jgL2iQ_x4fExa6lqyxSFAejmPs-1UoZidOJQTtU37GaH9opDec4f9DFB7S9QtPQk-6kWLjSe2wihCRh0hvDdArG_GKQlCPaGsbvD7OyDLrmJPTbABATZD","expires_in":7200}
	 * 失败示例：{"errcode":40125,"errmsg":"invalid appsecret, view more at http://t.cn/RAEkdVq hint: [1fUWsA04483054]"}
	 * @author AimSpeed
	 * @param accessTokenReqParamVo 返回的token参数为空的话，那么就是出错了
	 * @return AccessTokenRespVo
	 */
	AccessTokenRespVo getAccessToken(AccessTokenReqParamVo accessTokenReqParamVo);
	
	
}
