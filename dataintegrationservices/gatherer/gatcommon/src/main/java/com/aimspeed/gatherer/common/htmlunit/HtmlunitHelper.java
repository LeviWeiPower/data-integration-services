package com.aimspeed.gatherer.common.htmlunit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.aimspeed.common.enums.HttpRequestMethodEnum;
import com.aimspeed.common.file.FileUtils;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.util.NameValuePair;


/**
 * 
 * @author AimSpeed
 */
public class HtmlunitHelper {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        
		// 屏蔽HtmlUnit等系统 log
//		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
//		java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
 
        String url = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=2&tn=baiduhome_pg&wd=jsoup%20maen&rsv_spt=1&oq=jsoup&rsv_pq=9cef6f6700028983&rsv_t=23c8aXNm9qfoI9bZcmFgJUv4B7HFrqk1Mu0CfxPAd9e4P%2FyQb4Sn6%2FIIwMMtlbVpey2o&rqlang=cn&rsv_enter=1&inputT=1077&rsv_sug3=86&rsv_sug1=63&rsv_sug7=100&rsv_sug2=0&rsv_sug4=1300";
        
        //页面测试
        HtmlPage page = HtmlunitHelper.getSingleInstance().doGet(url);
        
        String pageAsXml = page.asXml();
        System.out.println(pageAsXml);
        
        //下载测试
        InputStream inputStream = HtmlunitHelper.getSingleInstance().downFile("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557047079&di=fae98c786db8ac7cf95292e8b4adb627&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.it610.com%2Fimage%2Finfo5%2Ff822902a661c4a198e34b7c0752df2c7.png");
        FileUtils.writeFileFromIS(new File("E:/1.jpg"), inputStream);
        
        // Jsoup解析处理
        /*Document doc = Jsoup.parse(pageAsXml);  
        Elements pngs = doc.select("img[src$=.png]");                   // 获取所有图片元素集
        
        // 此处省略其他操作
        System.out.println(pngs.size());*/
    }
	
	/**
	 * HtmlUnit模拟浏览器
	 */
	private WebClient webClient = null;
	
	/**
	 * 是否保持同一个会话，默认为false
	 */
	private boolean isKeepSession;
	
	/**
	 * Web Client 模拟浏览器的配置，全局配置
	 */
	private static WebClientConfig webClientConfig = new WebClientConfig();
	
	private HtmlunitHelper() {
		super();
	}

	public HtmlunitHelper(boolean isKeepSession) {
		super();
		this.isKeepSession = isKeepSession;
	}
	
	public HtmlunitHelper(boolean isKeepSession, WebClientConfig webClientConfig) {
		super();
		this.isKeepSession = isKeepSession;
		this.webClientConfig = webClientConfig;
	}

	public boolean isKeepSession() {
		return isKeepSession;
	}

	public void setKeepSession(boolean isKeepSession) {
		this.isKeepSession = isKeepSession;
	}

	public void setWebClient(WebClient webClient) {
		this.webClient = webClient;
	}
	
	/**
	 * 获取到对象实例，单例
	 * @author AimSpeed
	 * @return HtmlunitHelper
	 */
	public static HtmlunitHelper getSingleInstance() {
		return BrowserVersionUtils.HTMLUNIT_HELPER;
	}

	/**
	 * 获取到对象实例，多例
	 * @author AimSpeed
	 * @param isKeepSession 是否保持同一个会话，默认为false
	 * @return HtmlunitHelper
	 */
	public static HtmlunitHelper getNewInstance(boolean isKeepSession) {
		return new HtmlunitHelper(isKeepSession);
	}
	
	/**
	 * GET请求
	 * @author AimSpeed
	 * @param url 访问地址
	 * @return P 页面
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException P
	 */
	public <P extends Page> P doGet(String url) throws FailingHttpStatusCodeException, IOException{
    	return getPage(url, null, HttpMethod.GET, null, null, null);
    }
	
	/**
	 * GET请求
	 * @author AimSpeed
	 * @param url 访问地址
	 * @param coding 编码，默认为UTF-8
	 * @return P 页面
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException P
	 */
	public <P extends Page> P doGet(String url,String coding) throws FailingHttpStatusCodeException, IOException{
    	return getPage(url, coding, HttpMethod.GET, null, null, null);
    }
	
	/**
	 * GET请求
	 * @author AimSpeed
	 * @param url访问地址
	 * @param coding 编码，默认为UTF-8
	 * @param params 请求参数
	 * @return P 页面
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException P
	 */
	public <P extends Page> P doGet(String url,String coding, Map<String, String> params) throws FailingHttpStatusCodeException, IOException {
		return getPage(url, coding, HttpMethod.GET, params, null, null);
	}
	
	/**
	 * GET请求
	 * @author AimSpeed
	 * @param url 访问地址
	 * @param coding 编码，默认为UTF-8
	 * @param params 请求参数
	 * @param headers 请求头
	 * @param cookies 请求Cookies
	 * @return P 页面
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException P
	 */
	public <P extends Page> P doGet(String url,String coding, Map<String, String> params,
									Map<String, String> headers, Map<String, String> cookies) throws FailingHttpStatusCodeException, IOException {
		return getPage(url, coding, HttpMethod.GET, params, headers, cookies);
	}
	
	/**
	 * POST请求
	 * @author AimSpeed
	 * @param url 访问地址
	 * @param coding 编码，默认为UTF-8
	 * @return P 页面
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException P
	 */
	public <P extends Page> P doPost(String url) throws FailingHttpStatusCodeException, IOException{
    	return getPage(url, null, HttpMethod.POST, null, null, null);
    }
	
	/**
	 * POST请求
	 * @author AimSpeed
	 * @param url 访问地址
	 * @param coding 编码，默认为UTF-8
	 * @return P 页面
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException P
	 */
	public <P extends Page> P doPost(String url,String coding) throws FailingHttpStatusCodeException, IOException{
    	return getPage(url, coding, HttpMethod.POST, null, null, null);
    }
	
	/**
	 * POST请求
	 * @author AimSpeed
	 * @param url 访问地址
	 * @param coding 编码，默认为UTF-8
	 * @param params 请求参数
	 * @return P 页面
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException P
	 */
	public <P extends Page> P doPost(String url,String coding, Map<String, String> params) throws FailingHttpStatusCodeException, IOException {
		return getPage(url, coding, HttpMethod.POST, params, null, null);
	}
	
	/**
	 * POST请求
	 * @author AimSpeed
	 * @param url 访问地址
	 * @param coding 编码，默认为UTF-8
	 * @param params 请求参数
	 * @param headers 请求头
	 * @param cookies 请求Cookies
	 * @return P 页面
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException P
	 */
	public <P extends Page> P doPost(String url,String coding, Map<String, String> params,
									Map<String, String> headers, Map<String, String> cookies) throws FailingHttpStatusCodeException, IOException {
		return getPage(url, coding, HttpMethod.POST, params, headers, cookies);
	}
	
	/**
	 * 下载文件，默认用GET
	 * import org.apache.commons.io.IOUtils;
	 * IOUtils.write(IOUtils.readFully(contentAsStream,(int)page.getWebResponse().getContentLength()),new FileOutputStream("e:/1.jpg"));
	 * @author AimSpeed
	 * @param url 请求地址
	 * @return InputStream 数据流
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException InputStream
	 */
	public InputStream downFile(String url) throws FailingHttpStatusCodeException, IOException{
		return downFile(url, null, HttpMethod.GET);
	}
	
	/**
	 * 下载文件，默认用POST
	 * import org.apache.commons.io.IOUtils;
	 * IOUtils.write(IOUtils.readFully(contentAsStream,(int)page.getWebResponse().getContentLength()),new FileOutputStream("e:/1.jpg"));
	 * @author AimSpeed
	 * @param url 请求地址
	 * @param httpMethod 请求方式
	 * @return InputStream 数据流
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException InputStream
	 */
	public InputStream downFile(String url, HttpMethod httpMethod) throws FailingHttpStatusCodeException, IOException{
		return downFile(url, null, httpMethod);
	}
	
	/**
	 * 下载文件
	 * import org.apache.commons.io.IOUtils;
	 * IOUtils.write(IOUtils.readFully(contentAsStream,(int)page.getWebResponse().getContentLength()),new FileOutputStream("e:/1.jpg"));
	 * @author AimSpeed
	 * @param url 请求地址
	 * @param coding  编码，默认为UTF-8
	 * @param httpMethod 请求方式
	 * @return InputStream 数据流
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException InputStream
	 */
	public InputStream downFile(String url,String coding, HttpMethod httpMethod) throws FailingHttpStatusCodeException, IOException{
		Page page = getPage(url, coding, httpMethod, null, null, null);
		InputStream contentAsStream = page.getWebResponse().getContentAsStream();
		return contentAsStream;
	}
	
	/**
	 * 将参数，请求头、Cookie等内容设置好后请求页面
	 * 如果是想要一直保存连接的话，则调用getWebClient 和 getWebRequest后续使用同一个WebClient
	 * @author AimSpeed
	 * @param url 访问地址
	 * @param coding 编码，默认为UTF-8
	 * @param httpMethodStr 请求方式，默认是POST，此方法只支持GET和POST
	 * @param params 请求参数
	 * @param headers 请求头
	 * @param cookies 请求Cookies
	 * @return P 页面
	 * @throws IOException 
	 * @throws FailingHttpStatusCodeException 
	 * @throws MalformedURLException 
	 */
	public <P extends Page> P getPage(String url,String coding, String httpMethodStr, Map<String, String> params,
			Map<String, String> headers, Map<String, String> cookies) throws FailingHttpStatusCodeException, IOException {
		WebClient executableWebClient = getWebClient(url,headers, cookies);
		
		HttpMethod httpMethod = null;
		//抓取的数据的请求方式
		if(HttpRequestMethodEnum.GET.getValue().equalsIgnoreCase(httpMethodStr)){
			httpMethod = HttpMethod.GET;
		}else{
			httpMethod = HttpMethod.POST;
		}
		
		//设置Url、请求包、参数、附加请求头、访问代理等
		WebRequest webRequest = getWebRequest(url, coding, httpMethod, params);
		return executableWebClient.getPage(webRequest);
	}
	
	/**
	 * 将参数，请求头、Cookie等内容设置好后请求页面
	 * 如果是想要一直保存连接的话，则调用getWebClient 和 getWebRequest后续使用同一个WebClient
	 * @author AimSpeed
	 * @param url 访问地址
	 * @param coding 编码，默认为UTF-8
	 * @param httpMethod 请求方式，默认是POST
	 * @param params 请求参数
	 * @param headers 请求头
	 * @param cookies 请求Cookies
	 * @return P 页面
	 * @throws IOException 
	 * @throws FailingHttpStatusCodeException 
	 * @throws MalformedURLException 
	 */
	public <P extends Page> P getPage(String url,String coding, HttpMethod httpMethod, Map<String, String> params,
			Map<String, String> headers, Map<String, String> cookies) throws FailingHttpStatusCodeException, IOException {
		WebClient executableWebClient = getWebClient(url,headers, cookies);
		//设置Url、请求包、参数、附加请求头、访问代理等
		WebRequest webRequest = getWebRequest(url, coding, httpMethod, params);
		return executableWebClient.getPage(webRequest);
	}
	
	/**
	 * 将参数，请求头、Cookie等内容设置进去后
	 * 获取到一个可执行的WebClient
	 * @author AimSpeed
	 * @param url 访问地址
	 * @param coding 编码，默认为UTF-8
	 * @param httpMethod 请求方式，默认是POST
	 * @param params 请求参数
	 * @param headers 请求头
	 * @param cookies 请求Cookies
	 * @return P
	 * @throws MalformedURLException 
	 */
	public WebClient getWebClient(String url,Map<String, String> headers, Map<String, String> cookies) throws MalformedURLException {
		
		//设置浏览器请求模拟的参数的配置（加载SSL、访问代理、CSS加载、JS加载、Cookie等...）
		WebClient webClient = getWebClient();
		
		//设置请求头
		if(null != headers && headers.size() > 0) {
//				webRequest.setAdditionalHeaders(headers);
			
			for (Entry<String, String> header : headers.entrySet()) {
				webClient.addRequestHeader(header.getKey(),header.getValue());
			}
		}
		
		//设置cookie
		if(null != cookies && cookies.size() > 0) {
			
			//cookie管理器
			CookieManager cookieManager = webClient.getCookieManager();
			
			//设置参数值
			for (Entry<String, String> cookie : cookies.entrySet()) {
				cookieManager.addCookie(new Cookie(url,cookie.getKey(),cookie.getValue()));
			}
			
			//设置到请求包中
			webClient.setCookieManager(cookieManager);
		}
		
		//请求页面
		return webClient;
	}
	
	/**
	 * 设置Url、请求包、参数、附加请求头、访问代理等
	 * 获取到一个可执行的WebRequest
	 * @author AimSpeed
	 * @param url 访问地址
	 * @param coding 编码，默认为UTF-8
	 * @param httpMethod 请求方式，默认是POST
	 * @param params 请求参数
	 * @return WebRequest
	 * @throws MalformedURLException WebRequest
	 */
	public WebRequest getWebRequest(String url,String coding, HttpMethod httpMethod, Map<String, String> params) throws MalformedURLException {
		//设置Url、请求包、参数、附加请求头、访问代理等
		WebRequest webRequest = new WebRequest(new URL(url));

		//请求方式，默认为POST
		if(null == httpMethod) {
			httpMethod = HttpMethod.POST;
		}
		webRequest.setHttpMethod(httpMethod);
		
		//默认编码为UTF-8
		if(null == coding || "".equals(coding.trim())) {
			webRequest.setCharset(StandardCharsets.UTF_8);
		}
		
		//设置请求参数
		if(null != params && params.size() > 0) {
			List<NameValuePair> requestParameters = new ArrayList<>();
			
			//设置参数值
			for (Entry<String, String> param : params.entrySet()) {
				requestParameters.add(new NameValuePair(param.getKey(),param.getValue()));
			}
			
			//设置到请求包中
			if(requestParameters.size() > 0) {
				webRequest.setRequestParameters(requestParameters);
			}
		}
		return webRequest;
	}
	
	/**
	 * 获取到Web连接（相当于浏览器窗口）
	 * @author AimSpeed
	 * @return WebClient
	 */
	public WebClient getWebClient() {
		
		//保持同一个会话的情况下，webClient始终使用同一个
		if(isKeepSession && null == this.webClient) {
			this.webClient = new WebClient(new HtmlunitHelper.BrowserVersionUtils().getBrowserVersion());
			//启动客户端重定向  
			this.webClient.getOptions().setRedirectEnabled(webClientConfig.isRedirectEnabled());
			//启用JS解释器，默认为true
			this.webClient.getOptions().setJavaScriptEnabled(webClientConfig.isJavaScriptEnabled());    
			//禁用css支持
			this.webClient.getOptions().setCssEnabled(webClientConfig.isCssEnabled());     
			//js运行错误时，是否抛出异常
			this.webClient.getOptions().setThrowExceptionOnScriptError(webClientConfig.isThrowExceptionOnScriptError());   
			this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(webClientConfig.isThrowExceptionOnFailingStatusCode());
			//设置连接超时时间 10秒
			this.webClient.getOptions().setTimeout(webClientConfig.getTimeout());
			//等待js后台执行15秒      
			this.webClient.waitForBackgroundJavaScript(webClientConfig.getWaitForBackgroundJavaScript());
			return webClient;
		
		} else { //不保持同一个会话，则创建一个新的浏览器
			//HtmlUnit模拟浏览器
			webClient = new WebClient(new HtmlunitHelper.BrowserVersionUtils().getBrowserVersion());
			//启动客户端重定向  
			this.webClient.getOptions().setRedirectEnabled(webClientConfig.isRedirectEnabled());
			//启用JS解释器，默认为true
			this.webClient.getOptions().setJavaScriptEnabled(webClientConfig.isJavaScriptEnabled());    
			//禁用css支持
			this.webClient.getOptions().setCssEnabled(webClientConfig.isCssEnabled());     
			//js运行错误时，是否抛出异常
			this.webClient.getOptions().setThrowExceptionOnScriptError(webClientConfig.isThrowExceptionOnScriptError());   
			this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(webClientConfig.isThrowExceptionOnFailingStatusCode());
			//设置连接超时时间 10秒
			this.webClient.getOptions().setTimeout(webClientConfig.getTimeout());
			//等待js后台执行15秒      
			this.webClient.waitForBackgroundJavaScript(webClientConfig.getWaitForBackgroundJavaScript());
			return webClient;
		}
	}
	

	/**
	 * 浏览器版本
	 * @author AimSpeed
	 */
	static final class BrowserVersionUtils {
		
		/**
		 * 单例对象
		 */
		static final HtmlunitHelper HTMLUNIT_HELPER = new HtmlunitHelper();
		
		/**
		 * 浏览器版本
		 */
		BrowserVersion[] browserVersions = new BrowserVersion[]{BrowserVersion.BEST_SUPPORTED,
																BrowserVersion.CHROME,
																BrowserVersion.EDGE,
																BrowserVersion.FIREFOX_45,
																BrowserVersion.FIREFOX_52,
																BrowserVersion.INTERNET_EXPLORER};
        
		/**
		 * 随机获取浏览器版本
		 * @author AimSpeed
		 * @return BrowserVersion
		 */
		public BrowserVersion getBrowserVersion() {
			//随机生成
	        Integer ranDomNo = new Double(browserVersions.length * Math.random()).intValue();
	        return browserVersions[ranDomNo];
		}
		
	}
	
	/*
	 * 在对象销毁前，先关闭连接
	 * @author AimSpeed
	 * @throws Throwable
	 * @overridden @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
//		page.cleanUp();
		webClient.close();
		super.finalize();
	}
	
}
