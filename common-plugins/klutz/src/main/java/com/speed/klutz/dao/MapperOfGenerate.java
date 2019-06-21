package com.speed.klutz.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.speed.klutz.utils.CommentUtils;
import com.speed.klutz.vo.TableInfoVo;

/**
 * Mapper接口文件生成
 * @author AimSpeed
 */
public class MapperOfGenerate implements DaoGenerate {
	
	/**
	 * 生成Mapper接口文件
	 * @author AimSpeed
	 * @param mapperPath
	 * @param columns
	 * @param types
	 * @param comments
	 * @param tableMsgVo
	 * @throws IOException
	 * @overridden @see com.speed.klutz.dao.DaoGenerate#generateFile(java.lang.String, java.util.List, java.util.List, java.util.List, com.speed.klutz.vo.TableMsgVo)
	 */
	public void generateFile(String mapperPath,
								List<String> columns, 
								List<String> types, 
								List<String> comments, 
								TableInfoVo tableMsgVo) throws IOException{
		File folder = new File(mapperPath);
		
        //文件夹不存在则创建文件夹
        if ( !folder.exists() ) {
            folder.mkdirs();
        }
        
        //文件名称
        File mapperFile = new File(mapperPath, tableMsgVo.getMapperName() + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
        
        //包导入信息
        bw.write("package " + tableMsgVo.getMapperPackage() + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import " + tableMsgVo.getBeanPackage() + "." + tableMsgVo.getBeanName() + ";");
        bw.newLine();
        bw.write("import org.apache.ibatis.annotations.Param;");
        bw.newLine();
        bw.write("import " + tableMsgVo.getPagePackaging() + ";");
        bw.newLine();
        bw.write("import java.util.List;");
        bw.newLine();
        bw.write("import java.util.Map;");
        bw.newLine();
        bw.write("import com.aimspeed.mysql.BaseMySqlMapper;");
        bw.newLine();
        
        
        //类注解
        bw = CommentUtils.generateClassComment(bw, tableMsgVo.getTableComment());
        bw.newLine();
        
        //如果后面有需要，可以直接改为false
        if(true) {
        	//类名
            bw.write("public interface " + tableMsgVo.getMapperName() + " extends BaseMySqlMapper<" + tableMsgVo.getBeanName() + "> {");
            bw.newLine();
            bw.newLine();
        	
            //结束
            bw.write("}");
            bw.flush();
            bw.close();	
            return ;
        }
        
        //类名
        bw.write("public interface " + tableMsgVo.getMapperName() + " {");
        bw.newLine();
        bw.newLine();
        
        
        // ----------定义Mapper中的方法Begin----------
        //======================== 查询
        bw = CommentUtils.generateMethodComment(bw, "根据对象的不为null的值作为条件进行查找，并且确定值找出一个值");
        bw.newLine();
        bw.write("\t " + tableMsgVo.getBeanName() + "  selectOnlyOfSelective("+tableMsgVo.getBeanName()+" record);");
        bw.newLine();
        
        bw = CommentUtils.generateMethodComment(bw, "根据对象的不为null的值作为条件进行查找");
        bw.newLine();
        bw.write("\t List<" + tableMsgVo.getBeanName() + ">  selectSelective("+tableMsgVo.getBeanName()+" record);");
        bw.newLine();
        
        bw = CommentUtils.generateMethodComment(bw, "动态分页，筛选条件 - 默认为最新时间排序");
        bw.newLine();
        bw.write("\t List<" + tableMsgVo.getBeanName() + ">  selectPageSelective(@Param(\"pageVo\") PageVo<" + tableMsgVo.getBeanName() + "> pageVo);");
        bw.newLine();
        
        bw = CommentUtils.generateMethodComment(bw, "查找数据总记录数");
        bw.newLine();
        bw.write("\t" + "Integer selectDataCountSize(@Param(\"condition\") Map<String, String> condition,@Param(\"likeCondition\")Map<String, String> likeCondition);");
        bw.newLine();
        
        //--------------------------------------------
        bw = CommentUtils.generateMethodComment(bw, "根据Id进行查询");
        bw.newLine();
        bw.write("\t" + "" + tableMsgVo.getBeanName() + "  selectOfId(@Param(\"id\") Integer id);");
        bw.newLine();
        
        bw = CommentUtils.generateMethodComment(bw, "根据多个Id进行查询");
        bw.newLine();
        bw.write("\t" + "List<" + tableMsgVo.getBeanName() + ">  selectOfIds(@Param(\"ids\") List<Integer> id);");
        bw.newLine();
        
        bw = CommentUtils.generateMethodComment(bw, "根据Id范围进查询");
        bw.newLine();
        bw.write("\t" + "List<" + tableMsgVo.getBeanName() + ">  selectOfIdScopes(@Param(\"lessId\") Integer lessId,@Param(\"largeId\")Integer largeId);");
        bw.newLine();
        
        bw = CommentUtils.generateMethodComment(bw, "根据多条件值进行查询");
        bw.newLine();
        bw.write("\t" + "List<" + tableMsgVo.getBeanName() + ">  selectSelectiveMany(@Param(\"batchBeans\") List<" + tableMsgVo.getBeanName() + "> record);");
        bw.newLine();
        
        bw = CommentUtils.generateMethodComment(bw, "根据条件进行查询去重");
        bw.newLine();
        bw.write("\t" + "List<" + tableMsgVo.getBeanName() + ">  selectSelectiveOfDistince(" + tableMsgVo.getBeanName() + " record);");
        bw.newLine();
        
        bw = CommentUtils.generateMethodComment(bw, "根据多条件值进行查询去重");
        bw.newLine();
        bw.write("\t" + "List<" + tableMsgVo.getBeanName() + ">  selectSelectiveManyOfDistince(@Param(\"batchBeans\") List<" + tableMsgVo.getBeanName() + "> record);");
        bw.newLine();
        
        //======================== 删除
        bw = CommentUtils.generateMethodComment(bw, "根据对象的不为null的值作为条件进行删除");
        bw.newLine();
        bw.write("\t" + "Integer deleteSelective( " + tableMsgVo.getBeanName() + " record );");
        bw.newLine();
        
        bw = CommentUtils.generateMethodComment(bw, "根据Id进行删除");
        bw.newLine();
        bw.write("\t" + "Integer deleteSelectiveOfId(@Param(\"id\") Integer id);");
        bw.newLine();
        
        bw = CommentUtils.generateMethodComment(bw, "根据多个Id进行删除");
        bw.newLine();
        bw.write("\t" + "Integer deleteSelectiveOfIds(@Param(\"ids\") List<Integer> ids);");
        bw.newLine();
        
        //======================== 插入
        bw = CommentUtils.generateMethodComment(bw, "添加数据，只添加不为null的数据，并且返回添加之后的数据记录Id（直接调用回对应的插入类.id），\n\t * 如果这个类没有id字段，那么请把Xml中的[useGeneratedKeys=true keyProperty=id keyColumn=id]这段代码删除");
        bw.newLine();
        bw.write("\t" + "Integer insertSelective( " + tableMsgVo.getBeanName() + " record );");
        bw.newLine();
        
        bw = CommentUtils.generateMethodComment(bw, "批量添加数据，添加所有字段的数据");
        bw.newLine();
        bw.write("\t" + "Integer batchInsert(@Param(\"batchField\") List<" + tableMsgVo.getBeanName() + "> record);");
        bw.newLine();
        
        bw = CommentUtils.generateMethodComment(bw, "批量添加数据，添加动态字段的数据，建议每次100条");
        bw.newLine();
        bw.write("\t" + "Integer batchInsertSelective(@Param(\"batchField\") List<" + tableMsgVo.getBeanName() + "> record);");
        bw.newLine();
        
        //======================== 更新
        bw = CommentUtils.generateMethodComment(bw, "更新不为null的数据，不会将其他字段更新为null");
        bw.newLine();
        bw.write("\t" + "Integer updateSelective(@Param(\"updateRecord\") "+tableMsgVo.getBeanName()+" updateRecord,@Param(\"conditionRecord\") "+tableMsgVo.getBeanName()+" conditionRecord);");
        bw.newLine();
        bw.newLine();
        
        bw = CommentUtils.generateMethodComment(bw, "根据Id作为条件进行更新");
        bw.newLine();
        bw.write("\t" + "Integer updateSelectiveOfId(@Param(\"updateRecord\") "+tableMsgVo.getBeanName()+" updateRecord);");
        bw.newLine();
        bw.newLine();
        
        bw = CommentUtils.generateMethodComment(bw, "根据批量Id进行更新");
        bw.newLine();
        bw.write("\t" + "Integer batchUpdateOfIds(@Param(\"updateRecord\") "+tableMsgVo.getBeanName()+" updateRecord,@Param(\"ids\") List<Integer> ids);");
        bw.newLine();
        bw.newLine();
        
        //结束
        bw.write("}");
        bw.flush();
        bw.close();	
        
	}
	
	
}
