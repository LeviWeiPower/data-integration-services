package com.aimspeed.common.file.excel.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.aimspeed.common.enums.ExcelVersionEnum;

/**
 * Excel导出的解析类：可根据自行需求自定义样式，输出位置，标头等
 * @author AimSpeed
 */
public class ExcelExportParse<T> {
	
	/*public static void main(String[] args) throws InvalidFormatException, InstantiationException, RuntimeException, IOException, ReflectiveOperationException {

		//测试用例
		ExcelExportParse<ExcelExportTest> excelExportParse = new ExcelExportParse(ExcelExportTest.class);
		
		List<ExcelExportTest> exportDatas = new ArrayList<>();
		
		ExcelExportTest excelExportTest1 = new ExcelExportTest();
		excelExportTest1.setName("666");
		excelExportTest1.setNikeName("你好1");
		excelExportTest1.setHobby("互联网11");
		
		ExcelExportTest excelExportTest2 = new ExcelExportTest();
		excelExportTest2.setName("666");
		excelExportTest2.setHobby("互联网22");
		excelExportTest2.setNikeName("你好1");
		
		exportDatas.add(excelExportTest1);
		exportDatas.add(excelExportTest2);
		
		excelExportParse.analysisExportFile("E:\\", "test.xlsx", ExcelVersionEnum.xlsx.getValue(),0, null, exportDatas);
		System.out.println("操作成功");
		
	}*/

	private Class clazz;
	
	public ExcelExportParse(Class clazz) {
		this.clazz = clazz;
	}

	/**
	 * 根据读取对应的数据 （导出一个sheet）
	 * @author AimSpeed
	 * @param exportFileType 导出的文件类型（97,2007）
	 * @param sheetName 表单名
	 * @param firstRow 第一行位置
	 * @param exportDatas 需要导出的数据
	 * @return Workbook excel对象
	 * @throws RuntimeException
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws ReflectiveOperationException Workbook 
	 */
	public Workbook analysis(Integer exportFileType,String sheetName,Integer firstRow,List<T> exportDatas) throws RuntimeException,InvalidFormatException, IOException, InstantiationException, ReflectiveOperationException{
		//创建Excel文件流
		Workbook workbook = createWorkBook(exportFileType);

		//创建Sheet
		Sheet sheet = createSheet(workbook,sheetName);
		
		//读取数据
		workbook = analysis(workbook,sheet,firstRow,exportDatas,null,null);
		
		return workbook;
	}
	
	/**
	 * 根据读取对应的数据 ，并导出数据（导出一个sheet）
	 * @author AimSpeed
	 * @param exportPath 导出的路径
	 * @param exportFileName 导出的文件名
	 * @param exportFileType 导出的文件类型（97,2007）
	 * @param firstRow 第一行位置
	 * @param sheetName 表单名
	 * @param exportDatas 需要导出的数据
	 * @return boolean 导出成功还是失败
	 * @throws RuntimeException
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws ReflectiveOperationException boolean 
	 */
	public boolean analysisExportFile(String exportPath,String exportFileName,Integer exportFileType,Integer firstRow,String sheetName,List<T> exportDatas) throws RuntimeException,InvalidFormatException, IOException, InstantiationException, ReflectiveOperationException{
		//创建Excel文件流
		Workbook workbook = createWorkBook(exportFileType);

		//创建Sheet
		Sheet sheet = createSheet(workbook,sheetName);
		
		//读取数据
		workbook = analysis(workbook,sheet,firstRow,exportDatas,null,null);
		
		//输出文件
		OutputStream out = new FileOutputStream(new File(exportPath + exportFileName));  
		workbook.write(out);
		
		return true;
	}
	
	/**
	 * 根据多个Sheet读取对应的数据 ， 1sheet对多数据的关系
	 * @author AimSpeed
	 * @param exportFileType  导出的文件类型（97,2007）
	 * @param firstRow 第一行位置
	 * @param manySheetToDatas 多个Sheet数据
	 * @return Workbook excel对象
	 * @throws RuntimeException
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws ReflectiveOperationException Workbook 
	 */
	public Workbook analysis(Integer exportFileType,Integer firstRow,Map<String, List<T>> manySheetToDatas) throws RuntimeException,InvalidFormatException, IOException, InstantiationException, ReflectiveOperationException{
		//创建Excel文件流
		Workbook workbook = createWorkBook(exportFileType);
		
		//读取对应的Sheet的数据
		for (Entry<String, List<T>> entry : manySheetToDatas.entrySet()) {
			String sheetName = entry.getKey();
			List<T> exportDatas =entry.getValue();

			//创建Sheet
			Sheet sheet = createSheet(workbook,sheetName);
			
			//读取数据
			workbook = analysis(workbook,sheet,firstRow,exportDatas,null,null);
			
		}
		return workbook;
	}
	
	/**
	 * 根据多个Sheet读取对应的数据 ， 1sheet对多数据的关系，并导出数据
	 * @author AimSpeed
	 * @param exportPath 导出的路径
	 * @param exportFileName 导出的文件名
	 * @param exportFileType 导出的文件类型（97,2007）
	 * @param firstRow 第一行位置
	 * @param manySheetToDatas 导出的多个表单数据
	 * @return boolean 导出成功还是失败
	 * @throws RuntimeException
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws ReflectiveOperationException boolean 
	 */
	public boolean analysisExportFile(String exportPath,String exportFileName,Integer exportFileType,Integer firstRow,Map<String, List<T>> manySheetToDatas) throws RuntimeException,InvalidFormatException, IOException, InstantiationException, ReflectiveOperationException{
		//创建Excel文件流
		Workbook workbook = createWorkBook(exportFileType);
		
		//读取对应的Sheet的数据
		for (Entry<String, List<T>> entry : manySheetToDatas.entrySet()) {
			String sheetName = entry.getKey();
			List<T> exportDatas =entry.getValue();
			
			//创建Sheet
			Sheet sheet = createSheet(workbook,sheetName);
			
			//读取数据
			workbook = analysis(workbook,sheet,firstRow,exportDatas,null,null);
		}
		
		return true;
	}
	
	/**
	 * Excel导出，可自定义标题的样式和内容样式
	 * @author AimSpeed
	 * @param workbook Excel输出对象
	 * @param sheet 输出的Sheet，可自定义，Excel的单元格整体的长宽等样式
	 * @param exportDatas 输出的内容数据
	 * @param headCellStyle 标题的样式：workbook.createCellStyle(); 
	 * @param contentCellStyle 内容的样式：workbook.createCellStyle(); 
	 * @return Workbook excel对象
	 * @throws RuntimeException
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws ReflectiveOperationException Workbook 
	 */
	public Workbook analysis(Workbook workbook,Sheet sheet,int firstRow,
			List<T> exportDatas,CellStyle headCellStyle,CellStyle contentCellStyle) throws RuntimeException,InvalidFormatException, IOException, InstantiationException, ReflectiveOperationException{
		//判断是否有Excel导出标识
		isExport();
		
		//获取到这个类的所有属性参数
		Field [] allFields = clazz.getDeclaredFields();
		
		//存储要导出的字段数据对应的注解配置：标题名 - 导出位置
		Map<String,Integer> headlineRuleMap = new LinkedHashMap<>();
		
		//导出的字段标题
		List<String> headlineNames = new ArrayList<>();
		
		//导出的字段位置
		List<Integer> headlineRows = new ArrayList<>();
		
		for (Field field : allFields) {
			field.setAccessible(true);//设置为可访问的
			ExcelExportField excelExportField = field.getDeclaredAnnotation(ExcelExportField.class);
			
			if(null != excelExportField){
				//存储对应的数据
				if(null != excelExportField.headlineName() && !"".equals(excelExportField.headlineName().trim())){
					headlineRuleMap.put(excelExportField.headlineName(), excelExportField.row());
					headlineNames.add(excelExportField.headlineName());
					
				}
				
				if(0 != excelExportField.row()){
					headlineRows.add(excelExportField.row());
				}
				
			}
		}
		
		if(null != headlineNames && headlineNames.size() <= 0
				&& null != headlineRows && headlineRows.size() <= 0){
			throw new RuntimeException(clazz.getSimpleName() + "类，请正确的配置@ExcelExportField注解");
		}
		
		//设置标题
		Row startRow = sheet.createRow(firstRow);
		createExcelHeadline(sheet, startRow, headCellStyle, headlineRuleMap);
		
		//输出数据
		exportContent(sheet,1,exportDatas,contentCellStyle);
		
		return workbook;
	}
	
	/**
	 * 判断该要导出的基础类是否有Excel导出标识
	 * @author AimSpeed
	 * @return Class 
	 * @throws RuntimeException Class 
	 */
	private Class isExport() throws RuntimeException {
		ExcelExport excelExport = (ExcelExport) clazz.getDeclaredAnnotation(ExcelExport.class);
		if(null == excelExport){
			throw new RuntimeException(clazz.getSimpleName() + "类没有@ExcelExport注解");
		}
		return clazz;
	}
	
	/**
	 * 根据版本创建对应的对象输出
	 * @author AimSpeed
	 * @Title createWorkBook
	 * @param type 类型
	 * @return Workbook Excel文件对象
	 * @date 2018年3月15日 
	 */
	public Workbook createWorkBook(int type) {  
        Workbook wb = null;  
        if(type == ExcelVersionEnum.XLSX.getValue()) {  
            wb = new XSSFWorkbook();  
        } else {  
            wb = new HSSFWorkbook();  
        }  
        return wb;  
    } 
	
	/**
	 * 根据是否有SheetName创建Sheet名称
	 * 例子：
	 * 设定合并首行单元格区域范围  firstRow，lastRow，lastRow，firstCol，lastCol
	 * CellRangeAddress firstCellRangeAddress = new CellRangeAddress(0, 0, 0, 9);
     * sheet.addMergedRegion(firstCellRangeAddress);
	 * @author AimSpeed
	 * @param workbook xcel文件对象
	 * @param sheetName 表单名
	 * @return Sheet 创建的表单
	 */
	public Sheet createSheet(Workbook workbook,String sheetName) {  
		Sheet sheet = null;
		if(null != sheetName){
			sheet = workbook.createSheet(sheetName);
		}else{
			sheet = workbook.createSheet();
		}
		return sheet; 
    } 
	
	/**
	 * 设置第一行的标题
	 * @author AimSpeed
	 * @param sheet 表单
	 * @param firstRow 第一行位置
	 * @param headCellStyle 标题的样式：workbook.createCellStyle(); 
	 * @param headlineRuleMap 表头的规则映射
	 * @return Row 创建的行对象
	 * @throws IOException Row 
	 */
	public Row createExcelHeadline(Sheet sheet,Row firstRow, CellStyle headCellStyle,Map<String,Integer> headlineRuleMap) throws IOException {  
		
		if(null == headlineRuleMap || headlineRuleMap.size() <= 0){
			return firstRow;
		}
		
		int len = 0;
        
		for (Entry<String, Integer> entry : headlineRuleMap.entrySet()) {
			//读取到的值
			String name = entry.getKey();
			Integer location = entry.getValue();
			
			Cell cell = null;
			
			//设置位置
			if(null != location && 0 != location){
				cell = firstRow.createCell(location - 1); //从1开始
			}else{
				cell = firstRow.createCell(len);
			}
			
			//设置单元格样式
			if(null != headCellStyle){
				cell.setCellStyle(headCellStyle);
			}
			
			cell.setCellValue(name);
			len++;
		}
		
        return firstRow;
    }  
	
	
	/**
	 * 内容输出，有设置位置则按照位置，没有则按照顺序
	 * @author AimSpeed
	 * @param sheet 表单
	 * @param startRow 开始的行数
	 * @param exportDatas 要输出的数据
	 * @param contentCellStyle 内容样式
	 * @return Integer 输出的多少行
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException Integer 
	 */
	public Integer exportContent(Sheet sheet,Integer startRow,List<T> exportDatas,CellStyle contentCellStyle) throws IllegalArgumentException, IllegalAccessException{
		Integer len = startRow;

		Integer needExportFiledLen = 0;
		
		for (T t : exportDatas) {
			Row row = sheet.createRow(len);
			
			Class tClazz = t.getClass();
			
			//获取到这个类的所有属性参数
			Field [] allFields = tClazz.getDeclaredFields();
			
			for (Field field : allFields) {
				field.setAccessible(true);//设置为可访问的
				//必须要有这个标识的字段导出标识
				ExcelExportField excelExportField = field.getDeclaredAnnotation(ExcelExportField.class);
				
				if(null != excelExportField){
					Cell cell = null;
					if(0 != excelExportField.row()){
						//按照读取顺序导出
						cell = row.createCell(excelExportField.row() - 1);
					}else{
						cell = row.createCell(needExportFiledLen);
					}
					
					if(null != contentCellStyle){
						cell.setCellStyle(contentCellStyle);
					}
					Object valObj = field.get(t);
					String value = null != valObj?valObj.toString() : null;
					cell.setCellValue(value);
					needExportFiledLen++;
				}
			}
			len++;
			needExportFiledLen = 0;
		}
		return len;
	}
	
	
}
