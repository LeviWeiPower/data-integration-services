package com.speed.klutz;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 文件生成的顶级接口
 * @author AimSpeed
 */
public interface Generate {
	
	/**
	 * 生成数据库所有表的所有该层的所有文件（如：实体文件，Mapper接口文件，Xml文件）
	 * @author AimSpeed
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException void 
	 */
	public void generateTable() throws ClassNotFoundException, IOException, SQLException;

	/**
	 * 生成数据库指定表的所有Mybatis的Dao层文件（实体文件，Mapper接口文件，Xml文件）
	 * @author AimSpeed
	 * @param tableNames
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException void 
	 */
	public void generateTable(List<String> tableNames) throws ClassNotFoundException, IOException, SQLException;
	
	
	
}
