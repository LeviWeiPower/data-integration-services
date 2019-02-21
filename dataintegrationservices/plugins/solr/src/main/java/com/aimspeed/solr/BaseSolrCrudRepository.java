package com.aimspeed.solr;

import java.io.Serializable;

import org.springframework.data.solr.repository.SolrCrudRepository;

/**
 * 基础的Solr内部查询，只需要对应的接口继承
 * @author AimSpeed
 * @Project AIM_SPEED_SOLR 
 * @Package com.aimspeed.repository.solr
 * @FileName BaseSolrCrudRepository.java 
 * @ClassName BaseSolrCrudRepository 
 * @date 2018年7月29日 下午10:42:17 
 */
public interface BaseSolrCrudRepository<T extends Serializable, ID extends Serializable> extends SolrCrudRepository<T,ID>,BaseSolrMapper<T> {

}
