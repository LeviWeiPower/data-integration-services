package com.test.http;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.protocol.HttpClientContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.aimspeed.gatherer.service.httpclient.HttpClientResult;
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
public class HtmlunitTest {
	
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        
		// 屏蔽HtmlUnit等系统 log
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
 
        String url = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=2&tn=baiduhome_pg&wd=jsoup%20maen&rsv_spt=1&oq=jsoup&rsv_pq=9cef6f6700028983&rsv_t=23c8aXNm9qfoI9bZcmFgJUv4B7HFrqk1Mu0CfxPAd9e4P%2FyQb4Sn6%2FIIwMMtlbVpey2o&rqlang=cn&rsv_enter=1&inputT=1077&rsv_sug3=86&rsv_sug1=63&rsv_sug7=100&rsv_sug2=0&rsv_sug4=1300";
        System.out.println("Loading page now-----------------------------------------------: "+url);
        
        // HtmlUnit 模拟浏览器
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //启动客户端重定向  
        webClient.getOptions().setRedirectEnabled(true);
        //启用JS解释器，默认为true
        webClient.getOptions().setJavaScriptEnabled(true);    
        //禁用css支持
        webClient.getOptions().setCssEnabled(false);     
        //js运行错误时，是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);   
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        //设置连接超时时间
        webClient.getOptions().setTimeout(10 * 1000);   
        
        //等待js后台执行10秒      
        webClient.waitForBackgroundJavaScript(10 * 1000);         
        
        //设置请求包
        WebRequest webRequest = new WebRequest(new URL(url));
        
        //设置请求参数
//        webRequest.setRequestParameters(requestParameters);
        
        //设置请求头
//        webRequest.setAdditionalHeaders(additionalHeaders);
        
        //设置代理服务器
//        webRequest.setProxyHost();
//        webRequest.setProxyPort();
//        webRequest.setSocksProxy(true);
        
        //设置请求body的数据
//        webRequest.setRequestBody();
        
        //设置请求方式
        webRequest.setHttpMethod(HttpMethod.GET);
        
        HtmlPage page = webClient.getPage(url);

//        HtmlPage page = webClient.getPage(webRequest);
        
        String pageAsXml = page.asXml();
        System.out.println(pageAsXml);
        
        // Jsoup解析处理
        Document doc = Jsoup.parse(pageAsXml);  
        Elements pngs = doc.select("img[src$=.png]");                   // 获取所有图片元素集
        
        // 此处省略其他操作
        System.out.println(pngs.size());
    }
	
	
}
