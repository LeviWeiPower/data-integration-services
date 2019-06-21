package com.speed.klutz.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.speed.klutz.utils.FieldUtils;
import com.speed.klutz.utils.TypeUtils;
import com.speed.klutz.vo.TableInfoVo;

/**
 * Mapper接口对应的XML生成类
 * @author AimSpeed
 */
public class MapperXmlOfGenerate implements DaoGenerate {
	
	/*
	 * 生成Mapper映射的Xml文件
	 * @author AimSpeed
	 * @param xmlPath
	 * @param columns
	 * @param types
	 * @param comments
	 * @param tableMsgVo
	 * @throws IOException
	 * @overridden @see com.speed.klutz.dao.DaoGenerate#generateFile(java.lang.String, java.util.List, java.util.List, java.util.List, com.speed.klutz.vo.TableMsgVo)
	 */
	@Override
	public void generateFile(String xmlPath,
								List<String> columns, 
								List<String> types, 
								List<String> comments, 
								TableInfoVo tableMsgVo) throws IOException{
		
		File folder = new File(xmlPath);
		
        //文件夹不存在则创建文件夹
        if ( !folder.exists() ) {
            folder.mkdirs();
        }
 
        //创建文件
        File mapperXmlFile = new File(xmlPath, tableMsgVo.getMapperName() + ".xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)));
        
        //Xml基础信息和字段信息
        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        bw.newLine();
        bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
        bw.newLine();
        bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        bw.newLine();
        bw.write("<mapper namespace=\"" + tableMsgVo.getMapperPackage() + "." + tableMsgVo.getMapperName() + "\">");
        bw.newLine();
        bw.newLine();
        
        //实体映射
        bw.write("\t<!--实体映射-->");
        bw.newLine();
        bw.write("\t<resultMap id=\"" + "BaseResultMap\" type=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();
        bw.write("\t\t<!--" + comments.get(0) + "-->");
        bw.newLine();
        
        //属性映射
        bw.write("\t\t<id column=\"" + columns.get(0) + "\" jdbcType=\"" + TypeUtils.processTypeToJdbc(TypeUtils.processTypeOfClean(types.get(0))).toUpperCase()  + "\" property=\"" + FieldUtils.ddatabaseFieldToJava(columns.get(0)) + "\" />");
        bw.newLine();
        int size = columns.size();
        //接下来的所有属性
        for ( int i = 1 ; i < size ; i++ ) {
            bw.write("\t\t<!--" + comments.get(i) + "-->");
            bw.newLine();
            
            bw.write("\t\t<id column=\"" + columns.get(i) 
            			+ "\" jdbcType=\"" + TypeUtils.processTypeToJdbc(TypeUtils.processTypeOfClean(types.get(i))).toUpperCase()
            			+ "\" property=\"" + FieldUtils.ddatabaseFieldToJava(columns.get(i)) + "\" />");

            bw.newLine();
        }
        
        bw.write("\t</resultMap>");
        bw.newLine();
        bw.newLine();
        bw.newLine();
 
        // 下面开始写SqlMapper中的方法
        // this.outputSqlMapperMethod(bw, columns, types);
        generateSql(bw, columns, types,tableMsgVo);
 
        bw.write("</mapper>");
        bw.flush();
        bw.close();						
		
        //生成自定义SQL文件
        generateCustomerFile(xmlPath, columns, types, comments, tableMsgVo);
        
	}
	
	/**
	 * 生成自定义的Xml文件，如果该文件已存在，则不生成，否则则生成
	 * @author AimSpeed
	 * @param xmlPath
	 * @param columns
	 * @param types
	 * @param comments
	 * @param tableMsgVo
	 * @throws IOException void
	 */
	public void generateCustomerFile(String xmlPath,
			List<String> columns, 
			List<String> types, 
			List<String> comments, 
			TableInfoVo tableMsgVo) throws IOException{
		
		//创建文件
        File mapperXmlCustomerFile = new File(xmlPath, tableMsgVo.getMapperName().split("Mapper")[0] + "Customer" + "Mapper" + ".xml");
        
        //文件已存在则不生成，因为这个是定制的SQL
        if(mapperXmlCustomerFile.exists()) {
        	return;
        }
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlCustomerFile)));
        
        //Xml基础信息和字段信息
        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        bw.newLine();
        bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
        bw.newLine();
        bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        bw.newLine();
        bw.write("<mapper namespace=\"" + tableMsgVo.getMapperPackage() + "." + tableMsgVo.getMapperName() + "\">");
        bw.newLine();
        bw.newLine();
        
        //实体映射
        bw.write("\t<!--实体映射-->");
        bw.newLine();
        bw.write("\t<resultMap id=\"" + "BaseResultCustomerMap\" type=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();
        bw.write("\t\t<!--" + comments.get(0) + "-->");
        bw.newLine();
        
        //属性映射
        bw.write("\t\t<id column=\"" + columns.get(0) + "\" jdbcType=\"" + TypeUtils.processTypeToJdbc(TypeUtils.processTypeOfClean(types.get(0))).toUpperCase()  + "\" property=\"" + FieldUtils.ddatabaseFieldToJava(columns.get(0)) + "\" />");
        bw.newLine();
        int size = columns.size();
        //接下来的所有属性
        for ( int i = 1 ; i < size ; i++ ) {
            bw.write("\t\t<!--" + comments.get(i) + "-->");
            bw.newLine();
            bw.write("\t\t<id column=\"" + columns.get(i) 
            			+ "\" jdbcType=\"" + TypeUtils.processTypeToJdbc(TypeUtils.processTypeOfClean(types.get(i))).toUpperCase()
            			+ "\" property=\"" + FieldUtils.ddatabaseFieldToJava(columns.get(i)) + "\" />");

            bw.newLine();
        }
        
        bw.write("\t</resultMap>");
        bw.newLine();
        bw.newLine();
        bw.newLine();
 
        bw.write("</mapper>");
        bw.flush();
        bw.close();						
	}
	
	/**
	 * 根据XML生成SQL
	 * @author AimSpeed
	 * @param bw
	 * @param columns
	 * @param types
	 * @param tableMsgVo
	 * @throws IOException void 
	 */
	private void generateSql(BufferedWriter bw, 
							List<String> columns, 
							List<String> types,
							TableInfoVo tableMsgVo) throws IOException {
        
		int size = columns.size();
		
        // 通用结果列
        bw.write("\t<!-- 通用的字段 -->");
        bw.newLine();
        bw.write("\t<sql id=\"Base_Column_List\">");
        bw.newLine();
 
        bw.write("\t\t " + columns.get(0) + ",");
        for ( int i = 1 ; i < size ; i++ ) {
            bw.write("\t" + columns.get(i));
            if ( i != size - 1 ) {
                bw.write(",");
            }
        }
 
        bw.newLine();
        bw.write("\t</sql>");
        bw.newLine();
        bw.newLine();
 
        //生成查询语句
        generateSelectSql(bw,columns,types,tableMsgVo);
        
        //生成删除语句
        generateDeleteSql(bw, columns, types, tableMsgVo);
        
        //生成插入语句
        generateInsertSql(bw, columns, types, tableMsgVo);
        
        //生成更新语句
        generateUpdateSql(bw, columns, types, tableMsgVo);
        
        
    }
	
	/**
	 * 生成查询SQL
	 * @author AimSpeed
	 * @param bw
	 * @param columns
	 * @param types
	 * @param tableMsgVo
	 * @throws IOException void 
	 */
	private void generateSelectSql(BufferedWriter bw, 
									List<String> columns, 
									List<String> types,
									TableInfoVo tableMsgVo) throws IOException{
		
		//============================== 查询 根据对象的值不为null的进行查找
        String tempField = null;
        bw.write("\t<!-- 根据对象的不为null的值作为条件进行查找，并且确定值找出一个值 -->");
        bw.newLine();
        
        //建立XML标签
        bw.write("\t<select id=\"selectOnlyOfSelective\" resultMap=\"BaseResultMap\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();
        
        //生成SQL
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM " + tableMsgVo.getTableName());
        bw.newLine();
        
        //条件
        bw.write("\t\t WHERE 1 = 1 ");
        bw.newLine();
        tempField = null;
        for ( int i = 0 ; i < columns.size() ; i++ ) {
        	
            tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
            bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
            bw.newLine();
            bw.write("\t\t\t\t and " + columns.get(i) + " = #{" + tempField + "," +  " jdbcType=" + TypeUtils.processTypeToJdbc(TypeUtils.processTypeOfClean(types.get(i))).toUpperCase() + "}");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        
        bw.write("\t<!-- 根据对象的不为null的值作为条件进行查找 -->");
        bw.newLine();
        
        //建立XML标签
        bw.write("\t<select id=\"selectSelective\" resultMap=\"BaseResultMap\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();
        
        //生成SQL
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM " + tableMsgVo.getTableName());
        bw.newLine();
        
        //条件
        bw.write("\t\t WHERE 1 = 1 ");
        bw.newLine();
        tempField = null;
        for ( int i = 0 ; i < columns.size() ; i++ ) {
        	
            tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
            bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
            bw.newLine();
            bw.write("\t\t\t\t and " + columns.get(i) + " = #{" + tempField + "," +  " jdbcType=" + TypeUtils.processTypeToJdbc(TypeUtils.processTypeOfClean(types.get(i))).toUpperCase() + "}");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        
        //============================== 查找 动态分页，筛选条件 - 默认为最新时间排序
        bw.write("\t<!-- 动态分页，筛选条件 - 默认为最新时间排序 -->");
        bw.newLine();
        
        //建立XML标签
        bw.write("\t<select id=\"selectPageSelective\" resultMap=\"BaseResultMap\" parameterType=\"" + tableMsgVo.getPagePackaging() + "\" >");
        bw.newLine();
        
        //生成SQL
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM " + tableMsgVo.getTableName());
        bw.newLine();
        
        //条件
        bw.write("\t\t <where> ");
        bw.newLine();
        bw.write("\t\t\t 1 = 1 ");
        bw.newLine();
        

        //绝对匹配筛选
        bw.write("\t\t\t <if test=\"" + "pageVo.filtrate" + " != null and pageVo.filtrate.size > 0\">");
        bw.newLine();
        bw.write("\t\t\t\t <foreach item=\"value\" index=\"key\" collection=\"pageVo.filtrate.entrySet()\" >");
        bw.newLine();
        bw.write("\t\t\t\t\t <if test=\"value != null and value != \'\' \">");
        bw.newLine();
        bw.write("\t\t\t\t\t\t and ${key} = #{value}");
        bw.newLine();
        bw.write("\t\t\t\t\t </if>");
        bw.newLine();
        bw.write("\t\t\t\t </foreach>");
        bw.newLine();
        bw.write("\t\t\t </if>");
        bw.newLine();
        bw.newLine();
        
        //模糊筛选
        bw.write("\t\t\t <if test=\"" + "pageVo.likeFiltrate" + " != null and pageVo.likeFiltrate.size > 0\">");
        bw.newLine();
        
        //单个模糊条件查询
        bw.write("\t\t\t\t <!-- 一个的情况下不用括号 -->");
        bw.newLine();
        bw.write("\t\t\t\t <if test=\"pageVo.likeFiltrate != null and pageVo.likeFiltrate.size == 1\">");
        bw.newLine();
        bw.write("\t\t\t\t\t <foreach item=\"value\" index=\"key\" collection=\"pageVo.likeFiltrate.entrySet()\" >");
        bw.newLine();
        bw.write("\t\t\t\t\t\t <if test=\"value != null and value != '' \">");
        bw.newLine();
        bw.write("\t\t\t\t\t\t\t or ${key} like CONCAT('%',#{value},'%')");
        bw.newLine();
        bw.write("\t\t\t\t\t\t </if>");
        bw.newLine();
        bw.write("\t\t\t\t\t </foreach>");
        bw.newLine();
        bw.write("\t\t\t\t </if>");
        bw.newLine();
        bw.newLine();
        
        //多个模糊条件查询
        bw.write("\t\t\t\t <!-- 2个或2个以上需要括号，第一个不需要加or -->");
        bw.newLine();
        bw.write("\t\t\t\t <if test=\"pageVo.likeFiltrate != null and pageVo.likeFiltrate.size > 1\">");
        bw.newLine();
        bw.write("\t\t\t\t\t <foreach item=\"value\" index=\"key\" collection=\"pageVo.likeFiltrate.entrySet()\" open=\"(\" close=\")\" separator=\" or \">");
        bw.newLine();
        bw.write("\t\t\t\t\t\t <if test=\"value != null and value != '' \">");
        bw.newLine();
        bw.write("\t\t\t\t\t\t\t ${key} like CONCAT('%',#{value},'%')");
        bw.newLine();
        bw.write("\t\t\t\t\t\t </if>");
        bw.newLine();
        bw.write("\t\t\t\t\t </foreach>");
        bw.newLine();
        bw.write("\t\t\t\t </if>");
        bw.newLine();
        
        /*bw.write("\t\t\t\t\t (");
        bw.newLine();
        bw.write("\t\t\t\t\t 1 = 1 ");
        bw.newLine();
        bw.write("\t\t\t\t <foreach item=\"value\" index=\"key\" collection=\"pageVo.likeFiltrate.entrySet()\" >");
        bw.newLine();
        bw.write("\t\t\t\t\t <if test=\"value != null and value != \'\' \">");
        bw.newLine();
        bw.write("\t\t\t\t\t\t or ${key} like CONCAT('%',#{value},'%')");
        bw.newLine();
        bw.write("\t\t\t\t\t </if>");
        bw.newLine();
        bw.write("\t\t\t\t </foreach>");
        bw.newLine();
        bw.write("\t\t\t\t\t )");
        bw.newLine();*/
        bw.write("\t\t\t </if>");
        bw.newLine();
        bw.newLine();
        bw.write("\t\t </where> ");
        bw.newLine();
        bw.newLine();
        
        //排序
        bw.write("\t\t\t<!-- 排序字段 -->");
        bw.newLine();
        bw.write("\t\t\t<if test=\"null != pageVo.orderField \">");
        bw.newLine();
        bw.write("\t\t\t\tORDER BY");
        bw.newLine();
        bw.write("\t\t\t\t${pageVo.orderField}");
        bw.newLine();
        bw.write("\t\t\t\t<!-- 排序规则 -->");
        bw.newLine();
        bw.write("\t\t\t\t<choose>");
        bw.newLine();
        bw.write("\t\t\t\t\t<when test=\"null != pageVo.orderingRule \">");
        bw.newLine();
        bw.write("\t\t\t\t\t${pageVo.orderingRule}");
        bw.newLine();
        bw.write("\t\t\t\t\t</when>");
        bw.newLine();
        bw.write("\t\t\t\t\t<otherwise>");
        bw.newLine();
        bw.write("\t\t\t\t\tDESC");
        bw.newLine();
        bw.write("\t\t\t\t\t</otherwise>");
        bw.newLine();
        bw.write("\t\t\t\t</choose>");
        bw.newLine();
        bw.write("\t\t\t</if>");
        bw.newLine();
        bw.newLine();
        
        //LIMIT分页
        bw.write("\t\tLIMIT #{pageVo.curPageCountSize},#{pageVo.pageSize}");
        bw.newLine();
        
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        
        //============================== 查找 数据总记录数 
        bw.write("\t<!-- 根据对象的不为null的值作为条件进行查找 -->");
        bw.newLine();

        //建立XML标签
        bw.write("\t<select id=\"selectDataCountSize\" resultType=\"" + "java.lang.Integer" + "\">");
        bw.newLine();

        //生成SQL
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t count(" + columns.get(0) + ")");
        bw.newLine();
        bw.write("\t\t FROM " + tableMsgVo.getTableName());
        bw.newLine();
        bw.write("\t\t <where> ");
        bw.newLine();
        bw.newLine();

        //模糊筛选
        bw.write("\t\t\t <if test=\"" + "likeCondition" + " != null\">");
        bw.newLine();
        bw.write("\t\t\t\t <foreach item=\"value\" index=\"key\" collection=\"likeCondition.entrySet()\" >");
        bw.newLine();
        bw.write("\t\t\t\t\t <if test=\"value != null and value != \'\' \">");
        bw.newLine();
        bw.write("\t\t\t\t\t\t or ${key} like CONCAT('%',#{value},'%')");
        bw.newLine();
        bw.write("\t\t\t\t\t </if>");
        bw.newLine();
        bw.write("\t\t\t\t </foreach>");
        bw.newLine();
        bw.write("\t\t\t </if>");
        bw.newLine();
        bw.newLine();
        
        bw.write("\t\t\t <if test=\"condition != null\">");
        bw.newLine();
        bw.write("\t\t\t\t <foreach item=\"value\" index=\"key\" collection=\"condition.entrySet()\" >");
        bw.newLine();
        bw.write("\t\t\t\t\t <if test=\"value != null and value != \'\' \">");
        bw.newLine();
        bw.write("\t\t\t\t\t\t and ${key} = #{value}");
        bw.newLine();
        bw.write("\t\t\t\t\t </if>");
        bw.newLine();
        bw.write("\t\t\t\t </foreach>");
        bw.newLine();
        bw.write("\t\t\t </if>");
        bw.newLine();
        bw.newLine();

        bw.write("\t\t\t and 1 = 1");
        bw.newLine();
        
        bw.write("\t\t </where> ");
        bw.newLine();
        bw.newLine();
        
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        
        //==============================  根据Id进行查询
        bw.write("\t<!-- 根据Id进行查询 -->");
        bw.newLine();
        
        //建立XML标签
        bw.write("\t<select id=\"selectOfId\" resultMap=\"BaseResultMap\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();
        
        //生成SQL
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM " + tableMsgVo.getTableName());
        bw.newLine();
        
        //条件
        bw.write("\t\t WHERE 1 = 1 ");
        bw.newLine();
        bw.write("\t\t <if test=\"id != null\">");
        bw.newLine();
        bw.write("\t\t\t and " + columns.get(0) + " = #{id, jdbcType=INTEGER}");
        bw.newLine();
        bw.write("\t\t </if>");
        bw.newLine();
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        
        //============================== 根据多Id进行查询
        bw.write("\t<!-- 根据多Id进行查询 -->");
        bw.newLine();
        
        //建立XML标签
        bw.write("\t<select id=\"selectOfIds\" resultMap=\"BaseResultMap\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();
        
        //生成SQL
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM " + tableMsgVo.getTableName());
        bw.newLine();
        
        //条件
        bw.write("\t\t WHERE 1 = 1 ");
        bw.newLine();
        bw.write("\t\t <if	test=\"ids != null and ids.size > 0\">");
        bw.newLine();
        bw.write("\t\t\t and " + columns.get(0) + " in ");
        bw.newLine();
        bw.write("\t\t\t <foreach collection =\"ids\" item=\"id\" index= \"index\" open=\"(\" separator=\",\" close=\")\">");
        bw.newLine();
        bw.write("\t\t\t\t #{id}");
        bw.newLine();
        bw.write("\t\t\t </foreach>");
        bw.newLine();
        bw.write("\t\t </if>");
        bw.newLine();
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        
        //============================== 根据Id范围进查询
        bw.write("\t<!-- 根据Id范围进查询 -->");
        bw.newLine();
        
        //建立XML标签
        bw.write("\t<select id=\"selectOfIdScopes\" resultMap=\"BaseResultMap\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();
        
        //生成SQL
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM " + tableMsgVo.getTableName());
        bw.newLine();
        
        //条件
        bw.write("\t\t WHERE 1 = 1 ");
        bw.newLine();
        bw.write("\t\t <if test=\"lessId != null\">");
        bw.newLine();
        bw.write("\t\t\t and " + columns.get(0) + " <![CDATA[ > ]]> #{lessId, jdbcType=INTEGER}");
        bw.newLine();
        bw.write("\t\t </if>");
        bw.newLine();
        bw.write("\t\t <if test=\"largeId != null\">");
        bw.newLine();
        bw.write("\t\t\t and " + columns.get(0) + " <![CDATA[ < ]]> #{largeId, jdbcType=INTEGER}");
        bw.newLine();
        bw.write("\t\t </if>");
        bw.newLine();
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        
        //============================== 根据多条件值进行查询
        bw.write("\t<!-- 根据多条件值进行查询 -->");
        bw.newLine();
        
        //建立XML标签
        bw.write("\t<select id=\"selectSelectiveMany\" resultMap=\"BaseResultMap\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();
        
        //生成SQL
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM " + tableMsgVo.getTableName());
        bw.newLine();
        
        //条件
        bw.write("\t\t WHERE 1 = 1 ");
        bw.newLine();
        bw.write("\t\t <foreach collection =\"batchField\" item=\"bean\" index= \"index\" >");
        bw.newLine();
        
        tempField = null;
        for ( int i = 0 ; i < columns.size() ; i++ ) {
        	
            tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
            bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
            bw.newLine();
            bw.write("\t\t\t\t and " + columns.get(i) + " = #{bean." + tempField + "," +  " jdbcType=" + TypeUtils.processTypeToJdbc(TypeUtils.processTypeOfClean(types.get(i))).toUpperCase() + "}");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
        bw.newLine();
        
        bw.write("\t\t </foreach>");
        bw.newLine();
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        
        //============================== 根据条件进行查询去重
        bw.write("\t<!-- 根据条件进行查询去重 -->");
        bw.newLine();
        
        //建立XML标签
        bw.write("\t<select id=\"selectSelectiveOfDistince\" resultMap=\"BaseResultMap\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();
        
        //生成SQL
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t DISTINCT <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM " + tableMsgVo.getTableName());
        bw.newLine();
        
        //条件
        bw.write("\t\t WHERE 1 = 1 ");
        bw.newLine();
        tempField = null;
        for ( int i = 0 ; i < columns.size() ; i++ ) {
        	
            tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
            bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
            bw.newLine();
            bw.write("\t\t\t\t and " + columns.get(i) + " = #{" + tempField + "," +  " jdbcType=" + TypeUtils.processTypeToJdbc(TypeUtils.processTypeOfClean(types.get(i))).toUpperCase() + "}");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        
        //============================== 根据多条件值进行去重查询
        bw.write("\t<!-- 根据多条件值进行去重查询 -->");
        bw.newLine();
        
        //建立XML标签
        bw.write("\t<select id=\"selectSelectiveManyOfDistince\" resultMap=\"BaseResultMap\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();
        
        //生成SQL
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t DISTINCT <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM " + tableMsgVo.getTableName());
        bw.newLine();
        
        //条件
        bw.write("\t\t WHERE 1 = 1 ");
        bw.newLine();
        bw.write("\t\t <foreach collection =\"batchField\" item=\"bean\" index= \"index\" >");
        bw.newLine();
        
        tempField = null;
        for ( int i = 0 ; i < columns.size() ; i++ ) {
        	
            tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
            bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
            bw.newLine();
            bw.write("\t\t\t\t and " + columns.get(i) + " = #{bean." + tempField + "," +  " jdbcType=" + TypeUtils.processTypeToJdbc(TypeUtils.processTypeOfClean(types.get(i))).toUpperCase() + "}");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
        bw.newLine();
        
        bw.write("\t\t </foreach>");
        bw.newLine();
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        
	}
	
	/**
	 * 生成删除语句
	 * @author AimSpeed
	 * @param bw
	 * @param columns
	 * @param types
	 * @param tableMsgVo
	 * @throws IOException void 
	 */
	private void generateDeleteSql(BufferedWriter bw, 
								List<String> columns, 
								List<String> types,
								TableInfoVo tableMsgVo) throws IOException{
		
		//==============================  删除  根据对象中不为null的值进行删除
		String tempField = null;
        bw.write("\t<!-- 根据对象中不为null的值进行删除 -->");
        bw.newLine();

        //建立XML标签
        bw.write("\t<delete id=\"deleteSelective\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();

        //生成SQL
        bw.write("\t\t DELETE FROM " + tableMsgVo.getTableName());
        bw.newLine();
        
        //条件
        bw.write("\t\t WHERE 1 = 1 ");
        bw.newLine();
        tempField = null;
        for ( int i = 0 ; i < columns.size() ; i++ ) {
            tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
            bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
            bw.newLine();
            bw.write("\t\t\t\t and " + columns.get(i) + " = #{" + tempField + "," +  " jdbcType=" + TypeUtils.processTypeToJdbc(TypeUtils.processTypeOfClean(types.get(i))).toUpperCase() + "}");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
        bw.write("\t</delete>");
        bw.newLine();
        bw.newLine();
        
        //==============================  根据Id进行删除
        bw.write("\t<!-- 根据对象中不为null的值进行删除 -->");
        bw.newLine();

        //建立XML标签
        bw.write("\t<delete id=\"deleteSelectiveOfId\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();

        //生成SQL
        bw.write("\t\t DELETE FROM " + tableMsgVo.getTableName());
        bw.newLine();
        //条件
        bw.write("\t\t WHERE 1 = 1 ");
        bw.newLine();
        bw.write("\t\t <if test=\"id != null\">");
        bw.newLine();
        bw.write("\t\t\t and " + columns.get(0) + " = #{id, jdbcType=INTEGER}");
        bw.newLine();
        bw.write("\t\t </if>");
        bw.newLine();
        bw.newLine();
        bw.write("\t</delete>");
        bw.newLine();
        
        //==============================  根据多个Id进行删除
        bw.write("\t<!-- 根据对象中不为null的值进行删除 -->");
        bw.newLine();

        //建立XML标签
        bw.write("\t<delete id=\"deleteSelectiveOfIds\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();

        //生成SQL
        bw.write("\t\t DELETE FROM " + tableMsgVo.getTableName());
        bw.newLine();
        //条件
        bw.write("\t\t WHERE 1 = 1 ");
        bw.newLine();
        bw.write("\t\t <if	test=\"ids != null and ids.size > 0\">");
        bw.newLine();
        bw.write("\t\t\t and " + columns.get(0) + " in ");
        bw.newLine();
        bw.write("\t\t\t <foreach collection =\"ids\" item=\"id\" index= \"index\" open=\"(\" separator=\",\" close=\")\">");
        bw.newLine();
        bw.write("\t\t\t\t #{id}");
        bw.newLine();
        bw.write("\t\t\t </foreach>");
        bw.newLine();
        bw.write("\t\t </if>");
        bw.newLine();
        bw.newLine();
        bw.write("\t</delete>");
        bw.newLine();
        
        bw.newLine();
        bw.newLine();
        
	}
	
	/**
	 * 生成插入语句
	 * @author AimSpeed
	 * @param bw
	 * @param columns
	 * @param types
	 * @param tableMsgVo
	 * @throws IOException void 
	 */
	private void generateInsertSql(BufferedWriter bw, 
									List<String> columns, 
									List<String> types,
									TableInfoVo tableMsgVo) throws IOException{
		//==============================  insert方法（匹配有值的字段）
		String tempField = null;
	    bw.write("\t<!-- 添加 （匹配有值的字段）并且返回添加之后的数据记录Id（直接调用回对应的插入类.id），\n\t\t  如果这个类没有id字段，那么请把Xml中的[useGeneratedKeys=true keyProperty=id keyColumn=id]这段代码删除-->");
	    bw.newLine();
	
	    //建立XML标签
	    bw.write("\t<insert id=\"insertSelective\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\" useGeneratedKeys=\"true\" keyProperty=\"" + columns.get(0) + "\" keyColumn=\"" + columns.get(0) + "\">");
	    bw.newLine();
	    bw.write("\t\t INSERT INTO " + tableMsgVo.getTableName());
	    bw.newLine();
	    bw.write("\t\t <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
	    bw.newLine();
	    
	    //插入的字段
	    tempField = null;
	    for ( int i = 0 ; i < columns.size() ; i++ ) {
	        tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
	        bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
	        bw.newLine();
	        bw.write("\t\t\t\t " + columns.get(i) + ",");
	        bw.newLine();
	        bw.write("\t\t\t</if>");
	            bw.newLine();
	        }
	 
	        bw.newLine();
	        bw.write("\t\t </trim>");
	        bw.newLine();
	 
	        //插入的数据
	    bw.write("\t\t <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
	        bw.newLine();
	 
	        //插入的数据
	    tempField = null;
	    for ( int i = 0 ; i < columns.size() ; i++ ) {
	        tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
	        bw.write("\t\t\t<if test=\"" + tempField + "!=null\">");
	        bw.newLine();
	        bw.write("\t\t\t\t #{" + tempField + "},");
	        bw.newLine();
	        bw.write("\t\t\t</if>");
	        bw.newLine();
	    }
	    bw.write("\t\t </trim>");
	    bw.newLine();
	    
	    bw.write("\t</insert>");
	    bw.newLine();
	    bw.newLine();
	    
	    //============================== 批量添加数据，添加所有字段的数据
	    bw.write("\t <!-- 批量添加数据，添加所有字段的数据-->");
	    bw.newLine();
	
	    //建立XML标签
	    int size = columns.size();
		
	    // 通用结果列
	    String columnsStr = "";
	    for ( int i = 0 ; i < size ; i++ ) {
	    	if("id".equals(columns.get(i))) {
	    		continue;
	    	}
	    	columnsStr += columns.get(i);
	        if ( i != size - 1 ) {
	        	columnsStr += ",";
	        }
	    }
	    
	    bw.write("\t <insert id=\"batchInsert\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
	    bw.newLine();
	    bw.write("\t\t INSERT INTO " + tableMsgVo.getTableName() + "("+columnsStr+")");
	    bw.newLine();
	    bw.write("\t\t VALUES");
	    bw.newLine();
	    
	    bw.write("\t\t <foreach collection =\"batchField\" item=\"bean\" index= \"index\" separator =\",\">");
	    bw.newLine();
	    
	    //循环字段
	    bw.write("\t\t\t (");
	    bw.newLine();
	    
	    tempField = null;
	    for ( int i = 0 ; i < columns.size() ; i++ ) {
	        tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
	        if("id".equals(tempField)) {
	        	continue;
	        }
	        if(i != columns.size()-1){
	        	bw.write("\t\t\t\t #{bean." + tempField + "},");
	        	bw.newLine();
	        }else{
	        	bw.write("\t\t\t\t #{bean." + tempField + "}");
	        	bw.newLine();
	        }
	        
	    }
	    
	    bw.write("\t\t\t )");
	    bw.newLine();
	    
	    bw.write("\t\t</foreach >");
	    bw.newLine();
	    bw.write("\t</insert>");
	    bw.newLine();
	    bw.newLine();
        
	    //==============================  insert方法（匹配有值的字段）
	    bw.write("\t<!-- 批量添加数据，添加动态字段的数据，建议每次100条 -->");
	    bw.newLine();
      
	    //建立XML标签
	    bw.write("\t<insert id=\"batchInsertSelective\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
	    bw.newLine();
	    bw.write("\t\t <foreach collection =\"batchField\" item=\"bean\" index= \"index\" separator =\",\">");
	    bw.newLine();
	    bw.write("\t\t\t INSERT INTO " + tableMsgVo.getTableName());
	    bw.newLine();
	    bw.write("\t\t\t <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
	    bw.newLine();
      
	    //插入的字段
	    tempField = null;
	    for ( int i = 0 ; i < columns.size() ; i++ ) {
	    	tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
	    	bw.write("\t\t\t\t<if test=\"bean." + tempField + " != null\">");
	    	bw.newLine();
	    	bw.write("\t\t\t\t\t " + columns.get(i) + ",");
	    	bw.newLine();
	    	bw.write("\t\t\t\t</if>");
    		bw.newLine();
    	}
   
        bw.newLine();
        bw.write("\t\t\t </trim>");
        bw.newLine();
   
        //插入的数据
        bw.write("\t\t\t <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
        bw.newLine();
   
        //插入的数据
        tempField = null;
        for ( int i = 0 ; i < columns.size() ; i++ ) {
        	tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
        	bw.write("\t\t\t\t<if test=\"bean." + tempField + "!=null\">");
        	bw.newLine();
        	bw.write("\t\t\t\t\t #{bean." + tempField + "},");
        	bw.newLine();
        	bw.write("\t\t\t\t</if>");
        	bw.newLine();
        }
        bw.write("\t\t\t </trim>");
        bw.newLine();
      
      
        bw.write("\t\t</foreach >");
      
        bw.write("\t</insert>");
        bw.newLine();
        bw.newLine();
       
	}
	
	/**
	 * 生成更新语句
	 * @author AimSpeed
	 * @param bw
	 * @param columns
	 * @param types
	 * @param tableMsgVo
	 * @throws IOException void 
	 */
	private void generateUpdateSql(BufferedWriter bw, 
									List<String> columns, 
									List<String> types,
									TableInfoVo tableMsgVo) throws IOException{
		
		//==============================  修改update方法
		String tempField = null;
        bw.write("\t<!-- 动态更新-->");
        bw.newLine();
        bw.write("\t<update id=\"updateSelective\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();
        bw.write("\t\t UPDATE " + tableMsgVo.getTableName());
        bw.newLine();
        bw.write(" \t\t <set> ");
        bw.newLine();
 
        tempField = null;
        for ( int i = 1 ; i < columns.size() ; i++ ) {
            tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
            bw.write("\t\t\t<if test=\"updateRecord != null and updateRecord." + tempField + " != null\">");
            bw.newLine();
            bw.write("\t\t\t\t " + columns.get(i) + " = #{updateRecord." + tempField + "},");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
 
        bw.newLine();
        bw.write(" \t\t </set>");
        bw.newLine();
        bw.write("\t\t WHERE 1 = 1 ");
        bw.newLine();
        tempField = null;
        
        for ( int i = 0 ; i < columns.size() ; i++ ) {
            tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
            bw.write("\t\t\t<if test=\"conditionRecord != null and conditionRecord." + tempField + " != null\">");
            bw.newLine();
            bw.write("\t\t\t\t and " + columns.get(i) + " = #{conditionRecord." + tempField + "," +  " jdbcType=" + TypeUtils.processTypeToJdbc(TypeUtils.processTypeOfClean(types.get(i))).toUpperCase() + "}");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
        
        bw.write("\t</update>");
        bw.newLine();
        bw.newLine();
        // update方法完毕
 
        bw.newLine();
        
        //=============================== 根据批量Id进行更新
        bw.write("\t<!-- 根据批量Id进行更新 -->");
        bw.newLine();
        bw.write("\t<update id=\"batchUpdateOfIds\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();
        bw.write("\t\t UPDATE " + tableMsgVo.getTableName());
        bw.newLine();
        bw.write(" \t\t <set> ");
        bw.newLine();
 
        tempField = null;
        for ( int i = 1 ; i < columns.size() ; i++ ) {
            tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
            bw.write("\t\t\t<if test=\"updateRecord != null and updateRecord." + tempField + " != null\">");
            bw.newLine();
            bw.write("\t\t\t\t " + columns.get(i) + " = #{updateRecord." + tempField + "},");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
 
        bw.newLine();
        bw.write(" \t\t </set>");
        bw.newLine();
        bw.write("\t\t WHERE 1 = 1 ");
        bw.newLine();
        
        //条件
        /*
         * 
		 		and id in 
		 		<foreach collection ="ids" item="id" index= "index" open="(" separator="," close=")">
		 			#{id}
		 		</foreach>
		 	</if>
         */
        bw.write("\t\t\t <if	test=\"ids != null and ids.size > 0\">");
        bw.newLine();
        bw.write("\t\t\t\t and " + columns.get(0) + " in ");
        bw.newLine();
        bw.write("\t\t\t\t <foreach collection =\"ids\" item=\"id\" index= \"index\" open=\"(\" separator=\",\" close=\")\">");
        bw.newLine();
        bw.write("\t\t\t\t\t #{id}");
        bw.newLine();
        bw.write("\t\t\t\t </foreach>");
        bw.newLine();
        bw.write("\t\t\t </if>");
        bw.newLine();
        
        
        bw.write("\t</update>");
        bw.newLine();
        bw.newLine();
        // update方法完毕
 
        bw.newLine();
        
        //=============================== 根据Id进行更新
        bw.write("\t<!-- 根据Id进行更新 -->");
        bw.newLine();
        bw.write("\t<update id=\"updateSelectiveOfId\" parameterType=\"" + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + "\">");
        bw.newLine();
        bw.write("\t\t UPDATE " + tableMsgVo.getTableName());
        bw.newLine();
        bw.write(" \t\t <set> ");
        bw.newLine();
 
        tempField = null;
        for ( int i = 1 ; i < columns.size() ; i++ ) {
            tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
            bw.write("\t\t\t<if test=\"updateRecord != null and updateRecord." + tempField + " != null\">");
            bw.newLine();
            bw.write("\t\t\t\t " + columns.get(i) + " = #{updateRecord." + tempField + "},");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
 
        bw.newLine();
        bw.write(" \t\t </set>");
        bw.newLine();
        bw.write("\t\t WHERE 1 = 1 ");
        bw.newLine();
        bw.write("\t\t <if test=\"updateRecord != null and updateRecord.id != null\">");
        bw.newLine();
        bw.write("\t\t\t and " + columns.get(0) + " = #{updateRecord.id, jdbcType=INTEGER}");
        bw.newLine();
        bw.write("\t\t </if>");
        bw.newLine();
        bw.newLine();
        
        
        bw.write("\t</update>");
        bw.newLine();
        bw.newLine();
        // update方法完毕
 
        bw.newLine();
        
	}
	
}
