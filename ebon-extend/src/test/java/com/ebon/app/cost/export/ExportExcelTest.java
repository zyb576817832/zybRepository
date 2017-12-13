package com.ebon.app.cost.export;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.app.service.cost.export.IExportExcel;

public class ExportExcelTest {
	@Autowired
	public IExportExcel	exprotExcel;
	
	public IExportExcel getExprotExcel() {
		return exprotExcel;
	}
	
	public void setExprotExcel(IExportExcel exprotExcel) {
		this.exprotExcel = exprotExcel;
	}
	
	@Test
	public void testExportCostExcel() {
		FileOutputStream fos = null;
		try {
			// FileInputStream fis = new FileInputStream("d:/a1.xls");
			Workbook wb = new HSSFWorkbook();
			wb = exprotExcel.exportCostExcel("2011-1-1", "2011-12-31", "d:/CostAllocationBaseOnCost.xls");
			fos = new FileOutputStream("d:/20120723_2011year01.xls");
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fos) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testExportDeptExcel() {
		FileOutputStream fos = null;
		try {
			Workbook wb = exprotExcel.exportDeptExcel("2011-1-1", "2011-12-31", "d:/CostAllocationBaseOnDept.xls");
			fos = new FileOutputStream("d:/aaa11.xls");
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fos) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
