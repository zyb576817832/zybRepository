package com.ebon.v3.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ebon.framework.util.StringUtil;

public class ExportExcelUtil {

	public static void to2007(List<Map<String, Object>> dataList, OutputStream os, List<String> heads, List<String> keys) {
		XSSFWorkbook xssf_w_book = new XSSFWorkbook();
		XSSFSheet xssf_w_sheet = xssf_w_book.createSheet();
		writeHeadRow(xssf_w_book, xssf_w_sheet, heads);
		writeDataRow(xssf_w_book, xssf_w_sheet, dataList, keys);
		try {
			xssf_w_book.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ByteArrayOutputStream to2007(List<Map<String, Object>> dataList, String sheetName, List<String> heads, List<String> keys) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XSSFWorkbook xssf_w_book = new XSSFWorkbook();
        XSSFSheet xssf_w_sheet = xssf_w_book.createSheet(sheetName);
        writeHeadRow(xssf_w_book, xssf_w_sheet, heads);
		writeDataRow(xssf_w_book, xssf_w_sheet, dataList, keys);
        try {
            xssf_w_book.write(os);
            os.flush();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return os;
    }
	
	public static XSSFWorkbook getXSSFWorkbook() {
		XSSFWorkbook xssf_w_book = new XSSFWorkbook();
		return xssf_w_book;
	}
	
	public static ByteArrayOutputStream getOSWithXSSFWorkbook(XSSFWorkbook xssf_w_book) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			xssf_w_book.write(os);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return os;
	}
	
	public static XSSFSheet getXSSFSheet(XSSFWorkbook xssf_w_book, String sheetName) {
		XSSFSheet xssf_w_sheet = xssf_w_book.createSheet(sheetName);
		return xssf_w_sheet;
	}

	public static void writeHeadRow(XSSFWorkbook xssf_w_book, XSSFSheet xssf_w_sheet, List<String> heads) {
		int col_count = heads.size();
		int titleRows = 0; // 标题占据的行数
		XSSFRow xssf_w_row = xssf_w_sheet.createRow(0 + titleRows);// 第一行写入标题行
		XSSFCellStyle head_cellStyle = getHeadCellStyle(xssf_w_book);
		for (int i = 0; i < col_count; i++) {
			XSSFCell xssf_w_cell = xssf_w_row.createCell((short) i);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			XSSFRichTextString xssfString = new XSSFRichTextString(heads.get(i));
			xssf_w_cell.setCellValue(xssfString);
			xssf_w_cell.setCellStyle(head_cellStyle);
			xssf_w_sheet.autoSizeColumn((short) i);
		}
	}
	
	public static Map<String,Object> writeHeadRow2(List<List<String>> headList,List<String> heads) {
		XSSFWorkbook xssf_w_book = new XSSFWorkbook();
		XSSFSheet xssf_w_sheet = xssf_w_book.createSheet("list");
		
		int row_count = headList.size();
		int col_count = heads.size();
		
		for(int j = 0; j < row_count; j++){
			XSSFRow xssf_w_row = xssf_w_sheet.createRow(j);
			XSSFCellStyle head_cellStyle = ExportExcelUtil.getHeadCellStyle(xssf_w_book);
			head_cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中  
			head_cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
			for (int i = 0; i < col_count; i++) {
				XSSFCell xssf_w_cell = xssf_w_row.createCell((short) i);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				XSSFRichTextString xssfString = new XSSFRichTextString(headList.get(j).get(i));
				xssf_w_cell.setCellValue(xssfString);
				xssf_w_cell.setCellStyle(head_cellStyle);
				xssf_w_sheet.autoSizeColumn((short) i);
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("xssf_w_book", xssf_w_book);
		map.put("xssf_w_sheet", xssf_w_sheet);
		return map;
	}

	public static XSSFCellStyle getHeadCellStyle(XSSFWorkbook xssf_w_book) {
		XSSFCellStyle head_cellStyle = xssf_w_book.createCellStyle();// 创建一个单元格样式
		XSSFFont head_font = xssf_w_book.createFont();
		head_font.setFontName("宋体");// 设置头部字体为宋体
		head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
		head_font.setFontHeightInPoints((short) 12);
		head_cellStyle.setFont(head_font);// 单元格样式使用字体
		 
		return head_cellStyle;
	}

	public static void writeDataRow(XSSFWorkbook xssf_w_book, XSSFSheet xssf_w_sheet, List<Map<String, Object>> dataList, List<String> keys) {
		int row_count = dataList.size();
		XSSFCellStyle cellStyle = getDataCellStyle(xssf_w_book);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中  
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中  
		for (int i = 0; i < row_count; i++) {
			XSSFRow xssf_w_row = xssf_w_sheet.createRow(i + 1);// 第一行写入标题行
			Map<String, Object> dataMap = dataList.get(i);
			for (int j = 0; j < keys.size(); j++) {
				XSSFCell xssf_w_cell = xssf_w_row.createCell(j);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				Object value = dataMap.get(keys.get(j));
				XSSFRichTextString xssfString = null;
				if(StringUtil.isNotNull(value)) { 
					//cellStyle.setFillBackgroundColor((short)125);
					// cellStyle.setFillBackgroundColor(HSSFColor.YELLOW.index); 
					xssfString = new XSSFRichTextString(value.toString());
				}else {
					xssfString = new XSSFRichTextString("");
				}
				xssf_w_cell.setCellValue(xssfString);
				xssf_w_cell.setCellStyle(cellStyle);
			}
		}
//		for (int j = 0; j < keys.size(); j++) {
//			xssf_w_sheet.autoSizeColumn(j);
//		}
	}
	
	public static <T> void writeDataRowForList(XSSFWorkbook xssf_w_book, XSSFSheet xssf_w_sheet, List<T> dataList, List<String> keys) {
		/*int row_count = dataList.size();
		XSSFCellStyle cellStyle = getDataCellStyle(xssf_w_book);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中  
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中  
		for (int i = 0; i < row_count; i++) {
			XSSFRow xssf_w_row = xssf_w_sheet.createRow(i + 1);// 第一行写入标题行
			T bussinessObject = dataList.get(i);
			for (int j = 0; j < keys.size(); j++) {
				XSSFCell xssf_w_cell = xssf_w_row.createCell(j);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				Object value = bussinessObject.get(keys.get(j));
				XSSFRichTextString xssfString = null;
				if(StringUtil.isNotNull(value)) { 
					//cellStyle.setFillBackgroundColor((short)125);
					// cellStyle.setFillBackgroundColor(HSSFColor.YELLOW.index); 
					xssfString = new XSSFRichTextString(value.toString());
				}else {
					xssfString = new XSSFRichTextString("");
				}
				xssf_w_cell.setCellValue(xssfString);
				xssf_w_cell.setCellStyle(cellStyle);
			}
		}*/
//		for (int j = 0; j < keys.size(); j++) {
//			xssf_w_sheet.autoSizeColumn(j);
//		}
	}
	
	public static void writeDataRow2(XSSFWorkbook xssf_w_book, XSSFSheet xssf_w_sheet, List<Map<String, Object>> dataList, List<String> keys) {
		int row_count = dataList.size();
		XSSFCellStyle cellStyle = ExportExcelUtil.getDataCellStyle(xssf_w_book);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中  
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中  
		for (int i = 0; i < row_count; i++) {
			XSSFRow xssf_w_row = xssf_w_sheet.createRow(i + 2);// 第一、二行写入标题行
			Map<String, Object> dataMap = dataList.get(i);
			for (int j = 0; j < keys.size(); j++) {
				XSSFCell xssf_w_cell = xssf_w_row.createCell(j);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				Object value = dataMap.get(keys.get(j));
				XSSFRichTextString xssfString = null;
				if(StringUtil.isNotNull(value)) { 
					xssfString = new XSSFRichTextString(value.toString());
				}else {
					xssfString = new XSSFRichTextString("");
				}
				xssf_w_cell.setCellValue(xssfString);
				xssf_w_cell.setCellStyle(cellStyle);
			}
		}
	}
	
	public static void writeDataRowMergedRegion(XSSFWorkbook xssf_w_book, XSSFSheet xssf_w_sheet, List<Map<String, Object>> dataList, List<String> keys) {
		int row_count = dataList.size();
		XSSFCellStyle cellStyle = getDataCellStyle(xssf_w_book);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中  
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中  
		for (int i = 0; i < row_count; i++) {
			XSSFRow xssf_w_row = xssf_w_sheet.createRow(i + 1);// 第一行写入标题行
			Map<String, Object> dataMap = dataList.get(i);
			for (int j = 0; j < keys.size(); j++) {
				XSSFCell xssf_w_cell = xssf_w_row.createCell(j);
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				Object value = dataMap.get(keys.get(j));
				XSSFRichTextString xssfString = null;
				if(StringUtil.isNotNull(value)) { 
					xssfString = new XSSFRichTextString(value.toString());
				}else {
					xssfString = new XSSFRichTextString("");
				}
				/*if (j != 0) {
					xssf_w_cell.setCellValue(xssfString);
					xssf_w_cell.setCellStyle(cellStyle);
				}else{
					if((i%4)==0){
						xssf_w_sheet.addMergedRegion(new CellRangeAddress(i+1,i+4,0,0));
						xssf_w_cell.setCellValue(xssfString);
						xssf_w_cell.setCellStyle(cellStyle);
					}
				}*/
				xssf_w_cell.setCellValue(xssfString);
				xssf_w_cell.setCellStyle(cellStyle);
			}
		}
		//cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
		for (int i = 0; i < row_count; i++) {
			if((i%4)==0){
				xssf_w_sheet.addMergedRegion(new CellRangeAddress(i+1,i+4,0,0));
			}
		}
	}

	public static XSSFCellStyle getDataCellStyle(XSSFWorkbook xssf_w_book) {
		XSSFCellStyle cellStyle = xssf_w_book.createCellStyle();// 创建一个单元格样式
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		return cellStyle;
	}
	 public static void main(String[] args) throws IOException {     
	        HSSFWorkbook wb = new HSSFWorkbook();     
	        HSSFSheet sheet = wb.createSheet("new sheet");     
//	        HSSFRow row = sheet.createRow(1);     
//	        HSSFCell cell = row.createCell((short)1);     
//	        cell.setCellValue("This is a test of merging");     
	        //新版用法 3.8版  
	       sheet.addMergedRegion(new CellRangeAddress(     
	               0, //first row (0-based)  from 行     
	               1, //last row  (0-based)  to 行     
	               0, //first column (0-based) from 列     
	               1  //last column  (0-based)  to 列     
	       ));     
	         FileOutputStream fileOut = new FileOutputStream("E:\\workbook.xls");     
	         wb.write(fileOut);     
	         fileOut.close();     
	     }     
}