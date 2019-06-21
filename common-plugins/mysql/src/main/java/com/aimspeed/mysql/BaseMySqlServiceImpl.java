package com.aimspeed.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aimspeed.common.ReflectUtils;
import com.aimspeed.common.enums.DefFieldNameEnum;
import com.aimspeed.common.enums.IsDeleteEnum;
import com.aimspeed.mysql.vo.PageVo;

/**
 * 自定义基础业务抽象实现类
 * @author AimSpeed
 */
public abstract class BaseMySqlServiceImpl<T extends BaseMySqlBean> implements BaseMySqlService<T> {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BaseMySqlMapper<T> baseMapper;

	/*
	 * 动态分页，筛选条件 - 默认为最新时间排序
	 * @author AimSpeed
	 * @param curPage
	 * @param pageSize
	 * @param orderField
	 * @param orderingRule
	 * @param filtrate
	 * @param likeFiltrate
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#selectPageSelective(int, int, java.lang.String, java.lang.String, java.util.Map, java.util.Map)
	 */
	@Override
	public PageVo<T> selectPageSelective(int curPage, int pageSize, String orderField, String orderingRule,
			Map<String,Object> filtrate,Map<String,Object> likeFiltrate) {
		PageVo<T> pageVo = new PageVo<>();
		pageVo.setCurPage(curPage);
		pageVo.setPageSize(pageSize);
		pageVo.setOrderField(orderField);
		pageVo.setOrderingRule(orderingRule);
		pageVo.setLikeFiltrate(likeFiltrate);
		pageVo.setFiltrate(filtrate);
		
		//查询出来的数据
		List<T> listTs = baseMapper.selectPageSelective(pageVo);
		PageVo<T> page = new PageVo<>();
		page.setFristPage(1);
		page.setTotalCount(this.selectDataCountSize(filtrate,pageVo.getLikeFiltrate()));//总条数
		page.setFiltrate(pageVo.getFiltrate());//保留筛选条件
		page.setPageSize(pageVo.getPageSize());
		page.setCurPage(pageVo.getCurPage());
		page.setTotalPage(page.getTotalPage() );
		page.setNextPage(page.getNextPage());
		page.setOrderField(pageVo.getOrderField());
		page.setOrderingRule(pageVo.getOrderingRule());
		page.setPageData(listTs);
		page.setPrePage(page.getPrePage());
		return page;
	}

	/*
	 * 根据对象的不为null的值作为条件进行查找，并且确定值找出一个值
	 * @author AimSpeed
	 * @param record
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#selectOnlyOfSelective(java.lang.Object)
	 */
	@Override
	public T selectOnlyOfSelective(T record) {
		return baseMapper.selectOnlyOfSelective(record);
	}
	
	/*
	 * 查询列表数据，但条件删除是N
	 * @author AimSpeed
	 * @param record
	 * @return
	 * @overridden @see com.aimspeed.analysis.service.BaseService#selectSelectiveOfIsDeleteN(com.aimspeed.analysis.entity.bean.BaseBean)
	 */
	@Override
	public List<T> selectSelectiveOfIsDeleteN(T record) {
		try {
			if(null == record) {
				record = (T) record.getClass().newInstance();
			}
			ReflectUtils.setFieldDefVal(record,DefFieldNameEnum.IS_DELETE.getValue(),IsDeleteEnum.N.getValue());
			return baseMapper.selectSelective(record);
		} catch (IllegalAccessException | NoSuchFieldException | SecurityException | IllegalArgumentException | InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 根据对象的不为null的值作为条件进行查找
	 * @author AimSpeed
	 * @param record
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#selectSelective(java.lang.Object)
	 */
	@Override
	public List<T> selectSelective(T record) {
		return baseMapper.selectSelective(record);
	}

	/*
	 * 动态分页，筛选条件 - 默认为最新时间排序
	 * @author AimSpeed
	 * @param pageVo
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#selectPageSelective(com.aimspeed.creeper.entity.vo.page.PageVo)
	 */
	@Override
	public List<T> selectPageSelective(PageVo<T> pageVo) {
		return baseMapper.selectPageSelective(pageVo);
	}

	/*
	 * 根据条件查找对应的统计数
	 * @author AimSpeed
	 * @param condition
	 * @param likeCondition
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#selectDataCountSize(java.util.Map, java.util.Map)
	 */
	@Override
	public Integer selectDataCountSize(Map<String,Object> condition, Map<String,Object> likeCondition) {
		return baseMapper.selectDataCountSize(condition,likeCondition);
	}

	/*
	 * 根据Id进行查询
	 * @author AimSpeed
	 * @param id
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#selectOfId(java.lang.Integer)
	 */
	@Override
	public T selectOfId(Integer id) {
		return baseMapper.selectOfId(id);
	}

	/*
	 * 根据多个Id进行查询
	 * @author AimSpeed
	 * @param id
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#selectOfIds(java.util.List)
	 */
	@Override
	public List<T> selectOfIds(List<Integer> id) {
		return baseMapper.selectOfIds(id);
	}

	/*
	 * 根据Id范围进查询
	 * @author AimSpeed
	 * @param lessId
	 * @param largeId
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#selectOfIdScopes(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<T> selectOfIdScopes(Integer lessId, Integer largeId) {
		return baseMapper.selectOfIdScopes(lessId,largeId);
	}

	 /*
	  * 根据多条件值进行查询
	  * @author AimSpeed
	  * @param record
	  * @return
	  * @overridden @see com.aimspeed.creeper.service.BaseService#selectSelectiveMany(java.util.List)
	  */
	@Override
	public List<T> selectSelectiveMany(List<T> record) {
		return baseMapper.selectSelectiveMany(record);
	}

	/*
	 * 根据条件进行查询去重
	 * @author AimSpeed
	 * @param record
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#selectSelectiveOfDistince(java.lang.Object)
	 */
	@Override
	public List<T> selectSelectiveOfDistince(T record) {
		return baseMapper.selectSelectiveOfDistince(record);
	}
	
	/*
	 * 根据多条件值进行查询去重
	 * @author AimSpeed
	 * @param record
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#selectSelectiveManyOfDistince(java.util.List)
	 */
	@Override
	public List<T> selectSelectiveManyOfDistince(List<T> record) {
		return baseMapper.selectSelectiveManyOfDistince(record);
	}

	/*
	 * 根据对象的不为null的值作为条件进行删除
	 * @author AimSpeed
	 * @param record
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#deleteSelective(java.lang.Object)
	 */
	@Override
	public Integer deleteSelective(T record) {
		return baseMapper.deleteSelective(record);
	}

	/*
	 * 根据Id进行删除
	 * @author AimSpeed
	 * @param id
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#deleteSelectiveOfId(java.lang.Integer)
	 */
	@Override
	public Integer deleteSelectiveOfId(Integer id) {
		return baseMapper.deleteSelectiveOfId(id);
	}

	/*
	 * 根据多个Id进行删除
	 * @author AimSpeed
	 * @param ids
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#deleteSelectiveOfIds(java.util.List)
	 */
	@Override
	public Integer deleteSelectiveOfIds(List<Integer> ids) {
		return baseMapper.deleteSelectiveOfIds(ids);
	}

	/*
	 * 添加数据，只添加不为null的数据
	 * @author AimSpeed
	 * @param record
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#insertSelective(java.lang.Object)
	 */
	@Override
	public Integer insertSelective(T record) {
		return baseMapper.insertSelective(record);
	}

	/*
	 * 在插入之前判断是否存在，存在则不插入
	 * @author AimSpeed
	 * @param record
	 * @return
	 * @overridden @see com.aimspeed.analysis.service.BaseService#insertSelectiveOfNotExist(java.lang.Object)
	 */
	@Override
	public Integer insertSelectiveOfNotExist( T record ) {
		List<T> exists = this.selectSelective(record);
		if(null == exists || exists.size() <= 0) {
			return this.insertSelective(record);
		}
		return 0;
	}

	/*
	 * 在插入之前判断是否存在，存在则不插入，查找条件是为未删除的数据
	 * @author AimSpeed
	 * @param record
	 * @return
	 * @overridden @see com.aimspeed.analysis.service.BaseService#insertSelectiveOfNotExist(java.lang.Object)
	 */
	@Override
	public Integer insertSelectiveByNotExistOfIsDeleteN( T record ) {
		List<T> exists = this.selectSelectiveOfIsDeleteN(record);
		if(null == exists || exists.size() <= 0) {
			return this.insertSelective(record);
		}
		return 0;
	}

	/*
	 * 批量添加数据，添加所有字段的数据
	 * @author AimSpeed
	 * @param record
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#batchInsert(java.util.List)
	 */
	@Override
	public Integer batchInsert(List<T> record) {
		return baseMapper.batchInsert(record);
	}

	/*
	 * 批量添加数据，添加动态字段的数据，建议每次100条
	 * @author AimSpeed
	 * @param record
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#batchInsertSelective(java.util.List)
	 */
	@Override
	public Integer batchInsertSelective(List<T> record) {
		return baseMapper.batchInsertSelective(record);
	}

	/*
	 * 更新不为null的数据，不会将其他字段更新为null
	 * @author AimSpeed
	 * @param updateRecord
	 * @param conditionRecord
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#updateSelective(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Integer updateSelective(T updateRecord, T conditionRecord) {
		return baseMapper.updateSelective(updateRecord,conditionRecord);
	}

	/*
	 * 根据Id作为条件进行更新
	 * @author AimSpeed
	 * @param updateRecord
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#updateSelectiveOfId(java.lang.Object)
	 */
	@Override
	public Integer updateSelectiveOfId(T updateRecord) {
		return baseMapper.updateSelectiveOfId(updateRecord);
	}

	/*
	 * 根据批量Id进行更新
	 * @author AimSpeed
	 * @param updateRecord
	 * @param ids
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#batchUpdateOfIds(java.lang.Object, java.util.List)
	 */
	@Override
	public Integer batchUpdateOfIds(T updateRecord, List<Integer> ids) {
		return baseMapper.batchUpdateOfIds(updateRecord,ids);
	}

	/*
	 * 是否存在
	 * @author AimSpeed
	 * @param t
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#isExists(java.lang.Object)
	 */
	@Override
	public boolean isExists(T t) {
		List<T> ts = baseMapper.selectSelective(t);
		if(null != ts && ts.size() > 0) {
			return true;
		}
		return false;
	}

	/*
	 * 根据批量Id进行更新
	 * @author AimSpeed
	 * @param updateRecord
	 * @param ids
	 * @param separator
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#batchUpdateOfIds(java.lang.Object, java.lang.String, java.lang.String)
	 */
	@Override
	public Integer batchUpdateOfIds(T updateRecord, String ids,String separator) {
		
		String [] idArr = ids.split(separator);
		List<Integer> idsList = new ArrayList<>();
		for (String string : idArr) {
			idsList.add(Integer.parseInt(string));
		}
		return baseMapper.batchUpdateOfIds(updateRecord,idsList);
	}

	/*
	 * 批量Id删除
	 * @author AimSpeed
	 * @param updateRecord
	 * @param ids
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#batchUpdateOfIds(java.lang.Object, java.lang.String[])
	 */
	@Override
	public Integer batchUpdateOfIds(T updateRecord, String[] ids) {
		List<Integer> idsList = new ArrayList<>();
		for (String string : ids) {
			idsList.add(Integer.parseInt(string));
		}
		return baseMapper.batchUpdateOfIds(updateRecord,idsList);
	}

	/*
	 * 根据条件逻辑删除
	 * @author AimSpeed
	 * @param conditionRecord
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.BaseService#logicDelete(java.lang.Object)
	 */
	@Override
	public Integer logicDelete(T conditionRecord) {
		
		try {
			T updateRecord = (T) conditionRecord.getClass().newInstance();
			ReflectUtils.setFieldDefVal(updateRecord,"isDelete","Y");
			return baseMapper.updateSelective(updateRecord, conditionRecord);
		} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
}
