package com.speed.klutz.utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.speed.klutz.Generate;
import com.speed.klutz.constants.CommonConstants;
import com.speed.klutz.constants.DaoFileTypeContants;
import com.speed.klutz.controller.ControllerGenerator;
import com.speed.klutz.dao.DaoGenerator;
import com.speed.klutz.service.ServiceGenerator;

/**
 * 生成工具类
 * @author AimSpeed
 */
public class GenerateUtils {
	
	/**
	 * 配置文件路径，如果有必须可以修改
	 */
	public static String SETTINGS_FILE_PATH = CommonConstants.DEF_FILE_PATH;
	
	private static DaoGenerator daoGenerator = null;
	private static Generate serviceGenerator = null;
	private static Generate controllerGenerator = null;
	
	public static final String HINT_SUFFIX = "，生成成功！";
	
	//初始化对象
	static {
		daoGenerator = new DaoGenerator();
		serviceGenerator = new ServiceGenerator();
		controllerGenerator = new ControllerGenerator();
	}

	
	
	/**
	 * 按照数据库中的所有表，生成该层所需要的所有文件（Bean、Mapper、Xml）
	 * @author AimSpeed
	 * @param daoFileType 生成类型，{@link com.speed.klutz.constants.DaoFileTypeContants}
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void generateDaoFile(String daoFileType) throws ClassNotFoundException, SQLException, IOException {
		if(daoFileType.equals(DaoFileTypeContants.BEAN)) {
			//Bean文件
			daoGenerator.generateTableForBean();
			System.out.println(DaoFileTypeContants.BEAN + GenerateUtils.HINT_SUFFIX);
			
		}else if (daoFileType.equals(DaoFileTypeContants.MAPPER)) {
			//Mapper文件
			daoGenerator.generateTableForMapper();
			System.out.println(DaoFileTypeContants.MAPPER + GenerateUtils.HINT_SUFFIX);
			
		}else if (daoFileType.equals(DaoFileTypeContants.MAPPER_XML)) {
			//Xml文件
			daoGenerator.generateTableForMapperXml();
			System.out.println(DaoFileTypeContants.MAPPER_XML + GenerateUtils.HINT_SUFFIX);
			
		}else {
			//所有文件（Bean、Mapper、Xml）
			daoGenerator.generateTable();
			System.out.println("Bean、Mapper、Xml" + GenerateUtils.HINT_SUFFIX);
		}
	}

	/**
	 * 按照指定表，生成该层所需要的文件（Bean、Mapper、Xml）
	 * @author AimSpeed
	 * @param tableNames 要生成的表信息
	 * @param daoFileType 生成类型，{@link com.speed.klutz.constants.DaoFileTypeContants}
	 * @return boolean
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void generateDaoFile(List<String> tableNames,String daoFileType) throws ClassNotFoundException, SQLException, IOException {
		if(daoFileType.equals(DaoFileTypeContants.BEAN)) {
			//Bean文件
			daoGenerator.generateTableForBean(tableNames);
			System.out.println(DaoFileTypeContants.BEAN + GenerateUtils.HINT_SUFFIX);
			
		}else if (daoFileType.equals(DaoFileTypeContants.MAPPER)) {
			//Mapper文件
			daoGenerator.generateTableForMapper(tableNames);
			System.out.println(DaoFileTypeContants.MAPPER + GenerateUtils.HINT_SUFFIX);
			
		}else if (daoFileType.equals(DaoFileTypeContants.MAPPER_XML)) {
			//Xml文件
			daoGenerator.generateTableForMapperXml(tableNames);
			System.out.println(DaoFileTypeContants.MAPPER_XML + GenerateUtils.HINT_SUFFIX);
			
		}else {
			//所有文件（Bean、Mapper、Xml）
			daoGenerator.generateTable(tableNames);
			System.out.println("Bean、Mapper、Xml" + GenerateUtils.HINT_SUFFIX);
		}
	}
	
	/**
	 * 按照数据库中的所有表，生成该层所需要的所有文件
	 * @author AimSpeed
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException void
	 */
	public static void generateServiceFile() throws ClassNotFoundException, IOException, SQLException {
		serviceGenerator.generateTable();
		System.out.println("Service" + GenerateUtils.HINT_SUFFIX);
	}
	
	/**
	 * 按照指定表，生成该层所需要的文件
	 * @author AimSpeed
	 * @param tableNames
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException void
	 */
	public static void generateServiceFile(List<String> tableNames) throws ClassNotFoundException, IOException, SQLException {
		serviceGenerator.generateTable(tableNames);
		System.out.println("Service" + GenerateUtils.HINT_SUFFIX);
	}

	/**
	 * 按照数据库中的所有表，生成该层所需要的所有文件
	 * @author AimSpeed
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException void
	 */
	public static void generateControllerFile() throws ClassNotFoundException, IOException, SQLException {
		controllerGenerator.generateTable();
		System.out.println("Controller" + GenerateUtils.HINT_SUFFIX);
	}
	
	/**
	 * 按照指定表，生成该层所需要的文件
	 * @author AimSpeed
	 * @param tableNames
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException void
	 */
	public static void generateControllerFile(List<String> tableNames) throws ClassNotFoundException, IOException, SQLException {
		controllerGenerator.generateTable(tableNames);
		System.out.println("Controller" + GenerateUtils.HINT_SUFFIX);
	}
	
	
	
}
