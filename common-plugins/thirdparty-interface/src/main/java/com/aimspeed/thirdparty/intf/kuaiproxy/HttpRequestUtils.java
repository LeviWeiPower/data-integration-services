package com.aimspeed.thirdparty.intf.kuaiproxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import com.aimspeed.thirdparty.intf.kuaiproxy.vo.BaseKuaiProxyParamVo;


/**
 * Http请求
 * @author AimSpeed
 */
public class HttpRequestUtils {
	
	/**
	 * 请求配置类
	 * @author AimSpeed
	 * @Project AIM_SPEED_INTERFACE
	 * @Package com.aimspeed.intf.utils.http
	 * @FileName HttpRequestUtils.java
	 * @ClassName ReqeustConfig
	 * @date 2018年8月16日
	 */
	class ReqeustConfig {
		private String defaultContentEncoding = Charset.defaultCharset().name();
	    private int connectTimeout = 1000;
	    private int readTimeout = 1000;
	    
	    /**
	     * 得到默认的响应字符集
	     */
	    public String getDefaultContentEncoding() {
	        return this.defaultContentEncoding;
	    }

	    /**
	     * 设置默认的响应字符集
	     */
	    public void setDefaultContentEncoding(String defaultContentEncoding) {
	        this.defaultContentEncoding = defaultContentEncoding;
	    }

	    public int getConnectTimeout() {
	        return connectTimeout;
	    }

	    public void setConnectTimeout(int connectTimeout) {
	        this.connectTimeout = connectTimeout;
	    }

	    public int getReadTimeout() {
	        return readTimeout;
	    }

	    public void setReadTimeout(int readTimeout) {
	        this.readTimeout = readTimeout;
	    }
	    
	}
	
	/**
	 * 发送GET请求
	 * @author AimSpeed
	 * @Title sendGet 
	 * @param urlString
	 * @param proxySettings 代理设置，null代表不设置代理
	 * @return
	 * @throws IOException HttpResponse 
	 * @date 2018年8月16日
	 */
	public HttpResponse sendGet(BaseKuaiProxyParamVo baseKuaiProxyParamVo) throws IOException {
		return this.send(baseKuaiProxyParamVo.getPageUrl(), "GET", 
						 baseKuaiProxyParamVo.getParams(), 
						 baseKuaiProxyParamVo.getHeaders(), 
						 baseKuaiProxyParamVo.getProxySettings());
	}
	
	/**
	 * 发送GET请求
	 * @author AimSpeed
	 * @Title sendGet 
	 * @param urlString
	 * @param proxySettings 代理设置，null代表不设置代理
	 * @return
	 * @throws IOException HttpResponse 
	 * @date 2018年8月16日
	 */
    public HttpResponse sendGet(String urlString, final Map<String, String> proxySettings) throws IOException {
        return this.send(urlString, "GET", null, null, proxySettings);
    }

    /**
     * 发送GET请求
     * @author AimSpeed
     * @Title sendGet 
     * @param urlString URL地址
     * @param params 参数集合
     * @param proxySettings 代理设置，null代表不设置代理
     * @return
     * @throws IOException HttpResponse 
     * @date 2018年8月16日
     */
    public HttpResponse sendGet(String urlString, Map<String, String> params, final Map<String, String> proxySettings)
            throws IOException {
        return this.send(urlString, "GET", params, null, proxySettings);
    }

    /**
     * 发送GET请求
     * @author AimSpeed
     * @Title sendGet 
     * @param urlString
     * @param params
     * @param headers
     * @param proxySettings 代理设置，null代表不设置代理
     * @return
     * @throws IOException HttpResponse 
     * @date 2018年8月16日
     */
    public HttpResponse sendGet(String urlString, Map<String, String> params,
            Map<String, String> headers, final Map<String, String> proxySettings) throws IOException {
        return this.send(urlString, "GET", params, headers, proxySettings);
    }
    
    /**
     * 发送POST请求
     * @author AimSpeed
     * @Title sendPost 
     * @param urlString
     * @param proxySettings 代理设置，null代表不设置代理
     * @return
     * @throws IOException HttpResponse 
     * @date 2018年8月16日
     */
    public HttpResponse sendPost(BaseKuaiProxyParamVo baseKuaiProxyParamVo) throws IOException {
    	return this.send(baseKuaiProxyParamVo.getPageUrl(), "POST", 
						 baseKuaiProxyParamVo.getParams(), 
						 baseKuaiProxyParamVo.getHeaders(), 
						 baseKuaiProxyParamVo.getProxySettings());
    }

    /**
     * 发送POST请求
     * @author AimSpeed
     * @Title sendPost 
     * @param urlString
     * @param proxySettings 代理设置，null代表不设置代理
     * @return
     * @throws IOException HttpResponse 
     * @date 2018年8月16日
     */
    public HttpResponse sendPost(String urlString, final Map<String, String> proxySettings) throws IOException {
        return this.send(urlString, "POST", null, null, proxySettings);
    }

    /**
     * 发送POST请求
     * @author AimSpeed
     * @Title sendPost 
     * @param urlString
     * @param params
     * @param proxySettings 代理设置，null代表不设置代理
     * @return
     * @throws IOException HttpResponse 
     * @date 2018年8月16日
     */
    public HttpResponse sendPost(String urlString, Map<String, String> params, final Map<String, String> proxySettings)
            throws IOException {
        return this.send(urlString, "POST", params, null, proxySettings);
    }

    /**
     * 发送POST请求
     * @author AimSpeed
     * @Title sendPost 
     * @param urlString
     * @param params
     * @param headers
     * @param proxySettings 代理设置，null代表不设置代理
     * @return
     * @throws IOException HttpResponse 
     * @date 2018年8月16日
     */
    public HttpResponse sendPost(String urlString, Map<String, String> params,
            Map<String, String> headers, final Map<String, String> proxySettings) throws IOException {
        return this.send(urlString, "POST", params, headers, proxySettings);
    }

    /**
     * 发送HTTP请求
     * @author AimSpeed
     * @Title send 
     * @param urlString
     * @param method
     * @param parameters
     * @param headers
     * @param proxySettings
     * @return
     * @throws IOException HttpResponse 
     * @date 2018年8月16日
     */
    private HttpResponse send(String urlString, String method,
            Map<String, String> parameters, Map<String, String> headers, final Map<String, String> proxySettings)
            throws IOException {
        HttpURLConnection urlConnection = null;

        if (method.equalsIgnoreCase("GET") && parameters != null) {
            StringBuffer param = new StringBuffer();
            int i = 0;
            for (String key : parameters.keySet()) {
                if (i == 0)
                    param.append("?");
                else
                    param.append("&");
                param.append(key).append("=").append(URLEncoder.encode(parameters.get(key), "utf-8"));
                i++;
            }
            urlString += param;
        }
        URL url = new URL(urlString);
        if(proxySettings != null){
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxySettings.get("ip"), Integer.parseInt(proxySettings.get("port"))));
            urlConnection = (HttpURLConnection) url.openConnection(proxy);
            if(proxySettings.containsKey("username")){
                Authenticator authenticator = new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return (new PasswordAuthentication(proxySettings.get("username"),
                                proxySettings.get("password").toCharArray()));
                    }
                };
                Authenticator.setDefault(authenticator);
            }
        }
        else{
            urlConnection = (HttpURLConnection) url.openConnection();
        }

        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);

        urlConnection.setConnectTimeout(new HttpRequestUtils.ReqeustConfig().getConnectTimeout());
        urlConnection.setReadTimeout(new HttpRequestUtils.ReqeustConfig().getReadTimeout());

        if (headers != null)
            for (String key : headers.keySet()) {
                urlConnection.addRequestProperty(key, headers.get(key));
            }

        if (method.equalsIgnoreCase("POST") && parameters != null) {
            StringBuffer param = new StringBuffer();
            int i = 0;
            for (String key : parameters.keySet()) {
                if(i > 0) param.append("&");
                param.append(key).append("=").append(URLEncoder.encode(parameters.get(key), "utf-8"));
                i++;
            }
            urlConnection.getOutputStream().write(param.toString().getBytes());
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }

        return this.makeContent(urlString, urlConnection);
    }

    /**
     * 组装结果数据
     * @author AimSpeed
     * @Title makeContent 
     * @param urlString
     * @param urlConnection
     * @return
     * @throws IOException HttpResponse 
     * @date 2018年8月16日
     */
    private HttpResponse makeContent(String urlString,
            HttpURLConnection urlConnection) throws IOException {
        HttpResponse response = new HttpResponse();
        try {
            InputStream in = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            if ("gzip".equals(urlConnection.getContentEncoding())) bufferedReader =  new BufferedReader(new InputStreamReader(new GZIPInputStream(in)));
            response.contentCollection = new Vector<String>();
            StringBuffer temp = new StringBuffer();
            String line = bufferedReader.readLine();
            while (line != null) {
                response.contentCollection.add(line);
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            String encoding = urlConnection.getContentEncoding();
            if (encoding == null) {
                encoding = new HttpRequestUtils.ReqeustConfig().getDefaultContentEncoding();
            }
            response.urlString = urlString;

            response.defaultPort = urlConnection.getURL().getDefaultPort();
            response.file = urlConnection.getURL().getFile();
            response.host = urlConnection.getURL().getHost();
            response.path = urlConnection.getURL().getPath();
            response.port = urlConnection.getURL().getPort();
            response.protocol = urlConnection.getURL().getProtocol();
            response.query = urlConnection.getURL().getQuery();
            response.ref = urlConnection.getURL().getRef();
            response.userInfo = urlConnection.getURL().getUserInfo();
            response.contentLength = urlConnection.getContentLength();

            response.content = new String(temp.toString().getBytes());
            response.contentEncoding = encoding;
            response.code = urlConnection.getResponseCode();
            response.message = urlConnection.getResponseMessage();
            response.contentType = urlConnection.getContentType();
            response.method = urlConnection.getRequestMethod();
            response.connectTimeout = urlConnection.getConnectTimeout();
            response.readTimeout = urlConnection.getReadTimeout();

            return response;
        } catch (IOException e) {
            throw e;
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
        }
    }
    

    
	
}
