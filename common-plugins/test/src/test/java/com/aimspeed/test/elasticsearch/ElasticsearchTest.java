package com.aimspeed.test.elasticsearch;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.aimspeed.common.ReflectUtils;
import com.aimspeed.elasticsearch.vo.PageVo;
import com.aimspeed.test.BaseTest;
import com.aimspeed.test.elasticsearch.bean.StudentElasticsearchBean;
import com.aimspeed.test.elasticsearch.bean.TeacherElasticsearchBean;
import com.aimspeed.test.elasticsearch.mapper.StudentElasticsearchMapper;
import com.aimspeed.test.elasticsearch.mapper.TeacherElasticsearchMapper;

/**
 * Elasticsearch测试用例
 * 
 * StudentElasticsearchMapper 是自定义扩展的
 * TeacherElasticsearchMapper 是未自定义扩展的
 * 
 * @author AimSpeed
 */
public class ElasticsearchTest extends BaseTest {
	
	@Autowired
	private StudentElasticsearchMapper studentElasticsearchMapper;
	
	@Autowired
	private TeacherElasticsearchMapper teacherElasticsearchMapper;
	
	/**
	 * 单个插入
	 * @author AimSpeed
	 * @param record
	 * @return boolean  
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	@Test
	public void insert() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		//数据存放容器
		List<StudentElasticsearchBean> students = new ArrayList<>();
		
		//组装数据
		for (int i = 0; i < 30; i++) {
			
			StudentElasticsearchBean student = new StudentElasticsearchBean(Integer.valueOf(i).longValue(),
											getNames(),
											i + new Random(5).nextInt(),
											getIntroduces());
			students.add(student);
		}
		
		//批量插入
//		studentElasticsearchMapper.setIndexName(StudentElasticsearchBean.class, "school2", false);
		studentElasticsearchMapper.saveAll(students);
		
		//单条插入
		TeacherElasticsearchBean teacher = new TeacherElasticsearchBean("老师",22,"教书");
//		teacherElasticsearchMapper.save(teacher);
		
		//批量插入
		List<TeacherElasticsearchBean> teachers = new ArrayList<>();
		teachers.add(new TeacherElasticsearchBean(1L,"小明",22,"大学"));
		teachers.add(new TeacherElasticsearchBean(2L,"中明",32,"中学"));
		teachers.add(new TeacherElasticsearchBean(3L,"大明",52,"高中"));
//		teacherElasticsearchMapper.saveAll(teachers);
	}

	/**
	 * 查询
	 * @author AimSpeed
	 * @param record
	 * @return boolean  
	 */
	@Test
	public void query() {
		
		System.out.println("------------------------ 查找所有");
		/*Iterator<StudentElasticsearchBean> iterator = studentElasticsearchMapper.findAll().iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
			
		}
		
		System.out.println();
		System.out.println("------------------------ 根据ID查找");
		Optional<StudentElasticsearchBean> findById = studentElasticsearchMapper.findById(1L);
		System.out.println(findById.get());

		System.out.println();
		System.out.println("------------------------ 根据多个ID查找");
		List<Long> ids = new ArrayList<>();
		ids.add(1L);
		ids.add(2L);
		ids.add(3L);
		Iterator<StudentElasticsearchBean> findAllByIds = studentElasticsearchMapper.findAllById(ids).iterator();
		while (findAllByIds.hasNext()) {
			System.out.println(findAllByIds.next());
		}*/

		//条件
		Map<String, Object> condition = new HashMap<>();
		condition.put("name", "小圣");
		condition.put("introduce", "熊霸");
		

		StudentElasticsearchBean student = new StudentElasticsearchBean("熊霸");
		
		Map<String, Object> condition2 = new HashMap<>();
		condition.put("name", "小圣");
		
		System.out.println();
		System.out.println("------------------------ 对象作为条件查找");
		List<StudentElasticsearchBean> query = studentElasticsearchMapper.query(new StudentElasticsearchBean());
		System.out.println(query);
		
		System.out.println();
		System.out.println("------------------------ Map作为条件查找");
		System.out.println(studentElasticsearchMapper.query(condition2,StudentElasticsearchBean.class));
		
		
		System.out.println();
		System.out.println("------------------------ 对象作为条件查找单个");
		System.out.println(studentElasticsearchMapper.queryByOnly(new StudentElasticsearchBean(1L,"小圣")));
		
		System.out.println();
		System.out.println("------------------------ Map作为条件查找单个");
		System.out.println(studentElasticsearchMapper.queryByOnly(condition,StudentElasticsearchBean.class));
		
		System.out.println();
		System.out.println("------------------------ 前缀查询");
		System.out.println(studentElasticsearchMapper.queryByPrefix(new StudentElasticsearchBean("熊")));
		
		System.out.println();
		System.out.println("------------------------ *通配符查询");
		System.out.println(studentElasticsearchMapper.queryByWildcard(new StudentElasticsearchBean("*霸")));
		
		System.out.println();
		System.out.println("------------------------ 模糊查询");
		System.out.println(studentElasticsearchMapper.queryByFuzzy(new StudentElasticsearchBean("李清照")));
		
		System.out.println();
		System.out.println("------------------------ 是否存在");
		System.out.println(studentElasticsearchMapper.exists(student));
		
		System.out.println();
		System.out.println("------------------------ 是否存在");
		System.out.println(studentElasticsearchMapper.exists(condition,StudentElasticsearchBean.class));
		
		System.out.println();
		System.out.println("------------------------ 总数量");
//		System.out.println(studentElasticsearchMapper.count());
		
		System.out.println();
		System.out.println("------------------------ Map作为条件查找总数量");
		System.out.println(studentElasticsearchMapper.count(condition2,StudentElasticsearchBean.class));
		
		System.out.println();
		System.out.println("------------------------ 对象作为条件查找总数量");
		System.out.println(studentElasticsearchMapper.count(student));
		
		System.out.println();
		System.out.println("------------------------ 扩展自定义的查询");
		//List<StudentElasticsearchBean> students2 = studentElasticsearchMapper.query2(student, StudentElasticsearchBean.class);
		//System.out.println(students2);
		
		System.out.println();
		System.out.println("------------------------ 分页查找");
		Map<String, Object> filterCondition = new HashMap<>();
		filterCondition.put("name", "小圣");
		PageVo<StudentElasticsearchBean> students3 = studentElasticsearchMapper.queryPage(2, 10, "id", "ASC", filterCondition, StudentElasticsearchBean.class);
		for (StudentElasticsearchBean student2 : students3.getPageData()) {
			System.out.println(student2);
		}
		System.out.println(students3.getTotalCount());
	}
	
	/**
	 * 分页查询
	 * @author AimSpeed
	 * @param record
	 * @return boolean  
	 */
	@Test
	public void queryPage() {
		Map<String, Object> filterCondition = new HashMap<>();
		filterCondition.put("name", "小圣");
		
		/*PageVo<StudentElasticsearchBean> pageVo = studentElasticsearchMapper.queryPage(1, 10, "id", "ASC", filterCondition, StudentElasticsearchBean.class);
		System.out.println(pageVo.toString());
		
		for (StudentElasticsearchBean student2 : pageVo.getPageData()) {
			System.out.println(student2);
		}*/
		

		
	}
	
	/**
	 * 重写的查询
	 * @author AimSpeed
	 * @param record
	 * @return boolean  
	 */
	@Test
	public void query2() {
		/*StudentElasticsearchBean student = new StudentElasticsearchBean();
		student.setName("小和");
		List<StudentElasticsearchBean> query = studentElasticsearchMapper.query2(student, StudentElasticsearchBean.class);
		System.out.println(query);*/
	}
	
	/**
	 * 分页查询
	 * @author AimSpeed
	 * @param record
	 * @return boolean  
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	@Test
	public void delete() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		
		System.out.println("----------=======---------------------");
		
		StudentElasticsearchBean student1 = new StudentElasticsearchBean(Integer.valueOf(1).longValue(),
				getNames(),
				2 + new Random(5).nextInt(),
				getIntroduces());

		studentElasticsearchMapper.save(student1);
		
		studentElasticsearchMapper.setIndexName(StudentElasticsearchBean.class, "school3");
		
		StudentElasticsearchBean student = new StudentElasticsearchBean(Integer.valueOf(2).longValue(),
																						getNames(),
																						2 + new Random(5).nextInt(),
																						getIntroduces());

		System.out.println("----------=======---------------------");							
		studentElasticsearchMapper.save(student);
		
		studentElasticsearchMapper.setIndexName(StudentElasticsearchBean.class, "school");
		
		StudentElasticsearchBean student321 = new StudentElasticsearchBean(Integer.valueOf(3).longValue(),
																						getNames(),
																						2 + new Random(5).nextInt(),
																						getIntroduces());

		System.out.println("----------=======---------------------");							
		studentElasticsearchMapper.save(student321);
		
	}
	
	
	


	
}
