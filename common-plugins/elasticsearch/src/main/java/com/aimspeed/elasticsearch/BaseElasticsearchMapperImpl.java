package com.aimspeed.elasticsearch;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import com.aimspeed.common.ReflectUtils;
import com.aimspeed.elasticsearch.utils.ElasticsearchUtils;
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
 * Elasticsearch查询共有4种查询类型
 * QUERY_AND_FETCH: 
 * 	主节点将查询请求分发到所有的分片中，各个分片按照自己的查询规则即词频文档频率进行打分排序，然后将结果返回给主节点，
 * 	主节点对所有数据进行汇总排序然后再返回给客户端，此种方式只需要和es交互一次。
 * 	 这种查询方式存在数据量和排序问题，主节点会汇总所有分片返回的数据这样数据量会比较大，二是各个分片上的规则可能不一致。
 * 
 * QUERY_THEN_FETCH:
 * 	主节点将请求分发给所有分片，各个分片打分排序后将数据的id和分值返回给主节点，
 * 	主节点收到后进行汇总排序再根据排序后的id到对应的节点读取对应的数据再返回给客户端，此种方式需要和es交互两次。
 * 	这种方式解决了数据量问题但是排序问题依然存在而且是es的默认查询方式
 * 
 * DEF_QUERY_AND_FETCH 和 DFS_QUERY_THEN_FETCH: 
 * 	将各个分片的规则统一起来进行打分。解决了排序问题但是DFS_QUERY_AND_FETCH仍然存在数据量问题，
 * DFS_QUERY_THEN_FETCH两种噢乖你问题都解决但是效率是最差的。
 * 
 * @author AimSpeed
 * @param <T> 映射的实体类
 * @param <ID> 文档的ID，ES的文档ID计算是会加入其主分片数量做计算的，所以是定了就不更改，不然会出现数据错乱和丢失的问题。
 * 				因为在做数据查找时，是对计算数据的ID，从而得出数据所在的位置，如果数据不再接收请求的节点上，
 * 				这个节点则作为协调节点，去访问对应的数据所在的节点，从而获取到数据在返回给客户端。
 */
public class BaseElasticsearchMapperImpl<T> implements BaseElasticsearchMapper<T> {

    private static final Logger logger = LoggerFactory.getLogger(BaseElasticsearchMapperImpl.class);
    
	@Autowired
	protected ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	protected ApplicationContext applicationContext;
	
	@Override
	public int insert(T t) {
		
		return 0;
	}

	@Override
	public int batchInsert(Iterable<T> entities) {
		/*List<IndexQuery> queries = new ArrayList<>();
		for (T t : entities) {
			queries.add(simpleElasticsearchRepository.save(entities)t);
		}
		new IndexQueryBuilder().build();
		elasticsearchTemplate.bulkIndex(queries);*/
		return 0;
	}
	
	/*
	 * 查找所有
	 * @author AimSpeed
	 * @param clazz
	 * @return
	 * @overridden @see com.aimspeed.elasticsearch.BaseElasticsearchMapper#queryAll(java.lang.Class)
	 */
	@Override
	public List<T> queryAll(Class<T> clazz) {
		SearchQuery query = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
		return elasticsearchTemplate.queryForList(query, clazz);
	}
	
	/*
	 * 按照对象内有的值作为条件查找，查找单条数据，必须确保是单条数据，否则会报错
	 * @author AimSpeed
	 * @param t
	 * @return
	 * @overridden @see com.aimspeed.elasticsearch.BaseElasticsearchMapper#queryByOnly(java.lang.Object)
	 */
	@Override
	public T queryByOnly(T t) {
		//组装条件
		Map<String, Object> params = ReflectUtils.objectNotEmptyConvertMap(t, false);
		return queryByOnly(params, (Class<T>) t.getClass());
	}
	
	/*
	 * 按照Map内有的值作为条件查找，查找单条数据，必须确保是单条数据，否则会报错
	 * @author AimSpeed
	 * @param condition
	 * @param clazz
	 * @return
	 * @overridden @see com.aimspeed.elasticsearch.BaseElasticsearchMapper#queryByOnly(java.util.Map, java.lang.Class)
	 */
	@Override
	public T queryByOnly(Map<String, Object> condition,Class<T> clazz) {
		//组装条件
		Criteria criteria = ElasticsearchUtils.mapConvertCriteria(condition);

		//查询
		CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
		T result = elasticsearchTemplate.queryForObject(criteriaQuery, clazz);
		return result;
	}
	
	/*
	 * 按照对象内有的值作为条件查找
	 * @author AimSpeed
	 * @param t
	 * @return
	 * @overridden @see com.aimspeed.elasticsearch.BaseElasticsearchMapper#query(java.lang.Object)
	 */
	@Override
	public List<T> query(T t) {
		//组装条件
		Map<String, Object> params = ReflectUtils.objectNotEmptyConvertMap(t, false);
		return query(params, (Class<T>) t.getClass());
	}
	
	/*
	 * 按照Map内有的值作为条件查找
	 * @author AimSpeed
	 * @param condition
	 * @param clazz
	 * @return
	 * @overridden @see com.aimspeed.elasticsearch.BaseElasticsearchMapper#query(java.util.Map, java.lang.Class)
	 */
	@Override
	public List<T> query(Map<String, Object> condition,Class<T> clazz) {
		//组装条件
		Criteria criteria = ElasticsearchUtils.mapConvertCriteria(condition);
		
		//查询
		CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
		List<T> result = elasticsearchTemplate.queryForList(criteriaQuery, clazz);
		return result;
	}

	/*
	 * 根据前缀做查找
	 * @author AimSpeed
	 * @param t
	 * @return
	 * @overridden @see com.aimspeed.elasticsearch.BaseElasticsearchMapper#queryByPrefix(java.lang.Object)
	 */
	@Override
	public List<T> queryByPrefix(T t){
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		
		//对象转换为查询条件
		Map<String, Object> params = ReflectUtils.objectNotEmptyConvertMap(t, false);
		
		//组装查询条件
		ElasticsearchUtils.mapConvertPrefixQuery(params, queryBuilder);
		
		NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryBuilder);
		
		List<T> result = elasticsearchTemplate.queryForList(nativeSearchQuery, (Class<T>) t.getClass());
		return result;
		
	}

	/*
	 * 根据通配符做查询 ?用来匹配任意字符，
	 * *用来匹配零个或者多个字符如"name":"a?b*" 
	 * 
	 * 注意，这个查询可能很慢需要迭代许多项。
	 * 	         为了防止异常慢的通配符查询
	 * @author AimSpeed
	 * @param t
	 * @return
	 * @overridden @see com.aimspeed.elasticsearch.BaseElasticsearchMapper#queryByWildcard(java.lang.Object)
	 */
	@Override
	public List<T> queryByWildcard(T t){
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		
		//对象转换为查询条件
		Map<String, Object> params = ReflectUtils.objectNotEmptyConvertMap(t, false);
		
		//组装查询条件
		ElasticsearchUtils.mapConvertWildcardQuery(params, queryBuilder);
		
		NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryBuilder);
		
		List<T> result = elasticsearchTemplate.queryForList(nativeSearchQuery, (Class<T>) t.getClass());
		return result;
	}
	
	/*
	 * 这个是完全的模糊查询
	 * @author AimSpeed
	 * @param t
	 * @return
	 * @overridden @see com.aimspeed.elasticsearch.BaseElasticsearchMapper#queryByFuzzy(java.lang.Object)
	 */
	@Override
	public List<T> queryByFuzzy(T t){
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

		//对象转换为查询条件
		Map<String, Object> params = ReflectUtils.objectNotEmptyConvertMap(t, false);
		
		//组装查询条件
		ElasticsearchUtils.mapConvertWildcardQuery(params, queryBuilder);
		
		NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryBuilder);
		
		List<T> result = elasticsearchTemplate.queryForList(nativeSearchQuery, (Class<T>) t.getClass());
		return result;
	}
	
	/*
	 * 查找数据量，精确查找用and，模糊查询用or，有需要可重写
	 * @author AimSpeed
	 * @param t
	 * @return
	 * @overridden @see com.aimspeed.elasticsearch.BaseElasticsearchMapper#count(java.lang.Object)
	 */
	@Override
	public Long count(T t) {
		//组装条件
		Map<String, Object> params = ReflectUtils.objectNotEmptyConvertMap(t, false);
		return count(params, (Class<T>) t.getClass());
	}
	
	/*
	 * 查找数据量，精确查找用and，模糊查询用or，有需要可重写
	 * @author AimSpeed
	 * @param condition
	 * @param clazz
	 * @return
	 * @overridden @see com.aimspeed.elasticsearch.BaseElasticsearchMapper#count(java.util.Map, java.lang.Class)
	 */
	@Override
	public Long count(Map<String, Object> condition,Class<T> clazz) {
		//组装条件
		Criteria criteria = ElasticsearchUtils.mapConvertCriteria(condition);
		
		//查询
		CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
		long result = elasticsearchTemplate.count(criteriaQuery, clazz);
		
		return result;
	}
	
	/*
	 * 分页查询，精确查找用and，模糊查询用or，有需要可重写
	 * @author AimSpeed
	 * @param curPage
	 * @param pageSize
	 * @param orderField
	 * @param orderingRule
	 * @param filtrate
	 * @param clazz
	 * @return
	 * @overridden @see com.aimspeed.elasticsearch.BaseElasticsearchMapper#queryPage(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.util.Map, java.lang.Class)
	 */
	@Override
	public PageVo<T> queryPage(Integer curPage, Integer pageSize,
								String orderField,String orderingRule,
								Map<String, Object> filtrate,Class<T> clazz) {
	  	
//		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		
	  	Criteria criteria = new Criteria();
	  	
		if(null != filtrate && filtrate.size() > 0) {
			//组装数据
			for (Entry<String, Object> entry : filtrate.entrySet()) {
				if(null != entry.getValue() && !"".equals(entry.getValue().toString().trim())) {
					criteria.and(new Criteria(entry.getKey()).is(entry.getValue().toString()));
//					boolQueryBuilder.must(QueryBuilders.matchQuery(entry.getKey(),entry.getValue().toString()));
				}
			}
		}

		//分页参数
		PageRequest pageRequest = PageRequest.of(curPage - 1, 
													pageSize, 
													ElasticsearchUtils.assembleMongoSort(orderField, orderingRule));
		
	  	//查询
	  	CriteriaQuery criteriaQuery = new CriteriaQuery(criteria,pageRequest);

	  	Page<T> aggregatedPage = elasticsearchTemplate.queryForPage(criteriaQuery, clazz);

//	  	NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageRequest).withQuery(boolQueryBuilder).build();
//	  	Page<T> aggregatedPage = elasticsearchTemplate.queryForPage(searchQuery, clazz);
	  	//组装结果数据
	    PageVo<T> pageVo = new PageVo<>();
		pageVo.setFristPage(1);
		pageVo.setTotalCount(aggregatedPage.getTotalElements());//总条数
		pageVo.setFiltrate(filtrate);//保留筛选条件
//		pageVo.setLikeFiltrate(likeFiltrate);
		pageVo.setPageSize(pageSize);
		pageVo.setCurPage(curPage.longValue());
//		pageVo.setTotalPage(page.getTotalPage() );
//		pageVo.setNextPage(page.getNextPage());
		pageVo.setOrderField(orderField);
		pageVo.setOrderingRule(orderingRule);
		pageVo.setPageData(aggregatedPage.getContent());
//		pageVo.setPrePage(page.getPrePage());
		return pageVo;
		
	}
	
	/*
	 * 分页查询，精确查找用and，模糊查询用or，有需要可重写
	 * @author AimSpeed
	 * @param pageVo
	 * @param clazz
	 * @return
	 * @overridden @see com.aimspeed.elasticsearch.BaseElasticsearchMapper#queryPage(com.aimspeed.elasticsearch.vo.PageVo, java.lang.Class)
	 */
	@Override
	public PageVo<T> queryPage(PageVo<T> pageVo,Class<T> clazz) {
		return queryPage(pageVo.getCurPage().intValue(),
						 pageVo.getPageSize().intValue(),
						 pageVo.getOrderField(),
						 pageVo.getOrderingRule(),
						 pageVo.getFiltrate(),
						 clazz);
	}

	/*
	 * 根据实体作为条件判断是否存在
	 * @author AimSpeed
	 * @param t
	 * @return
	 * @overridden @see com.aimspeed.elasticsearch.BaseElasticsearchMapper#exists(java.lang.Object)
	 */
	@Override
	public boolean exists(T t) {
		return count(t) > 0 ? true : false;
	}
	
	/*
	 * 根据条件判断是否存在
	 * @author AimSpeed
	 * @param condition
	 * @param clazz
	 * @return
	 * @overridden @see com.aimspeed.elasticsearch.BaseElasticsearchMapper#exists(java.util.Map, java.lang.Class)
	 */
	@Override
	public boolean exists(Map<String, Object> condition,Class<T> clazz) {
		return count(condition,clazz) > 0 ? true : false;
	}
	
	
	/*
	 * 设置Index
	 * 有一些ES的索引设计是 Id % 512，来决定数据的索引位置。
	 * 如果是单个索引位置的，则不需要在调用其他方法前，调用该方法，设置其数据索引位置。
	 * 
	 * 备注：如果操作完，要还原索引名称的话，记得需要在方法操作完了，在调用一次setIndexName
	 * @author AimSpeed
	 * @param clazzType
	 * @param indexName
	 * @return boolean true 成功，false 失败
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @overridden @see com.aimspeed.elasticsearch.BaseElasticsearchMapper#setIndexName(java.lang.Class, java.lang.String, boolean)
	 */
	@Override
	public boolean setIndexName(Class<T> clazzType,String indexName) {
		//获取到内部的实体
		ElasticsearchPersistentEntity entity = elasticsearchTemplate.getPersistentEntityFor(clazzType);
        Field field = null;
		try {
			//获取到索引字段
			field = SimpleElasticsearchPersistentEntity.class.getDeclaredField("indexName");
			field.setAccessible(true);
	        String indexDefault = field.get(entity).toString();
	        field.set(entity, indexName);
	        return true;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (SecurityException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return false;
	}
	

	
}
