package com.aimspeed.gatherer.service.rule;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aimspeed.common.enums.HttpResponseCurdEnum;
import com.aimspeed.common.enums.HttpResponseEnum;
import com.aimspeed.gatherer.entity.bean.mysql.rule.CrawlerMySqlBean;
import com.aimspeed.gatherer.entity.bean.mysql.rule.PagingIndexMySqlBean;
import com.aimspeed.gatherer.entity.vo.result.ResultVo;
import com.aimspeed.gatherer.service.httpclient.HttpClientResult;
import com.aimspeed.mysql.BaseMySqlServiceImpl;
import com.alibaba.fastjson.JSON;

/** 
 * @author AimSpeed
 */
@Service
public class PagingIndexServiceImpl extends BaseMySqlServiceImpl<PagingIndexMySqlBean> implements PagingIndexService {

	@Autowired
	private CrawlerService crawlerBeanService; 

	/*
	 * 根据模板页面获取下一页的规则
	 * @author AimSpeed
	 * @Title getNextPageRule 
	 * @param loginAccount
	 * @param sequence
	 * @param templateUrl
	 * @return
	 * @overridden @see com.aimspeed.gatherer.service.rule.PagingIndexBeanService#getNextPageRule(java.lang.String, java.lang.String, java.lang.String)
	 * @date 2018年3月27日
	 */
    @Override
	public ResultVo getNextPageRule(String loginAccount,String sequence,String templateUrl) {
		
        HttpClientResult responseClient = crawlerBeanService.crawlerRuleToRequest(sequence, templateUrl);
        if(null != responseClient) {
        	
        	//查询配置信息
    		CrawlerMySqlBean crawlerBean = new CrawlerMySqlBean();
    		crawlerBean.setSequence(sequence);
    		crawlerBean = crawlerBeanService.selectOnlyOfSelective(crawlerBean);
    		
        	
            String responseBody = responseClient.getResponseBody();
            if(null != responseBody) {
                Document doc = Jsoup.parse(responseBody);
                Elements elements = doc.getElementsByTag("a");
                for (Element element : elements) {
                    if("下一页".equals(element.text())){
                    	String className = element.attr("class");
                        String idName = element.attr("id");
                        String tagName = element.tagName();
                        
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("tag", tagName);
                        if(null != className && null != className.trim() && className.trim().length() > 0) {
                        	map.put("class", className);
                        }
                        if(null != idName && null != idName.trim() && idName.trim().length() > 0) {
                        	map.put("id", idName);
                        }
                    	
                        PagingIndexMySqlBean pagingIndexBean = new PagingIndexMySqlBean();
                        pagingIndexBean.setSequence(crawlerBean.getSequence());
                        pagingIndexBean.setRule(JSON.toJSONString(map));
                        pagingIndexBean.setCreator(loginAccount);
                        pagingIndexBean.setUpdator(loginAccount);
                        this.insertSelective(pagingIndexBean);
                    	break;
                    }
                }
            }
        }
        return new ResultVo(HttpResponseEnum.SUCCESS.getCode(), HttpResponseCurdEnum.SAVE_SUCCESS.getValue());
	}
    
    
}
