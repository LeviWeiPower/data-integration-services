package com.aimspeed.test.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aimspeed.test.BaseTest;
import com.aimspeed.test.mongodb.bean.StudentMongoDBBean;
import com.aimspeed.test.mongodb.bean.TeacherMongoDBBean;
import com.aimspeed.test.mongodb.mapper.StudentMongoDBBeanMapper;
import com.aimspeed.test.mongodb.mapper.TeacherMongoDBBeanMapper;


/**
 * Elasticsearch测试用例
 * @author AimSpeed
 */
//@Transactional //是否开启事务回滚
public class MongoDBTest extends BaseTest {

	@Autowired
	private StudentMongoDBBeanMapper studentMongoBeanMapper;
	
	@Autowired
	private TeacherMongoDBBeanMapper teacherMongoBeanMapper;
	
	/**
	 * 单个插入
	 * @author AimSpeed
	 * @param record
	 * @return boolean  
	 */
	@Test
	public void insert() {
		//数据存放容器
		List<StudentMongoDBBean> students = new ArrayList<>();
		
		//组装数据
		for (int i = 555; i < 10; i++) {
			
			StudentMongoDBBean student = new StudentMongoDBBean(Integer.valueOf(i).longValue(),
											getNames(),
											i + new Random(5).nextInt(),
											getIntroduces());
			students.add(student);
		}
		
		studentMongoBeanMapper.saveAll(students);
		
		//批量插入
		List<TeacherMongoDBBean> teachers = new ArrayList<>();
		teachers.add(new TeacherMongoDBBean(1L,"小明",22,"大学"));
		teachers.add(new TeacherMongoDBBean(2L,"中明",32,"中学"));
		teachers.add(new TeacherMongoDBBean(3L,"大明",52,"高中"));
		teacherMongoBeanMapper.saveAll(teachers);
		
		System.err.println("=================== 数据添加成功！");
		
		
		
	}
	
	/**
	 * 查询
	 * @author AimSpeed void
	 */
	@Test
	public void query() {
		StudentMongoDBBean student = new StudentMongoDBBean();
		student.setName("小李子");
		System.out.println(studentMongoBeanMapper.query(student,StudentMongoDBBean.class));
	}
	

	
}
