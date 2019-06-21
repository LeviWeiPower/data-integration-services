package com.aimspeed.thirdparty.intf.alibaba;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aimspeed.thirdparty.intf.Sms;
import com.aimspeed.thirdparty.intf.alibaba.vo.sms.SmsBaseReqParamVo;
import com.aimspeed.thirdparty.intf.alibaba.vo.sms.SmsTemplateVo;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * 阿里云短信工具类
 * @author AimSpeed
 */
public class AliSmsHelper implements Sms {
	
	public static void main(String[] args) throws ClientException {
		
		SmsBaseReqParamVo aliMessageVo = new SmsBaseReqParamVo();
		aliMessageVo.setProduct("1111");
		aliMessageVo.setDomain("222222");
		aliMessageVo.setAccessKeyId("33333333");
		aliMessageVo.setAccessKeySecret("666666666666");
		aliMessageVo.setDefaultConnectTimeout(10000);
		aliMessageVo.setDefaultReadTimeout(10000);
		
		
		SmsTemplateVo smsTemplateVo = new SmsTemplateVo();
		smsTemplateVo.setSignName("555555555555");
		smsTemplateVo.setTemplateCode("777777777777");
		
		AliSmsHelper aliSms = new AliSmsHelper();
		
		//生成验证码
    	
        Map contentMap = new HashMap<String,String>();
        contentMap.put("code","123456");
		String sendSms = aliSms.sendSms("7777777777777", contentMap, aliMessageVo,smsTemplateVo);
		
		System.out.println();
		

	}
	
	/**
	 * 发送验证码短信接口
	 * 将短信发送频率限制在正常的业务流控范围内，默认流控：
	 * 短信验证码 ：使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时 ，累计10条/天。
	 * @author AimSpeed
	 * @param phone 发送的手机号码
	 * @param contentMap 内容Map
	 * @param aliMessageVo 配置信息
	 * @return String 回执Id
	 * @throws ClientException String
	 */
	public String sendSms(String phone, Map contentMap, SmsBaseReqParamVo aliMessageVo,SmsTemplateVo smsTemplateVo) throws ClientException {

       //可自助调整超时时间
       System.setProperty("sun.net.client.defaultConnectTimeout", aliMessageVo.getDefaultConnectTimeout() + "");
       System.setProperty("sun.net.client.defaultReadTimeout", aliMessageVo.getDefaultReadTimeout() + "");

       //初始化acsClient,暂不支持region化
       IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliMessageVo.getAccessKeyId(), aliMessageVo.getAccessKeySecret());
       DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", aliMessageVo.getProduct(), aliMessageVo.getDomain());
       IAcsClient acsClient = new DefaultAcsClient(profile);

       //组装请求对象-具体描述见控制台-文档部分内容
       SendSmsRequest request = new SendSmsRequest();
       //必填:待发送手机号
       request.setPhoneNumbers(phone);
       //必填:短信签名-可在短信控制台中找到
       request.setSignName(smsTemplateVo.getSignName());
       //必填:短信模板-可在短信控制台中找到
       request.setTemplateCode(smsTemplateVo.getTemplateCode());
       //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//	        String param = "{\"code\":'"+code+"'}";
       String param = JSON.toJSONString(contentMap);
       request.setTemplateParam(param);

       //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
       //request.setSmsUpExtendCode("90997");

       //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//	        request.setOutId("yourOutId");

       //hint 此处可能会抛出异常，注意catch
       SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

       if("OK".equals(sendSmsResponse.getCode())){
    	   return sendSmsResponse.getBizId();
       }else{
    	   return null;
       }
    }
	
	/**
	 * 查询发送明细（分页查询）
	 * @author AimSpeed
	 * @param bizId 回执id
	 * @param phone 手机号码
	 * @param pageIndex 当前页
	 * @param pageSize 每一页大小
	 * @param aliMessageVo 配置信息
	 * @return QuerySendDetailsResponse 响应的详细信息 
	 * @throws ClientException QuerySendDetailsResponse
	 */
	 public QuerySendDetailsResponse querySendDetails(String bizId,String phone,Long pageIndex, Long pageSize,SmsBaseReqParamVo aliMessageVo) throws ClientException {

       //可自助调整超时时间
       System.setProperty("sun.net.client.defaultConnectTimeout", aliMessageVo.getDefaultConnectTimeout() + "");
       System.setProperty("sun.net.client.defaultReadTimeout", aliMessageVo.getDefaultReadTimeout() + "");

       //初始化acsClient,暂不支持region化
       IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliMessageVo.getAccessKeyId(), aliMessageVo.getAccessKeySecret());
       DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", aliMessageVo.getProduct(), aliMessageVo.getDomain());
       IAcsClient acsClient = new DefaultAcsClient(profile);

       //组装请求对象
       QuerySendDetailsRequest request = new QuerySendDetailsRequest();
       //必填-号码
       request.setPhoneNumber(phone);
       //可选-流水号
       request.setBizId(bizId);
       //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
       SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
       request.setSendDate(ft.format(new Date()));
       //必填-页大小
       request.setPageSize(pageSize);
       //必填-当前页码从1开始计数
       request.setCurrentPage(pageIndex);

       //hint 此处可能会抛出异常，注意catch
       QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
       return querySendDetailsResponse;
    }

}
