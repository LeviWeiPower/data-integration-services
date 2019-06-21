package com.aimspeed.gatherer.core.configuration.mysql;

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
 * Seesion工厂
 * @author AimSpeed
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.aimspeed.gatherer.repository.mysql.*")
public class SessionFactoryConfig implements TransactionManagementConfigurer{
	
	@Autowired 
	private DataSource dataSource;

	private String typeAliasPackage = "com.aimspeed.gatherer.entity.bean.mysql";

	private String sqlXmlPath = "classpath:/mapper/**/*.xml";
	
	/**
	 * 创建sqlSessionFactoryBean 实例 
	 * 并且设置configtion 如驼峰命名.等等 
	 * 设置mapper 映射路径 
	 * 设置datasource数据源 
	 * @author AimSpeed
	 * @return
	 * @throws Exception SqlSessionFactoryBean 
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

	@Bean
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
}
