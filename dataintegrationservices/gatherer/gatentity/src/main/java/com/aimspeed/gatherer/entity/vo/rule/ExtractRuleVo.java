package com.aimspeed.gatherer.entity.vo.rule;

import java.util.List;

import com.aimspeed.gatherer.entity.bean.mysql.rule.CommonRuleMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.rule.ExtractRuleMySqlBean;

/**
 * 提取规则数据传输对象
 * @author AimSpeed
 */
public class ExtractRuleVo {
	
	private List<ExtractRuleMySqlBean> extractRules;

	private CommonRuleMySqlBean ruleCommon;
	
	private String templateUrl;
	
	public List<ExtractRuleMySqlBean> getExtractRules() {
		return extractRules;
	}

	public void setExtractRules(List<ExtractRuleMySqlBean> extractRules) {
		this.extractRules = extractRules;
	}

	public CommonRuleMySqlBean getRuleCommon() {
		return ruleCommon;
	}

	public void setRuleCommon(CommonRuleMySqlBean ruleCommon) {
		this.ruleCommon = ruleCommon;
	}

	public String getTemplateUrl() {
		return templateUrl;
	}

	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}
	
	
	
	
}
