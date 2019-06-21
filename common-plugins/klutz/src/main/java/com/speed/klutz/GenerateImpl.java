package com.speed.klutz;

import java.sql.Connection;
import java.util.Map;

import com.speed.klutz.constants.AnnotationPropertiesConstants;
import com.speed.klutz.vo.ClassAnnotationInfoVo;

/**
 * 生成文件基础类
 * @author AimSpeed
 */
public abstract class GenerateImpl implements Generate {

	private Connection conn;
	
	protected InitConnDatabase initConnDatabase;
	
	protected Map<String, String> propertiesInfo;
	
	/**
	 * 注释信息是所有都用到的，所以就直接做成一个静态
	 */
	public static ClassAnnotationInfoVo classAnnotationInfoVo;
	
	
	/**
	 * 一旦有要生成信息，则必然会触发到此，那么则初始化一些信息
	 */
	public GenerateImpl() {
		super();
		//初始化数据库链接
		initConnDatabase = new InitConnDatabase();
		
		//连接
		conn = initConnDatabase.getConn();
		
		//配置文件信息
		propertiesInfo = initConnDatabase.getProperties();
		
		//把配置文件的注释信息注入到类中
		classAnnotationInfoVo = new ClassAnnotationInfoVo();
		classAnnotationInfoVo.setAuthor(propertiesInfo.get(AnnotationPropertiesConstants.AUTHOR));
		classAnnotationInfoVo.setDate(propertiesInfo.get(AnnotationPropertiesConstants.DATE));
	}



	public Connection getConn() {
		return conn;
	}
	
	
	
	

	
}
