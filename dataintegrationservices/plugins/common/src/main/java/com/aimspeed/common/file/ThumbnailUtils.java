package com.aimspeed.common.file;

import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;


/**
 * 图片操作工具类（大小，旋转...）
 * @author AimSpeed
 */
public class ThumbnailUtils {
	
	/**
	 * 根据长宽缩放图片
	 * @author AimSpeed
	 * @param bigImgPath 大图路径
	 * @param smallImgPath 缩略图路径   
	 * @param length 长度
	 * @param width 宽度
	 */
	 public static void thumbnailOfLW(String bigImgPath,String smallImgPath,int length,int width){
		try {
			Thumbnails.of(bigImgPath)   
			.size(length,width)  
			.toFile(smallImgPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 /**
	  * 按比例缩放图片
	  * @author AimSpeed
	  * @param bigImgPath 大图路径
	  * @param smallImgPath 小图路径
	  * @param scale 比例    
	  */
	 public static void thumbnailOfScale(String bigImgPath,String smallImgPath,double scale){
		 try {
			 Thumbnails.of(bigImgPath)   
			 .scale(scale) 
			 .toFile(smallImgPath);
		 } catch (IOException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
	 }
	
	 /**
	  * 根据长宽缩放图片并旋转
	  * @author AimSpeed
	  * @param bigImgPath 大图路径
	  * @param smallImgPath 小图路径
	  * @param length 长
	  * @param width 宽
	  * @param rotate 旋转角度 
	  */
	public static void thumbnailOfLWAndRotate(String bigImgPath,String smallImgPath,int length,int width,int rotate){
		//rotate(角度),正数：顺时针 负数：逆时针  
		try {
			Thumbnails.of(bigImgPath)   
			        .size(length, width)  
			        .rotate(rotate)   
			        .toFile(smallImgPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}
	
	/**
	 * 根据比例缩放图片并旋转
	 * @author AimSpeed
	 * @param bigImgPath  大图路径
	 * @param smallImgPath 小图路径
	 * @param scale 比例    
	 * @param rotate 旋转角度  
	 */
	public static void thumbnailOfScaleAndRotate(String bigImgPath,String smallImgPath,int scale,int rotate){
		//rotate(角度),正数：顺时针 负数：逆时针  
		try {
			Thumbnails.of(bigImgPath)   
			        .scale(scale) 
			        .rotate(rotate)   
			        .toFile(smallImgPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}
	
	

}
