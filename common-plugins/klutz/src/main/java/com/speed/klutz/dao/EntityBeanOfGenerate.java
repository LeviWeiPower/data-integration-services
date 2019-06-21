package com.speed.klutz.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.speed.klutz.utils.CommentUtils;
import com.speed.klutz.utils.FieldUtils;
import com.speed.klutz.utils.TypeUtils;
import com.speed.klutz.vo.TableInfoVo;

/**
 * 生成实体类
 * @author AimSpeed
 */
public class EntityBeanOfGenerate implements DaoGenerate {
	
	/*
	 * 生成实体类文件，*.java
	 * @author AimSpeed
	 * @param beanPath
	 * @param columns
	 * @param types
	 * @param comments
	 * @param tableMsgVo
	 * @throws IOException
	 * @overridden @see com.speed.klutz.dao.DaoGenerate#generateFile(java.lang.String, java.util.List, java.util.List, java.util.List, com.speed.klutz.vo.TableMsgVo)
	 */
	@Override
	public void generateFile(String beanPath,
								List<String> columns, 
								List<String> types, 
								List<String> comments, 
								TableInfoVo tableMsgVo) throws IOException{
		
		File folder = new File(beanPath);
        //文件夹不存在则创建文件夹
    	if ( !folder.exists() ) {
            folder.mkdir();
        }
    	//文件名称
        File beanFile = new File(beanPath, tableMsgVo.getBeanName() + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        
        //包信息和导入信息 
        bw.write("package " + tableMsgVo.getBeanPackage() + ";");
        bw.newLine();
        bw.newLine();
        
        bw.write("import com.aimspeed.mysql.BaseMySqlBean;");
        bw.newLine();
        
        //类注解信息
        bw = CommentUtils.generateClassComment(bw, tableMsgVo.getTableComment());
        bw.newLine();
        
        //类名
        bw.write("public class " + tableMsgVo.getBeanName() + " extends BaseMySqlBean {");
        bw.newLine();
        bw.newLine();
        
        int size = columns.size();
        for ( int i = 0 ; i < size ; i++ ) {
        	//类属性注解
        	bw = CommentUtils.generateFieldComment(bw,comments.get(i));
            bw.newLine();
            						//处理数据类型							处理字段信息
            bw.write("\tprivate " + TypeUtils.processTypeToJava(types.get(i)) + " " + FieldUtils.ddatabaseFieldToJava(columns.get(i)) + ";");
            bw.newLine();
            bw.newLine();
        }
        
        bw.newLine();
        
        // 生成get 和 set方法
        String tempField = null;
        String _tempField = null;
        String tempType = null;
        
        for ( int i = 0 ; i < size ; i++ ) {
            //将其数据库的数据转换为Java对应的规则数据
        	tempType = TypeUtils.processTypeToJava(types.get(i));
            _tempField = FieldUtils.ddatabaseFieldToJava(columns.get(i));
            
            //首字母转换为大写
            tempField = _tempField.substring(0, 1).toUpperCase() + _tempField.substring(1);
            bw.newLine();
            bw.write("\tpublic void set" + tempField + "(" + tempType + " " + _tempField + "){");
            bw.newLine();
            bw.write("\t\tthis." + _tempField + " = " + _tempField + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();
            bw.write("\tpublic " + tempType + " get" + tempField + "(){");
            bw.newLine();
            bw.write("\t\treturn this." + _tempField + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
        }
        
        //结束
        bw.newLine();
        bw.write("}");
        bw.newLine();
        bw.flush();
        bw.close();
        
	}
	
	
}
