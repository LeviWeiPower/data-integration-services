package com.aimspeed.gatherer.service.rule;

import com.aimspeed.gatherer.entity.bean.mysql.rule.PagingIndexMySqlBean;
import com.aimspeed.gatherer.entity.vo.result.ResultVo;
import com.aimspeed.mysql.BaseMySqlService;

/**
 * 翻页位置
 * @author AimSpeed
 */
public interface PagingIndexService extends BaseMySqlService<PagingIndexMySqlBean> {
	
	/**
	 * 根据模板页面获取下一页的规则
	 * @author AimSpeed
	 * @Title getNextPageRule 
	 * @param loginAccount
	 * @param sequence
	 * @param templateUrl
	 * @return ResultVo 
	 * @date 2018年3月27日
	 */
	ResultVo getNextPageRule(String loginAccount,String sequence,String templateUrl);


}