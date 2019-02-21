package com.aimspeed.gatherer.service.action;

import com.aimspeed.gatherer.entity.vo.result.ResultVo;

/**
 * 数据采集者行动
 * @author AimSpeed
 */
public interface GathererActionService {
	
	/**
	 * 按照Id开始采集者的数据采集
	 * @author AimSpeed
	 * @Title startCrawlingData 
	 * @param loginAccount
	 * @param crawlerId
	 * @return ResultVo 
	 * @date 2018年3月27日
	 */
	ResultVo startCrawlingData(String loginAccount,Integer crawlerId);
	
}
