package com.aimspeed.test.mongodb.mapper;

import java.util.List;

import com.aimspeed.test.mongodb.bean.StudentMongoDBBean;


/**
 * 学生数据-扩展
 * @author AimSpeed
 */
public interface StudentMongoDBBeanCusomerMapper {
	
	
	
	/**
	 * 按照条件查找，查找单个
	 * @author AimSpeed
	 * @param clazz
	 * @param filtrate
	 * @param likeFiltrate
	 * @return T 
	 */
	List<StudentMongoDBBean> query2(StudentMongoDBBean t,Class<StudentMongoDBBean> clazz);
	
	
	
	
	
	
	
}
