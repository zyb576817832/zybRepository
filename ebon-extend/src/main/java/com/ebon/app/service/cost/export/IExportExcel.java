package com.ebon.app.service.cost.export;

import org.apache.poi.ss.usermodel.Workbook;

public interface IExportExcel {
	
	/**
	 * 导出基于成本中心的Excel成本归集表格
	 * 
	 * @param startDate
	 * @param endDate
	 * @throws Exception
	 */
	public Workbook exportCostExcel(String requestStartDate, String requestEndDate, String costSrcPath) throws Exception;
	
	/**
	 * 导出基于部门的Excel成本归集表格
	 * 
	 * @param requestStartDate
	 * @param requestEndDate
	 * @throws Exception
	 */
	public Workbook exportDeptExcel(String requestStartDate, String requestEndDate, String departmentSrcPath) throws Exception;
}
