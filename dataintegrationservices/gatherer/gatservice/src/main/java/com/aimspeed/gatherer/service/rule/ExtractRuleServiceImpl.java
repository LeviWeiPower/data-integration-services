package com.aimspeed.gatherer.service.rule;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aimspeed.common.enums.HttpResponseCurdEnum;
import com.aimspeed.common.enums.HttpResponseEnum;
import com.aimspeed.gatherer.entity.bean.mysql.rule.CommonRuleMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.rule.ExtractRuleMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.user.UserMySqlBean;
import com.aimspeed.gatherer.entity.vo.result.ResultVo;
import com.aimspeed.gatherer.entity.vo.rule.ExtractRuleVo;
import com.aimspeed.gatherer.repository.mysql.rule.CommonRuleMySqlBeanMapper;
import com.aimspeed.gatherer.repository.mysql.rule.ExtractRuleMySqlBeanMapper;
import com.aimspeed.gatherer.service.httpclient.HttpClientResult;
import com.aimspeed.mysql.BaseMySqlServiceImpl;

/** 
 * @author AimSpeed
 */
@Service
public class ExtractRuleServiceImpl extends BaseMySqlServiceImpl<ExtractRuleMySqlBean> implements ExtractRuleService {

	private final Logger log = LoggerFactory.getLogger(ExtractRuleServiceImpl.class);
	
	@Autowired
	private ExtractRuleMySqlBeanMapper extractRuleMapper; 
	
	@Autowired
	private CrawlerService crawlerBeanService; 

    @Autowired
    private CommonRuleMySqlBeanMapper ruleCommonDivMapper;
    
    @Autowired
    private PagingIndexService pagingIndexBeanService;
    
    /*
     * 获取到添加提取规则的模板HTML源码
     * @author AimSpeed
     * @Title getTemplatePageHtml 
     * @param crawlerId
     * @param templateUrl
     * @return
     * @overridden @see com.aimspeed.gatherer.service.rule.ExtractRuleBeanService#getTemplatePageHtml(java.lang.Integer, java.lang.String)
     * @date 2018年3月27日
     */
	@Override
	public ResultVo getTemplatePageHtml(String  sequence,String templateUrl) {
		//根据请求的信息，获取到页面信息
		HttpClientResult result = crawlerBeanService.crawlerRuleToRequest(sequence, templateUrl,true,false,true);
		
		//处理获取到的信息
		try {
			String responseBody = result.getResponseBody();
			Document doc = Jsoup.parse(responseBody);
			//将script、style、link的索引标签给删除
			doc.select("script").remove();
			doc.select("style").remove();
			doc.select("link").remove();
			doc.outputSettings().prettyPrint(true);// 是否格式化
			
			Elements allElements = doc.getAllElements();
			
			//定义不添加事件的标签
			List<String> tags = new ArrayList<String>();
			tags.add("html");
			tags.add("#root");
			tags.add("body");
			tags.add("br");
			
			for (Element element : allElements) {
				String tagName = element.tagName();
				//只要不是定义的不添加事件的标签，都跳过
				if(!tags.contains(tagName)){
					
					//添加获取内容的标签
					element.attr("onclick","extract_rule.getTagRule(this,event)");
					
					//将a标签的链接给清理掉，避免出现页面多开的情况
					if("a".equalsIgnoreCase(tagName)) {
						element.removeAttr("href");
						element.attr("href","javascript:void(0);");
					}
					//清理input，submit的类型标签，避免报错
					if("input".equalsIgnoreCase(tagName)) {
						if("submit".equalsIgnoreCase(element.attr("type"))) {
							element.removeAttr("type");
							element.attr("type","button");
						}
						
					}
				}
			}
			
			return new ResultVo(HttpResponseEnum.SUCCESS.getCode(), HttpResponseCurdEnum.SAVE_SUCCESS	.getValue(),doc.html());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return new ResultVo(HttpResponseEnum.FAIL.getCode(), HttpResponseEnum.FAIL.getValue());
	}

	/*
	 * 保存配置的提取规则
	 * @author AimSpeed
	 * @Title saveExtractRule 
	 * @param sysUserBean
	 * @param extractRuleVo
	 * @return
	 * @overridden @see com.aimspeed.gatherer.service.rule.ExtractRuleBeanService#saveExtractRule(com.aimspeed.gatherer.entity.bean.system.SysUserBean, com.aimspeed.gatherer.entity.vo.rule.ExtractRuleVo)
	 * @date 2018年3月27日
	 */
	@Override
	public ResultVo saveExtractRule(UserMySqlBean sysUserBean,ExtractRuleVo extractRuleVo) {
		List<ExtractRuleMySqlBean> extractRules = extractRuleVo.getExtractRules();
    	
    	//保存提取规则数据
    	if(null != extractRules && extractRules.size() > 0) {
    		for (ExtractRuleMySqlBean extractRule : extractRules) {
    			extractRule.setCreator(sysUserBean.getAccount());
    			extractRule.setUpdator(sysUserBean.getAccount());
    			extractRuleMapper.insertSelective(extractRule);
			}
    	}
    	
    	//保存公共标签数据
    	CommonRuleMySqlBean ruleCommon = extractRuleVo.getRuleCommon();
    	if(null != ruleCommon) {
    		ruleCommon.setCreator(sysUserBean.getAccount());
        	ruleCommon.setUpdator(sysUserBean.getAccount());
        	ruleCommonDivMapper.insertSelective(ruleCommon);
    	}
    	//自动判断是否有下一页有则获取到下一页的规则
    	pagingIndexBeanService.getNextPageRule(sysUserBean.getAccount(),extractRuleVo.getRuleCommon().getSequence(), extractRuleVo.getTemplateUrl());
    	return new ResultVo(HttpResponseEnum.SUCCESS.getCode(), HttpResponseCurdEnum.SAVE_SUCCESS.getValue());
	}
    
}
