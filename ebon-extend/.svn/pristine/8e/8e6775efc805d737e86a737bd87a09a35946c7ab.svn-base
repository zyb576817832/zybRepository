package com.ebon.platform.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;



public class ExcelUtil {
	
	public static final int MAX_SHEET_ROW = 65534;
	/**
	 * 将数据导出到文件中
	 * @param srcPath  源路径
	 * @param exportPath  导出路径
	 * @param exportData  导出数据
	 */
	public static void exportExcelFiles(String srcPath, String exportPath, List<Map<String,Object>> exportData){
		HSSFWorkbook workbook = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		int sheetCounter = 0;
		int rowCounter = 4;
		int dataCounter = 0;
		try {
			fis = new FileInputStream(srcPath);
			workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = getNewSheet(workbook, "成本归集", fis, 0);
			System.out.println(sheet.getRow(0));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 新建一个工作页
	 * @param workbook 目标工作簿
	 * @param sheetName 工作页名字
	 * @param fis 是否有读取模板
	 * @param sheetCount  读取的模板工作页的索引
	 * @return
	 */
	private static HSSFSheet getNewSheet (HSSFWorkbook workbook,String sheetName,FileInputStream fis, int sheetCount){
		HSSFSheet sheet = workbook.createSheet(sheetName);
		if(null != fis){
			try {
				HSSFWorkbook workbookModel = new HSSFWorkbook(fis);
				sheet = workbookModel.getSheetAt(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sheet;
	}
}
