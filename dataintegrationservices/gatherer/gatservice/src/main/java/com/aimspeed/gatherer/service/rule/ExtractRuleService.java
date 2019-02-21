package com.aimspeed.gatherer.service.rule;

import com.aimspeed.gatherer.entity.bean.mysql.rule.ExtractRuleMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.user.UserMySqlBean;
import com.aimspeed.gatherer.entity.vo.result.ResultVo;
import com.aimspeed.gatherer.entity.vo.rule.ExtractRuleVo;
import com.aimspeed.mysql.BaseMySqlService;

/**
 * 提取过的Url
 * @author AimSpeed
 */
public interface ExtractRuleService extends BaseMySqlService<ExtractRuleMySqlBean> {

	/**
	 * 获取到添加提取规则的模板HTML源码
	 * @author AimSpeed
	 * @Title getTemplatePageHtml 
	 * @param crawlerId
	 * @param templateUrl
	 * @return ResultVo 
	 * @date 2018年3月27日
	 */
	ResultVo getTemplatePageHtml(String  sequence,String templateUrl);

	/**
	 * 保存配置的提取规则
	 * @author AimSpeed
	 * @Title saveExtractRule 
	 * @param sysUserBean
	 * @param extractRuleVo
	 * @return ResultVo 
	 * @date 2018年3月27日
	 */
	ResultVo saveExtractRule(UserMySqlBean sysUserBean,ExtractRuleVo extractRuleVo);
}
