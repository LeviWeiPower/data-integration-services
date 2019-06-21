package com.aimspeed.mysql;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aimspeed.mysql.vo.PageVo;

/**
 * MySQL数据库Mybatis操作接口
 * @author AimSpeed
 */
public interface BaseMySqlMapper<T extends BaseMySqlBean> {
	
	/**
	 * 
	 * 根据对象的不为null的值作为条件进行查找，并且确定值找出一个值
	 * 
	 */
	 T  selectOnlyOfSelective(T record);

	/**
	 * 
	 * 根据对象的不为null的值作为条件进行查找
	 * 
	 */
	 List<T>  selectSelective(T record);

	/**
	 * 
	 * 动态分页，筛选条件 - 默认为最新时间排序
	 * 
	 */
	 List<T>  selectPageSelective(@Param("pageVo") PageVo<T> pageVo);

	/**
	 * 
	 * 查找数据总记录数
	 * 
	 */
	Integer selectDataCountSize(@Param("condition") Map<String,Object> condition,@Param("likeCondition")Map<String,Object> likeCondition);

	/**
	 * 
	 * 根据Id进行查询
	 * 
	 */
	T  selectOfId(@Param("id") Integer id);

	/**
	 * 
	 * 根据多个Id进行查询
	 * 
	 */
	List<T>  selectOfIds(@Param("ids") List<Integer> id);

	/**
	 * 
	 * 根据Id范围进查询
	 * 
	 */
	List<T>  selectOfIdScopes(@Param("lessId") Integer lessId,@Param("largeId")Integer largeId);

	/**
	 * 
	 * 根据多条件值进行查询
	 * 
	 */
	List<T>  selectSelectiveMany(@Param("batchField") List<T> record);

	/**
	 * 
	 * 根据条件进行查询去重
	 * 
	 */
	List<T>  selectSelectiveOfDistince(T record);

	/**
	 * 
	 * 根据多条件值进行查询去重
	 * 
	 */
	List<T>  selectSelectiveManyOfDistince(@Param("batchBeans") List<T> record);

	/**
	 * 
	 * 根据对象的不为null的值作为条件进行删除
	 * 
	 */
	Integer deleteSelective( T record );

	/**
	 * 
	 * 根据Id进行删除
	 * 
	 */
	Integer deleteSelectiveOfId(@Param("id") Integer id);

	/**
	 * 
	 * 根据多个Id进行删除
	 * 
	 */
	Integer deleteSelectiveOfIds(@Param("ids") List<Integer> ids);

	/**
	 * 
	 * 添加数据，只添加不为null的数据
	 * 
	 */
	Integer insertSelective( T record );

	/**
	 * 
	 * 批量添加数据，添加所有字段的数据
	 * 
	 */
	Integer batchInsert(@Param("batchField") List<T> record);

	/**
	 * 
	 * 批量添加数据，添加动态字段的数据，建议每次100条
	 * 
	 */
	Integer batchInsertSelective(@Param("batchField") List<T> record);

	/**
	 * 
	 * 更新不为null的数据，不会将其他字段更新为null
	 * 
	 */
	Integer updateSelective(@Param("updateRecord") T updateRecord,@Param("conditionRecord") T conditionRecord);


	/**
	 * 
	 * 根据Id作为条件进行更新
	 * 
	 */
	Integer updateSelectiveOfId(@Param("updateRecord") T updateRecord);


	/**
	 * 
	 * 根据批量Id进行更新
	 * 
	 */
	Integer batchUpdateOfIds(@Param("updateRecord") T updateRecord,@Param("ids") List<Integer> ids);

	
}
