package com.speed.klutz;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.speed.klutz.constants.DaoFileTypeContants;
import com.speed.klutz.utils.GenerateUtils;

/** 
 * 代码生成工具，只是用来生成基本的Dao、Service、Controller相关的文件。
 * 在生成Xml时，会在生成一个Customer的自定义文件，这个文件主要是用来写一些自定义的SQL
 * @author AimSpeed
 */
public class TestGenerate {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		
		//=============== 测试生成所有表
		//注：如果E:/TestGenerate 这个文件夹是不存在的话，而且报寻找不到文件夹，
		//那么则可能因为安全原因，无法再根下创建，要手动的创建这个文件夹，其下的子文件夹就不用创建了
//		GenerateUtils.generateControllerFile();
//		GenerateUtils.generateServiceFile();
//		GenerateUtils.generateDaoFile(DaoFileTypeContants.ALL);
		
		//=============== 测试生成指定的表信息
		List<String> tableNames = new ArrayList<>();
		tableNames.add("");
		tableNames.add("");
		
		GenerateUtils.generateControllerFile(tableNames);
		GenerateUtils.generateServiceFile(tableNames);
		GenerateUtils.generateDaoFile(tableNames, DaoFileTypeContants.BEAN);
		GenerateUtils.generateDaoFile(tableNames, DaoFileTypeContants.MAPPER);
		GenerateUtils.generateDaoFile(tableNames, DaoFileTypeContants.MAPPER_XML);
		
	}
	
}
