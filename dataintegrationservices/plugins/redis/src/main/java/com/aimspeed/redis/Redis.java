package com.aimspeed.redis;

import java.util.List;
import java.util.Map;

/**
 * Redis接口
 * @author AimSpeed
 */
public interface Redis {
	
	/**
	 * 存储K - V，不设置时间，默认为永久保存
	 * @author AimSpeed
	 * @param key
	 * @param value
	 * @return boolean 
	 */
	boolean set(String key, String value);  

	/**
	 * 存储K - V，不设置时间，默认为永久保存
	 * @author AimSpeed
	 * @param key
	 * @param value 自动转换为JSON
	 * @return boolean 
	 */
	boolean set(String key, Object value);  
	
	/**
	 * 存储K - V，并且设置存活时间 (秒)
	 * @author AimSpeed
	 * @param key
	 * @param value
	 * @param expire
	 * @return boolean 
	 */
	boolean set(String key, String value,long expire);  

	/**
	 * 存储K - V，并且设置存活时间 (秒)
	 * @author AimSpeed
	 * @param key
	 * @param value 自动转换为JSON
	 * @param expire
	 * @return boolean 
	 */
	boolean set(String key, Object value,long expire);  
    
	/**
	 * 存储K - 多V ，不设置时间，默认为永久保存
	 * @author AimSpeed
	 * @param key
	 * @param list
	 * @return boolean 
	 */
	<T> boolean setList(String key ,List<T> list);
	
	/**
	 * 存储K - 多V，并且设置存活时间 (秒)
	 * @author AimSpeed
	 * @param key
	 * @param list
	 * @param expire
	 * @return boolean 
	 */
	<T> boolean setList(String key ,List<T> list,long expire);
	
	/**
	 * 设置已存储的Key的存活时间 (秒)
	 * @author AimSpeed
	 * @param key
	 * @param expire
	 * @return boolean 
	 */
    boolean setKeyExpire(String key,long expire);  
  
    /**
     * 通过Key获取Value 
     * @author AimSpeed
     * @param key
     * @return String 
     */
    String get(String key);  
    
    /**
     * 通过Key获取Value，并转换成对应的对象
     * @author AimSpeed
     * @param key
     * @param clazz
     * @return T 
     */
    <T> T  get(String key,Class<T> clazz);
    
    /**
     * 通过Key获取Value，并转换成对应的对象
     * @author AimSpeed
     * @param key
     * @param clazz
     * @return List<T> 
     */
    <T> List<T> getList(String key,Class<T> clazz);  
      
    /**
     * 判断 key 是否在redis缓存中
     * @author AimSpeed
     * @param key
     * @return Boolean 
     */
     Boolean exists( String key);
    
    /**
     * 根据对应的key删除对应
     * @author AimSpeed
     * @param key void 
     */
    void del( String key);  
    
    /**
     * 批量删除 
     * @author AimSpeed
     * @param keys void 
     */
    void del(List<String> keys); 
	
    /**
     * 将对应的数据存储到Redis，以Hash的方式进行存储
     * @author AimSpeed
     * @param key
     * @param field
     * @param value
     * @return Boolean 
     */
     Boolean hset( String key,  String field,  String value);

    /**
     * 将对应的数据存储到Redis，以Hash的方式进行存储，对应的field的值为对象
     * @author AimSpeed
     * @param key
     * @param field
     * @param value
     * @return Boolean 
     */
     Boolean hset(String key, String field, Object value);
    
    /**
     * 获取到对应的key下的对应的属性数据
     * 如：hmap = Map.put("username","adminstractor")
     * 	    "adminstractor" = hget("hmap","username"); 
     * @author AimSpeed
     * @param key Hash数据对应的key
     * @param field key对应的value中的对应数据
     * @return String 
     */
     String hget( String key,  String field);

    /**
     * 获取到对应的key下的对应的属性数据，并且转换成为对应的类
     * 如：hmap = Map.put("username","adminstractor")
     * 		"adminstractor" = hget("hmap","username"); 
     * @author AimSpeed
     * @param key
     * @param field
     * @param clazz
     * @return T 
     */
     <T> T hget(String key, String field, Class<T> clazz);

    /**
     * 根据key获取到所有的Hash的值
     * @author AimSpeed
     * @param key
     * @return Map<String,String> 
     */
    Map<String, String> hgetAll(String key);
    
    /**
     * 通过key获取到所有的Hash - Vlaue将对应的Value转换为对应的对象
     * @author AimSpeed
     * @param key
     * @param clazz
     * @return Map<String,?> 
     */
    <T> Map<String, ?> hgetAllObject( String key,  Class<T> clazz);
    
    /**
     * 是否存在这个属性
     * @author AimSpeed
     * @param key
     * @param field
     * @return Boolean 
     */
    Boolean hExists(String key, String field);

    /**
     * 删除对应的属性
     * @author AimSpeed
     * @param key
     * @param field
     * @return Long 
     */
    Long hDel(String key, String field);
    
    /**
     * 根据Key进行刷新
     * @author AimSpeed
     * @param key
     * @param second void 
     */
    void refresh( String key,long second);
    
}
