package com.ebon.v2.rpt.service;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.platform.BaseTest;

public class ExportExcelServiceTest extends BaseTest{

	@Test
	public void testDept() {
		String requestStartDate = "2014/01/01";
		String requestEndDate = "2015/09/09";
		String costSrcPath = "src/main/webapp/template/CostAllocationBaseOnDept.xls";
		try {
			Workbook workbook = exportExcelService.exportDeptExcel(requestStartDate, requestEndDate, costSrcPath);
			this.createFile(workbook);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testCost() {
		String requestStartDate = "2014/01/01";
		String requestEndDate = "2015/09/09";
		String costSrcPath = "src/main/webapp/template/CostAllocationBaseOnCost.xls";
		try {
			Workbook workbook = exportExcelService.exportCostExcel(requestStartDate, requestEndDate, costSrcPath);
			this.createFile(workbook);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createFile(Workbook workbook ) throws Exception{
		FileOutputStream fos = new FileOutputStream(System.currentTimeMillis()+".xls");
		workbook.write(fos);
		fos.flush();
		fos.close();
	}

	@Autowired
	ExportExcelService exportExcelService;
 
	
}
