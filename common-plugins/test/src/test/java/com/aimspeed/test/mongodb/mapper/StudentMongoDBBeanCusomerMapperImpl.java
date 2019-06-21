package com.aimspeed.test.mongodb.mapper;

import java.util.List;

import com.aimspeed.mongodb.BaseMongoDBMapperImpl;
import com.aimspeed.test.mongodb.bean.StudentMongoDBBean;


/**
 * 学生数据
 * @author AimSpeed
 */
public class StudentMongoDBBeanCusomerMapperImpl extends BaseMongoDBMapperImpl<StudentMongoDBBean> implements StudentMongoDBBeanCusomerMapper {
	
	@Override
	public List<StudentMongoDBBean> query2(StudentMongoDBBean t, Class<StudentMongoDBBean> clazz) {
		return query(t, clazz);
	}
	
}
