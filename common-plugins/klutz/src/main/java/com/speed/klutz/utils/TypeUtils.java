package com.speed.klutz.utils;

import com.speed.klutz.constants.DataTypeConstants;

/**
 * 类型转换工具类
 * @author AimSpeed
 */
public abstract class TypeUtils {

	/**
	 * 处理数据类型，转换为对应的Java类型
     * 	注意：数据库字段类型为 Bit -对应- Java类型为 Boolean
     * 	            数据库字段类型为 tinyint -对应- Java类型为 Integer
	 * @author AimSpeed
	 * @param type
	 * @return String 
	 */
    public static String processTypeToJava( String type ) {
    	//字符类型
        if ( type.indexOf(DataTypeConstants.CHAR) > -1 
        		|| type.indexOf(DataTypeConstants.VARCHAR) > -1 
        		|| type.indexOf(DataTypeConstants.LONGVARCHAR) > -1 
        		|| type.indexOf(DataTypeConstants.TEXT) > -1 
        		|| type.indexOf(DataTypeConstants.TEXT) > -1 
        		|| type.indexOf(DataTypeConstants.LONGTEXT) > -1 
        		|| type.indexOf(DataTypeConstants.MEDIUMTEXT) > -1 
        		|| type.indexOf(DataTypeConstants.TINYTEXT) > -1 ) {
            return "String";
        }
        
        if(type.indexOf(DataTypeConstants.BLOB) > -1 
	    		|| type.indexOf(DataTypeConstants.LONGBLOB) > -1 
	    		|| type.indexOf(DataTypeConstants.MEDIUMBLOB) > -1 
	    		|| type.indexOf(DataTypeConstants.TINYBLOB) > -1){
        	return "byte []";
        }
        
        //数值类型
        if(type.indexOf(DataTypeConstants.BIT) > -1){
        	return "Boolean";
        }else if(type.indexOf(DataTypeConstants.TINYINT) > -1){
        	return "Integer";
        	
        }else if(type.indexOf(DataTypeConstants.INT) > -1 || type.indexOf(DataTypeConstants.INTEGER) > -1){
        	return "Integer";
        
        }else if(type.indexOf(DataTypeConstants.BIGINT) > -1){
        	return "Long";
        	
        }else if(type.indexOf(DataTypeConstants.DECIMAL) > -1){
        	return "java.math.BigDecimal";
        	
        }else if(type.indexOf(DataTypeConstants.MEDIUMINT) > -1){
        	return "java.math.BigDecimal";
        
        }else if(type.indexOf(DataTypeConstants.FLOAT) > -1){
        	return "Float";
        	
        }else if(type.indexOf(DataTypeConstants.DOUBLE) > -1){
        	return "Double";
        }
        
        //日期类型
        switch (type) {
			case DataTypeConstants.DATE:
				return "java.sql.Date";
				
			case DataTypeConstants.TIME:
				return "java.sql.Time";
				
			case DataTypeConstants.YEAR:
				return "Integer";
				
			case DataTypeConstants.DATETIME:
				return "java.util.Date";
				
			case DataTypeConstants.TIMESTAMP:
				return "java.util.Date";
		}
        
        return null;
    }
    
    /**
     * 处理数据类型，将类型转换为对应的JDBC对应的类型
     * @author AimSpeed
     * @param type
     * @return String 
     */
    public static String processTypeToJdbc(String type){
    	if(type.equalsIgnoreCase(DataTypeConstants.BIGINT)){
    		return "BIGINT";
    	
    	}else if(type.equalsIgnoreCase(DataTypeConstants.TINYINT)){
    		return "TINYINT";
    	
    	}else if(type.equalsIgnoreCase(DataTypeConstants.SMALLINT)){
    		return "SMALLINT";
    	
    	}else if(type.equalsIgnoreCase(DataTypeConstants.MEDIUMINT)){
    		return "INTEGER";

    	}else if(type.equalsIgnoreCase(DataTypeConstants.INTEGER)){
    		return "INTEGER";
    	
    	}else if(type.equalsIgnoreCase(DataTypeConstants.INT)){
    		return "INTEGER";
    	
    	}else if(type.equalsIgnoreCase(DataTypeConstants.FLOAT)){
    		return "REAL";
    	
    	}else if(type.equalsIgnoreCase(DataTypeConstants.TEXT)){
    		return "LONGVARCHAR";

    	}else if(type.equalsIgnoreCase(DataTypeConstants.BLOB)){
    		return "BINARY";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.TINYTEXT)){
    		return "VARCHAR";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.TINYBLOB)){
    		return "BINARY";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.VARCHAR)){
    		return "VARCHAR";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.CHAR)){
    		return "CHAR";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.NUMERIC)){//====
    		return "DECIMAL";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.DECIMAL)){
    		return "DECIMAL";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.DOUBLE)){
    		return "DOUBLE";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.TIMESTAMP)){
    		return "TIMESTAMP";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.DATETIME)){
    		return "TIMESTAMP";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.YEAR)){
    		return "DATE";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.TIME)){
    		return "TIME";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.DATE)){
    		return "DATE";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.LONGTEXT)){
    		return "LONGVARCHAR";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.LONGBLOB)){
    		return "LONGVARBINARY";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.MEDIUMTEXT)){
    		return "LONGVARCHAR";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.MEDIUMBLOB)){
    		return "LONGVARBINARY";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.INT)){
    		type = "INTEGER";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.TEXT)){
    		type = "VARCHAR";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.LONGTEXT)){
    		type = "LONGVARCHAR";
    
    	}else if(type.equalsIgnoreCase(DataTypeConstants.BIGINT)){
    		type = "BIGINT";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.TINYINT)){
    		type = "LONGVARCHAR";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.SMALLINT)){//====
    		type = "LONGVARCHAR";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.LONGTEXT)){
    		type = "LONGVARCHAR";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.LONGTEXT)){
    		type = "LONGVARCHAR";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.LONGTEXT)){
    		type = "LONGVARCHAR";
    		
    	}else if(type.equalsIgnoreCase(DataTypeConstants.LONGTEXT)){
    		type = "LONGVARCHAR";
    		
    	}
    	return type;
    }
    
    /**
     * 处理数据类型，值留下类型   varchar(20) = varchar 
     * @author AimSpeed
     * @param type
     * @return String 
     */
    public static String processTypeOfClean(String type) {
    	type = type.replaceAll("([0-9])", "").replace("(", "").replace(")", "").replace(",", "").trim().toUpperCase();
    	return type;
    }
 
    
}
