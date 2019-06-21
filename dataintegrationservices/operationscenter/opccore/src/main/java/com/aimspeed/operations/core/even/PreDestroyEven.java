package com.aimspeed.operations.core.even;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author AimSpeed
 */
@Component
public class PreDestroyEven {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(SecurityDataRunnerInitEven.class);
	
	/*@Autowired
	private SystemApiService systemApiService;
	
	@Autowired
	private RedisRepository redisRepository;
	
	
	
	@PreDestroy
    public void destory() {  
		logger.info("==================== 容器销毁前操作 ====================");
		
		String sequenceCode = PropertiesUtils.getProperty(CommonPropertiesConstant.API_FILE_PATH, CommonPropertiesConstant.OPERATION_TO_SSO_SEQUENCE_CODE);
		
		//重置数据
		SSOApiBaseParamVo ssoApiBaseParamVo = redisRepository.get(sequenceCode, SSOApiBaseParamVo.class);
		systemApiService.resetPriKey(ssoApiBaseParamVo.getParams());
		
		//删除缓存的单点系统数据
  		redisRepository.del(sequenceCode);
  		
    }*/
	
	
	
	
}
