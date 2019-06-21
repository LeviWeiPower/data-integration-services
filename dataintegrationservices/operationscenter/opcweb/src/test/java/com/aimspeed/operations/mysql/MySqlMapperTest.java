package com.aimspeed.operations.mysql;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.aimspeed.common.enums.IsDeleteEnum;
import com.aimspeed.operations.BaseTest;
import com.aimspeed.operations.api.gatherer.rule.CrawlerApi;
import com.aimspeed.operations.entity.bean.mysql.user.UserMySqlBean;
import com.aimspeed.operations.repository.mysql.user.UserMySqlBeanMapper;
import com.aimspeed.operations.service.user.UserService;

/**
 * MySQL测试用例
 * @author AimSpeed
 */
@Transactional //是否开启事务回滚
public class MySqlMapperTest extends BaseTest {
	
	@Autowired
	private UserMySqlBeanMapper userBeanMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private CrawlerApi crawlerApi;

	//=============================== 添加 ===============================
	@Test
	public void test() {
		System.err.println("======================================");
		System.err.println("======================================");
		System.err.println("======================================");
		System.err.println("======================================");
		System.err.println("======================================");
		System.err.println("======================================");
		System.out.println(crawlerApi.sum(1));
//		crawlerApi.runOfChangeStatus(null,null,1, "1");
	}
	
	/**
	 * 单个插入
	 * @author AimSpeed
	 * @param record
	 * @return boolean  
	 */
	@Test
	public void insert() {
		UserMySqlBean userBean = new UserMySqlBean();
		userBean.setId(65480780);
		userBean.setAccount("test");
		userBean.setPassword("test");
		userBean.setCreator("test");
		userBean.setUpdator("test");
		userBean.setIsDelete(IsDeleteEnum.Y.getValue());
//		userBeanMapper.insertSelective(userBean);
		userService.insertSelective(userBean);
	}

	//=============================== 更新 ===============================
	
	/**
	 * 根据Id单个更新，Id不存在则不更新
	 * @author AimSpeed
	 * @param record void  
	 */
	@Test
	public void updateById() {
		UserMySqlBean userBean = new UserMySqlBean();
//		userBean.setId(16);
//		userBean.setAccount("testUpdate");
//		userBeanMapper.updateSelectiveOfId(userBean);
		userBean.setIsDelete(IsDeleteEnum.N.getValue());
		
		List<Integer> ids = new ArrayList<>();
		ids.add(65480780);
		userService.batchUpdateOfIds(userBean, ids);
	}
	
	//=============================== 查找 ===============================
	/**
	 * 根据ID进行获取并转换为对应的对象数据
	 * @author AimSpeed
	 * @param id
	 * @return SolrDocument  
	 */
	@Test
	public void getById(){
		System.out.println(userBeanMapper.selectOfId(18).getAccount());
	}
	
    
	
}
