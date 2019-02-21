package com.test.http;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aimspeed.gatherer.entity.bean.mongo.content.ContentDataMongoBean;
import com.aimspeed.gatherer.service.httpclient.HttpClientService;
import com.test.BaseTest;

/**
 * 内容数据测试
 * @author AimSpeed
 */
public class HttpTest extends BaseTest{
	
	@Autowired
   	private HttpClientService httpClientService;

	@Test
	public void insertTest() throws InterruptedException {
//		List<ContentDataMongoBean> contentDataBeans1 = new ArrayList<ContentDataMongoBean>();
		ContentDataMongoBean contentDataMongoBean = new ContentDataMongoBean();
		contentDataMongoBean.setCrawlerName("中国建材");
//		System.out.println("------" + contentDataBeanMongoMapper.query(ContentDataMongoBean.class, contentDataMongoBean));
		
		/*content.put("name", "contentName");
		content.put("系统设置", 6.66);
		content.put("用户管理", 6.66);
		
		contentDataMongoBean.setContent(content);
		contentDataMongoBean.setIsDelete("setIsDelete");
		contentDataMongoBean.setCreateTime(new Date());
		contentDataMongoBean.setUpdator("6setUpdator66");
		contentDataBeans1.add(contentDataMongoBean);
		contentDataBeanMongoMapper.insert(contentDataBeans1);*/
		
	}

	
	
	
	
}
