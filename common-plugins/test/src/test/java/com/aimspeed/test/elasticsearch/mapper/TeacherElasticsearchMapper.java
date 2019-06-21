package com.aimspeed.test.elasticsearch.mapper;

import com.aimspeed.elasticsearch.BaseElasticsearchCurdMapper;
import com.aimspeed.elasticsearch.BaseElasticsearchMapper;
import com.aimspeed.test.elasticsearch.bean.StudentElasticsearchBean;
import com.aimspeed.test.elasticsearch.bean.TeacherElasticsearchBean;

/**
 * 学生ES测试类
 * @author AimSpeed
 */
public interface TeacherElasticsearchMapper extends BaseElasticsearchCurdMapper<TeacherElasticsearchBean, Long>,
													BaseElasticsearchMapper<TeacherElasticsearchBean> {

}
