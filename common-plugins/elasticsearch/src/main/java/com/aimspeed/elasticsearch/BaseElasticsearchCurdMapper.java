package com.aimspeed.elasticsearch;

import java.io.Serializable;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Elasticsearch内部提供的查询接口
 * @author AimSpeed
 */
@NoRepositoryBean
public interface BaseElasticsearchCurdMapper<T, ID extends Serializable> extends ElasticsearchCrudRepository<T, ID> {

}
