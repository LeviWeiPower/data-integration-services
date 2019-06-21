package com.aimspeed.mongodb;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * MonogoDB内部提供的查询接口
 * @author AimSpeed
 */
@NoRepositoryBean
public interface BaseMongoDBCrudMapper<T, ID extends Serializable> extends MongoRepository<T, ID> {

}
