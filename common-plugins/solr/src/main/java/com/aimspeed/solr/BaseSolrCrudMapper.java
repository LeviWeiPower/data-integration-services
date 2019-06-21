package com.aimspeed.solr;

import java.io.Serializable;

import org.springframework.data.solr.repository.SolrCrudRepository;

/**
 * 基础的Solr内部查询，只需要对应的接口继承
 * @author AimSpeed
 */
public interface BaseSolrCrudMapper<T extends Serializable, ID extends Serializable> extends SolrCrudRepository<T,ID>,BaseSolrMapper<T> {

}
