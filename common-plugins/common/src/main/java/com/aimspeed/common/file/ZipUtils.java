package com.aimspeed.common.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.aimspeed.common.datatype.StringUtils;


/**
 * 解压和压缩文件或文件夹
 * @author AimSpeed
 */
public class ZipUtils{

	/**
	 * 对文件或文件目录进行压缩
	 * @author AimSpeed
	 * @param srcPath 要压缩的源文件路径。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
	 * @param zipPath 压缩文件保存的路径。注意：zipPath不能是srcPath路径下的子文件夹
	 * @param zipFileName 压缩文件名
	 * @throws Exception  
	 */
	public static void zip(String srcPath, String zipPath, String zipFileName) throws Exception {
		if (StringUtils.isEmpty(srcPath,true) || StringUtils.isEmpty(zipPath,true) || StringUtils.isEmpty(zipFileName,true)) {
			throw new Exception("参数错误!");
		}
		CheckedOutputStream cos = null;
		ZipOutputStream zos = null;
		try {
			File srcFile = new File(srcPath);
			// 判断压缩文件保存的路径是否为源文件路径的子文件夹，如果是，则抛出异常（防止无限递归压缩的发生）
			if (srcFile.isDirectory() && zipPath.indexOf(srcPath) != -1) {
				throw new Exception("zipPath must not be the child directory of srcPath.");
			}

			// 判断压缩文件保存的路径是否存在，如果不存在，则创建目录
			File zipDir = new File(zipPath);
			if (!zipDir.exists() || !zipDir.isDirectory()) {
				zipDir.mkdirs();
			}

			// 创建压缩文件保存的文件对象
			String zipFilePath = zipPath + File.separator + zipFileName;
			File zipFile = new File(zipFilePath);
			if (zipFile.exists()) {
				// 删除已存在的目标文件
				zipFile.delete();
			}
			cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());
			zos = new ZipOutputStream(cos);

			// 如果只是压缩一个文件，则需要截取该文件的父目录
			String srcRootDir = srcFile.getPath();
			if (srcFile.isFile()) {
				int index = srcPath.lastIndexOf(File.separator);
				if (index != -1) {
					srcRootDir = srcPath.substring(0, index);
				}
			}
			// 调用递归压缩方法进行目录或文件压缩
			zip(srcRootDir, srcFile, zos);
			zos.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统压缩压力过大!");
		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 递归压缩文件夹
	 * @author AimSpeed
	 * @param srcRootDir 压缩文件夹根目录的子路径
	 * @param file 当前递归压缩的文件或目录对象
	 * @param zos 压缩文件存储对象
	 * @throws Exception 
	 */
	public static void zip(String srcRootDir, File file, ZipOutputStream zos) throws Exception {
		if (file == null) {
			return;
		}
		// 如果是文件，则直接压缩该文件
		if (file.isFile()) {
			int count, bufferLen = 1024;
			byte data[] = new byte[bufferLen];

			// 获取文件相对于压缩文件夹根目录的子路径
			String subPath = file.getAbsolutePath();
			int index = subPath.indexOf(srcRootDir);
			if (index != -1) {
				subPath = subPath.substring(srcRootDir.length() + File.separator.length());
			}
			ZipEntry entry = new ZipEntry(subPath);
			zos.putNextEntry(entry);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			while ((count = bis.read(data, 0, bufferLen)) != -1) {
				zos.write(data, 0, count);
			}
			bis.close();
			zos.closeEntry();
		}
		// 如果是目录，则压缩整个目录
		else {
			// 压缩目录中的文件或子目录
			File[] childFileList = file.listFiles();
			for (int n = 0; n < childFileList.length; n++) {
				zip(srcRootDir, childFileList[n], zos);
			}
		}
	}

	/**
	 * 解压文件(解压当前目录)
	 * @author AimSpeed
	 * @param folderPath 文件夹路径
	 * @param fileName 压缩包名称
	 * @throws Exception 
	 */
	public static void decompression(String folderPath,String fileName) throws Exception{
		Map<String, String> renameMaps = new HashMap<>();
		List<File> list = new ArrayList<File>();
		try {
			byte[] b = new byte[1024] ;
			File newFile=new File(folderPath+File.separator+fileName);
			ZipFile zipFile = new ZipFile(newFile, "GBK");
			for( Enumeration entries =zipFile.getEntries() ; entries.hasMoreElements() ; ){
				ZipEntry entry = (ZipEntry)entries.nextElement() ;
				File file = new File(folderPath + File.separator + entry.getName()) ;
				//创建文件夹
				if( entry.isDirectory() ){
					renameMaps.put(entry.getName().replace("/", ""), folderPath + File.separator + entry.getName());
					file.mkdirs() ;
				}else{
					File parent = file.getParentFile() ;
					if( !parent.exists() ){
						parent.mkdirs() ;
					}
					InputStream in = zipFile.getInputStream(entry);
					OutputStream out = new FileOutputStream(file) ;
					int len = 0 ;
					while( (len = in.read(b)) > 0){
						out.write(b, 0, len);
					}
					in.close(); 
					out.flush();
					out.close();
					list.add(file) ;
				}
			}
			zipFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("系统解压压力过大!");
		}
	}
}
