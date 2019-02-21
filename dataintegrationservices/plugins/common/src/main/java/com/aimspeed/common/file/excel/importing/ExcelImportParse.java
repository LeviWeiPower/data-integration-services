package com.aimspeed.common.file.excel.importing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.aimspeed.common.datatype.DataTypeChangeUtils;
import com.aimspeed.common.file.excel.ExcelUtils;

/**
 * Excel导入解析工具类
 * @author AimSpeed
 */
@ExcelImportPosition(position=false)
public class ExcelImportParse<T> {
	
	@ExcelImportHeadline(row=1)
//	@ExcelImportHeadline(headlineName="爱好")
	String name;
	Integer value;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public static void main(String[] args) throws InvalidFormatException, IOException, InstantiationException, RuntimeException, ReflectiveOperationException {
		ExcelImportParse<ExcelImportParse> excelParse = new ExcelImportParse<>();
		
		InputStream inputStream = new FileInputStream(new File("E:\\新建 Microsoft Excel 工作表.xlsx"));
		List<ExcelImportParse> result = excelParse.analysis(ExcelImportParse.class,inputStream);
		for (ExcelImportParse object : result) {
			System.out.println(object.getName() + " --- " + object.getValue());
		}
	}
	
	/**
	 * 解析要Excel数据
	 * @author AimSpeed
	 * @param clazz 要填写的类类型
	 * @param inputStream Excel文件的输入流
	 * @return List<T> 解析的结果数据
	 * @throws RuntimeException
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws ReflectiveOperationException List<T> 
	 */
	public List<T> analysis(Class clazz,InputStream inputStream) throws RuntimeException,InvalidFormatException, IOException, InstantiationException, ReflectiveOperationException{
		
		List<T> result = new ArrayList<>();
		
		//获取到这个类的注解，判断需要读取数据的位置
		ExcelImportPosition excelDataPositionTag = (ExcelImportPosition) clazz.getDeclaredAnnotation(ExcelImportPosition.class);
		
		if(null != excelDataPositionTag) {
			
			boolean position = excelDataPositionTag.position();
			
			//获取到这个类的所有属性参数
			Field [] allFields = clazz.getDeclaredFields();
			
			//存储属性名称和对应的规则的值
			Map<Integer, String> fieldToRuleMap = new LinkedHashMap<>();
			//按照标题名和对于的属性名称进行存储
			Map<String, String> headlineNameToRuleMap = new LinkedHashMap<>();
			
			for (Field field : allFields) {
				field.setAccessible(true);//设置为可访问的
				ExcelImportHeadline excelHeadline = field.getDeclaredAnnotation(ExcelImportHeadline.class);
				
				if(null != excelHeadline){
					//是否按照标题读取
					if(position){
						headlineNameToRuleMap.put(excelHeadline.headlineName(),field.getName());
					}else{
						fieldToRuleMap.put(excelHeadline.row(),field.getName());
					}
				}
			}
			
			if(fieldToRuleMap.size() > 0 || headlineNameToRuleMap.size() > 0){
				//调用工厂方法使得 Excel 2003/2007/2010 都是可以处理的
				Workbook workbook = WorkbookFactory.create(inputStream);   
				int sheetCount = workbook.getNumberOfSheets();  //获取到所有的Sheet数量
				
				for (int i = 0; i < sheetCount; i++) {
					Sheet sheet = workbook.getSheetAt(i);
					
					//是否按照标题读取
					if(position){
						List<T> datas = readByHeadline(clazz,headlineNameToRuleMap,sheet);
						result.addAll(datas);
					}else{
						List<T> datas = readByIndex(clazz,fieldToRuleMap,sheet);
						result.addAll(datas);
					}
				}
				
			}else {
				throw new RuntimeException("没有字段定义@ExcelDataPositionTag注解");
			}
			
		}else {
			throw new RuntimeException("类没有定义@ExcelDataPositionTag注解");
		}
		
		return result;
	}
	
	/**
	 * 根据标题进行读取
	 * @author AimSpeed
	 * @param clazz 要填写的类类型
	 * @param headlineNameToRuleMap 标题映射规则
	 * @param sheet 表单
	 * @return List<T> 读取结果
	 * @throws InstantiationException
	 * @throws ReflectiveOperationException List<T> 
	 */
	private List<T> readByHeadline(Class clazz,Map<String, String> headlineNameToRuleMap,Sheet sheet) throws InstantiationException, ReflectiveOperationException{
		
		//获取到当前这个sheet的总行数
		int rowNumber = sheet.getLastRowNum();
		
		//存储第一行的标题和位置
		Map<Integer, String> indexHeadlineMap = new LinkedHashMap<>();
		
		//读取第一行
		Row firstRow = sheet.getRow(0);
		int maxCellNum = firstRow.getLastCellNum();
		
		for (int i = 0; i < maxCellNum; i++) {
			Cell cell = firstRow.getCell(i);
			String cellText = ExcelUtils.readCellVal(cell);
			indexHeadlineMap.put(i, cellText);
		}
		
		List<T> result = new ArrayList<>();
		//读取内容数据
		for (int j = 1; j <= rowNumber; j++) {
			Row row = sheet.getRow(j);
			int cellNum = row.getLastCellNum();
			
			T object = (T) clazz.newInstance();
			Class clazzObj = object.getClass();
			
			for (int i = 0; i < cellNum; i++) {
				Cell cell = row.getCell(i);
				String cellText = ExcelUtils.readCellVal(cell);
				
				//获取到当前这一列的标题
				String headlineName = indexHeadlineMap.get(i);
				//获取到这列存储的属性名称
				String fieldName = headlineNameToRuleMap.get(headlineName);
				if(null != fieldName){
					Field field = clazzObj.getDeclaredField(fieldName);
					field.setAccessible(true);
					Class type = field.getType();
					field.set(object, DataTypeChangeUtils.changeType(type.getSimpleName(),cellText));
				}
			}
			result.add(object);
		}
		return result;
	}
	
	/**
	 * 根据位置进行读取
	 * @author AimSpeed
	 * @param clazz 要填写的类类型
	 * @param fieldToRuleMap 字段映射规则
	 * @param sheet 表单
	 * @return List<T> 读取结果
	 * @throws InstantiationException
	 * @throws ReflectiveOperationException List<T> 
	 */
	private List<T> readByIndex(Class clazz,Map<Integer, String> fieldToRuleMap,Sheet sheet) throws InstantiationException, ReflectiveOperationException{
		//获取到当前这个sheet的总行数
		int rowNumber = sheet.getLastRowNum();
		
		List<T> result = new ArrayList<>();
		
		//读取内容数据
		for (int j = 1; j <= rowNumber; j++) {
			Row row = sheet.getRow(j);
			int cellNum = row.getLastCellNum();
			
			T object = (T) clazz.newInstance();
			Class clazzObj = object.getClass();
			
			for (int i = 0; i < cellNum; i++) {
				Cell cell = row.getCell(i);
				String cellText = ExcelUtils.readCellVal(cell);
				
				//获取到这列存储的属性名称
				String fieldName = fieldToRuleMap.get(i);
				if(null != fieldName){
					Field field = clazzObj.getDeclaredField(fieldName);
					field.setAccessible(true);
					Class type = field.getType();
					field.set(object, DataTypeChangeUtils.changeType(type.getSimpleName(),cellText));
				}
			}
			result.add(object);
		}
		return result;
	}
	
	
	
	
}