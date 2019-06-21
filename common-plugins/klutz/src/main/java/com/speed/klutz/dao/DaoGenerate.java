package com.speed.klutz.dao;

import java.io.IOException;
import java.util.List;

import com.speed.klutz.vo.TableInfoVo;

/**
 * 生成文件接口
 * @author AimSpeed
 */
public interface DaoGenerate {
	
	/**
	 * 根据表信息生成对应的文件
	 * @author AimSpeed
	 * @param path
	 * @param columns
	 * @param types
	 * @param comments
	 * @param tableMsgVo
	 * @throws IOException void 
	 */
	public void generateFile(String path,
								List<String> columns, 
								List<String> types, 
								List<String> comments, 
								TableInfoVo tableMsgVo) throws IOException;
	
	
}
