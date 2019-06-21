package com.aimspeed.common.file.excel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Excel操作工具类
 * @author AimSpeed
 */
public abstract class ExcelUtils {
	
	/**
	 * 日期类型数字
	 */
	private static final int DATE_TYPE = 1;
	
	/**
	 * 货币类型
	 */
	private static final int MONEY_TYPE = 2;
	
	/**
	 * 数量类型
	 */
	private static final int COUNT_TYPE = 3;
	

	/**
	 * 读取单元格数据内容为字符串类型的数据
	 * @author AimSpeed
	 * @param cell 需要读取的单元格
	 * @return String 
	 */
	public static String readCellVal(Cell cell){
    	String cellVal="";
    	if (null != cell) {     
            switch (cell.getCellType()) {     
            case HSSFCell.CELL_TYPE_NUMERIC: // 数字     
            	if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
                    SimpleDateFormat sdf = null;  
                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat  
                            .getBuiltinFormat("h:mm")) {  
                        sdf = new SimpleDateFormat("HH:mm");  
                    } else {// 日期  
                        sdf = new SimpleDateFormat("yyyy-MM-dd");  
                    }  
                    Date date = cell.getDateCellValue();  
                    cellVal = sdf.format(date);  
                } else if (cell.getCellStyle().getDataFormat() == 58) {  
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                    double value = cell.getNumericCellValue();  
                    Date date = org.apache.poi.ss.usermodel.DateUtil  
                            .getJavaDate(value);  
                    cellVal = sdf.format(date);  
                } else {  
                    double value = cell.getNumericCellValue();  
                    CellStyle style = cell.getCellStyle();  
                    DecimalFormat format = new DecimalFormat();  
                    String temp = style.getDataFormatString();  
                    // 单元格设置成常规  
                    if (temp.equals("General")) {  
                        format.applyPattern("#");  
                    }  
                    //cellVal = format.format(value);  
                    cellVal = value+"";  
                }  
                break;     
            case HSSFCell.CELL_TYPE_STRING: // 字符串     
            	cellVal=cell.getStringCellValue();
                break;     
            case HSSFCell.CELL_TYPE_BLANK: // 空值     
            	cellVal="";     
                break;     
            case HSSFCell.CELL_TYPE_ERROR: // 故障     
            	cellVal="";     
                break;     
            default:     
            	cellVal=""; 
                break;     
            }  
    	}
    	return cellVal;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * @author AimSpeed
	 * @param sheet 表单
	 * @param row 行索引
	 * @param column 列索引
	 * @return boolean 成功还是失败
	 */
	public static boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		CellRangeAddress ca;
		for (int i = 0; i < sheetMergeCount; i++) {
			ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if ((row >= firstRow) && (row <= lastRow) && (column >= firstColumn) && (column <= lastColumn)){
				return true;
			}	
		}
		return false;
	}

	/**
	 * 获取合并单元格的值
	 * @author AimSpeed
	 * @param sheet 表单
	 * @param row 行索引
	 * @param column 列索引
	 * @return String 结果数据
	 */
	public static String getMergedRegionValue(Sheet sheet, int row, int column) {
		//得到所有的合并单元格
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			//得到某一个合并单元格
			CellRangeAddress ca = sheet.getMergedRegion(i);
			//起始列
			int firstColumn = ca.getFirstColumn();
			//结束列
			int lastColumn = ca.getLastColumn();
			//起始行
			int firstRow = ca.getFirstRow();
			//结束行
			int lastRow = ca.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return readCellVal(fCell);
				}
			}
		}
		return null;
	}


	/**
	 * 判断指定的单元格的合并单元格类型；0：不是合并单元格；1：行合并单元格；2：列合并单元格
	 * @author AimSpeed
	 * @param sheet 表单
	 * @param row 行索引
	 * @param column 列索引
	 * @return int 
	 */
	public static int getMergedRegionType(Sheet sheet, int row, int column) {
		int result = 0;
		int sheetMergeCount = sheet.getNumMergedRegions();
		CellRangeAddress ca;
		for (int i = 0; i < sheetMergeCount; i++) {
			ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if ((row >= firstRow) && (row <= lastRow) && (column >= firstColumn) && (column <= lastColumn)){
				if((column == firstColumn) && (firstRow != lastRow)){
					result = 1 ;
				}else{
					result = 2 ;
				}
			}	
		}
		return result;
	}

	/**
	 * 判断指定的单元格的合并单元格类型；0：不是合并单元格；1：行合并单元格；2：列合并单元格
	 * @author AimSpeed
	 * @param sheet 表单
	 * @param row 行索引
	 * @param column 列索引
	 * @return int 数量
	 */
	public static int getMergedRegionCount(Sheet sheet, int row, int column) {
		int count = 0;
		int sheetMergeCount = sheet.getNumMergedRegions();
		CellRangeAddress ca;
		for (int i = 0; i < sheetMergeCount; i++) {
			ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if ((row >= firstRow) && (row <= lastRow) && (column >= firstColumn) && (column <= lastColumn)){
				if((column == firstColumn) && (firstRow != lastRow)){	//行
					count = lastRow- firstRow+1;
				}else{	
					count = lastColumn-firstColumn+1;					//列
				}
			}	
		}
		return count;
	}
	

}
