package com.aimspeed.operations.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.aimspeed.common.ReflectUtils;
import com.aimspeed.common.enums.DefFieldNameEnum;
import com.aimspeed.mysql.BaseMySqlBean;
import com.aimspeed.mysql.BaseMySqlServiceImpl;
import com.aimspeed.mysql.vo.PageVo;
import com.aimspeed.solr.BaseSolrMapperImpl;

/**
 * 抽象的BaseService，主要是为了重写父类的BaseMysqlService
 * @author AimSpeed
 */
public abstract class AbstractBaseSolrService<M extends BaseMySqlBean,S extends Serializable> extends BaseMySqlServiceImpl<M> {
	
	@Autowired
	protected BaseSolrMapperImpl<S> baseSolrMapper;
	
	/*
	 * 分页查询改为Solr
	 * @param curPage
	 * @param pageSize
	 * @param orderField
	 * @param orderingRule
	 * @param filtrate
	 * @param likeFiltrate
	 * @return
	 * @overridden @see com.aimspeed.mysql.service.BaseMySqlServiceImpl#selectPageSelective(int, int, java.lang.String, java.lang.String, java.util.Map, java.util.Map)
	 */
	@Override
	public PageVo<M> selectPageSelective(int curPage, int pageSize, String orderField, String orderingRule,
			Map<String,Object> filtrate, Map<String,Object> likeFiltrate) {
		/*SolrPageVo<T> pageVo = new SolrPageVo<>();
		pageVo.setCurPage(curPage);
		pageVo.setPageSize(pageSize);
		pageVo.setOrderField(orderField);
		pageVo.setOrderingRule(orderingRule);
		pageVo.setLikeFiltrate(likeFiltrate);
		pageVo.setFiltrate(filtrate);*/
		//查询出来的数据
		com.aimspeed.solr.vo.PageVo<S> pageVo = baseSolrMapper.queryPageToHighlight(curPage, pageSize, orderField, orderingRule, filtrate, likeFiltrate);
			
		PageVo page = new PageVo<>();
		page.setFristPage(1);
		page.setTotalCount(pageVo.getTotalCount());//总条数
		page.setFiltrate(pageVo.getFiltrate());//保留筛选条件
		page.setPageSize(pageVo.getPageSize());
		page.setCurPage(pageVo.getCurPage());
		page.setTotalPage(page.getTotalPage() );
		page.setNextPage(page.getNextPage());
		page.setOrderField(pageVo.getOrderField());
		page.setOrderingRule(pageVo.getOrderingRule());
		page.setPageData(pageVo.getPageData());
		page.setPrePage(page.getPrePage());
		return page;
	}
	
	/*
	 * 重写插入
	 * @author AimSpeed
	 * @param record
	 * @return
	 * @overridden @see com.aimspeed.mysql.service.BaseMySqlServiceImpl#insertSelective(java.io.Serializable)
	 */
	@Override
	public Integer insertSelective(M record) {
		//获取到泛型对象
		S sRecord = ReflectUtils.getEntityByGenericity(this.getClass(),1);
		try {
			//复制对象数据
			sRecord = ReflectUtils.copyIdenticalFieldVal(record, sRecord);
			ReflectUtils.setFieldDefVal(sRecord,DefFieldNameEnum.ID.getValue(),UUID.randomUUID().toString());
			baseSolrMapper.insert(sRecord);
			return super.insertSelective(record);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/*
	 * 根据ID进行更新
	 * @author AimSpeed
	 * @param updateRecord
	 * @return
	 * @overridden @see com.aimspeed.mysql.service.BaseMySqlServiceImpl#updateSelectiveOfId(java.io.Serializable)
	 */
	@Override
	public Integer updateSelectiveOfId(M updateRecord) {
		S sRecord = ReflectUtils.getEntityByGenericity(this.getClass(),1);
		try {
			//复制对象数据
			sRecord = ReflectUtils.copyIdenticalFieldVal(updateRecord, sRecord);
			baseSolrMapper.updateById(sRecord);
			return super.updateSelectiveOfId(updateRecord);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/*
	 * 批量根据ID进行更新
	 * @author AimSpeed
	 * @param updateRecord
	 * @param ids
	 * @return
	 * @overridden @see com.aimspeed.mysql.service.BaseMySqlServiceImpl#batchUpdateOfIds(java.io.Serializable, java.util.List)
	 */
	@Override
	public Integer batchUpdateOfIds(M updateRecord, List<Integer> ids) {
		S sRecord = ReflectUtils.getEntityByGenericity(this.getClass(),1);
		try {
			sRecord = ReflectUtils.copyIdenticalFieldVal(updateRecord, sRecord);
			for (Integer id : ids) {
				ReflectUtils.setFieldDefVal(sRecord,DefFieldNameEnum.ID.getValue(),id);
				baseSolrMapper.updateById(sRecord);
			}
			return super.batchUpdateOfIds(updateRecord, ids);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
