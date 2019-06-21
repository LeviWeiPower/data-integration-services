package com.speed.klutz.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.speed.klutz.GenerateImpl;
import com.speed.klutz.InitConnDatabase;
import com.speed.klutz.constants.DaoPropertiesConstants;
import com.speed.klutz.constants.OtherPropertiesConstants;
import com.speed.klutz.utils.TableUtils;
import com.speed.klutz.vo.TableInfoVo;

/**
 * Dao层文件生成者
 * @author AimSpeed
 */
public class DaoGenerator extends GenerateImpl {
	
	/**
	 * 生成数据库所有表，实体文件
	 * @author AimSpeed
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException void 
	 */
	public void generateTableForBean() throws ClassNotFoundException, SQLException, IOException{
		generateTable(null,true,false,false);
	}
	
	/**
	 * 生成数据库中指定的表，实体文件
	 * @author AimSpeed
	 * @param tableNames
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException void 
	 */
	public void generateTableForBean(List<String> tableNames) throws ClassNotFoundException, SQLException, IOException{
		generateTable(tableNames,true,false,false);
	}
	
	/**
	 * 生成数据库所有表，Mapper接口文件
	 * @author AimSpeed
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException void 
	 */
	public void generateTableForMapper() throws ClassNotFoundException, SQLException, IOException{
		generateTable(null,false,true,false);
	}
	
	/**
	 * 生成数据库中指定的表，Mapper接口文件
	 * @author AimSpeed
	 * @param tableNames
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException void 
	 */
	public void generateTableForMapper(List<String> tableNames) throws ClassNotFoundException, SQLException, IOException{
		generateTable(tableNames,false,true,false);
	}
	
	/**
	 * 生成数据库所有表，Xml文件
	 * @author AimSpeed
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException void 
	 */
	public void generateTableForMapperXml() throws ClassNotFoundException, SQLException, IOException{
		generateTable(null,false,false,true);
	}

	/**
	 * 生成数据库中指定的表，Xml文件
	 * @author AimSpeed
	 * @param tableNames
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException void 
	 */
	public void generateTableForMapperXml(List<String> tableNames) throws ClassNotFoundException, SQLException, IOException{
		generateTable(tableNames,false,false,true);
	}
	
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
	 * @param tableNames
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 * @overridden @see com.speed.klutz.Generate#generateTable(java.util.List)
	 */
	@Override
	public void generateTable(List<String> tableNames) throws ClassNotFoundException, IOException, SQLException {
		generateTable(tableNames,true,true,true);
	}
	
	/**
	 * 生成表，通过选择进行判断是需要生成什么文件
	 * @author AimSpeed
	 * @param tableNames
	 * @param generateEntity
	 * @param generateMapper
	 * @param generateXmlMapper
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException void 
	 */
	protected void generateTable(List<String> tableNames,
								  boolean generateEntity,
								  boolean generateMapper,
								  boolean generateXmlMapper) throws SQLException, ClassNotFoundException, IOException{
//		InitConnDatabase initConnDatabase = new InitConnDatabase();
		try {
		
			//初始化连接信息
//			Map<String, String> propertiesInfo = initConnDatabase.getProperties();
			
			//生成部分表
			if(null != tableNames && tableNames.size() > 0){
				//已经有了部分表名信息了，所以不需要获取表名信息
				generateTableForFile(tableNames,propertiesInfo,
										generateEntity,generateMapper,generateXmlMapper);
			
			}else{//生成全部表
				//获取到所有表信息
				List<String> tablesNames = TableUtils.getTablesName(getConn());
				generateTableForFile(tablesNames,propertiesInfo,
										generateEntity,generateMapper,generateXmlMapper);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("数据库连接信息有误！");
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("资源文件信息有误！");
		}
	}
	
	/**
	 * 根据需要生成的信息，生成对应的文件
	 * @author AimSpeed
	 * @param tablesNames
	 * @param propertiesInfo
	 * @param generateEntity
	 * @param generateMapper
	 * @param generateXmlMapper
	 * @throws SQLException
	 * @throws IOException void 
	 */
	private void generateTableForFile(List<String> tablesNames,
									  Map<String, String> propertiesInfo,
									  boolean generateEntity,
									  boolean generateMapper,
									  boolean generateXmlMapper) throws SQLException, IOException{

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
			
			//处理表名 - 生成Bean名称和Mapper接口名称
			TableInfoVo tableMsgVo = TableUtils.processTableNameOfDao(table,propertiesInfo.get(OtherPropertiesConstants.IGNORE_FILE_PREFIX),propertiesInfo.get(DaoPropertiesConstants.FILE_SUFFIX));
			tableMsgVo.setTableComment(tableComments.get(table));//表注释
			tableMsgVo.setBeanPackage(propertiesInfo.get(DaoPropertiesConstants.PACKAGE_BEAN));
			tableMsgVo.setMapperPackage(propertiesInfo.get(DaoPropertiesConstants.PACKAGE_MAPPER));
			tableMsgVo.setPagePackaging(propertiesInfo.get(DaoPropertiesConstants.PACKAGE_PAGING));
			
			
			DaoGenerate generateFile = null;
			//生成实体类文件
			if(generateEntity){
				generateFile = new EntityBeanOfGenerate();
				generateFile.generateFile(propertiesInfo.get(DaoPropertiesConstants.PATH_ENTITY), 
											columns, types, comments, tableMsgVo);
			}
			
			//生成接口映射文件
			if(generateMapper){
				generateFile = new MapperOfGenerate();
				generateFile.generateFile(propertiesInfo.get(DaoPropertiesConstants.PATH_MAPPER), 
											null, null, null, tableMsgVo);
			}
			
			//生成XmlSql文件
			if(generateXmlMapper){
				generateFile = new MapperXmlOfGenerate();
				generateFile.generateFile(propertiesInfo.get(DaoPropertiesConstants.PATH_MAPPER_XML), 
											columns, types, comments, tableMsgVo);
			}
		}
	
	}
	
}
