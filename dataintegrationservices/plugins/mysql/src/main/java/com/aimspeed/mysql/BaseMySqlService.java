package com.aimspeed.mysql;

import java.util.List;
import java.util.Map;

import com.aimspeed.mysql.vo.MySqlPageVo;

/**
 * 自定义基础业务接口
 * @author AimSpeed
 */
public interface BaseMySqlService<T> {

	
	/**
	 * 动态分页，筛选条件 - 默认为最新时间排序
	 * @author AimSpeed
	 * @param curPage 当前页
	 * @param pageSize 每页的数据条数
	 * @param orderField 排序字段
	 * @param orderingRule 排序规则：ASC / DESC
	 * @param filtrate 筛选信息 - 数据库字段名 = 值
	 * @param likeFiltrate 模糊筛选
	 * @return PageVo<T> 
	 */
	MySqlPageVo<T> selectPageSelective(int curPage, int pageSize,
									String orderField,String orderingRule,
									Map<String,Object> filtrate,Map<String,Object> likeFiltrate);
	 
	/**
	 * 根据对象的不为null的值作为条件进行查找，并且确定值找出一个值
	 * @author AimSpeed
	 * @param record
	 * @return T 
	 */
	T  selectOnlyOfSelective(T record);

	 /**
	  * 查询列表数据，但条件删除是N
	  * @author AimSpeed
	  * @param record
	  * @return List<T> 
	  */
	 List<T>  selectSelectiveOfIsDeleteN(T record);

	/**
	 * 根据对象的不为null的值作为条件进行查找
	 * @author AimSpeed
	 * @param record
	 * @return List<T> 
	 */
	 List<T>  selectSelective(T record);

	/**
	 * 动态分页，筛选条件 - 默认为最新时间排序
	 * @author AimSpeed
	 * @param pageVo
	 * @return List<T> 
	 */
	List<T>  selectPageSelective(MySqlPageVo<T> pageVo);

	/**
	 * 根据条件查找对应的统计数
	 * @author AimSpeed
	 * @param condition
	 * @param likeCondition
	 * @return Integer 
	 */
	Integer selectDataCountSize(Map<String,Object> condition,Map<String,Object> likeCondition);

	/**
	 * 根据Id进行查询
	 * @author AimSpeed
	 * @param id
	 * @return T 
	 */
	T  selectOfId(Integer id);

	/**
	 * 根据多个Id进行查询
	 * @author AimSpeed
	 * @param id
	 * @return List<T> 
	 */
	List<T>  selectOfIds(List<Integer> id);

	/**
	 * 根据Id范围进查询
	 * @author AimSpeed
	 * @param lessId
	 * @param largeId
	 * @return List<T> 
	 */
	List<T>  selectOfIdScopes(Integer lessId,Integer largeId);

	/**
	 * 根据多条件值进行查询
	 * @author AimSpeed
	 * @param record
	 * @return List<T> 
	 */
	List<T>  selectSelectiveMany(List<T> record);

	/**
	 * 根据条件进行查询去重
	 * @author AimSpeed
	 * @param record
	 * @return List<T> 
	 */
	List<T>  selectSelectiveOfDistince(T record);

	/**
	 * 根据多条件值进行查询去重
	 * @author AimSpeed
	 * @param record
	 * @return List<T> 
	 */
	List<T>  selectSelectiveManyOfDistince(List<T> record);

	/**
	 * 根据对象的不为null的值作为条件进行删除
	 * @author AimSpeed
	 * @param record
	 * @return Integer 
	 */
	Integer deleteSelective( T record );

	/**
	 * 根据Id进行删除
	 * @author AimSpeed
	 * @param id
	 * @return Integer 
	 */
	Integer deleteSelectiveOfId(Integer id);

	/**
	 * 根据多个Id进行删除
	 * @author AimSpeed
	 * @param ids
	 * @return Integer 
	 */
	Integer deleteSelectiveOfIds(List<Integer> ids);

	/**
	 * 添加数据，只添加不为null的数据
	 * @author AimSpeed
	 * @param record
	 * @return Integer 
	 */
	Integer insertSelective( T record );

	/**
	 * 在插入之前判断是否存在，存在则不插入
	 * @author AimSpeed
	 * @param record
	 * @return Integer 
	 */
	Integer insertSelectiveOfNotExist(T record );

	 /**
	  * 在插入之前判断是否存在，存在则不插入，查找条件是为未删除的数据
	  * @author AimSpeed
	  * @param record
	  * @return List<T> 
	  */
	Integer insertSelectiveByNotExistOfIsDeleteN(T record);
	
	/**
	 * 批量添加数据，添加所有字段的数据
	 * @author AimSpeed
	 * @param record
	 * @return Integer 
	 */
	Integer batchInsert(List<T> record);

	/**
	 * 批量添加数据，添加动态字段的数据，建议每次100条
	 * @author AimSpeed
	 * @param record
	 * @return Integer 
	 */
	Integer batchInsertSelective(List<T> record);

	/**
	 * 更新不为null的数据，不会将其他字段更新为null
	 * @author AimSpeed
	 * @param updateRecord
	 * @param conditionRecord
	 * @return Integer 
	 */
	Integer updateSelective(T updateRecord,T conditionRecord);

	/**
	 * 根据Id作为条件进行更新
	 * @author AimSpeed
	 * @param updateRecord
	 * @return Integer 
	 */
	Integer updateSelectiveOfId(T updateRecord);

	/**
	 * 根据批量Id进行更新
	 * @author AimSpeed
	 * @param updateRecord
	 * @param ids
	 * @return Integer 
	 */
	Integer batchUpdateOfIds(T updateRecord,List<Integer> ids);

	/**
	 * 根据批量Id进行更新
	 * @author AimSpeed
	 * @param updateRecord
	 * @param ids
	 * @param separator
	 * @return Integer 
	 */
	Integer batchUpdateOfIds(T updateRecord,String ids,String separator);

	/**
	 * 根据批量Id进行更新
	 * @author AimSpeed
	 * @param updateRecord
	 * @param ids
	 * @return Integer 
	 */
	Integer batchUpdateOfIds(T updateRecord,String [] ids);

	/**
	 * 是否存在
	 * @author AimSpeed
	 * @param t
	 * @return boolean 
	 */
	boolean isExists(T t);
	
	/**
	 * 根据条件逻辑删除
	 * @author AimSpeed
	 * @param conditionRecord
	 * @return Integer 
	 */
	Integer logicDelete(T conditionRecord);
	
	
	
}
