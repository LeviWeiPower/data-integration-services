package com.aimspeed.solr.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aimspeed.common.ReflectUtils;
import com.aimspeed.common.constants.RepositorySearchConstants;
import com.aimspeed.common.datatype.StringUtils;

/**
 * Solr工具类
 * @author AimSpeed
 */
public abstract class SolrUtils<T> {
	
	/**
	 * 日志记录
	 */
	private static Logger logger = LoggerFactory.getLogger(SolrUtils.class);
	
	/**
	 * SolrDocument转换为对象
	 * @author AimSpeed
	 * @param <T>
	 * @param solrDocument
	 * @return T 
	 */
	public static <T> T solrDocumentConvertToBean(Class<?> clazz,SolrDocument solrDocument){
        try {
        	T t = ReflectUtils.getEntityByGenericity(clazz);
        	if(t == null) {
        		logger.error("无法获取到泛型对象!");
        		return null;
        	}
			ReflectUtils.setFieldByMap(t, solrDocument.entrySet());
			return t;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * SolrDocumentList转换为对象
	 * @author AimSpeed
	 * @param <T>
	 * @param solrDocuments
	 * @return List<T> 
	 */
	public static <T> List<T> solrDocumentListConvertToBean(Class<?> clazz,List<SolrDocument> solrDocuments) {
		//转换对象
		List<T> result = new ArrayList<>();
		for (SolrDocument solrDocument : solrDocuments) {
			T t = solrDocumentConvertToBean(clazz,solrDocument);
			result.add(t);
		}
        return result;
	}
	
	/**
	 * 参数转换为查询语句
	 * @author AimSpeed
	 * @param params
	 * @param jointCondition
	 * @return String 
	 */
	public static String mapConvertToQString(Map<String, Object> params,String jointCondition) {
		String result = "";
		int count = 0;
		for (Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			key = StringUtils.lineToHump(key);
			
			Object value = entry.getValue();

			if(null == value || "".equals(value.toString().trim())) {
				continue;
			}
			
			result += key + ":" + value;
			
			//最后一个不用加
			if(count != params.size() - 1) {
				result += " " + jointCondition.toUpperCase() + " ";
			}
			count++;
		}
		return result;
	}

	/**
	 * 参数转换为查询语句
	 * @author AimSpeed
	 * @param params
	 * @param jointCondition
	 * @return String 
	 */
	public static String mapConvertToQStringOfLike(Map<String, Object> params,String jointCondition) {
		String result = "";
		int count = 0;
		for (Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			key = StringUtils.lineToHump(key);
			
			Object value = entry.getValue();
			
			if(null == value || "".equals(value.toString().trim())) {
				continue;
			}
			
			result += key + ":" + "*" + value + "*";
			
			//最后一个不用加
			if(count != params.size() - 1) {
				result += " " + jointCondition.toUpperCase() + " ";
			}
			count++;
		}
		return result;
	}
	
	/**
	 * 拼接查询语句字符串，如果没有条件，那么则查询*:*
	 * @author AimSpeed
	 * @param filtrate
	 * @param likeFiltrate void 
	 */
	public static String jointQueryStr(Map<String, Object> filtrate, Map<String, Object> likeFiltrate) {
		//拼接查询字符串
		String query = "";
		if(null != filtrate && filtrate.size() > 0) {
			query += SolrUtils.mapConvertToQString(filtrate, RepositorySearchConstants.CONNECTOR_AND);
		}
		
		if(null != likeFiltrate && likeFiltrate.size() > 0) {
			String likeQuery = SolrUtils.mapConvertToQStringOfLike(likeFiltrate, RepositorySearchConstants.CONNECTOR_OR);
			
			//组装
			if(null != likeQuery && !"".equals(likeQuery.trim())) {
				if(!"".equals(query.trim())) {
					query += " " + RepositorySearchConstants.CONNECTOR_AND + " (";
				}
				query += likeQuery;
				query += ")";
			}
		}
		
		//默认查询
		if("".equals(query.trim())) {
			query = "*:*";
		}
		return query;
	}
	
}
