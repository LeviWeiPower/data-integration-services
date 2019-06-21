package com.aimspeed.operations.core.even;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 单点登录系统在系统运行的时候初始化
 * 
 * 应用服务启动时，加载一些数据和执行一些应用的初始化动作。如：删除临时文件，清除缓存信息，读取配置文件信息，数据库连接等。
 * 
 * 1、SpringBoot提供了CommandLineRunner接口。当有该接口多个实现类时，只适合基础的数据初始化，提供了@order注解实现自定义执行顺序，也可以实现Ordered接口来自定义顺序。
 * 注意：数字越小，优先级越高，也就是@Order(1)注解的类会在@Order(2)注解的类之前执行。
 * 
 * 2、SpringBoot提供的ApplicationRunner接口也可以满足该业务场景。
 * 	    不同点：ApplicationRunner中run方法的参数为ApplicationArguments， 而CommandLineRunner接口中run方法的参数为String数组。
 *    想要更详细地获取命令行参数，那就使用ApplicationRunner接口
 * 3、如果使用了Spring框架，则使用ApplicationListener接口也可以满足该业务场景。
 * 
 * 4、Spring内置的事件
 *	ContextRefreshedEvent：ApplicationContext容器初始化或者刷新时触发该事件。 
 *	ContextStartedEvent: 当使用ConfigurableApplicationContext接口的start()方法启动ApplicationContext容器时触发该事件。
 *	ContextClosedEvent: 当使用ConfigurableApplicationContext接口的close()方法关闭ApplicationContext容器时触发该事件。
 *	ContextStopedEvent: 当使用ConfigurableApplicationContext接口的stop()方法停止ApplicationContext容器时触发该事件。
 * 
 * @author AimSpeed
 */
@Component
@Order(value=1)
public class SecurityDataRunnerInitEven implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/*private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SystemApiService systemApiService;
	
	@Autowired
	private RedisRepository redisRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("==================== 初始化数据 ====================");
		
		//缓存单点登录系统数据
		SSOApiBaseParamVo ssoApiBaseParamVo = buildSSOApiBaseParam();
		
		boolean setFlag = redisRepository.set(ssoApiBaseParamVo.getSourceSequenceCode(), buildSSOApiBaseParam());
		if(!setFlag) {
			logger.info("初始化数据时，缓存单点系统数据失败!");
		}else {
			logger.info("初始化单点系统数据成功!");
			
			
		}
		
	}
	
	*//**
     * 组装SSO基础参数数据
     * @author AimSpeed
     * @return SSOApiBaseParamVo  
     *//*
    public SSOApiBaseParamVo buildSSOApiBaseParam() {
    	//序列码
  		String sequenceCode = PropertiesUtils.getProperty(CommonPropertiesConstant.API_FILE_PATH, CommonPropertiesConstant.OPERATION_TO_SSO_SEQUENCE_CODE);
  		
  		//公共Key
  		String pubKey = PropertiesUtils.getProperty(CommonPropertiesConstant.API_FILE_PATH, CommonPropertiesConstant.OPERATION_TO_SSO_PUBKEY);
  		
  		//初始化单点登录系统的序列码
  		String mergeSequenceCode = sequenceCode + pubKey;
  		//加密
  		try {
  			mergeSequenceCode = new MD5Arithmetic().encrypt32bit(mergeSequenceCode);
  			mergeSequenceCode = new MD5Arithmetic().encrypt16bit(mergeSequenceCode);
  		} catch (NoSuchAlgorithmException e) {
  			e.printStackTrace();
  		}
  		
  		Map<String, Object> params = new HashMap<>();
  		params.put("pubKey", pubKey);
  		params.put("sequenceCode", mergeSequenceCode);
  		params.put("priKey", null);
        
  		SSOApiBaseParamVo ssoApiBaseParamVo = new SSOApiBaseParamVo();
  		ssoApiBaseParamVo.setSequenceCode(mergeSequenceCode);
  		ssoApiBaseParamVo.setPubKey(pubKey);
  		ssoApiBaseParamVo.setSourceSequenceCode(sequenceCode);
  		ssoApiBaseParamVo.setParams(params);
  		return ssoApiBaseParamVo;
    	
    }
	
    public static void main(String[] args) {
		String temp = "700370b3aab82d50e45893d0e3a320a8" + "00d003c6fd40b8ac3acbf6773a2aca0e";
		//加密
  		try {
  			temp = new MD5Arithmetic().encrypt32bit(temp);
  			temp = new MD5Arithmetic().encrypt16bit(temp);
  		} catch (NoSuchAlgorithmException e) {
  			e.printStackTrace();
  		}
  		System.out.println(temp);
  		
  		
  		//序列码
  		String sequenceCode = PropertiesUtils.getProperty(CommonPropertiesConstant.API_FILE_PATH, CommonPropertiesConstant.OPERATION_TO_SSO_SEQUENCE_CODE);
  		
  		//公共Key
  		String pubKey = PropertiesUtils.getProperty(CommonPropertiesConstant.API_FILE_PATH, CommonPropertiesConstant.OPERATION_TO_SSO_PUBKEY);
  		
  		System.out.println(sequenceCode);
  		System.out.println(pubKey);
  		
  		//初始化单点登录系统的序列码
  		String mergeSequenceCode = sequenceCode + pubKey;
  		//加密
  		try {
  			mergeSequenceCode = new MD5Arithmetic().encrypt32bit(mergeSequenceCode);
  			mergeSequenceCode = new MD5Arithmetic().encrypt16bit(mergeSequenceCode);
  		} catch (NoSuchAlgorithmException e) {
  			e.printStackTrace();
  		}
  		System.out.println(mergeSequenceCode);
	}*/
	
}
