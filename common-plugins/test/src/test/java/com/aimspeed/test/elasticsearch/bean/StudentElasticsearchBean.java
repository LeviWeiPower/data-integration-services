package com.aimspeed.test.elasticsearch.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Component;

/**
 * 学生测试类
 * 
 * Elasticsearch位于实体映射类的注解作用：
 * 
 * @Document 作用在类，标记实体类为文档对象，一般有两个属性
 * 		indexName：对应索引库名称，最好是全部小写，不然可能会出异常。（版本）
 * 		type：对应在索引库中的类型
 * 		shards：分片数量，默认5
 * 		replicas：副本数量，默认1
 * 
 * 注意：默认情况下添加@Document注解会对实体中的所有属性建立索引，所以也可以不写@Id和Field，可对比Teacher
 * 
 * @Id 作用在成员变量，标记一个字段作为id主键
 * @Id 注解必须是springframework包下的
 * 
 * @Field 作用在成员变量，标记为文档的字段，并指定字段映射属性：
 * 		type：字段类型，是枚举：FieldType，可以是text、long、short、date、integer、object等
 * 			text：存储数据时候，会自动分词，并生成索引
 * 			keyword：存储数据时候，不会分词建立索引
 * 			Numerical：数值类型，分两类
 * 				基本数据类型：long、interger、short、byte、double、float、half_float
 * 				浮点数的高精度类型：scaled_float
 * 					需要指定一个精度因子，比如10或100。elasticsearch会把真实值乘以这个因子后存储，取出时再还原。
 * 			Date：日期类型
 * 				elasticsearch可以对日期格式化为字符串存储，但是建议我们存储为毫秒值，存储为long，节省空间。
 * 		index：是否索引，布尔类型，默认是true
 * 		store：是否存储，布尔类型，默认是false
 * 		analyzer：分词器名称，这里的ik_max_word即使用ik分词器
 * @author AimSpeed
 */
@Component
@Document(indexName = "school",type = "student", shards = 1, replicas = 0)
public class StudentElasticsearchBean {
	
	/**
	 * ID
	 */
	@Id
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

	public StudentElasticsearchBean() {
		super();
	}

	public StudentElasticsearchBean(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public StudentElasticsearchBean(String name, String introduce) {
		super();
		this.name = name;
		this.introduce = introduce;
	}

	public StudentElasticsearchBean(String introduce) {
		super();
		this.introduce = introduce;
	}

	public StudentElasticsearchBean(String name, Integer age, String introduce) {
		super();
		this.name = name;
		this.age = age;
		this.introduce = introduce;
	}
	
	public StudentElasticsearchBean(Long id, String name, Integer age, String introduce) {
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
