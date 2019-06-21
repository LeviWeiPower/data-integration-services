package com.speed.klutz.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.speed.klutz.utils.CommentUtils;
import com.speed.klutz.vo.TableInfoVo;

/**
 * service，继承类生成
 * @author AimSpeed
 */
public class ServiceImplOfGenerate implements ServiceGenerate {

	/*
	 * 生成Service业务接口文件
	 * @author AimSpeed
	 * @param filePath
	 * @param serviceName
	 * @param serviceImplName
	 * @param packagePath
	 * @param tableMsgVo
	 * @throws IOException
	 * @overridden @see com.speed.klutz.service.ServiceGenerate#generateFile(java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.speed.klutz.vo.TableMsgVo)
	 */
	@Override
	public void generateFile(String filePath, String serviceName, String serviceImplName,  String packagePath,TableInfoVo tableMsgVo)
			throws IOException {
		File folder = new File(filePath);
        //文件夹不存在则创建文件夹
    	if ( !folder.exists() ) {
            folder.mkdir();
        }
    	
    	//文件名称
        File beanFile = new File(filePath, serviceImplName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        
        //包信息和导入信息 
        bw.write("package " + packagePath + ";");
        bw.newLine();
        bw.newLine();
           
        bw.write("import org.springframework.stereotype.Service;");
        bw.newLine();
        bw.write("import " + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + ";");
        bw.newLine();
        bw.newLine();
        
        //类注解信息
        bw = CommentUtils.generateClassComment(bw, tableMsgVo.getTableComment());
        bw.newLine();
        
        //类名
        bw.write("@Service");
        bw.newLine();
        bw.write("public class " + serviceImplName + "<" + tableMsgVo.getBeanName() + ">" + " implements " + serviceName + "<" + tableMsgVo.getBeanName() + ">" + " {");
        bw.newLine();
        bw.newLine();
        bw.newLine();
        bw.newLine();
        bw.newLine();
        bw.newLine();
        bw.newLine();
        bw.newLine();
        bw.write("}");
        bw.newLine();
        bw.newLine();
        bw.flush();
        bw.close();
		
	}
	
	
	
	
	
	
	
	
	
	
}
