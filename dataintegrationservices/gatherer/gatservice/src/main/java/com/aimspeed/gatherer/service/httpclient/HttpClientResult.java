package com.aimspeed.gatherer.service.httpclient;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;

/**
 * HttpClient返回结果集
 * @author AimSpeed
 */
public class HttpClientResult {
	
	/**
	 * 响应结果的对象
	 */
	private CloseableHttpResponse response;
	
	/**
	 * 头部信息
	 */
	private Header [] headers;
	
	/**
	 * 编码
	 */
	private String coding;
	
	/**
	 * 响应内容
	 */
	private String responseBody;
	
	public HttpClientResult() {
		super();
	}

	public HttpClientResult(CloseableHttpResponse response, Header[] headers, String coding, String responseBody) {
		super();
		this.response = response;
		this.headers = headers;
		this.coding = coding;
		this.responseBody = responseBody;
	}

	public CloseableHttpResponse getResponse() {
		return response;
	}

	public void setResponse(CloseableHttpResponse response) {
		this.response = response;
	}

	public Header[] getHeaders() {
		return headers;
	}

	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}

	public String getCoding() {
		return coding;
	}

	public void setCoding(String coding) {
		this.coding = coding;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	
	
	
	
	
}
