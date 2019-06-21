package com.aimspeed.gatherer.service.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.protocol.HttpClientContext;

/**
 * HttpClient业务接口
 * @author AimSpeed
 */
public interface HttpClientService {
	
	/**
	 * 不带参数的doGet
	 * @author AimSpeed
	 * @param url
	 * @param coding
	 * @param httpClientContext
	 * @return HttpClientResult
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalArgumentException HttpClientResult 
	 */
	HttpClientResult doGet(String url,String coding,HttpClientContext httpClientContext) throws URISyntaxException, IOException ,IllegalArgumentException ,IllegalArgumentException;
	
	/**
	 * 带参数的doGet
	 * @author AimSpeed
	 * @param url
	 * @param coding
	 * @param param
	 * @param httpClientContext
	 * @return HttpClientResult
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalArgumentException HttpClientResult 
	 */
	HttpClientResult doGet(String url,String coding,Map<String, String> param,HttpClientContext httpClientContext) throws URISyntaxException, IOException ,IllegalArgumentException ,IllegalArgumentException;
	
	/**
	 * 带参数的doGet并且是带头部数据
	 * @author AimSpeed
	 * @param url
	 * @param coding
	 * @param params
	 * @param headers
	 * @param cookies
	 * @param httpClientContext
	 * @return HttpClientResult
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalArgumentException HttpClientResult 
	 */
	HttpClientResult doGet(String url, String coding,Map<String, String> params,Map<String, String> headers,Map<String, String> cookies,HttpClientContext httpClientContext) throws URISyntaxException, IOException ,IllegalArgumentException ,IllegalArgumentException;
	
	/**
	 * 带参数的doGet并且是带头部数据
	 * @author AimSpeed
	 * @param url
	 * @param coding
	 * @param params
	 * @param headers
	 * @param httpClientContext
	 * @return HttpClientResult
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalArgumentException HttpClientResult 
	 */
	HttpClientResult doGet(String url, String coding,Map<String, String> params,Map<String, String> headers,HttpClientContext httpClientContext) throws URISyntaxException, IOException ,IllegalArgumentException ,IllegalArgumentException;
	
	/**
	 * 不带参数的doPost
	 * @author AimSpeed
	 * @param url
	 * @param coding
	 * @param httpClientContext
	 * @return HttpClientResult
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws IllegalArgumentException HttpClientResult 
	 */
	HttpClientResult doPost(String url,String coding,HttpClientContext httpClientContext) throws ClientProtocolException,IOException ,IllegalArgumentException ;
    
	/**
	 * 带有参数的doPost请求
	 * @author AimSpeed
	 * @param url
	 * @param coding
	 * @param param
	 * @param httpClientContext
	 * @return HttpClientResult
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws IllegalArgumentException HttpClientResult 
	 */
    HttpClientResult doPost(String url, String coding,Map<String, String> param,HttpClientContext httpClientContext) throws ClientProtocolException,IOException ,IllegalArgumentException ;
	
    /**
     * 带有参数和请求包头的doPost请求
     * @author AimSpeed
     * @param url
     * @param coding
     * @param param
     * @param headers
     * @param httpClientContext
     * @return HttpClientResult
     * @throws ClientProtocolException
     * @throws IOException
     * @throws IllegalArgumentException HttpClientResult 
     */
    HttpClientResult doPost(String url,
									String coding,
									Map<String, String> param,
									Map<String, String> headers,
									HttpClientContext httpClientContext) throws ClientProtocolException,IOException ,IllegalArgumentException ;
    
    /**
     * 带有参数、请求包头、Cookie的doPost请求
     * @author AimSpeed
     * @param url
     * @param coding
     * @param param
     * @param headers
     * @param cookies
     * @param httpClientContext
     * @return HttpClientResult
     * @throws ClientProtocolException
     * @throws IOException
     * @throws IllegalArgumentException HttpClientResult 
     */
    HttpClientResult doPost(String url,
    									String coding,
   		 								Map<String, String> param,
   		 								Map<String, String> headers,
   		 								Map<String, String> cookies,
   		 								HttpClientContext httpClientContext) throws ClientProtocolException,IOException ,IllegalArgumentException ;

    /**
     * 带参数为JSON的doPost请求
     * @author AimSpeed
     * @param url
     * @param coding
     * @param json
     * @param httpClientContext
     * @return HttpClientResult
     * @throws ClientProtocolException
     * @throws IOException
     * @throws IllegalArgumentException HttpClientResult 
     */
    HttpClientResult doPostJson(String url, String coding,String json,HttpClientContext httpClientContext) throws ClientProtocolException, IOException ,IllegalArgumentException;

    /**
     * 带有参数、请求包头的请求，根据请求的方式，选择Get或Post  - 默认为GET
     * @author AimSpeed
     * @param url
     * @param coding
     * @param method
     * @param params
     * @param headers
     * @param httpClientContext
     * @return HttpClientResult
     * @throws URISyntaxException
     * @throws IOException
     * @throws IllegalArgumentException HttpClientResult 
     */
    HttpClientResult getResultOfMethod(String url,String coding,String method,Map<String, String> params,Map<String, String> headers,HttpClientContext httpClientContext) throws URISyntaxException, IOException ,IllegalArgumentException;

    /**
     * 带有参数、请求包头、Cookie的请求，根据请求的方式，选择Get或Post  - 默认为GET
     * @author AimSpeed
     * @param url
     * @param coding
     * @param method
     * @param params
     * @param headers
     * @param cookies
     * @param httpClientContext
     * @return HttpClientResult
     * @throws URISyntaxException
     * @throws IOException
     * @throws IllegalArgumentException HttpClientResult 
     */
    HttpClientResult getResultOfMethod(String url,String coding,String method,Map<String, String> params,Map<String, String> headers,Map<String, String> cookies,HttpClientContext httpClientContext) throws URISyntaxException, IOException ,IllegalArgumentException;
	
    /**
     * 下载图片
     * @author AimSpeed
     * @param url
     * @param filePathName
     * @return
     * @throws IOException HttpClientResult 
     */
	HttpClientResult downloadImg(String url,String filePathName) throws IOException ;

	/**
	 * 获取到图片流
	 * @author AimSpeed
	 * @param url
	 * @return byte[]
	 */
	byte[] getImgByte(String url);

}
