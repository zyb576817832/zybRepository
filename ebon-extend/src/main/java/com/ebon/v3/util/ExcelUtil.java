package com.ebon.v3.util;

import java.math.BigDecimal; 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ebon.framework.bo.BusinessObject;

/**
 * 需要拓展
 * @ClassName: ExcelUtil 
 * @Description: 
 * @author g 
 * @date 2015年5月20日 下午3:57:17
 */
public class ExcelUtil extends ExportExcelUtil {

	public static XSSFWorkbook createXSSFWorkbook(
			List<Map<String, Object>> dataList, 
			String sheetName,
			List<String> heads, 
			List<String> keys) {
		XSSFWorkbook xssf_w_book = new XSSFWorkbook();
		XSSFSheet xssf_w_sheet = xssf_w_book.createSheet(sheetName);
		writeHeadRow(xssf_w_book, xssf_w_sheet, heads);
		writeDataRow(xssf_w_book, xssf_w_sheet, dataList, keys);

		return xssf_w_book;
	}	
	/**
	 * 
	 * @param dataList
	 * @param sheetName
	 * @param heads
	 * @param keys
	 * @return
	 */
	public static <T> XSSFWorkbook createXSSFWorkbooks(
			List<T> dataList, 
			String sheetName,
			List<String> heads, 
			List<String> keys) {
		XSSFWorkbook xssf_w_book = new XSSFWorkbook();
		XSSFSheet xssf_w_sheet = xssf_w_book.createSheet(sheetName);
		writeHeadRow(xssf_w_book, xssf_w_sheet, heads);
		writeDataRowForList(xssf_w_book, xssf_w_sheet, dataList, keys);

		return xssf_w_book;
	}	
	
	
	public static XSSFWorkbook createXSSFWorkbookMergedRegion(
			List<Map<String, Object>> dataList, 
			String sheetName,
			List<String> heads, 
			List<String> keys) {
		XSSFWorkbook xssf_w_book = new XSSFWorkbook();
		XSSFSheet xssf_w_sheet = xssf_w_book.createSheet(sheetName);
		writeHeadRow(xssf_w_book, xssf_w_sheet, heads);
		writeDataRowMergedRegion(xssf_w_book, xssf_w_sheet, dataList, keys);

		return xssf_w_book;
	}	
	
	/**
	 * 
	 * @Title: getCellString 
	 * @Description: 
	 * @param @param cell
	 * @param @return
	 * @return String
	 * @throws 
	 * @Author g
	 * @createDate 2015年5月20日 下午4:55:57
	 */
	public static String getCellString(Cell cell) {
        String result = null;
        if(cell != null){
            //单元格类型：Numeric:0,String:1,Formula:2,Blank:3,Boolean:4,Error:5
            int cellType = cell.getCellType();
            switch (cellType) {
            case Cell.CELL_TYPE_STRING:
                result = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
				if(HSSFDateUtil.isCellDateFormatted(cell))
				{
					
					Date date  = cell.getDateCellValue(); //如果excel单元格格式设置的为时间，这里返回Date类型时间格式
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					result = sf.format(date);
				
				}else{
					Double numericCellValue = cell.getNumericCellValue();  
					BigDecimal db = new BigDecimal(numericCellValue.toString());//转换科学计数法形势的数字格式
			    	result = db.toPlainString();
				}
				break;
            case Cell.CELL_TYPE_FORMULA:
                Double numericCellValue = cell.getNumericCellValue();
                result = numericCellValue.toString();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                Boolean booleanCellValue = cell.getBooleanCellValue();
                result = booleanCellValue.toString();
                break;
            case Cell.CELL_TYPE_BLANK:
                result = null;
                break;
            case Cell.CELL_TYPE_ERROR:
                result = null;
                break;
            default:
                System.out.println("枚举了所有类型");
                break;
            }
        }
        return result;
    }
	
	
	
	
}
