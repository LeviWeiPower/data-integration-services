package com.aimspeed.mongodb.utils;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;

import com.aimspeed.common.ReflectUtils;

/**
 * MongoDB工具类
 * @author AimSpeed
 */
public abstract class MongoDBUtils {
	
	/**
	 * 组装为精确查询参数
	 * @author AimSpeed
	 * @param params
	 * @return Criteria[] 
	 */
	public static <T> Criteria[] objectConvertCriteria(T t) {
		Map<String, Object> params = ReflectUtils.objectNotEmptyConvertMap(t, false);
		Criteria[] criterias = new Criteria[params.size()];//结果
		//组装数据
		int count = 0;
		for (Entry<String, Object> entry : params.entrySet()) {
			if(null != entry.getValue() && !"".equals(entry.getValue().toString().trim())) {
				criterias[count] = Criteria.where(entry.getKey()).is(entry.getValue().toString());
				count++;
			}
		}
		return criterias;
	}
	
	/**
	 * 组装为精确查询参数
	 * @author AimSpeed
	 * @param params
	 * @return Criteria[] 
	 */
	public static Criteria[] mapConvertCriteria(Map<String, Object> params){
		if(null != params && params.size() > 0) {
			Criteria[] criterias = new Criteria[params.size()];//结果
			
			//组装数据
			int count = 0;
			for (Entry<String, Object> entry : params.entrySet()) {
				if(null != entry.getValue() && !"".equals(entry.getValue().toString().trim())) {
					criterias[count] = Criteria.where(entry.getKey()).is(entry.getValue().toString());
					count++;
				}
			}
			return criterias;
		}
		return new Criteria[0];
	}

	/**
	 * 组装为模糊查询参数
	 * @author AimSpeed
	 * @param params
	 * @return Criteria[] 
	 */
	public static Criteria[] mapConvertCriteriaOfStrLike(Map<String, Object> params){
		Criteria[] criterias = new Criteria[params.size()];//结果
		
		//组装数据
		int count = 0;
		for (Entry<String, Object> entry : params.entrySet()) {
			if(null != entry.getValue() && !"".equals(entry.getValue().toString().trim())) {
				Pattern pattern=Pattern.compile("^.*" + entry.getValue().toString() + ".*$", Pattern.CASE_INSENSITIVE);
				criterias[count] = Criteria.where(entry.getKey()).regex(pattern);
				count++;
			}
		}
		return criterias;
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
