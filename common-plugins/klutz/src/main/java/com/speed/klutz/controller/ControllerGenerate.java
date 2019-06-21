package com.speed.klutz.controller;

import java.io.IOException;

/**
 * 控制层生成类
 * @author AimSpeed
 */
public interface ControllerGenerate {
	
	/**
	 * Controller层文件生成
	 * @author AimSpeed
	 * @param filePath
	 * @param controllerName
	 * @param packagePath
	 * @param comment
	 * @throws IOException void 
	 */
	public void generateFile(String filePath,
								 String controllerName,
								 String packagePath,
								 String comment) throws IOException;
	
	
	
	
	
	
	
	
}
