package com.aimspeed.gatherer.common.htmlunit;

/**
 * Web Client 模拟浏览器的配置
 * @author AimSpeed
 */
public class WebClientConfig {
	
	/**
	 * 启动客户端重定向 ，默认为true
	 */
	private boolean redirectEnabled = true;
	
	/**
	 * 启用JS解释器，默认为true
	 */
	private boolean javaScriptEnabled = true;
	
	/**
	 * 禁用css支持，默认为false
	 */
	private boolean cssEnabled = false;
	
	/**
	 * js运行错误时，是否抛出异常，默认为
	 */
	private boolean throwExceptionOnScriptError = false;
	
	/**
	 * js运行错误时，是否抛出异常，默认为
	 */
	private boolean throwExceptionOnFailingStatusCode = false;
	
	/**
	 * 设置连接超时时间，默认为10秒
	 */
	private int timeout = 10 * 1000;
	
	/**
	 * 等待js后台，默认为15秒
	 */
	private int waitForBackgroundJavaScript = 15 * 1000;

	public WebClientConfig() {
		super();
	}

	public WebClientConfig(boolean redirectEnabled, boolean javaScriptEnabled, boolean cssEnabled,
			boolean throwExceptionOnScriptError, boolean throwExceptionOnFailingStatusCode, int timeout,
			int waitForBackgroundJavaScript) {
		super();
		this.redirectEnabled = redirectEnabled;
		this.javaScriptEnabled = javaScriptEnabled;
		this.cssEnabled = cssEnabled;
		this.throwExceptionOnScriptError = throwExceptionOnScriptError;
		this.throwExceptionOnFailingStatusCode = throwExceptionOnFailingStatusCode;
		this.timeout = timeout;
		this.waitForBackgroundJavaScript = waitForBackgroundJavaScript;
	}

	public boolean isRedirectEnabled() {
		return redirectEnabled;
	}

	public void setRedirectEnabled(boolean redirectEnabled) {
		this.redirectEnabled = redirectEnabled;
	}

	public boolean isJavaScriptEnabled() {
		return javaScriptEnabled;
	}

	public void setJavaScriptEnabled(boolean javaScriptEnabled) {
		this.javaScriptEnabled = javaScriptEnabled;
	}

	public boolean isCssEnabled() {
		return cssEnabled;
	}

	public void setCssEnabled(boolean cssEnabled) {
		this.cssEnabled = cssEnabled;
	}

	public boolean isThrowExceptionOnScriptError() {
		return throwExceptionOnScriptError;
	}

	public void setThrowExceptionOnScriptError(boolean throwExceptionOnScriptError) {
		this.throwExceptionOnScriptError = throwExceptionOnScriptError;
	}

	public boolean isThrowExceptionOnFailingStatusCode() {
		return throwExceptionOnFailingStatusCode;
	}

	public void setThrowExceptionOnFailingStatusCode(boolean throwExceptionOnFailingStatusCode) {
		this.throwExceptionOnFailingStatusCode = throwExceptionOnFailingStatusCode;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getWaitForBackgroundJavaScript() {
		return waitForBackgroundJavaScript;
	}

	public void setWaitForBackgroundJavaScript(int waitForBackgroundJavaScript) {
		this.waitForBackgroundJavaScript = waitForBackgroundJavaScript;
	}

	
}

