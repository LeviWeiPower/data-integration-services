package com.aimspeed.operations.core.configuration.mysql;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/** 
 * @author: 苏志伟
 * 
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.aimspeed.operations.repository.mysql.*")
public class SessionFactoryConfig implements TransactionManagementConfigurer{
	
	@Autowired 
	private DataSource dataSource;

	private String typeAliasPackage = "com.aimspeed.operations.entity.bean.mysql";

	private String sqlXmlPath = "classpath:/mapper/**/*.xml";
	
	/** 
	*创建sqlSessionFactoryBean 实例 
	* 并且设置configtion 如驼峰命名.等等 
	* 设置mapper 映射路径 
	* 设置datasource数据源 
	* @return 
	* @throws Exception 
	*/
	@Bean(name = "sqlSessionFactory") 
	public SqlSessionFactoryBean createSqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean(); 
		//设置datasource
		sqlSessionFactoryBean.setDataSource(dataSource); 
		//设置typeAlias 包扫描路径
		sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasPackage); 
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();  
		//设置SQL文件路径
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources(sqlXmlPath));  
		return sqlSessionFactoryBean; 
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Override
	@Bean
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
}
