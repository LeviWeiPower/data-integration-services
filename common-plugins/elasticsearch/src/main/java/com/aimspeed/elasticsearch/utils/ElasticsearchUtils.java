package com.aimspeed.elasticsearch.utils;

import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.query.Criteria;

import com.aimspeed.common.ReflectUtils;

/**
 * Es在查询时用的工具类
 * @author AimSpeed
 */
public abstract class ElasticsearchUtils {

	/**
	 * 将对象组装为精确查询参数
	 * @author AimSpeed
	 * @param t 对象
	 * @return Criteria
	 */
	public static <T> Criteria objectConvertCriteria(T t) {
		
		//查询条件
		Criteria criteria = new Criteria();
		
		//组装查询条件
		Map<String, Object> params = ReflectUtils.objectNotEmptyConvertMap(t, false);
		
		if(null != params && params.size() > 0) {
			//开始组装
			for (Entry<String, Object> entry : params.entrySet()) {
				if(null != entry.getValue() && !"".equals(entry.getValue().toString().trim())) {
					criteria.and(Criteria.where(entry.getKey()).is(entry.getValue().toString()));
				}
			}
			
		}
		return criteria;
	}
	
	/**
	 * 组装为精确查询参数
	 * @author AimSpeed
	 * @param params
	 * @return Criteria 
	 */
	public static Criteria mapConvertCriteria(Map<String, Object> params){
		
		//查询条件
		Criteria criteria = new Criteria();
		
		if(null != params && params.size() > 0) {
			//组装数据
			for (Entry<String, Object> entry : params.entrySet()) {
				if(null != entry.getValue() && !"".equals(entry.getValue().toString().trim())) {
					criteria.and(Criteria.where(entry.getKey()).is(entry.getValue().toString()));
				}
			}
		}
		return criteria;
	}

	/**
	 * 将对象组装为模糊查询参数
	 * @author AimSpeed
	 * @param t 对象
	 * @return Criteria
	 */
	public static <T> Criteria objectConvertFuzzyCriteria(T t) {
		
		//查询条件
		Criteria criteria = new Criteria();
		
		//组装查询条件
		Map<String, Object> params = ReflectUtils.objectNotEmptyConvertMap(t, false);
		
		if(null != params && params.size() > 0) {
			//开始组装
			for (Entry<String, Object> entry : params.entrySet()) {
				if(null != entry.getValue() && !"".equals(entry.getValue().toString().trim())) {
					criteria.and(Criteria.where(entry.getKey()).contains(entry.getValue().toString()));
				}
			}
		}
		return criteria;
	}
	
	/**
	 * 组装为模糊查询参数
	 * @author AimSpeed
	 * @param params
	 * @return Criteria 
	 */
	public static Criteria mapConvertFuzzyCriteria(Map<String, Object> params){
		
		//查询条件
		Criteria criteria = new Criteria();
		
		if(null != params && params.size() > 0) {
			//组装数据
			for (Entry<String, Object> entry : params.entrySet()) {
				if(null != entry.getValue() && !"".equals(entry.getValue().toString().trim())) {
					criteria.and(Criteria.where(entry.getKey()).contains(entry.getValue().toString()));
				}
			}
		}
		
		return criteria;
	}
	
	/**
	 * 将参数转换为前缀查询
	 * @author AimSpeed
	 * @param params
	 * @param queryBuilder
	 * @return BoolQueryBuilder
	 */
	public static void mapConvertPrefixQuery(Map<String, Object> params,BoolQueryBuilder queryBuilder) {
		if(null != params && params.size() > 0) {
			//开始组装
			for (Entry<String, Object> entry : params.entrySet()) {
				if(null != entry.getValue() && !"".equals(entry.getValue().toString().trim())) {
					queryBuilder.must(new PrefixQueryBuilder(entry.getKey(),entry.getValue().toString()));
				}
			}
		}
	}
	
	/**
	 * 将参数转换为通配符查询
	 * ?用来匹配任意字符，*用来匹配零个或者多个字符
	 * 
	 * 如"name":"a?b*"
	 * @author AimSpeed
	 * @param params
	 * @param queryBuilder
	 * @return BoolQueryBuilder
	 */
	public static void mapConvertWildcardQuery(Map<String, Object> params,BoolQueryBuilder queryBuilder) {
		if(null != params && params.size() > 0) {
			//开始组装
			for (Entry<String, Object> entry : params.entrySet()) {
				if(null != entry.getValue() && !"".equals(entry.getValue().toString().trim())) {
					queryBuilder.must(new WildcardQueryBuilder(entry.getKey(),entry.getValue().toString()));
				}
			}
		}
	}
	
	/**
	 * 将参数转换为模糊查询
	 * @author AimSpeed
	 * @param params
	 * @param queryBuilder
	 * @return BoolQueryBuilder
	 */
	public static void mapConvertFuzzyQuery(Map<String, Object> params,BoolQueryBuilder queryBuilder) {
		if(null != params && params.size() > 0) {
			//开始组装
			for (Entry<String, Object> entry : params.entrySet()) {
				if(null != entry.getValue() && !"".equals(entry.getValue().toString().trim())) {
					queryBuilder.must(new FuzzyQueryBuilder(entry.getKey(),entry.getValue().toString()));
				}
			}
		}
	}
	
	/**
	 * 将参数转换为模糊查询
	 * @author AimSpeed
	 * @param params
	 * @param queryBuilder
	 * @return BoolQueryBuilder
	 */
	public static void mapConvertMatchQuery(Map<String, Object> params,BoolQueryBuilder queryBuilder) {
		if(null != params && params.size() > 0) {
			//开始组装
			for (Entry<String, Object> entry : params.entrySet()) {
				if(null != entry.getValue() && !"".equals(entry.getValue().toString().trim())) {
					queryBuilder.must(new MatchQueryBuilder(entry.getKey(),entry.getValue().toString()));
				}
			}
		}
	}
	
	/**
	 * 组装为排序类
	 * @author AimSpeed
	 * @param orderField
	 * @param orderingRule
	 * @return Sort 
	 */
	public static Sort assembleMongoSort(String orderField, String orderingRule){
		Sort sort = null;
		if(null != orderField && "desc".equalsIgnoreCase(orderingRule)) {
			sort = new Sort(Direction.DESC, orderField);
		}else if(null != orderField && "asc".equalsIgnoreCase(orderingRule)) {
			sort = new Sort(Direction.ASC, orderField);
		}
		return sort;
	}
	
}
