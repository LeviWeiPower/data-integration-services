package com.aimspeed.test.elasticsearch.mapper;

import com.aimspeed.elasticsearch.BaseElasticsearchCurdMapper;
import com.aimspeed.elasticsearch.BaseElasticsearchMapper;
import com.aimspeed.test.elasticsearch.bean.StudentElasticsearchBean;

/**
 * 学生ES测试类
 * @author AimSpeed
 */
public interface StudentElasticsearchMapper extends BaseElasticsearchCurdMapper<StudentElasticsearchBean, Long>,
													BaseElasticsearchMapper<StudentElasticsearchBean>,
													StudentElasticsearchCusomerMapper {
	
	
}
