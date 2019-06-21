package com.aimspeed.test.mongodb.bean;

import org.springframework.data.mongodb.core.mapping.Document;

import com.aimspeed.mongodb.BaseMongoBean;

/**
 * 学生测试类
 * @author AimSpeed
 */
@Document(collection="student")
public class StudentMongoDBBean extends BaseMongoBean {
	
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

	public StudentMongoDBBean() {
		super();
	}

	public StudentMongoDBBean(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public StudentMongoDBBean(String name, String introduce) {
		super();
		this.name = name;
		this.introduce = introduce;
	}

	public StudentMongoDBBean(String introduce) {
		super();
		this.introduce = introduce;
	}

	public StudentMongoDBBean(String name, Integer age, String introduce) {
		super();
		this.name = name;
		this.age = age;
		this.introduce = introduce;
	}
	
	public StudentMongoDBBean(Long id, String name, Integer age, String introduce) {
		super();
		this.id = id;
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

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", introduce=" + introduce + "]";
	}

	
    
    
}
