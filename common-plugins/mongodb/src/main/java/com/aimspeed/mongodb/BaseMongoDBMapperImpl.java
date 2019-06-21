package com.aimspeed.mongodb;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.aimspeed.common.ReflectUtils;
import com.aimspeed.mongodb.utils.MongoDBUtils;
import com.aimspeed.mongodb.vo.PageVo;

/**
 * MongoDB操作基础接口实现类
 * 
 * MongoDB基本概念：
 * 1、文档(document)是MongoDB中数据的基本单元，非常类似于关系型数据库系统中的行(但是比行要复杂的多)。
 * 2、集合(collection)就是一组文档，如果说MongoDB中的文档类似于关系型数据库中的行，那么集合就如同表。
 * 3、MongoDB的单个计算机可以容纳多个独立的数据库，每一个数据库都有自己的集合和权限。
 * 4、MongoDB自带简洁但功能强大的JavaScript shell，这个工具对于管理MongoDB实例和操作数据作用非常大。
 * 5、每一个文档都有一个特殊的键”_id”,它在文档所处的集合中是唯一的，相当于关系数据库中的表的主键。
 * 
 * MongoDB和关系数据库的对比
 * 对比项	      mongoDB	    mysql
 * 表	                   集合	               二维表table
 * 行数据	          文档document	   一条记录recoder
 * 表字段	                  键key      	字段filed
 * 字段值	                值value	            值value
 * 主外键	                       无	         PK FK
 * 灵活度扩展性	极高	                          差
 * 
 * @author AimSpeed
 */
public class BaseMongoDBMapperImpl<T extends BaseMongoBean> implements BaseMongoDBMapper<T> {

	/**
	 * 日志记录
	 */
	private Logger logger = LoggerFactory.getLogger(BaseMongoDBMapperImpl.class);
	

	@Autowired
    private MongoTemplate mongoTemplate;

	//=============================== 查找 ===============================
	/*
	 * 按照条件查找，查找单个
	 * @author AimSpeed
	 * @param pageVo
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.mongodb.BaseMongoMapper#queryPage(com.aimspeed.mysql.vo.MySqlPageVo)
	 */
	@Override
	public <T> T queryByOnly(T t,Class<T> clazz) {
		//查询条件
		Query query = new Query();
		Criteria criteria = new Criteria();
		if(null != t) {
			criteria.andOperator(MongoDBUtils.objectConvertCriteria(t));
		}
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, clazz);
	}
	
	/*
	 * 按照条件查找，查找单个
	 * @author AimSpeed
	 * @param pageVo
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.mongodb.BaseMongoMapper#queryPage(com.aimspeed.mysql.vo.MySqlPageVo)
	 */
	@Override
	public <T> T queryByOnly(Class<T> clazz,Map<String, Object> filtrate, Map<String, Object> likeFiltrate) {
		//查询条件
		Query query = new Query();
		Criteria criteria = new Criteria();
		if(null != filtrate) {
			criteria.andOperator(MongoDBUtils.mapConvertCriteria(filtrate));
		}
		if(null != filtrate) {
			criteria.orOperator(MongoDBUtils.mapConvertCriteriaOfStrLike(likeFiltrate));
		}
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, clazz);
	}
	
	/*
	 * 按照条件查找
	 * @author AimSpeed
	 * @param pageVo
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.mongodb.BaseMongoMapper#queryPage(com.aimspeed.mysql.vo.MySqlPageVo)
	 */
	@Override
	public List<T> query(T t,Class<T> clazz) {
		//查询条件
		Query query = new Query();
		Criteria criteria = new Criteria();
		if(null != t) {
			criteria.andOperator(MongoDBUtils.objectConvertCriteria(t));
		}
		query.addCriteria(criteria);
		return mongoTemplate.find(query, clazz);
	}

	/*
	 * 按照条件查找
	 * @author AimSpeed
	 * @param pageVo
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.mongodb.BaseMongoMapper#queryPage(com.aimspeed.mysql.vo.MySqlPageVo)
	 */
	@Override
	public List<T> query(Class<T> clazz,Map<String, Object> filtrate, Map<String, Object> likeFiltrate) {
		//查询条件
		Query query = new Query();
		Criteria criteria = new Criteria();
		if(null != filtrate) {
			criteria.andOperator(MongoDBUtils.mapConvertCriteria(filtrate));
		}
		if(null != likeFiltrate) {
			criteria.orOperator(MongoDBUtils.mapConvertCriteriaOfStrLike(likeFiltrate));
		}
		query.addCriteria(criteria);
		return mongoTemplate.find(query, clazz);
	}
	
	/*
	 * 分页查找，精确查找用and，模糊查询用or，有需要可重写
	 * @author AimSpeed
	 * @param curPage
	 * @param pageSize
	 * @param orderField
	 * @param orderingRule
	 * @param filtrate
	 * @param likeFiltrate
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.mongodb.BaseMongoMapper#queryPage(int, int, java.lang.String, java.lang.String, java.util.Map, java.util.Map)
	 */
	@Override
	public PageVo<T> queryPage(int curPage, int pageSize, String orderField, String orderingRule,
			Map<String, Object> filtrate, Map<String, Object> likeFiltrate) {
		
		//排序
		Sort sort = MongoDBUtils.assembleMongoSort(orderField, orderingRule);
		
		//查询条件
		Query query = new Query();
		Criteria criteria = new Criteria();
		if(null != filtrate) {
			criteria.andOperator(MongoDBUtils.mapConvertCriteria(filtrate));
		}
		if(null != likeFiltrate) {
			criteria.orOperator(MongoDBUtils.mapConvertCriteriaOfStrLike(likeFiltrate));
		}
		query.addCriteria(criteria);
        
		//页数
		query.with(new PageRequest(curPage-1,pageSize,sort));
		
		Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
		List<T> result = mongoTemplate.find(query, entityClass);
		
		PageVo<T> pageVo = new PageVo<>();
		pageVo.setFristPage(1);
		pageVo.setTotalCount(countSize(filtrate, likeFiltrate));//总条数
		pageVo.setFiltrate(filtrate);//保留筛选条件
		pageVo.setLikeFiltrate(likeFiltrate);
		pageVo.setPageSize(pageSize);
		pageVo.setCurPage(curPage);
//		pageVo.setTotalPage(page.getTotalPage() );
//		pageVo.setNextPage(page.getNextPage());
		pageVo.setOrderField(orderField);
		pageVo.setOrderingRule(orderingRule);
		pageVo.setPageData(result);
//		pageVo.setPrePage(page.getPrePage());
		return pageVo;
	}

	/*
	 * 分页查找，精确查找用and，模糊查询用or，有需要可重写
	 * @author AimSpeed
	 * @param pageVo
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.mongodb.BaseMongoMapper#queryPage(com.aimspeed.mysql.vo.MySqlPageVo)
	 */
	@Override
	public PageVo<T> queryPage(PageVo<T> pageVo) {
		return queryPage(pageVo.getCurPage(), pageVo.getPageSize(), pageVo.getOrderField(), pageVo.getOrderingRule(),
				pageVo.getFiltrate(), pageVo.getLikeFiltrate());
	}

	/*
	 * 查找总记录数，精确查找用and，模糊查询用or，有需要可重写
	 * @author AimSpeed
	 * @param condition
	 * @param likeCondition
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.mongodb.BaseMongoMapper#countSize(java.util.Map, java.util.Map)
	 */
	@Override
	public Long countSize(Map<String, Object> condition, Map<String, Object> likeCondition) {
		//查询条件
		Query query = new Query();
		Criteria criteria = new Criteria();
		if(null != condition) {
			criteria.andOperator(MongoDBUtils.mapConvertCriteria(condition));
		}
		if(null != likeCondition) {
			criteria.orOperator(MongoDBUtils.mapConvertCriteriaOfStrLike(likeCondition));
		}
		query.addCriteria(criteria);  
		return mongoTemplate.count(query, ReflectUtils.getEntityByGenericity(this.getClass()).getClass());
	}
	
	/*
	 * 按照条件查找数据是否存在
	 * @author AimSpeed
	 * @param pageVo
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.mongodb.BaseMongoMapper#queryPage(com.aimspeed.mysql.vo.MySqlPageVo)
	 */
	@Override
	public boolean exists(T t,Class<T> clazz) {
		//查询条件
		Query query = new Query();
		Criteria criteria = new Criteria();
		if(null != t) {
			criteria.andOperator(MongoDBUtils.objectConvertCriteria(t));
		}
		query.addCriteria(criteria);
		return mongoTemplate.exists(query, clazz);
	}
	
	//=============================== 更新 ===============================
	/*
	 * 根据ID更新
	 * @author AimSpeed
	 * @param record
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.mongodb.BaseMongoMapper#updateById(java.io.Serializable)
	 */
	@Override
	public boolean updateById(T record) {
		mongoTemplate.insert(record);
		return true;
	}

	/*
	 * 批量根据ID更新
	 * @author AimSpeed
	 * @param records
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.mongodb.BaseMongoMapper#batchUpdateBeanById(java.util.Collection)
	 */
	@Override
	public boolean batchUpdateBeanById(Collection<T> records) {
		mongoTemplate.insert(records);
		return true;
	}
	
	

	
	
	
}
