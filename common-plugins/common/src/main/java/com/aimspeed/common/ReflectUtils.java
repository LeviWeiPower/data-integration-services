package com.aimspeed.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.aimspeed.common.datatype.DataTypeChangeUtils;
import com.aimspeed.common.datatype.StringUtils;

/**
 * 反射工具类
 * @author AimSpeed
 */
public class ReflectUtils {
	
	/**
	 * 通过反射给对象设置默认值
	 * @author AimSpeed
	 * @param record
	 * @param fieldName
	 * @param fieldVal
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException void 
	 */
	public static void setFieldDefVal(Object record,String fieldName,Object fieldVal) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Class clazz = record.getClass();
//		Field field = clazz.getDeclaredField(fieldName);
		Field field = getField(clazz, fieldName);
		if(null != field) {
			field.setAccessible(true);
			Object value = field.get(record);
			if(null == value){
				//添加默认值
				field.set(record, DataTypeChangeUtils.changeType(field.getType().getSimpleName(), fieldVal.toString()));
		   	}
		}
	   
	}
	
	/**
	 * 根据反射类获取到字段信息
	 * @author AimSpeed
	 * @param clazz
	 * @param fieldName
	 * @return Field  
	 */
	public static Field getField(Class clazz,String fieldName) {
		//先在自身查找
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
//			e.printStackTrace();
		}
		
		//自身获取没有，则到父类查找
		if(null == field) {
			//获取是否有父类，如果有父类，则到父类查找。
			Class superclass = clazz.getSuperclass();
			if(null != superclass) {
				try {
					field = superclass.getDeclaredField(fieldName);
				} catch (NoSuchFieldException | SecurityException e) {
//					e.printStackTrace();
				}
			}
		}
		return field;
	}

	/**
	 * 根据类获取到字段信息
	 * @author AimSpeed
	 * @param clazz
	 * @param fieldName
	 * @return Field  
	 */
	public static Field getField(Object record,String fieldName) {
		//先在自身查找
		Class clazz = record.getClass();
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
//			e.printStackTrace();
		}
		
		//自身获取没有，则到父类查找
		if(null == field) {
			//获取是否有父类，如果有父类，则到父类查找。
			Class superclass = clazz.getSuperclass();
			if(null != superclass) {
				try {
					field = superclass.getDeclaredField(fieldName);
				} catch (NoSuchFieldException | SecurityException e) {
//					e.printStackTrace();
				}
			}
		}
		return field;
	}
	
	/**
	 * 将两个对象间相同属性名称的值复制到目标对象中
	 * @author AimSpeed
	 * @param valObj
	 * @param toObj
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException T  
	 */
	public static <V,T> T copyIdenticalFieldVal(V valObj,T toObj) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		//获取到两个对象的所有属性
		Class valClazz = valObj.getClass();
		Field [] valFields = valClazz.getDeclaredFields();
		
		Class toClazz = toObj.getClass();
		Field [] toFields = toClazz.getDeclaredFields();
		
		//查找出名称重合的属性名，并设置到目标对象中
		Map<String,Object> fieldNameValMap = new HashMap<>();
		
		for (Field valField : valFields) {
			String valFieldName = valField.getName();
			valField.setAccessible(true);
			Object value = valField.get(valObj);
			fieldNameValMap.put(valField.getType() + valFieldName,value);
		}

		//复制值
		for (Field toField : toFields) {
			//final值无法用反射操作
			boolean isFinal = Modifier.isFinal(toField.getModifiers());
			if(isFinal) {
				continue;
			}
			
			String toFieldName = toField.getName();
			Object val = fieldNameValMap.get(toField.getType() + toFieldName);
			
			if(null != val) {
				toField.setAccessible(true);
				toField.set(toObj, val);
			}
		}
		
		return toObj;
	   
	}
	
	/**
	 * 根据MapKey中对应的属性名给对象设置值
	 * @author AimSpeed
	 * @param record
	 * @param feildWithValue
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException void  
	 */
	public static void setFieldByMap(Object record,Map<String, ?> feildWithValue) throws IllegalArgumentException, IllegalAccessException{
		for (Entry<String, ?> entry : feildWithValue.entrySet()) {
			 try {
				setFieldDefVal(record,entry.getKey(),entry.getValue());
			} catch (NoSuchFieldException | SecurityException e) {
				//查找不到或因为final等原因等自动忽略
				//				e.printStackTrace();
				
			}
		}
	}
	
	/**
	 * 根据MapKey中对应的属性名给对象设置值
	 * @author AimSpeed
	 * @param record
	 * @param feildWithValue
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException void  
	 */
	public static void setFieldByMap(Object record,Set<Entry<String, Object>> feildWithValue) throws IllegalArgumentException, IllegalAccessException{
		for (Entry<String, ?> entry : feildWithValue) {
			 try {
				setFieldDefVal(record,entry.getKey(),entry.getValue());
			} catch (NoSuchFieldException | SecurityException e) {
				//查找不到或因为final等原因等自动忽略
				//				e.printStackTrace();
				
			}
		}
	}
	
	/**
	 * 根据泛型获取到实体类对象，默认获取第一个
	 * @author AimSpeed
	 * @param clazz
	 * @return T  
	 */
	public static <T> T getEntityByGenericity(Class clazz) {
		ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
		try {
			return ((Class<T>) type.getActualTypeArguments()[0]).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据泛型获取到实体类对象
	 * @author AimSpeed
	 * @param clazz
	 * @return T  
	 */
	public static <T> T getEntityByGenericity(Class clazz,int argumentIndex) {
		ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
		try {
			return ((Class<T>) type.getActualTypeArguments()[argumentIndex]).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将对象转换为Map<属性,值>
	 * @author AimSpeed
	 * @param record
	 * @param humpToLine 是否驼峰转换为下划线
	 * @return Map<String,Object> 
	 */
	public static Map<String, Object> objectConvertMap(Object record,boolean humpToLine) {
		Map<String, Object> result = new LinkedHashMap<>();
		if(null != record) {
			Class clazz = record.getClass();
			Field[] fields = clazz.getDeclaredFields();
			if(null != fields) {
				try {
					for (Field field : fields) {
						field.setAccessible(true);
						String name = field.getName();
						if(null != name && "serialVersionUID".equals(name.trim())) {
							continue;
						}
						Object value = field.get(record);
						//是否驼峰转换为下划线
						if(humpToLine) {
							name = StringUtils.humpToLine(name);
						}
						result.put(name, value);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 将对象转换为Map<属性,值>，只转换不为空的数据
	 * @author AimSpeed
	 * @param record
	 * @param humpToLine 是否驼峰转换为下划线
	 * @return Map<String,Object> 
	 */
	public static Map<String, Object> objectNotEmptyConvertMap(Object record,boolean humpToLine) {
		Map<String, Object> result = new LinkedHashMap<>();
		if(null != record) {
			Class clazz = record.getClass();
			Field[] fields = clazz.getDeclaredFields();
			if(null != fields) {
				try {
					for (Field field : fields) {
						field.setAccessible(true);
						String name = field.getName();
						if(null != name && "serialVersionUID".equals(name.trim())) {
							continue;
						}
						Object value = field.get(record);
						if(null == value || "".equals(value)) {
							continue;
						}
						//是否驼峰转换为下划线
						if(humpToLine) {
							name = StringUtils.humpToLine(name);
						}
						result.put(name, value);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 获取到类注解的字段和值
	 * @author AimSpeed
	 * @param t 要获取注解的类
	 * @param annotaionClazz 注解的Class 
	 * @return Map<String, Object> 字段 - 值，如果返回null，则这个类没有这个注解
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException Map<String,Object>
	 */
	public static <T,A> Map<String, Object> getAnnotationFieldWithValue(T t,Class annotaionClazz) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> clazz = t.getClass();
		return getAnnotationFieldWithValue(clazz, annotaionClazz);
	}
	
	/**
	 * 获取到类注解的字段和值
	 * @author AimSpeed
	 * @param t 要获取注解的类
	 * @param annotaionClazz 注解的Class 
	 * @return Map<String, Object> 字段 - 值，如果返回null，则这个类没有这个注解
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException Map<String,Object>
	 */
	public static <T,A> Map<String, Object> getAnnotationFieldWithValue(Class clazz,Class annotaionClazz) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Annotation declaredAnnotation = clazz.getDeclaredAnnotation(annotaionClazz);
		if(null == declaredAnnotation) {
			return null;
		}

		//代理获取到注解类
		InvocationHandler invocationHandler = Proxy.getInvocationHandler(declaredAnnotation);
		
		//获取到字段
        Field values = invocationHandler.getClass().getDeclaredField("memberValues");
        values.setAccessible(true);
        Map<String, Object> memberValues =(Map<String, Object>) values.get(invocationHandler);
        return memberValues;
	}
	
	/**
	 * 设置（修改）对象的注解的值
	 * @author AimSpeed
	 * @param t 对象类
	 * @param annotaionClazz 注解类
	 * @param field 字段
	 * @param value 值
	 * @return boolean 修改成功，修改失败
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException boolean
	 */
	public static <T,A> boolean setAnnotationFieldValue(T t,Class annotaionClazz,String field,Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> clazz = t.getClass();
		
		Annotation declaredAnnotation = clazz.getDeclaredAnnotation(annotaionClazz);
		if(null == declaredAnnotation) {
			return false;
		}

		//代理获取到注解类
		InvocationHandler invocationHandler = Proxy.getInvocationHandler(declaredAnnotation);
		
		//获取到字段
        Field values = invocationHandler.getClass().getDeclaredField("memberValues");
        values.setAccessible(true);
        Map<String, Object> memberValues =(Map<String, Object>) values.get(invocationHandler);
         
        //设置值
        memberValues.put(field, value);
		return true;
	}
}

