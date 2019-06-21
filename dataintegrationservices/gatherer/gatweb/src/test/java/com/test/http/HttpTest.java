package com.test.http;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aimspeed.common.file.FileUtils;
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
	
	/**
	 * 测试数据抓取，添加
	 * @author AimSpeed
	 * @throws InterruptedException void
	 */
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

	/**
	 * 测试图片下载
	 * @author AimSpeed
	 * @throws InterruptedException void
	 * @throws IOException 
	 */
	@Test
	public void downloadImg() throws InterruptedException, IOException {
		byte[] imgInputStream = httpClientService.getImgByte("https://himg.bdimg.com/sys/portrait/item/2f5be4bbbbe680a7e58fabe585bd6758");
		System.out.println(imgInputStream);
		
		FileOutputStream fos = new FileOutputStream("E:\\Test\\xxx.png");
		fos.write(imgInputStream);
		fos.close();
		
//		FileUtils.writeFileFromIS(new File("E:\\Test\\xxx.png"), imgInputStream, false);
		
//		writeByteStream(FileUtils.streamToByte(imgInputStream));
		
	}
	
	
}
