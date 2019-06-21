package com.aimspeed.thirdparty.intf.expressage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.aimspeed.thirdparty.intf.expressage.vo.CodeDiscernVo;
import com.aimspeed.thirdparty.intf.expressage.vo.OrderCodeLogisticsVo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 快递鸟接口
 * @author AimSpeed
 */
public class ExpressageBird {
	

	//DEMO
    public static void main(String[] args) {
        try {
        	/*
        	 * {  "LogisticCode" : "9769183628782",  
        	 *    "Shippers" : [ 
        	 *                   {    "ShipperName" : "EMS",    
        	 *                        "ShipperCode" : "EMS"  }, 
        	 *                        {    "ShipperName" : "邮政快递",    
        	 *                             "ShipperCode" : "YZPY"  
        	 *                        } 
        	 *                  ],  
        	 *                  "EBusinessID" : 
        	 *                  "1392118",  
        	 *                  "Code" : "100",  
        	 *                  "Success" : true}
        	 */
        	
//        	CodeDiscernVo result = ;
        	OrderCodeLogisticsVo result = new ExpressageBird("1392118","6797230a-5c5e-4fcb-92e6-33b9aa26c8ce").orderQuery("9769183628782");
            
            /*
             * {  "LogisticCode" : "9769183628782",  
             *    "ShipperCode" : "EMS",  
             *    "Traces" : [ 
             *                    {    "AcceptStation" : "南通叠石桥姜灶揽投站已收件（揽投员姓名：黄欢,联系电话:13814745878）",    
             *                    	   "AcceptTime" : "2018-09-25 16:48:00"  
             *                    }, 
             *                    {    "AcceptStation" : "已离开南通转运中心，发往无锡长三角邮件处理中心",    
             *                         "AcceptTime" : "2018-09-25 20:25:35"  
             *                    }, 
             *                    {    "AcceptStation" : "到达无锡长三角邮件处理中心处理中心（经转）",    
             *                         "AcceptTime" : "2018-09-26 02:28:49"  
             *                    }, 
             *                    {    "AcceptStation" : "离开无锡市 发往中山市（经转）",    
             *                         "AcceptTime" : "2018-09-26 08:39:59"  
             *                    }, 
             *                    {    "AcceptStation" : "到达中山三角处理中心处理中心（经转）",    
             *                         "AcceptTime" : "2018-09-27 14:54:00"  
             *                    }, 
             *                    {    "AcceptStation" : "投递并签收，签收人：他人收 申通菜鸟",    
             *                         "AcceptTime" : "2018-09-28 11:11:34"  
             *                    } 
             *              ],  
             *     "State" : "3",  
             *     "EBusinessID" : "1392118",  
             *     "Success" : true}
             */
            
            System.out.print(result.getTraces());
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    //商户ID
    private String eBusinessID ;//E_BUSINESS_ID = "1392118"
    
    //加密私钥，快递鸟提供，注意保管，不要泄漏
    private String appKey;//APP_KEY = "6797230a-5c5e-4fcb-92e6-33b9aa26c8ce";
    
    public ExpressageBird(String eBusinessID, String appKey) {
		super();
		this.eBusinessID = eBusinessID;
		this.appKey = appKey;
	}

	/**
	 * 即时查询
	 * @author AimSpeed
	 * @param logisticCode
	 * @return
	 * @throws Exception OrderCodeLogisticsVo
	 */
    public OrderCodeLogisticsVo orderQuery(String logisticCode) throws Exception{
    	String ReqURL="http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";
    	
    	String requestData= "{'OrderCode':'','ShipperCode':'" + codeDiscern(logisticCode).getShipperCodeEn() + "','LogisticCode':'" + logisticCode + "'}";
         
        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", geteBusinessID());
        params.put("RequestType", "1002");
        
        String dataSign = encrypt(requestData, getAppKey(), "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");
         
        String result=sendPost(ReqURL, params);
        
        if(null != result && !"".equals(result)){
        	JSONObject jsonObject = JSONObject.parseObject(result);
        	
        	OrderCodeLogisticsVo orderCodeLogisticsVo = new OrderCodeLogisticsVo();
        	orderCodeLogisticsVo.setLogisticCode(jsonObject.getString("LogisticCode"));
        	
        	if(null != jsonObject.getString("Traces") && !"".equals(jsonObject.getString("Traces")) && jsonObject.getJSONArray("Traces").size() > 0){
        		JSONArray jsonArray = jsonObject.getJSONArray("Traces");
        		
        		List<LinkedHashMap<String, String>> traces = new ArrayList<>();
        		
        		for (int i = 0; i < jsonArray.size(); i++) {
        			JSONObject jsonObjectTrace = jsonArray.getJSONObject(i);
        			
        			LinkedHashMap<String, String> map = new LinkedHashMap<>();
        			map.put("acceptStation", jsonObjectTrace.getString("AcceptStation"));
        			map.put("acceptTime", jsonObjectTrace.getString("AcceptTime"));
        			traces.add(map);
				}
        		orderCodeLogisticsVo.setTraces(traces);
        	}else{
        		//Reason
        		List<LinkedHashMap<String, String>> traces = new ArrayList<>();
        		LinkedHashMap<String, String> map = new LinkedHashMap<>();
    			map.put("reason", jsonObject.getString("Reason")
    										.replace("[", "").replace("{", "")
    										.replace("]", "").replace("}", "")
    										.replace("ShipperCode", "").replace("【", "")
    										.replace("】", ""));
    			traces.add(map);
    			orderCodeLogisticsVo.setTraces(traces);
        	}
        	orderCodeLogisticsVo.setShipperCode(jsonObject.getString("ShipperCode"));
        	orderCodeLogisticsVo.setState(jsonObject.getString("State"));
        	orderCodeLogisticsVo.seteBusinessID(jsonObject.getString("EBusinessID"));
        	return orderCodeLogisticsVo;
        }
        //根据公司业务处理返回的信息......
        return null;
    }
    
    /**
     * 单号识别，可以识别哪个物流公司的
     * @author AimSpeed
     * @param logisticCode
     * @return
     * @throws Exception CodeDiscernVo
     */
    public CodeDiscernVo codeDiscern(String logisticCode) throws Exception{
    	String ReqURL="http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";
    	
        String requestData= "{'LogisticCode':'" + logisticCode + " '}";
         
        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", geteBusinessID());
        params.put("RequestType", "2002");
        
        String dataSign = encrypt(requestData, getAppKey(), "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");
         
        String result = sendPost(ReqURL, params);
        
        if(null != result && !"".equals(result)){
        	/*
        	 * {  "LogisticCode" : "9769183628782",  
        	 *    "Shippers" : [ 
        	 *                   {    "ShipperName" : "EMS",    
        	 *                        "ShipperCode" : "EMS"  }, 
        	 *                        {    "ShipperName" : "邮政快递",    
        	 *                             "ShipperCode" : "YZPY"  
        	 *                        } 
        	 *                  ],  
        	 *    "EBusinessID" : "1392118",  
        	 *    "Code" : "100",  
        	 *    "Success" : true}
        	 */
        	JSONObject jsonObject = JSONObject.parseObject(result);
        	
        	if("100".equals(jsonObject.getString("Code"))){
            	CodeDiscernVo codeDiscernVo = new CodeDiscernVo();
            	codeDiscernVo.setLogisticCode(jsonObject.getString("LogisticCode"));
            	
            	if(null != jsonObject.getString("Shippers") && !"".equals(jsonObject.getString("Shippers")) && jsonObject.getJSONArray("Shippers").size() > 0){
            		JSONArray jsonArray = jsonObject.getJSONArray("Shippers");
            		if(null != jsonArray.getJSONObject(0) && !"".equals(jsonArray.getJSONObject(0))){
            			codeDiscernVo.setShipperNameEn(jsonArray.getJSONObject(0).getString("ShipperName"));
                		codeDiscernVo.setShipperCodeEn(jsonArray.getJSONObject(0).getString("ShipperCode"));
            		}
            		
            		if(null != jsonArray.getJSONObject(1) && !"".equals(jsonArray.getJSONObject(1))){
            			codeDiscernVo.setShipperNameChinese(jsonArray.getJSONObject(1).getString("ShipperName"));
                		codeDiscernVo.setShipperCodeChinese(jsonArray.getJSONObject(1).getString("ShipperCode"));
            		}
            		
            	}
            	
            	codeDiscernVo.seteBusinessID(jsonObject.getString("EBusinessID"));
            	codeDiscernVo.setCode(jsonObject.getString("Code"));
            	return codeDiscernVo;
        	}
        	
        }
        
        
        //根据公司业务处理返回的信息......
        return null;
    }
  
    /**
     * MD5加密
     * @author AimSpeed
     * @param str
     * @param charset
     * @return String
     * @throws Exception String
     */
    @SuppressWarnings("unused")
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }
     
    /**
     * base64编码
     * @param str 内容      
     * @param charset 编码方式
     * @return String
     * @throws UnsupportedEncodingException String
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException{
        String encoded = base64Encode(str.getBytes(charset));
        return encoded;   
    }  
     
    @SuppressWarnings("unused")
    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
        String result = URLEncoder.encode(str, charset);
        return result;
    }
     
    /**
     * 
     * 电商Sign签名生成
     * @author AimSpeed
     * @param content 内容  
     * @param keyValue Appkey 
     * @param charset 编码方式
     * @return String
     * @throws UnsupportedEncodingException
     * @throws Exception String
     */
    @SuppressWarnings("unused")
    private String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception{
        if (keyValue != null)
        {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }
     
     /**
     * 向指定 URL 发送POST方法的请求    
     * @param url 发送请求的 URL   
     * @param params 请求的参数集合    
     * @return String 远程资源的响应结果
     */
    @SuppressWarnings("unused")
    private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;       
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            
            // POST方法
            conn.setRequestMethod("POST");
            
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.connect();
            
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            
            // 发送请求参数           
            if (params != null) {
                  StringBuilder param = new StringBuilder();
                  for (Map.Entry<String, String> entry : params.entrySet()) {
                      if(param.length()>0){
                          param.append("&");
                      }              
                      param.append(entry.getKey());
                      param.append("=");
                      param.append(entry.getValue());                    
                      //System.out.println(entry.getKey()+":"+entry.getValue());
                  }
                  //System.out.println("param:"+param.toString());
                  out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {           
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
     
     	
    private char[] base64EncodeChars = new char[] {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z', '0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9', '+', '/' 
    };
     
    public String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len)
            {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len)
            {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

	public String geteBusinessID() {
		return eBusinessID;
	}

	public void seteBusinessID(String eBusinessID) {
		this.eBusinessID = eBusinessID;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	
	
}
