package com.speed.klutz.service;

import java.io.IOException;

import com.speed.klutz.vo.TableInfoVo;

/**
 * Service文件生成接口
 * @author AimSpeed
 */
public interface ServiceGenerate {
	
	/**
	 * 生成Service业务接口文件
	 * @author AimSpeed
	 * @param filePath 文件路径
	 * @param serviceName 接口名称
	 * @param serviceImplName 实现类名称
	 * @param packagePath 包路径
	 * @param tableMsgVo 实体名称和路径、注释
	 * @throws IOException void 
	 */
	public void generateFile(String filePath,
							 String serviceName,
							 String serviceImplName,
							 String packagePath,
							 TableInfoVo tableMsgVo) throws IOException;
						
}
