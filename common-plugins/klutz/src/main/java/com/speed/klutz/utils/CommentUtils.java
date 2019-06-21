package com.speed.klutz.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.speed.klutz.GenerateImpl;

/**
 * 注解处理工具类
 * @author AimSpeed
 */
public abstract class CommentUtils {
	
	/**
	 * 生成类注解信息
	 * @author AimSpeed
	 * @param bw
	 * @param text
	 * @return
	 * @throws IOException BufferedWriter 
	 */
	public static BufferedWriter generateClassComment( BufferedWriter bw, String text ) throws IOException {
        bw.newLine();
        bw.newLine();
        bw.write("/**");
        bw.newLine();
        bw.write(" * " + text);
        bw.newLine();

        //注解
        if(null != GenerateImpl.classAnnotationInfoVo.getAuthor() && !"".equals(GenerateImpl.classAnnotationInfoVo.getAuthor().trim())) {
        	bw.write(" * @author " + GenerateImpl.classAnnotationInfoVo.getAuthor());
            bw.newLine();
        }
        
        if(GenerateImpl.classAnnotationInfoVo.isDate()) {
        	bw.write(" * @date " + new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
            bw.newLine();
        }
        
        bw.write(" */");
        return bw;
        
        
        
        
    }
	
	/**
	 * 生成字段注释信息
	 * @author AimSpeed
	 * @param bw
	 * @param text
	 * @return
	 * @throws IOException BufferedWriter 
	 */
	public static BufferedWriter generateFieldComment( BufferedWriter bw, String text ) throws IOException {
        bw.write("\t/**");
        bw.newLine();
        bw.write("\t * " + text);
        bw.newLine();
        bw.write("\t */");
        return bw;
    }
	
	/**
	 * 生成方法注解信息
	 * @author AimSpeed
	 * @param bw
	 * @param text
	 * @return
	 * @throws IOException BufferedWriter 
	 */
	public static BufferedWriter generateMethodComment( BufferedWriter bw, String text ) throws IOException {
        bw.newLine();
        bw.write("\t/**");
        bw.newLine();
        bw.write("\t * " + text);
        bw.newLine();
        
        //注解
        if(null != GenerateImpl.classAnnotationInfoVo.getAuthor() && !"".equals(GenerateImpl.classAnnotationInfoVo.getAuthor().trim())) {
        	bw.write("\t * @author " + GenerateImpl.classAnnotationInfoVo.getAuthor());
            bw.newLine();
        }
        
        if(GenerateImpl.classAnnotationInfoVo.isDate()) {
        	bw.write("\t * @date " + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date()));
            bw.newLine();
        }
        
        bw.write("\t */");
        return bw;
    }
	
}
