package com.speed.klutz.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.speed.klutz.GenerateImpl;
import com.speed.klutz.constants.ControllerConstants;
import com.speed.klutz.constants.OtherPropertiesConstants;
import com.speed.klutz.utils.TableUtils;

/**
 * 控制层生成者
 * @author AimSpeed
 */
public class ControllerGenerator extends GenerateImpl {
	

	/*
	 * 生成所有表的所有Mybatis的Dao层文件（实体文件，Mapper接口文件，Xml文件）
	 * @author AimSpeed
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 * @overridden @see com.speed.klutz.Generate#generateTable()
	 */
	@Override
	public void generateTable() throws ClassNotFoundException, IOException, SQLException {
		generateTable(null);
		
	}

	/*
	 * 生成部分表的所有Mybatis的Dao层文件（实体文件，Mapper接口文件，Xml文件）
	 * @author AimSpeed
	 * @param tablesNames
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 * @overridden @see com.speed.klutz.Generate#generateTable(java.util.List)
	 */
	@Override
	public void generateTable(List<String> tablesNames) throws ClassNotFoundException, IOException, SQLException {
		
		//为空则是查所有
		if(null == tablesNames ) {
			tablesNames = TableUtils.getTablesName(getConn());
		}
		
//		InitConnDatabase initConnDatabase = new InitConnDatabase();
		
		//初始化连接信息
//		Map<String, String> propertiesInfo = initConnDatabase.getProperties();
		
		//获取到所有表的注解信息
		Map<String, String> tableComments = TableUtils.getTableComment(getConn());
		
		String prefix = "show full fields from "; //查找某张表的所有字段信息，字段名称、字段类型、字段备注
		List<String> columns = null;//字段名称
		List<String> types = null;//字段类型
		List<String> comments = null;//字段备注
		PreparedStatement pstate = null; 
		
		for (String table : tablesNames) {
		
			columns = new ArrayList<String>();
			types = new ArrayList<String>();
			comments = new ArrayList<String>();
			
			//查找某张表的所有字段信息，字段名称、字段类型、字段备注
			pstate = getConn().prepareStatement(prefix + table);
			
			ResultSet results = pstate.executeQuery();
			
			//获取到查询出来的表，字段名称、字段类型、字段备注等信息存放到集合中
			while ( results.next() ) {
				columns.add(results.getString("FIELD"));
				types.add(results.getString("TYPE"));
				comments.add(results.getString("COMMENT"));
			}
			
			
			//处理表名- 生成Controller名称
			String controllerName = TableUtils.processTableNameOfController(table,propertiesInfo.get(OtherPropertiesConstants.IGNORE_FILE_PREFIX), ControllerConstants.SUFFIX_NAME);
			
			ControllerGenerate controllerGenerate = new ControllerOfGenerate();
			controllerGenerate.generateFile(propertiesInfo.get(ControllerConstants.PATH), 
											controllerName, 
											propertiesInfo.get(ControllerConstants.PACKAGE), 
											tableComments.get(table));
			
			

		}
	}
	
}
