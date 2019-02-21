package com.aimspeed.redis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.aimspeed.common.datatype.ByteUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Redis仓库操作
 * @author AimSpeed
 */
public class RedisMapper implements Redis {
	
	@Autowired  
    private RedisTemplate<String, ?> redisTemplate;
	
	/*
	 * 存储K - V，不设置时间，默认为永久保存
	 * @author AimSpeed
	 * @param key
	 * @param value
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#set(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean set(String key, String value) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() { 
            @Override  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                connection.set(serializer.serialize(key), serializer.serialize(value));  
                return true;  
            }  
        });  
        return result; 
	}

	/*
	 * 存储K - V，不设置时间，默认为永久保存
	 * @author AimSpeed
	 * @param key
	 * @param value
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#set(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean set(String key, Object value) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() { 
			@Override  
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
				connection.set(serializer.serialize(key), serializer.serialize(JSONObject.toJSONString(value)));  
				return true;  
			}  
		});  
		return result; 
	}
	
	/*
	 * 存储K - V，并且设置存活时间 (秒)
	 * @author AimSpeed
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#set(java.lang.String, java.lang.String, long)
	 */
	@Override
	public boolean set(String key, String value, long expire) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() { 
            @Override  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                connection.setEx(serializer.serialize(key),expire , serializer.serialize(value));
                return true;  
            }  
        });  
        return result; 
	}

	/*
	 * 存储K - V，并且设置存活时间 (秒)
	 * @author AimSpeed
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#set(java.lang.String, java.lang.String, long)
	 */
	@Override
	public boolean set(String key, Object value, long expire) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() { 
			@Override  
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
				connection.setEx(serializer.serialize(key),expire , serializer.serialize(JSONObject.toJSONString(value)));
				return true;  
			}  
		});  
		return result; 
	}
	
	/*
	 * 存储K - 多V ，不设置时间，默认为永久保存
	 * @author AimSpeed
	 * @param key
	 * @param list
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#setList(java.lang.String, java.util.List)
	 */
	@Override
	public <T> boolean setList(String key, List<T> list) {
		String value = JSON.toJSONString(list);  
        return set(key,value);  
	}

	/*
	 * 存储K - 多V，并且设置存活时间 (秒)
	 * @author AimSpeed
	 * @param key
	 * @param list
	 * @param expire
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#setList(java.lang.String, java.util.List, long)
	 */
	@Override
	public <T> boolean setList(String key, List<T> list, long expire) {
		String value = JSON.toJSONString(list);
		return set(key, value, expire);
	}

	/*
	 * 设置已存储的Key的存活时间 (秒)
	 * @author AimSpeed
	 * @param key
	 * @param expire
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#setKeyExpire(java.lang.String, long)
	 */
	@Override
	public boolean setKeyExpire(String key, long expire) {
		return redisTemplate.expire(key, expire, TimeUnit.SECONDS); 
	}

	/*
	 * 通过Key获取Value 
	 * @author AimSpeed
	 * @param key
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#get(java.lang.String)
	 */
	@Override
	public String get(String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {  
            @Override  
            public String doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] value =  connection.get(serializer.serialize(key));  
                return serializer.deserialize(value);  
            }  
        });  
        return result;  
	}

	/*
	 * 通过Key获取Value，并转换成对应的对象
	 * @author AimSpeed
	 * @param key
	 * @param clazz
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#get(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T get(String key, Class<T> clazz) {
		String json = get(key);  
        if(json!=null){  
            return JSON.parseObject(json, clazz);  
        }  
        return null;  
	}

	/*
	 * 通过Key获取Value，并转换成对应的对象
	 * @author AimSpeed
	 * @param key
	 * @param clazz
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#getList(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> List<T> getList(String key, Class<T> clazz) {
		String json = get(key);  
        if(json!=null){  
            return JSONArray.parseArray(json, clazz);  
        }  
        return null;  
	}
	
	/*
	 * 判断 key 是否在redis缓存中
	 * @author AimSpeed
	 * @param key
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#exists(java.lang.String)
	 */
	@Override
	public Boolean exists(String key) {
		String value = get(key);
		return null == value ? false : true; 
	}

	/*
	 * 根据对应的key删除对应
	 * @author AimSpeed
	 * @param key
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#del(java.lang.String)
	 */
	@Override
	public void del(String key) {
		redisTemplate.delete(key);
	}
	
	/*
	 * 批量删除 
	 * @author AimSpeed
	 * @param keys
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#del(java.util.List)
	 */
	@Override
	public void del(List<String> keys) {
		redisTemplate.delete(keys);
	}
	
	/*
	 * 将对应的数据存储到Redis，以Hash的方式进行存储
	 * @author AimSpeed
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#hset(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean hset(String key, String field, String value) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() { 
            @Override  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                connection.hSet(serializer.serialize(key), serializer.serialize(field), serializer.serialize(value));
                return true;  
            }  
        });  
		return result;
	}

	/*
	 * 将对应的数据存储到Redis，以Hash的方式进行存储，对应的field的值为对象
	 * @author AimSpeed
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#hset(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public Boolean hset(String key, String field, Object value) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() { 
            @Override  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                connection.hSet(serializer.serialize(key), serializer.serialize(field), serializer.serialize(JSON.toJSONString(value)));
                return true;  
            }  
        });  
		return result;
	} 
	
	/*
	 * 获取到对应的key下的对应的属性数据
     * 	如；hmap = Map.put("username","adminstractor")
     * 		"adminstractor" = hget("hmap","username"); 
	 * @author AimSpeed
	 * @param key
	 * @param field
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#hget(java.lang.String, java.lang.String)
	 */
	@Override
	public String hget(String key, String field) {
		String result = redisTemplate.execute(new RedisCallback<String>() { 
            @Override  
            public String doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] value = connection.hGet(serializer.serialize(key), serializer.serialize(field));
                return serializer.deserialize(value); 
            }  
        });  
        return result; 
	}

	/*
	 * 获取到对应的key下的对应的属性数据，并且转换成为对应的类
     * 	如；hmap = Map.put("username","adminstractor")
     * 		"adminstractor" = hget("hmap","username"); 
	 * @author AimSpeed
	 * @param key
	 * @param field
	 * @param clazz
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#hget(java.lang.String, java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T hget(String key, String field, Class<T> clazz) {
		String result = redisTemplate.execute(new RedisCallback<String>() { 
            @Override  
            public String doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] value = connection.hGet(serializer.serialize(key), serializer.serialize(field));
                return serializer.deserialize(value); 
            }  
        });  
		return null != result ? JSON.parseObject(result, clazz) : null;
	}

	/*
	 * 根据key获取到所有的Hash的值
	 * @author AimSpeed
	 * @param key
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#hgetAll(java.lang.String)
	 */
	@Override
	public Map<String, String> hgetAll(String key) {
		Map<String, String> result = redisTemplate.execute(new RedisCallback<Map<String, String>>() { 
            @Override  
            public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                Map<byte[], byte[]> map = connection.hGetAll(serializer.serialize(key));
                return ByteUtils.changeStringMap(map);
            }  
        });  
        return result; 
	}

	/*
	 * 通过key获取到所有的Hash - Vlaue将对应的Value转换为对应的对象
	 * @author AimSpeed
	 * @param key
	 * @param clazz
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#hgetAllObject(java.lang.String, java.lang.Class)
	 */
	@Override
	public  <T> Map<String, ?> hgetAllObject( String key,  Class<T> clazz) {
		Map<String, ?> result = redisTemplate.execute(new RedisCallback<Map<String, ?>>() { 
            @Override  
            public Map<String, ?> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                Map<byte[], byte[]> map = connection.hGetAll(serializer.serialize(key));
                return ByteUtils.changeMapValueClassForJson(map, clazz);
            }  
        });  
        return result; 
	}

	/*
	 * 是否存在这个属性
	 * @author AimSpeed
	 * @param key
	 * @param field
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#hExists(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean hExists(String key, String field) {
		Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() { 
            @Override  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                Boolean bool = connection.hExists(serializer.serialize(key), serializer.serialize(field));
                return bool;
            }  
        });  
        return result; 
	}

	/*
	 * 删除对应的属性
	 * @author AimSpeed
	 * @param key
	 * @param field
	 * @return
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#hDel(java.lang.String, java.lang.String)
	 */
	@Override
	public Long hDel(String key, String field) {
		Long result = redisTemplate.execute(new RedisCallback<Long>() { 
            @Override  
            public Long doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                Long long1 = connection.hDel(serializer.serialize(key), serializer.serialize(field));
                return long1;
            }  
        });  
        return result; 
	}

	/*
	 * 根据Key进行刷新
	 * @author AimSpeed
	 * @param key
	 * @param second
	 * @overridden @see com.aimspeed.creeper.service.redis.RedisBeanService#refresh(java.lang.String, long)
	 */
	@Override
	public void refresh(String key, long second) {
		String value = this.get(key);
		this.set(key, value,second);
	}

}
