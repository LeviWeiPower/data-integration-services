package com.speed.klutz.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.speed.klutz.vo.TableInfoVo;

/**
 * 处理表信息
 * @author AimSpeed
 */
public abstract class TableUtils {
	
	
	/**
	 * 处理获取到的这张表名称，根据驼峰命名转为Java命名，Dao层
	 * @author AimSpeed
	 * @param tableName 表名
	 * @param beanSuffix Bean后缀
	 * @return TableMsgVo 
	 */
    public static TableInfoVo processTableNameOfDao(String sourceTableName,String ignorePrefix,String beanSuffix) {
    	String tableName = sourceTableName;
    	if(null != ignorePrefix && !"".equals(ignorePrefix)) {
    		tableName = sourceTableName.replace(ignorePrefix, "");
    	}
        StringBuffer sb = new StringBuffer(tableName.length());
        String tableNew = tableName.toLowerCase();//转换为小写
        String[] tables = tableNew.split("_");//驼峰规则
        
        String temp = null;
        
        for ( int i = 0 ; i < tables.length ; i++ ) {
            temp = tables[i].trim();
            //基于驼峰规则：首字母大写
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        if(null != beanSuffix && beanSuffix.length() > 0) {
        	return new TableInfoVo(sourceTableName,
    				sb.toString() + beanSuffix,
    				sb.toString() + beanSuffix + "Mapper");//表信息传输类;
        }
        return new TableInfoVo(sourceTableName,
				sb.toString(),
				sb.toString() + "Mapper");//表信息传输类;
    }
    
    /**
     * 处理获取到的这张表名称，根据驼峰命名转为Java命名，Service 
     * @author AimSpeed
     * @param tableName
     * @param beanSuffix
     * @param serviceSuffix
     * @return String 
     */
    public static String processTableNameOfService(String tableName,String ignorePrefix,String beanSuffix,String serviceSuffix) {
    	if(null != ignorePrefix && !"".equals(ignorePrefix)) {
    		tableName = tableName.replace(ignorePrefix, "");
    	}
        StringBuffer sb = new StringBuffer(tableName.length());
        String tableNew = tableName.toLowerCase();//转换为小写
        String[] tables = tableNew.split("_");//驼峰规则
        
        String temp = null;
        
        for ( int i = 0 ; i < tables.length ; i++ ) {
            temp = tables[i].trim();
            //基于驼峰规则：首字母大写
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        if(null != beanSuffix && beanSuffix.length() > 0) {
        	return sb.toString() + beanSuffix + serviceSuffix;//表信息传输类
        }
        return sb.toString() + serviceSuffix;//表信息传输类
    }
    
    /**
     * 处理获取到的这张表名称，根据驼峰命名转为Java命名，Controller
     * @author AimSpeed
     * @param tableName
     * @param controllerSuffix
     * @return String 
     */
    public static String processTableNameOfController(String tableName,String ignorePrefix,String controllerSuffix) {
    	if(null != ignorePrefix && !"".equals(ignorePrefix)) {
    		tableName = tableName.replace(ignorePrefix, "");
    	}
        StringBuffer sb = new StringBuffer(tableName.length());
        String tableNew = tableName.toLowerCase();//转换为小写
        String[] tables = tableNew.split("_");//驼峰规则
        
        String temp = null;
        
        for ( int i = 0 ; i < tables.length ; i++ ) {
            temp = tables[i].trim();
            //基于驼峰规则：首字母大写
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        return sb.toString() + controllerSuffix;//表信息传输类
    }
	
    /**
     * 获取到表注释
     * @author AimSpeed
     * @param conn
     * @return
     * @throws SQLException Map<String,String> 
     */
	public static Map<String, String> getTableComment(Connection conn) throws SQLException {
        Map<String, String> maps = new HashMap<String, String>();
        PreparedStatement pstate = conn.prepareStatement("show table status");
        ResultSet results = pstate.executeQuery();
        while ( results.next() ) {
            String tableName = results.getString("NAME");
            String comment = results.getString("COMMENT");
            maps.put(tableName, comment);
        }
        return maps;
    }

	/**
	 * 获取到所有数据库表名
	 * @author AimSpeed
	 * @param conn
	 * @return
	 * @throws SQLException List<String> 
	 */
	public static List<String> getTablesName(Connection conn) throws SQLException {
        List<String> tables = new ArrayList<String>();
        PreparedStatement pstate = conn.prepareStatement("show tables");
        ResultSet results = pstate.executeQuery();
        while ( results.next() ) {
            String tableName = results.getString(1);
            tables.add(tableName);
        }
        return tables;
    }
	
}
