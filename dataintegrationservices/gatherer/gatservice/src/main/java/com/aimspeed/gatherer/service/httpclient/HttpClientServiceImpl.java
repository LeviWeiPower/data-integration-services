package com.aimspeed.gatherer.service.httpclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aimspeed.common.enums.HttpRequestMethodEnum;
import com.aimspeed.common.file.FileUtils;
import com.aimspeed.common.http.HttpRequestUtils;



/** 
 * @author AimSpeed
 */
@Service
@Transactional
public class HttpClientServiceImpl implements HttpClientService {
	
	private Logger logger = LoggerFactory.getLogger(HttpClientServiceImpl.class);
	
	/**
	 * 注入HttpClient实例  
	 */
	@Autowired
    private CloseableHttpClient client;  
	
    /*
     * 不带参数的doGet
     * @author AimSpeed
     * @param url
     * @param coding
     * @param httpClientContext
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws IllegalArgumentException
     * @overridden @see com.aimspeed.gatherer.service.httpclient.HttpClientService#doGet(java.lang.String, java.lang.String, org.apache.http.client.protocol.HttpClientContext)
     */
    @Override
    public HttpClientResult doGet(String url,String coding,HttpClientContext httpClientContext) throws URISyntaxException, IOException ,IllegalArgumentException{
    	return doGet(url,null,null,httpClientContext);
    }

    /*
     * 带参数的doGet
     * @author AimSpeed
     * @param url
     * @param coding
     * @param param
     * @param httpClientContext
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws IllegalArgumentException
     * @overridden @see com.aimspeed.gatherer.service.httpclient.HttpClientService#doGet(java.lang.String, java.lang.String, java.util.Map, org.apache.http.client.protocol.HttpClientContext)
     */
    @Override
    public HttpClientResult doGet(String url,String coding,Map<String, String> param,HttpClientContext httpClientContext) throws URISyntaxException, IOException ,IllegalArgumentException{
    	return doGet(url,coding,param,null,null,httpClientContext);
    }
    
    /*
     * 带参数的doGet并且是带头部数据
     * @author AimSpeed
     * @param url
     * @param coding
     * @param params
     * @param headers
     * @param httpClientContext
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws IllegalArgumentException
     * @overridden @see com.aimspeed.gatherer.service.httpclient.HttpClientService#doGet(java.lang.String, java.lang.String, java.util.Map, java.util.Map, org.apache.http.client.protocol.HttpClientContext)
     */
    @Override
	public HttpClientResult doGet(String url, String coding, Map<String, String> params, Map<String, String> headers,
			HttpClientContext httpClientContext)
			throws URISyntaxException, IOException, IllegalArgumentException, IllegalArgumentException {
		return doGet(url,coding,params,headers,null,httpClientContext);
	}
    
    /*
     * 带参数的doGet并且是带头部数据和Cookie信息
     * @author AimSpeed
     * @param url
     * @param coding
     * @param params
     * @param headers
     * @param cookies
     * @param httpClientContext
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws IllegalArgumentException
     * @overridden @see com.aimspeed.gatherer.service.httpclient.HttpClientService#doGet(java.lang.String, java.lang.String, java.util.Map, java.util.Map, java.util.Map, org.apache.http.client.protocol.HttpClientContext)
     */
    @Override
    public HttpClientResult doGet(String url,String coding, Map<String, String> params,Map<String, String> headers,Map<String, String> cookies,HttpClientContext httpClientContext) throws URISyntaxException, IOException ,IllegalArgumentException{
    	// 定义请求的参数
    	if(null != params 
    			&& !params.isEmpty()){
    		URIBuilder uriBuilder = new URIBuilder(url);
    		for (Map.Entry<String, String> entry : params.entrySet()) {
    			uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
    		}
    	}
    	
    	// 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        //定义头部数据
        if(null != headers && !headers.isEmpty()){
        	for (Map.Entry<String, String> entry : headers.entrySet()) {
        		
        		if(null != entry.getKey() && !"".equals(entry.getKey().trim()) &&
        				null != entry.getValue() && !"".equals(entry.getValue().trim())) {
        			httpGet.addHeader(entry.getKey(), entry.getValue());
        		}
        		
        	}
        	
        }
        
        //定义Cookie数据
        if(null != cookies && !cookies.isEmpty()){
        	String cookieStr = "";
        	for (Map.Entry<String, String> entry : cookies.entrySet()) {
        		if(null != entry.getKey() && !"".equals(entry.getKey().trim()) &&
        				null != entry.getValue() && !"".equals(entry.getValue().trim())) {
        			cookieStr += entry.getKey() + "=" + entry.getValue() + ";";
        		}
        		
        	}
        	if(null != cookieStr && !"".equals(cookieStr.trim())) {
        		httpGet.addHeader("Cookie", cookieStr);
        	}
        	
        }
        
        CloseableHttpResponse response = null;
        // 执行请求
        try {
        	//是否需要创建需要维持一个回话，如果需要维护一个会话，那么则每次都需要对这个会话的内容维持一个唯一的
        	if(null != httpClientContext){
        		response = client.execute(httpGet,httpClientContext);
        	}else{
        		response = client.execute(httpGet);
        	}
			// 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
            	
            	HttpClientResult httpClientResult = new HttpClientResult(response,
            												response.getAllHeaders(),
            												coding,
            												FileUtils.stringOfReadStream(response.getEntity().getContent(),coding));
            	
            	return httpClientResult;

            }
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally {
            if (response != null) {
				response.close();
            }
        }
        return null;
    }

    /*
     * 不带参数的doPost
     * @author AimSpeed
     * @param url
     * @param coding
     * @param httpClientContext
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws IllegalArgumentException
     * @overridden @see com.aimspeed.gatherer.service.httpclient.HttpClientService#doPost(java.lang.String, java.lang.String, org.apache.http.client.protocol.HttpClientContext)
     */
    public HttpClientResult doPost(String url,String coding,HttpClientContext httpClientContext) throws ClientProtocolException,IOException ,IllegalArgumentException {
    	return doPost(url,null,null,httpClientContext);
    }
    
    /*
     * 带有参数的doPost请求
     * @author AimSpeed
     * @param url
     * @param coding
     * @param param
     * @param httpClientContext
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws IllegalArgumentException
     * @overridden @see com.aimspeed.gatherer.service.httpclient.HttpClientService#doPost(java.lang.String, java.lang.String, java.util.Map, org.apache.http.client.protocol.HttpClientContext)
     */
    public HttpClientResult doPost(String url,String coding, Map<String, String> param,HttpClientContext httpClientContext) throws ClientProtocolException,IOException ,IllegalArgumentException {
    	return doPost(url,coding,param,null,null,httpClientContext);
    }

    /*
     * 带有参数和请求包头的doPost请求
     * @author AimSpeed
     * @param url
     * @param coding
     * @param param
     * @param headers
     * @param httpClientContext
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws IllegalArgumentException
     * @overridden @see com.aimspeed.gatherer.service.httpclient.HttpClientService#doPost(java.lang.String, java.lang.String, java.util.Map, java.util.Map, org.apache.http.client.protocol.HttpClientContext)
     */
	@Override
	public HttpClientResult doPost(String url, String coding, Map<String, String> param, Map<String, String> headers,
			HttpClientContext httpClientContext) throws ClientProtocolException, IOException, IllegalArgumentException {
		return doPost(url,coding,param,headers,null,httpClientContext);
	}
	
	/*
	 * 带有参数、请求包头、Cookie的doPost请求
	 * @author AimSpeed
	 * @param url
	 * @param coding
	 * @param param
	 * @param headers
	 * @param cookies
	 * @param httpClientContext
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @overridden @see com.aimspeed.gatherer.service.httpclient.HttpClientService#doPost(java.lang.String, java.lang.String, java.util.Map, java.util.Map, java.util.Map, org.apache.http.client.protocol.HttpClientContext)
	 */
    public HttpClientResult doPost(String url, 
    						 String coding,
				    		 Map<String, String> param,
				    		 Map<String, String> headers,
				    		 Map<String, String> cookies,
				    		 HttpClientContext httpClientContext) throws ClientProtocolException,IOException ,IllegalArgumentException {
    	
    	// 创建http POST请求
    	HttpPost httpPost = new HttpPost(url);
    	
    	// 设置post参数
    	List<NameValuePair> parameters = new ArrayList<NameValuePair>();

    	if (null != param && !param.isEmpty()) {
    		for (Map.Entry<String, String> entry : param.entrySet()) {
    			parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
    		}
    		httpPost.setEntity(new UrlEncodedFormEntity(parameters, coding));
    	}
    	
    	//定义头部数据
    	if (null != headers && !headers.isEmpty()) {
	    	//定义头部数据
	    	for (Map.Entry<String, String> entry : headers.entrySet()) {

        		if(null != entry.getKey() && !"".equals(entry.getKey().trim()) &&
        				null != entry.getValue() && !"".equals(entry.getValue().trim())) {
        			httpPost.addHeader(entry.getKey(), entry.getValue());
        		}
        		
	    		
	    	}
    	}
    	
        //定义Cookie数据
        if(null != cookies && !cookies.isEmpty()){
        	String cookieStr = "";
        	for (Map.Entry<String, String> entry : cookies.entrySet()) {
        		if(null != entry.getKey() && !"".equals(entry.getKey().trim()) &&
        				null != entry.getValue() && !"".equals(entry.getValue().trim())) {
        			cookieStr += entry.getKey() + "=" + entry.getValue() + ";";
        		}
        		
        	}
        	if(null != cookieStr && !"".equals(cookieStr.trim())) {
        		httpPost.addHeader("Cookie", cookieStr);
        	}
        	
        }
    	
    	CloseableHttpResponse response = null;
    	try {
    		//执行请求
        	if(null != httpClientContext){
        		response = client.execute(httpPost,httpClientContext);
        	}else{
        		response = client.execute(httpPost);
        	}

    		// 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
            	
            	HttpClientResult httpClientResult = new HttpClientResult(response,
            												response.getAllHeaders(),
            												coding,
            												FileUtils.stringOfReadStream(response.getEntity().getContent(),coding));
            	
            	return httpClientResult;
            }
    	} finally {
    		if (response != null) {
    			response.close();
    		}
    	}
    	return null;
    }
    
    /*
     * 带参数为JSON的doPost请求
     * @author AimSpeed
     * @param url
     * @param coding
     * @param json
     * @param httpClientContext
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws IllegalArgumentException
     * @overridden @see com.aimspeed.gatherer.service.httpclient.HttpClientService#doPostJson(java.lang.String, java.lang.String, java.lang.String, org.apache.http.client.protocol.HttpClientContext)
     */
    @Override
    public HttpClientResult doPostJson(String url,String coding, String json,HttpClientContext httpClientContext) throws ClientProtocolException, IOException ,IllegalArgumentException {

		// 创建http POST请求
		HttpPost httpPost = new HttpPost(url);
	
		if (json != null) {
			// 构造一个josn表单式的实体
			StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
			
			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(stringEntity);
		}
		
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			if(null != httpClientContext){
        		response = client.execute(httpPost,httpClientContext);
        	}else{
        		response = client.execute(httpPost);
        	}
			// 判断返回状态是否为200
	        if (response.getStatusLine().getStatusCode() == 200) {
            	
            	HttpClientResult httpClientResult = new HttpClientResult(response,
            												response.getAllHeaders(),
            												coding,
            												FileUtils.stringOfReadStream(response.getEntity().getContent(),coding));
            	
            	return httpClientResult;
            }
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}

    /*
     * 带有参数、请求包头的请求，根据请求的方式，选择Get或Post - 默认为GET
     * @author AimSpeed
     * @param url
     * @param coding
     * @param method
     * @param params
     * @param headers
     * @param httpClientContext
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws IllegalArgumentException
     * @overridden @see com.aimspeed.gatherer.service.httpclient.HttpClientService#getResultOfMethod(java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.util.Map, org.apache.http.client.protocol.HttpClientContext)
     */
	@Override
	public HttpClientResult getResultOfMethod(String url, String coding, String method, Map<String, String> params,
			Map<String, String> headers, HttpClientContext httpClientContext)
			throws URISyntaxException, IOException, IllegalArgumentException {
		return getResultOfMethod(url, coding, method, params, headers,null, httpClientContext);
	}
    
	/*
	 * 带有参数、请求包头、Cookie的请求，根据请求的方式，选择Get或Post - 默认为GET
	 * @author AimSpeed
	 * @param url
	 * @param coding
	 * @param method
	 * @param params
	 * @param headers
	 * @param cookies
	 * @param httpClientContext
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @overridden @see com.aimspeed.gatherer.service.httpclient.HttpClientService#getResultOfMethod(java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.util.Map, java.util.Map, org.apache.http.client.protocol.HttpClientContext)
	 */
	@Override
	public HttpClientResult getResultOfMethod(String url,String coding, String method, Map<String, String> params,
			Map<String, String> headers, Map<String, String> cookies,HttpClientContext httpClientContext) throws URISyntaxException, IOException ,IllegalArgumentException {
		HttpClientResult result = null;

		//抓取的数据的请求方式
		if(HttpRequestMethodEnum.POST.getValue().equalsIgnoreCase(method)){
			result = doPost(url,coding,params,headers,cookies,httpClientContext);
		}else{
			result = doGet(url,coding,params,headers,cookies,httpClientContext);
		}
		return result;
	}
    
	/*
	 * 下载图片
	 * @author AimSpeed
	 * @param url
	 * @param filePathName
	 * @return
	 * @throws IOException
	 * @overridden @see com.aimspeed.gatherer.service.httpclient.HttpClientService#downloadImg(java.lang.String, java.lang.String)
	 */
	@Override
	public HttpClientResult downloadImg(String url, String filePathName) throws IOException {
		CloseableHttpResponse response = null;
		HttpGet httpget = new HttpGet(url);
		//HttpPost httpget = new HttpPost(url);
		//伪装成浏览器
		httpget.setHeader("User-Agent", HttpRequestUtils.getRandomBrowserVersion());
		try {
			response = client.execute(httpget);
			if (response.getStatusLine().getStatusCode() == 200) {
            	
				 //获取文件
				 File storeFile = new File(filePathName);
				 FileOutputStream output = new FileOutputStream(storeFile);
				 // 得到网络资源的字节数组,并写入文件
				 HttpEntity entity = response.getEntity();
				 if (entity != null) {
					InputStream instream = entity.getContent();
					try {
						byte b[] = new byte[4096];
						int j = 0;
						while( (j = instream.read(b))!=-1){
							output.write(b,0,j);
						}
						output.flush();
						output.close();
						
					} catch (IOException ex) {
					
					} finally {
						instream.close();
					}
				 }
				 HttpClientResult httpClientResult = new HttpClientResult(response,
							response.getAllHeaders(),
							null,
							null);

					return httpClientResult;
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			response.close();
		}
		return null;
	}
	
	/*
	 * 获取到图片流
	 * @author AimSpeed
	 * @param url
	 * @return
	 * @overridden @see com.aimspeed.gatherer.service.httpclient.HttpClientService#getImgInputStream(java.lang.String)
	 */
	@Override
	public byte[] getImgByte(String url) {
		CloseableHttpResponse response = null;
		HttpGet httpget = new HttpGet(url);
		//伪装成浏览器
		httpget.setHeader("User-Agent", HttpRequestUtils.getRandomBrowserVersion());
		try {
			response = client.execute(httpget);
			if (response.getStatusLine().getStatusCode() == 200) {
            	
				 // 得到网络资源的字节流
				 HttpEntity entity = response.getEntity();
				 if (entity != null) {
//					InputStream instream = entity.getContent();
					byte[] byteArray = EntityUtils.toByteArray(entity);
					if(null != byteArray && byteArray.length > 0) {
						return byteArray;
					}
				 }
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}


    
    
}
