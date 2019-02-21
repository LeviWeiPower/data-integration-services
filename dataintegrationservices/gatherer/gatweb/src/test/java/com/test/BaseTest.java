package com.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.aimspeed.gatherer.ApplicationRun;

/**
 * 基础测试类
 * @author AimSpeed
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@Transactional //是否开启事务回滚
//@SpringApplicsationConfiguration(ApplicationRun.class)
@SpringBootTest(classes = ApplicationRun.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BaseTest {
	/**
	 * 在执行测试方法之前要执行的方法
	 */
	@Before
	public void before() {
	
	}
	
	
}
