package com.aimspeed.gatherer.service.action;

import com.aimspeed.common.vo.ResultVo;

/**
 * 数据采集者行动
 * @author AimSpeed
 */
public interface GathererActionService {
	
	/**
	 * 按照Id开始采集者的数据采集
	 * @author AimSpeed
	 * @param loginAccount
	 * @param crawlerId
	 * @return ResultVo 
	 */
	ResultVo startCrawlingData(String loginAccount,Integer crawlerId);
	
}
