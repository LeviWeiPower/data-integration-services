package com.aimspeed.elasticsearch;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.aimspeed.elasticsearch.vo.PageVo;

/**
 * Elasticsearch操作接口，自定义的扩展通用方法
 * 
 * Elasticsearch是一个基于Lucene的搜索服务器，他提供了一个分布式多用户能力的全文搜索引擎，基于RESTful Web接口。
 * Elasticsearch是用Java开发的，隐藏了复杂性，开箱即用，并设计用于云计算，能够达到实时搜索，稳定、可靠、快速、安装方便(插件可不方便)，
 * 不仅能对海量规模的数据完成分布式索引和检索，还能提供数据聚合分析。
 * Lucene就是一个jar包里面有着大量各种建立倒排索引，以及进行搜索的代码、算法等。
 * 
 * Elasticsearch存储数据也是按照切片的方式，默认是5个切片，而且切片都是有副本的
 * Elasticsearch服务默认端口是9300
 * Elasticsearch Web管理端口9200
 * Elasticsearch 2.x系列的版本和5.x版本或以上的Api区别在于内部数个组件的整合，
 * 
 * Elasticsearch内置分词器
 * Standard分词器(默认)：会将词汇单元转换为小写形式，并去掉停用词和标点符号，支持中文采用的方式为单字切分。
 * Simple分词器：首先会通过非字母字符来分割文本信息，然后将词汇单元统一为小写形式，该分词器会去掉数字类型的字符。
 * Whitespace分词器：只是去除空格，对字符没有lowcase化，不支持中文，并不对生成的词汇单元进行其他的标准化处理。
 * Language分词器：特定语言的分词器，不支持中文。
 * IK分词器(不是内置的分词器)：对中文切分较好，通常会使用这个。
 * 
 * 
 * 
 * 数据库				Elasticsearch			Elasticsearch描述
 * database(库)		index (索引)				一个索引通常代表一类数据
 * table(表)			type(类型)				Elasticsearch在index中建立type，通过mapping进行映射
 * column (列)		field(字段)
 * row(行)			document(文档)			Elasticsearch存储是文档形的，相当于数据库的行
 * constraint(约束)	mapping(映射)				创建index时，可以预先定义好字段的类型和相关属性，这样就可以自动进行类型转换，如：字符串转日期
 * SQL(数据操作语句)	Query DSL(数据操作语句)		GET/PUT/POST/DELETE 对应数据库 select/update/delete
 * index(索引)		indexed(索引)				Elasticsearc默认是加上索引，触发特殊指定不建立索引
 * 
 * 
 * 注意1：如果两个字段在相同索引的不同类型中具有相同的名称，那么它们需要具有相同的字段类型(字符串、日期等)并具有相同的配置。
 * 		例如：学生-name和老师-name，那么这2个数据类型都在同一索引下，那么则需要是相同类型
 * 
 * 注意2：官方在6.X不支持一个index多个type了，对于5.X版本中多个Mapping依旧可以使用。计划在7.0.0中完全移除Mapping。
 * 		所以如果使用6.X，相同索引下的不能有多个type，否则会报错：Rejecting mapping update to [school] as the final mapping 
 * 												 would have more than 1 type
 * 		例如：学生和老师都是相同index，但是是不同的索引就会报错。		
 * 
 * 
 * 
 * Elasticsearch位于实体映射类的注解作用：
 * @Document 作用在类，标记实体类为文档对象，一般有两个属性
 * 		indexName：对应索引库名称
 * 		type：对应在索引库中的类型
 * 		shards：分片数量，默认5
 * 		replicas：副本数量，默认1
 * 
 * 注意：默认情况下添加@Document注解会对实体中的所有属性建立索引，所以也可以不写@Id和Field
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
 * 
 * @author AimSpeed
 * @param <T> 映射的实体类
 * @param <ID> 文档的ID，ES的文档ID计算是会加入其主分片数量做计算的，所以是定了就不更改，不然会出现数据错乱和丢失的问题。
 * 				因为在做数据查找时，是对计算数据的ID，从而得出数据所在的位置，如果数据不再接收请求的节点上，
 * 				这个节点则作为协调节点，去访问对应的数据所在的节点，从而获取到数据在返回给客户端。
 */
public interface BaseElasticsearchMapper<T> {
	
	/*
	 * 单条插入 - 
	 * 批量插入 - 
	 * 
	 * Id查询  - 
	 * 单条查询，实体，map（精确匹配，模糊匹配，排序，and/or）
	 * 多条查询，实体，map（精确匹配，模糊匹配，排序，and/or）
	 * 查找全部
	 * 
	 * 条数查询，实体，map（精确匹配，模糊匹配，and/or）
	 * 
	 * 前缀匹配、通配符匹配，全文匹配 - 
	 * 
	 * 分页查询
	 * 
	 * 是否存在（实体，Map，and/or） - 
	 * 
	 */
	
	//=============================== 查找 ===============================
	
	/**
	 * 单条插入
	 * @author AimSpeed
	 * @param t
	 * @return int
	 */
	int insert(T t);
	
	/**
	 * 批量插入
	 * @author AimSpeed
	 * @param t
	 * @return int
	 */
	int batchInsert(Iterable<T> entities);
	
	//=============================== 查找 ===============================
	
	/**
	 * 查找所有
	 * @author AimSpeed
	 * @param clazz
	 * @return List<T>
	 */
	List<T> queryAll(Class<T> clazz);
	
	/**
	 * 按照对象内有的值作为条件查找，查找单条数据，必须确保是单条数据，否则会报错
	 * @author AimSpeed
	 * @param t
	 * @return T
	 */
	T queryByOnly(T t);
	
	/**
	 * 按照Map内有的值作为条件查找，查找单条数据，必须确保是单条数据，否则会报错
	 * @author AimSpeed
	 * @param condition
	 * @param clazz 结果Bean类型的class，如Student.class
	 * @return T
	 */
	T queryByOnly(Map<String, Object> condition,Class<T> clazz);
	
	/**
	 * 按照对象内有的值作为条件查找
	 * @author AimSpeed
	 * @param t
	 * @return List<T>
	 */
	List<T> query(T t);
	
	/**
	 * 按照Map内有的值作为条件查找
	 * @author AimSpeed
	 * @param clazz
	 * @param filtrate
	 * @param likeFiltrate
	 * @return List<T> 
	 */
	List<T> query(Map<String, Object> condition,Class<T> clazz);
	
	
	/**
	 * 根据前缀做查找
	 * @author AimSpeed
	 * @param t
	 * @return List<T>
	 */
	List<T> queryByPrefix(T t);
	
	/**
	 * 根据通配符做查询
	 * ?用来匹配任意字符，*用来匹配零个或者多个字符
	 * 
	 * 如"name":"a?b*"
	 * 
	 * 注意，这个查询可能很慢
	 * 需要迭代许多项。为了防止异常慢的通配符查询
	 * 
	 * @author AimSpeed
	 * @param t
	 * @return List<T>
	 */
	List<T> queryByWildcard(T t);
	
	/**
	 * 这个是完全的模糊查询
	 * @author AimSpeed
	 * @param t
	 * @return List<T>
	 */
	List<T> queryByFuzzy(T t);
	
	/**
	 * 查找数据量，精确查找用and，模糊查询用or，有需要可重写
	 * @author AimSpeed
	 * @param t
	 * @return Long
	 */
	Long count(T t);
	
	/**
	 * 查找数据量，精确查找用and，模糊查询用or，有需要可重写
	 * @author AimSpeed
	 * @param condition
	 * @param clazz
	 * @return Long
	 */
	Long count(Map<String, Object> condition, Class<T> clazz);
	
	/**
	 * 分页查询，精确查找用and，模糊查询用or，有需要可重写
	 * @author AimSpeed
	 * @param curPage 从1开始
	 * @param pageSize
	 * @param orderField
	 * @param orderingRule
	 * @param filtrate
	 * @param clazz
	 * @return PageVo<T>
	 */
	PageVo<T> queryPage(Integer curPage, Integer pageSize,
								String orderField,String orderingRule,
								Map<String, Object> filtrate, Class<T> clazz);
	
	/**
	 * 分页查询，精确查找用and，模糊查询用or，有需要可重写
	 * @author AimSpeed
	 * @param pageVo
	 * @param clazz
	 * @return PageVo<T>
	 */
	PageVo<T> queryPage(PageVo<T> pageVo, Class<T> clazz);
	
	/**
	 * 根据实体作为条件判断是否存在
	 * @author AimSpeed
	 * @param t
	 * @return boolean
	 */
	boolean exists(T t);
	
	/**
	 * 根据条件判断是否存在
	 * @author AimSpeed
	 * @param condition
	 * @param clazz
	 * @return boolean
	 */
	boolean exists(Map<String, Object> condition, Class<T> clazz);
	
	/**
	 * 设置Index
	 * 有一些ES的索引设计是 Id % 512，来决定数据的索引位置。
	 * 如果是单个索引位置的，则不需要在调用其他方法前，调用该方法，设置其数据索引位置。
	 * 
	 * 备注：如果操作完，要还原索引名称的话，记得需要在方法操作完了，在调用一次setIndexName
	 * @author AimSpeed
	 * @param t 类，必须要设置org.springframework.data.elasticsearch.annotations.Document注解
	 * @param indexName 索引名称
	 * @return boolean true 真
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException String
	 */
	boolean setIndexName(Class<T> clazzType,String indexName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException;
		
	
}
