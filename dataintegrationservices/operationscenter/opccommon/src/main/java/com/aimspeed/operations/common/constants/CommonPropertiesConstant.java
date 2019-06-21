package com.aimspeed.operations.common.constants;

/**
 * 公共的Properties文件的常量
 * @author AimSpeed
 */
public abstract class CommonPropertiesConstant {
	
	/**
	 * Api资源文件路径
	 * 
	 */
	public static final String API_FILE_PATH = "api";

	/**
	 * 常量值资源文件路径
	 * 
	 */
	public static final String COMMON_FILE_PATH = "common";
	
	/**
	 * 不需要登录就通过拦截器的url
	 */
	public static final String LOGIN_PASS_URL = "login.pass.url";
	
	//==========================  SSO  ==========================
	/**
	 * 运营系统位于单点登录系统的的序列码
	 */
	public static final String OPERATION_TO_SSO_SEQUENCE_CODE = "operation.to.sso.sequenceCode";
	
	/**
	 * 运营系统位于单点登录系统的pubKey
	 */
	public static final String OPERATION_TO_SSO_PUBKEY = "operation.to.sso.pubKey";

	//==========================  图片路径  ==========================
	/**
	 * 财务图片路径
	 */
	public static final String FINACE_PICTURE_PATH = "finace.picture.path";
	
	
}
