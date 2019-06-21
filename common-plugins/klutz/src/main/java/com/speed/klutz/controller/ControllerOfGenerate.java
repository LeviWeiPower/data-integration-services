package com.speed.klutz.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.speed.klutz.utils.CommentUtils;

/**
 * 控制层生成
 * @author AimSpeed
 */
public class ControllerOfGenerate implements ControllerGenerate {

	/*
	 * Controller层文件生成
	 * @author AimSpeed
	 * @param filePath
	 * @param controllerName
	 * @param packagePath
	 * @param comment
	 * @throws IOException
	 * @overridden @see com.speed.klutz.controller.ControllerGenerate#generateFile(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void generateFile(String filePath, String controllerName, String packagePath,String comment) throws IOException {
		
		File folder = new File(filePath);
        //文件夹不存在则创建文件夹
    	if ( !folder.exists() ) {
            folder.mkdir();
        }
    	
    	//文件名称
        File beanFile = new File(filePath, controllerName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        
        //包信息和导入信息 
        bw.write("package " + packagePath + ";");
        bw.newLine();
        bw.newLine();
           
        bw.write("import org.springframework.stereotype.Controller;");
        bw.newLine();
        bw.newLine();
        
        //类注解信息
        bw = CommentUtils.generateClassComment(bw, comment);
        bw.newLine();
        
        //类名
        bw.write("@Controller");
        bw.newLine();
        bw.write("public class " + controllerName + " {");
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
