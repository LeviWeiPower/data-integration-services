package com.test.content;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aimspeed.gatherer.entity.bean.mongo.content.ContentDataMongoBean;
import com.aimspeed.gatherer.repository.mongo.content.ContentDataMongoBeanMapper;
import com.test.BaseTest;

/**
 * 内容数据测试
 * @author AimSpeed
 */
public class ContentDataTest extends BaseTest{
	
	@Autowired
	private ContentDataMongoBeanMapper contentDataBeanMongoMapper;
	
	@Test
	public void insertTest() throws InterruptedException {
//		List<ContentDataMongoBean> contentDataBeans1 = new ArrayList<ContentDataMongoBean>();
		ContentDataMongoBean contentDataMongoBean = new ContentDataMongoBean();
		contentDataMongoBean.setCrawlerName("中国建材");
		
		System.out.println(contentDataBeanMongoMapper.exists(contentDataMongoBean, ContentDataMongoBean.class));
		System.out.println("------" + contentDataBeanMongoMapper.query(contentDataMongoBean,ContentDataMongoBean.class));
		
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

	@Test
	public void selectTest() {
		Optional<ContentDataMongoBean> contentDataMongoBean = contentDataBeanMongoMapper.findById("5b72539e2c46b00f684b2850");
		System.out.println(contentDataMongoBean.get().getContent());
		System.out.println(contentDataMongoBean.get().getContentUrl());
	}
	
	
	
	
}
