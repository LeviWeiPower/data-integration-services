package com.aimspeed.mongodb;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.aimspeed.mongodb.vo.PageVo;

/**
 * MongoDB操作，自定义基础类接口
 * @author AimSpeed
 */
public interface BaseMongoDBMapper<T extends BaseMongoBean> {
	
	//=============================== 查找 ===============================
	
	/**
	 * 按照条件查找，查找单个
	 * @author AimSpeed
	 * @param clazz
	 * @param filtrate
	 * @param likeFiltrate
	 * @return T 
	 */
	<T> T queryByOnly(T t,Class<T> clazz);
	
	/**
	 * 按照条件查找，查找单个
	 * @author AimSpeed
	 * @param clazz
	 * @param filtrate
	 * @param likeFiltrate
	 * @return T 
	 */
	<T> T queryByOnly(Class<T> clazz,Map<String, Object> filtrate, Map<String, Object> likeFiltrate);
	
	/**
	 * 按照条件查找
	 * @author AimSpeed
	 * @param clazz
	 * @param filtrate
	 * @param likeFiltrate
	 * @return List<T> 
	 */
	List<T> query(T t,Class<T> clazz);
	
	/**
	 * 按照条件查找
	 * @author AimSpeed
	 * @param clazz
	 * @param filtrate
	 * @param likeFiltrate
	 * @return List<T> 
	 */
	List<T> query(Class<T> clazz,Map<String, Object> filtrate, Map<String, Object> likeFiltrate);

	/**
	 * 分页查询，精确查找用and，模糊查询用or，有需要可重写
	 * @author AimSpeed
	 * @param curPage
	 * @param pageSize
	 * @param solrParams
	 * @return PageVo<T>  
	 */
	PageVo<T> queryPage(int curPage, int pageSize,
								String orderField,String orderingRule,
								Map<String, Object> filtrate,Map<String, Object> likeFiltrate);
	
	/**
	 * 分页查询，精确查找用and，模糊查询用or，有需要可重写
	 * @author AimSpeed
	 * @param pageVo
	 * @return MongoPageVo<T> 
	 */
	PageVo<T> queryPage(PageVo<T> pageVo);
	
	/**
	 * 查找数据量，精确查找用and，模糊查询用or，有需要可重写
	 * @author AimSpeed
	 * @param solrQuery
	 * @return Integer 
	 */
	Long countSize(Map<String, Object> condition,Map<String, Object> likeCondition);
	
	/**
	 * 按照条件查找数据是否存在
	 * @author AimSpeed
	 * @param clazz
	 * @param t
	 * @return boolean 
	 */
	boolean exists(T t,Class<T> clazz);
	
	//=============================== 更新 ===============================
	
	/**
	 * 根据Id单个更新，Id不存在则不更新
	 * @author AimSpeed
	 * @param record
	 * @return boolean  
	 */
	boolean updateById(T record);
	
	/**
	 * 批量更新，如果Id不存在则插入（无法保证Id不存在就不更新）
	 * @author AimSpeed
	 * @param records
	 * @return boolean  
	 */
	boolean batchUpdateBeanById(Collection<T> records);
	
	
	
	
	
	
}
