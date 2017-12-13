package com.ebon.v2.rpt.service;

import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.ebon.app.service.cost.export.IExportExcel;
import com.ebon.platform.dao.DaoException;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.util.StringUtil;
import com.ebon.v2.rpt.model.Cost;

@Component
public class ExportExcelService extends BaseService implements IExportExcel{
 
	/**
	 * @todo
	 */
	private void loadSapFee(){
		
	}
	
	private void fillWorkbook(Workbook workbook, List costs, String paramStartDate, String paramEndDate) {
		Sheet sheet = workbook.getSheetAt(0);
		this.writeRptHeader(workbook, sheet, paramStartDate, paramEndDate);
		this.writeRptData(workbook, sheet,costs);
	}
	
	
	private void writeRptData(Workbook workbook, Sheet sheet, List costs) {
		
		for(int i=0;i<costs.size();i++){
			
			Cost cost = (Cost)costs.get(i);				
			Row row = sheet.createRow(i+4);
			
			row.createCell(0).setCellValue(cost.getProjectCode());
			row.createCell(1).setCellValue(cost.getProjectInternalOrder());
			row.createCell(2).setCellValue(cost.getProjectDescription());
			row.createCell(3).setCellValue(cost.getProjectCategory());
			row.createCell(4).setCellValue(cost.getProjectHnte());
			row.createCell(5).setCellValue(cost.getProjectTechCategory());
			row.createCell(6).setCellValue(cost.getPh1());
			row.createCell(7).setCellValue(cost.getPh2());
			row.createCell(8).setCellValue(cost.getPh1Code());
			row.createCell(9).setCellValue(cost.getPh2Code());
			row.createCell(10).setCellValue(cost.getBu());
			row.createCell(11).setCellValue(cost.getCustomerGroupCode());
			row.createCell(12).setCellValue(cost.getSapCode());
			row.createCell(13).setCellValue(cost.getSapName());
			
			row.createCell(14).setCellValue(cost.getEmployeeDept());
			row.createCell(15).setCellValue(cost.getEmployeeRate());
			if (StringUtil.isNotNull(cost.getEmployeeHours())){
				row.createCell(16).setCellValue(Double.valueOf(cost.getEmployeeHours()));
			}
			if (StringUtil.isNotNull(cost.getEmployeeAmount())){
				row.createCell(17).setCellValue(Double.valueOf(cost.getEmployeeAmount()));
			}
			
			row.createCell(18).setCellValue(cost.getEquipmentDept());
			row.createCell(19).setCellValue(cost.getEquipmentGroup());
			row.createCell(20).setCellValue(cost.getEquipmentName());
			row.createCell(21).setCellValue(cost.getEquipmentRate());
			if (StringUtil.isNotNull(cost.getEquipmentHours())){
				row.createCell(22).setCellValue(Double.valueOf(cost.getEquipmentHours()));
			}
			if (StringUtil.isNotNull(cost.getEuqipmentAmount())){
				row.createCell(23).setCellValue(Double.valueOf(cost.getEuqipmentAmount()));
			}
			
			row.createCell(24).setCellValue(cost.getCostCenter());
			row.createCell(25).setCellValue(cost.getCostElement());
			row.createCell(26).setCellValue(cost.getCostTransationtype());
			if (StringUtil.isNotNull(cost.getCostTransationcost())){
				row.createCell(27).setCellValue(Double.valueOf(cost.getCostTransationcost()));
			}
			if (StringUtil.isNotNull(cost.getCostCOCost())){
				row.createCell(28).setCellValue(Double.valueOf(cost.getCostCOCost()));
			}
			if (StringUtil.isNotNull(cost.getTotalProjectCost())){
				row.createCell(29).setCellValue(Double.valueOf(cost.getTotalProjectCost()));
			}
		}		
	
	}


	/**
	 * 往Excel中写入开始-结束日期
	 * 
	 * @param sheet
	 * @param paramStartDate
	 * @param paramEndDate
	 */
	private static void writeRptHeader(Workbook wb, Sheet sheet, String paramStartDate, String paramEndDate) {
		
		Row dateRow = sheet.getRow(0);
		Cell dateCell = dateRow.createCell(14);
		CellStyle styleCenter = wb.createCellStyle();
		styleCenter.setAlignment(CellStyle.ALIGN_CENTER);
		dateCell.setCellValue(paramStartDate + " - " + paramEndDate);
		dateCell.setCellStyle(styleCenter);
	}


	private Workbook getWorkbook( String costSrcPath) throws Exception{
		FileInputStream fis = fis = new FileInputStream(costSrcPath);
		return new HSSFWorkbook(fis);
	}
	
	@Override
	public Workbook exportCostExcel(String requestStartDate,
			String requestEndDate, String costSrcPath) throws Exception {
		loadSapFee();
		List costs = this.getCosts(requestStartDate, requestEndDate);
		Workbook  workbook = getWorkbook(costSrcPath);
		fillWorkbook(workbook,costs, requestStartDate, requestEndDate);
		return workbook;
	}
	
	/**
	 * 给成本归集报表准备报表数据
	 * @param requestStartDate
	 * @param requestEndDate
	 * @return
	 * @throws DaoException
	 */
	private List getCosts(String requestStartDate,
			String requestEndDate) throws DaoException{
		java.util.HashMap<String,String> params = new java.util.HashMap<String,String>();
		params.put("requestStartDate", requestStartDate);
		params.put("requestEndDate", requestEndDate);
		// 通过这个SQL生成整个成本归集报表的原始数据
		return this.myBatisDao.getList("V2Mapper.selectCost", params);
	}
	
	@Override
	public Workbook exportDeptExcel(String requestStartDate,
			String requestEndDate, String departmentSrcPath) throws Exception {
		loadSapFee();
		List costs = this.getCostsByDept(requestStartDate, requestEndDate);
		Workbook  workbook = getWorkbook(departmentSrcPath);
		fillWorkbook(workbook,costs, requestStartDate, requestEndDate);
		return workbook;
	}
	
	private List getCostsByDept(String requestStartDate,
			String requestEndDate) throws DaoException{
		java.util.HashMap<String,String> params = new java.util.HashMap<String,String>();
		params.put("requestStartDate", requestStartDate);
		params.put("requestEndDate", requestEndDate);
		
		return this.myBatisDao.getList("V2Mapper.selectCostByDept", params);
	}
	

}
