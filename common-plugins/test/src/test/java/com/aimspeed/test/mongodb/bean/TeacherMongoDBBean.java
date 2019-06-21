package com.aimspeed.test.mongodb.bean;

import org.springframework.data.mongodb.core.mapping.Document;

import com.aimspeed.mongodb.BaseMongoBean;

/**
 * 老师测试类
 * 
 * Elasticsearch位于实体映射类的注解作用：
 * @author AimSpeed
 */
@Document(collection="teacher")
public class TeacherMongoDBBean extends BaseMongoBean {
	
	/**
	 * ID
	 */
	private Long id;
    
	/**
	 * 名称
	 */
	private String name; 
    
	/**
	 * 年龄
	 */
    private Integer age;
    
    /**
     * 介绍
     */
    private String introduce;

	public TeacherMongoDBBean() {
		super();
	}

	public TeacherMongoDBBean(Long id, String name, Integer age, String introduce) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.introduce = introduce;
	}
	
	public TeacherMongoDBBean(String name, Integer age, String introduce) {
		super();
		this.name = name;
		this.age = age;
		this.introduce = introduce;
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	
    
    
}
