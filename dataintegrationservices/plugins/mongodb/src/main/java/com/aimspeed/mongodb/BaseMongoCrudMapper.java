package com.aimspeed.mongodb;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * MonogoDB内部提供的查询接口
 * @author AimSpeed
 */
public interface BaseMongoCrudMapper<T, ID extends Serializable> extends MongoRepository<T, ID> {

}
