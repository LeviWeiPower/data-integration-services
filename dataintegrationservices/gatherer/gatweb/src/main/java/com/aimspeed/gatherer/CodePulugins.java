package com.aimspeed.gatherer;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.speed.klutz.constants.DaoFileTypeContants;
import com.speed.klutz.dao.DaoGenerator;
import com.speed.klutz.utils.GenerateUtils;


/**
 * 代码生成
 * @author AimSpeed
 */
public class CodePulugins {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		

		//要生成的表名
		List<String> tableNames = new ArrayList<>();
		
		//内容
//		tableNames.add("content_data");
//		tableNames.add("executed_url");

		//请求信息
//		tableNames.add("cookie");
//		tableNames.add("request_header");
//		tableNames.add("request_param");

		//规则
		tableNames.add("crawler");
//		tableNames.add("extract_rule");
//		tableNames.add("paging_index");
//		tableNames.add("common_rule");
		
//		GenerateUtils.generateControllerFile(tableNames);
//		GenerateUtils.generateServiceFile(tableNames);
		GenerateUtils.generateDaoFile(tableNames, DaoFileTypeContants.BEAN);
//		GenerateUtils.generateDaoFile(tableNames, DaoFileTypeContants.MAPPER);
		GenerateUtils.generateDaoFile(tableNames, DaoFileTypeContants.MAPPER_XML);
		
	}


}
