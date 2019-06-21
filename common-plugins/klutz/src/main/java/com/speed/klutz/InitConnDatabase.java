package com.speed.klutz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.speed.klutz.constants.DaoPropertiesConstants;
import com.speed.klutz.utils.GenerateUtils;
import com.speed.klutz.utils.PropertiesUtils;

/**
 * 初始化数据库连接
 * @author AimSpeed
 */
public class InitConnDatabase {
	
	/**
	 * 数据库链接
	 */
	private static Connection conn = null;
	
	/**
	 * 配置文件中的数据库链接的值
	 */
	private Map<String, String> properties = null;
	
	{
		try {
			//读取配置文件
			properties = readProperties();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//获取到
			Class.forName(properties.get(DaoPropertiesConstants.DRIVER_NAME_KEY));
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
        try {
			conn = DriverManager.getConnection(properties.get(DaoPropertiesConstants.DATABASE_URL), 
												properties.get(DaoPropertiesConstants.DATABASE_USERNAME), 
												properties.get(DaoPropertiesConstants.DATABASE_PASSWORD));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, String> getProperties() {
		return properties;
	}


	/**
	 * 读取配置文件
	 * @author AimSpeed
	 * @return
	 * @throws IOException Map<String,String> 
	 */
	public Map<String, String> readProperties() throws IOException{
		List<String> propertiesFilePaths = new ArrayList<>();
//		propertiesFilePaths.add(CommonConstants.DEF_FILE_PATH);
		propertiesFilePaths.add(GenerateUtils.SETTINGS_FILE_PATH.toString());
		PropertiesUtils.loadProperties(propertiesFilePaths);
		return PropertiesUtils.getPropertiesDatas();
	}

	public static Connection getConn() {
		return conn;
	}

	public static void setConn(Connection conn) {
		InitConnDatabase.conn = conn;
	}
	
}
