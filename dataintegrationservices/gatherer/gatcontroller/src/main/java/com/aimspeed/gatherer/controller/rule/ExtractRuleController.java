package com.aimspeed.gatherer.controller.rule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aimspeed.gatherer.controller.BaseController;
import com.aimspeed.gatherer.entity.bean.mysql.rule.ExtractRuleMySqlBean;
import com.aimspeed.gatherer.entity.vo.result.ResultVo;
import com.aimspeed.gatherer.entity.vo.rule.ExtractRuleVo;
import com.aimspeed.gatherer.service.rule.ExtractRuleService;

/**
 * 提取规则
 * @author AimSpeed
 */
@Controller()
@RequestMapping("/extract")
public class ExtractRuleController extends BaseController<ExtractRuleMySqlBean>{


	public ExtractRuleController() {
		super("");
	}

	private final Logger log = LoggerFactory.getLogger(ExtractRuleController.class);
	
	@Autowired
	private ExtractRuleService extractRuleService;

	/**
	 * 跳转到信息采集页面
	 * @author AimSpeed
	 * @Title addBaseRuleView 
	 * @param request
	 * @param response
	 * @return String 
	 * @date 2018年3月27日
	 */
	@RequestMapping(value="/addExtractPage",method = {RequestMethod.GET,RequestMethod.POST})
	public String addBaseRuleView(HttpServletRequest request,HttpServletResponse response){
		return "/rule/extract_rule";
	}
	
	/**
	 * 根据url获取到html
	 * @author AimSpeed
	 * @Title getTemplatePageHtml 
	 * @param request
	 * @param response
	 * @param sequence
	 * @param templateUrl
	 * @return ResultVo 
	 * @date 2018年3月27日
	 */
	@RequestMapping(value="/getTemplatePageHtml",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultVo getTemplatePageHtml(HttpServletRequest request,HttpServletResponse response,
											String  sequence,String templateUrl){
		ResultVo resultVo = extractRuleService.getTemplatePageHtml(sequence,templateUrl);
		return resultVo; 
		
	}
	
	/**
	 * 保存配置的提取规则
	 * @author AimSpeed
	 * @Title saveExtractRule 
	 * @param request
	 * @param response
	 * @param extractRuleVo
	 * @return ResultVo 
	 * @date 2018年3月27日
	 */
	@ResponseBody
	@RequestMapping(value = "/saveExtractRule", method = RequestMethod.POST)
	public ResultVo saveExtractRule(HttpServletRequest request,HttpServletResponse response,ExtractRuleVo extractRuleVo) {
		ResultVo resultVo = extractRuleService.saveExtractRule(getCurrentUser(request),extractRuleVo);
		return resultVo;
	}
	

	
	
}
