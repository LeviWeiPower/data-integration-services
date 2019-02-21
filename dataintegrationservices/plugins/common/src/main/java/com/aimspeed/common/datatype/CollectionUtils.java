package com.aimspeed.common.datatype;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 集合数据工具类
 * @author AimSpeed
 */
public abstract class CollectionUtils {

	/**
	 * 根据索引位置获取到对应的Value
	 * @author AimSpeed
	 * @param maps 需要获取的Map
	 * @param index 索引位置
	 * @return K 返回的Value
	 */
	public static <K, V>K getKey(Map<K,V> maps,Integer index){
		Integer foreach = 0;
		for (Entry<K, V> entry : maps.entrySet()) {
			if(foreach.equals(index)){
				return entry.getKey();
			}
			foreach++;
		}
		
		return null;
	}
	
	/**
	 * 删除重复的数据
	 * @author AimSpeed
	 * @param duplicate 需要去重的List
	 * @return List<K> 去重后的List
	 */
	public static <K>List<K> removeDuplicate(List<K> duplicate){
		 Set<K> set=new LinkedHashSet<>();         
	     set.addAll(duplicate);//给set填充         
	     duplicate.clear();//清空list，不然下次把set元素加入此list的时候是在原来的基础上追加元素的         
	     duplicate.addAll(set);//把set的     
		return duplicate;
	}
	

    /**
     * 通过一个value值来模糊获取一个map中的key
     * @author AimSpeed
     * @param resour Map数据
     * @param value 需要提取的Value对应的key
     * @return String 结果
     */
    public static String getKeyByLikeValue(Map<String, String> resour, String value) {
        if (resour == null || resour.size() == 0 || org.apache.commons.lang3.StringUtils.isBlank(value)) {
            return null;
        }
        String key = null;
        String val = "";
        for (String k : resour.keySet()) {
            if (value.equals(resour.get(k))) {
                return k;
            }
            String v = StringUtils.getMaxLenStr(resour.get(k), value);
            if (v.length() > val.length()) {
                key = k;
                val = v;
            }
        }
        return key;
    }

    /**
     * 删除ArrayList中重复元素，保持顺序 
     * @author AimSpeed
     * @param list 需要执行的List
     * @return List<String> 结果
     */
    public static List<String> removeDuplicateWithOrder(List<String> list) {
        Set<String> set = new HashSet<String>();
        List<String> newList = new ArrayList<String>();
        for (Iterator<String> iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element.toString())) {
                newList.add(element.toString());
            }
        }
        list.clear();
        list.addAll(newList);
        return list;
    }
    
}
