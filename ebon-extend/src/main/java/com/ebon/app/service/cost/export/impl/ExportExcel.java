package com.ebon.app.service.cost.export.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebon.app.service.cost.export.IExportExcel;
import com.ebon.app.service.cost.person.IPersonService;
import com.ebon.app.service.nonproj.INonProjectService;
import com.ebon.app.service.project.IProjectService;
import com.ebon.app.service.tef.ITefService;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.service.ServiceException;
import com.ebon.platform.util.DateHelper;
import com.ebon.platform.util.StringUtil;
import com.ebon.rpc.equipment.IEquipmentService;
import com.ebon.rpc.sap.service.IThreeFeeService;

@Service
public class ExportExcel extends BaseService implements IExportExcel {
	
	private static final String	personKey		= "person";
	
	private static final String	sapKey			= "sap";
	
	private static final String	equipmentKey	= "equipment";
	
	private static final String	nonProjectKey	= "nonProject";
	
	@Autowired
	private IProjectService		projectService;
	
	@Autowired
	private IEquipmentService	equipmentService;
	
	@Autowired
	private IThreeFeeService	threeFeeService;
	
	@Autowired
	private ITefService			tefService;
	
	@Autowired
	private IPersonService		personService;
	
	@Autowired
	private INonProjectService	nonProjectService;
	
	@Override
	public Workbook exportCostExcel(String requestStartDate, String requestEndDate, String costSrcPath) throws ServiceException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> paramMapForEquipment = null;
		paramMap.put("startDate", requestStartDate);
		paramMap.put("endDate", requestEndDate);
		// 接收数据
		FileInputStream fis = null;
		Workbook wb = null;
		try {
			paramMapForEquipment = matchDateForEquipment(requestStartDate, requestEndDate);
			List<Map<String, Object>> equipList = equipmentService.getEquipmentInfo(paramMapForEquipment);
			List<Map<String, Object>> sapList = threeFeeService.getSAPInfo(paramMap);
			List<Map<String, Object>> personelList = personService.getPersonelInfo(paramMap);
			fis = new FileInputStream(costSrcPath);
			// 设置缓冲区
			wb = new HSSFWorkbook(fis);
			Sheet sheet = wb.getSheetAt(0);
			// 向Excel中写入查询的日期信息
			exportDateData(wb, sheet, requestStartDate, requestEndDate);
			// 新构建数据
			Map<String, Map<String, List<Map<String, Object>>>> newData = this.rebuilderData(equipList, sapList, personelList);
			int rowNum = 4;
			int baseRowNum = 0;
			TreeSet<String> keySet = new TreeSet<String>(newData.keySet());
			
			for (String projNo : keySet) {
				double projSumCount = 0;
				baseRowNum = rowNum;
				Map<String, List<Map<String, Object>>> dataMap = newData.get(projNo);
				Map<String, Object> projectMap = projectService.getProjectInfoById(projNo);
				if (null != projectMap) {
					Map<String, Object> tempMap = null;
					List<Map<String, Object>> personData = dataMap.get(ExportExcel.personKey);
					tempMap = this.setPersonToExcel(personData, projectMap, sheet, rowNum, projSumCount);
					rowNum = (Integer) tempMap.get("rowNum");
					projSumCount = (Double) tempMap.get("projSumCount");
					List<Map<String, Object>> sapData = dataMap.get(ExportExcel.sapKey);
					tempMap = this.setSapToExcel(sapData, projectMap, sheet, rowNum, "sapCostCenter", projSumCount);
					rowNum = (Integer) tempMap.get("rowNum");
					projSumCount = (Double) tempMap.get("projSumCount");
					List<Map<String, Object>> equipmentData = dataMap.get(ExportExcel.equipmentKey);
					tempMap = this.setEquipmentToExcel(equipmentData, projectMap, sheet, rowNum, projSumCount);
					rowNum = (Integer) tempMap.get("rowNum");
					projSumCount = (Double) tempMap.get("projSumCount");
					int endRowNum = rowNum;
					writeTotalProjCost(sheet, baseRowNum, projSumCount, endRowNum);
				}
			}
			// 非项目报工
			List<Map<String, Object>> nonProjectList = nonProjectService.getNonProjectInfo(paramMap);
			List<Map<String, Object>> sapListNp = threeFeeService.getSAPInfoForNonProject(paramMap);
			Map<String, Map<String, List<Map<String, Object>>>> newNPData = this.rebuilderData(nonProjectList, sapListNp);
			TreeSet<String> keySet2 = new TreeSet<String>(newNPData.keySet());
			for (String nonProjNo : keySet2) {
				double projSumCount = 0;
				baseRowNum = rowNum;
				Map<String, List<Map<String, Object>>> nonProjectDataMap = newNPData.get(nonProjNo);
				Map<String, Object> nonProjectMap = nonProjectService.getNonProjectInfoById(nonProjNo);
				if (null != nonProjectMap) {
					Map<String, Object> tempMap = null;
					List<Map<String, Object>> nonProjectData = nonProjectDataMap.get(ExportExcel.nonProjectKey);
					tempMap = this.setNonProjectToExcel(nonProjectData, nonProjectMap, sheet, rowNum, projSumCount);
					rowNum = (Integer) tempMap.get("rowNum");
					projSumCount = (Double) tempMap.get("projSumCount");
					List<Map<String, Object>> sapNpData = nonProjectDataMap.get(ExportExcel.sapKey);
					tempMap = this.setSapToExcel(sapNpData, nonProjectMap, sheet, rowNum, "sapCostCenter", projSumCount);
					rowNum = (Integer) tempMap.get("rowNum");
					projSumCount = (Double) tempMap.get("projSumCount");
					
					int endRowNum = rowNum;
					writeTotalProjCost(sheet, baseRowNum, projSumCount, endRowNum);
				}
			}
			// TEF报工
			List<Map<String, Object>> tefList = tefService.getTefInfo(paramMap);
			Map<String, List<Map<String, Object>>> newTefData = this.rebuilderData(tefList);
			TreeSet<String> keySet3 = new TreeSet<String>(newTefData.keySet());
			for (String tefNo : keySet3) {
				double projSumCount = 0;
				baseRowNum = rowNum;
				Map<String, Object> tempMap = null;
				
				List<Map<String, Object>> dataLsit = newTefData.get(tefNo);
				tempMap = this.setTefToExcel(dataLsit, sheet, rowNum, projSumCount);
				rowNum = (Integer) tempMap.get("rowNum");
				projSumCount = (Double) tempMap.get("projSumCount");
				
				int endRowNum = rowNum;
				writeTotalProjCost(sheet, baseRowNum, projSumCount, endRowNum);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw new ServiceException(e);
		} finally {
			try {
				if (null != fis) {
					fis.close();
				}
			} catch (IOException e) {
				log.error(e);
				throw new ServiceException(e);
			}
			
		}
		return wb;
	}
	
	@Override
	public Workbook exportDeptExcel(String requestStartDate, String requestEndDate, String departmentSrcPath) throws ServiceException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> paramMapForEquipment = null;
		paramMap.put("startDate", requestStartDate);
		paramMap.put("endDate", requestEndDate);
		// 接收数据
		FileInputStream fis = null;
		Workbook wb = null;
		try {
			paramMapForEquipment = matchDateForEquipment(requestStartDate, requestEndDate);
			List<Map<String, Object>> equipList = equipmentService.getEquipmentInfo(paramMapForEquipment);
			List<Map<String, Object>> sapList = threeFeeService.getSAPInfo(paramMap);
			List<Map<String, Object>> personelList = personService.getPersonelInfo(paramMap);
			// 设置缓冲区
			fis = new FileInputStream(departmentSrcPath);
			wb = new HSSFWorkbook(fis);
			Sheet sheet = wb.getSheetAt(0);
			// 向Excel中写入查询的日期信息
			exportDateData(wb, sheet, requestStartDate, requestEndDate);
			// 新构建数据
			Map<String, Map<String, List<Map<String, Object>>>> newData = this.rebuilderData(equipList, sapList, personelList);
			int rowNum = 4;
			int baseRowNum = 0;
			TreeSet<String> keySet = new TreeSet<String>(newData.keySet());
			for (String projNo : keySet) {
				double projSumCount = 0;
				baseRowNum = rowNum;
				Map<String, List<Map<String, Object>>> dataMap = newData.get(projNo);
				Map<String, Object> projectMap = projectService.getProjectInfoById(projNo);
				if (null != projectMap) {
					Map<String, Object> tempMap = null;
					List<Map<String, Object>> personData = dataMap.get(ExportExcel.personKey);
					tempMap = this.setPersonDeptToExcel(personData, projectMap, sheet, rowNum, projSumCount);
					rowNum = (Integer) tempMap.get("rowNum");
					projSumCount = (Double) tempMap.get("projSumCount");
					List<Map<String, Object>> sapData = dataMap.get(ExportExcel.sapKey);
					tempMap = this.setSapToExcel(sapData, projectMap, sheet, rowNum, "sapDeptName", projSumCount);
					rowNum = (Integer) tempMap.get("rowNum");
					projSumCount = (Double) tempMap.get("projSumCount");
					List<Map<String, Object>> equipmentData = dataMap.get(ExportExcel.equipmentKey);
					tempMap = this.setEquipmentToExcel(equipmentData, projectMap, sheet, rowNum, projSumCount);
					rowNum = (Integer) tempMap.get("rowNum");
					projSumCount = (Double) tempMap.get("projSumCount");
					
					int endRowNum = rowNum;
					writeTotalProjCost(sheet, baseRowNum, projSumCount, endRowNum);
				}
			}
			//非项目报工
			List<Map<String, Object>> nonProjectList = nonProjectService.getNonProjectInfo(paramMap);
			List<Map<String, Object>> sapListNp = threeFeeService.getSAPInfoForNonProject(paramMap);
			Map<String, Map<String, List<Map<String, Object>>>> newNPData = this.rebuilderData(nonProjectList, sapListNp);
			TreeSet<String> keySet2 = new TreeSet<String>(newNPData.keySet());
			for (String nonProjNo : keySet2) {
				double projSumCount = 0;
				baseRowNum = rowNum;
				Map<String, List<Map<String, Object>>> nonProjectDataMap = newNPData.get(nonProjNo);
				Map<String, Object> nonProjectMap = nonProjectService.getNonProjectInfoById(nonProjNo);
				if (null != nonProjectMap) {
					Map<String, Object> tempMap = null;
					List<Map<String, Object>> nonProjectData = nonProjectDataMap.get(ExportExcel.nonProjectKey);
					tempMap = this.setNonProjectDeptToExcel(nonProjectData, nonProjectMap, sheet, rowNum, projSumCount);
					rowNum = (Integer) tempMap.get("rowNum");
					projSumCount = (Double) tempMap.get("projSumCount");
					List<Map<String, Object>> sapNpData = nonProjectDataMap.get(ExportExcel.sapKey);
					tempMap = this.setSapToExcel(sapNpData, nonProjectMap, sheet, rowNum, "sapDeptName", projSumCount);
					rowNum = (Integer) tempMap.get("rowNum");
					projSumCount = (Double) tempMap.get("projSumCount");
					
					int endRowNum = rowNum;
					writeTotalProjCost(sheet, baseRowNum, projSumCount, endRowNum);
				}
			}
			// TEF报工
			List<Map<String, Object>> tefList = tefService.getTefInfo(paramMap);
			Map<String, List<Map<String, Object>>> newTefData = this.rebuilderData(tefList);
			TreeSet<String> keySet3 = new TreeSet<String>(newTefData.keySet());
			for (String tefNo : keySet3) {
				double projSumCount = 0;
				baseRowNum = rowNum;
				Map<String, Object> tempMap = null;
				
				List<Map<String, Object>> dataLsit = newTefData.get(tefNo);
				tempMap = this.setTefToExcel(dataLsit, sheet, rowNum, projSumCount);
				rowNum = (Integer) tempMap.get("rowNum");
				projSumCount = (Double) tempMap.get("projSumCount");
				int endRowNum = rowNum;
				writeTotalProjCost(sheet, baseRowNum, projSumCount, endRowNum);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw new ServiceException();
		} finally {
			try {
				if (null != fis) {
					fis.close();
				}
			} catch (IOException e) {
				log.error(e);
				throw new ServiceException(e);
			}
		}
		return wb;
	}
	
	/**
	 * 记录某项目的总费用
	 * 
	 * @param sheet
	 * @param baseRowNum
	 * @param projSumCount
	 * @param endRowNum
	 */
	private void writeTotalProjCost(Sheet sheet, int baseRowNum, double projSumCount, int endRowNum) {
		if (endRowNum > baseRowNum) {
			Row row = sheet.getRow(baseRowNum);
			Cell cell = row.createCell(28);
			cell.setCellValue(projSumCount);
			sheet.addMergedRegion(new CellRangeAddress(baseRowNum, endRowNum - 1, 28, 28));
		}
	}
	
	/**
	 * 成本中心人员部分
	 * 
	 * @param personelList
	 * @param projectMap
	 * @param cacheProjMap
	 * @param row
	 * @param sheet
	 * @param rowNum
	 */
	private Map<String, Object> setPersonToExcel(List<Map<String, Object>> personelData, Map<String, Object> projectMap, Sheet sheet, int rowNum, double projSumCount) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		if (StringUtil.isNotNull4List(personelData)) {
			Map<String, List<Map<String, Object>>> personMap = this.rebuilderList(personelData, "personelCostCenter");
			Set<String> keySet = personMap.keySet();
			for (String personelCostCenter : keySet) {
				if (!StringUtil.isNotNull(personelCostCenter)) {
					continue;
				}
				List<Map<String, Object>> personList = personMap.get(personelCostCenter);
				Double personelHourRate = 0.00; // 人员费率
				Double personHour = 0.00; // 人员工时
				BigDecimal personelHourRateFromMap = (BigDecimal) personList.get(0).get("personelHourRate");
				if (null == personelHourRateFromMap) {
					personelHourRateFromMap = new BigDecimal(0.00);
				}
				personelHourRate = personelHourRateFromMap.doubleValue();
				for (Map<String, Object> map : personList) {
					BigDecimal personelHourFromMap = (BigDecimal) map.get("personelHour");
					if (null == personelHourFromMap) {
						personelHourFromMap = new BigDecimal(0.00);
					}
					personHour = personHour + personelHourFromMap.doubleValue();
				}
				projSumCount += personelHourRate * personHour;
				Row row = sheet.createRow(rowNum);
				this.setProjInfoToExcel(row, projectMap);
				row.createCell(13).setCellValue(personelCostCenter);
				row.createCell(14).setCellValue(personelHourRate);
				row.createCell(15).setCellValue(personHour);
				// 定位计算
				CellReference cr1 = new CellReference(row.getRowNum(), row.getCell(14).getColumnIndex());
				CellReference cr2 = new CellReference(row.getRowNum(), row.getCell(15).getColumnIndex());
				row.createCell(16).setCellFormula(cr1.formatAsString() + "*" + cr2.formatAsString());
				rowNum++;
			}
		}
		tempMap.put("rowNum", rowNum);
		tempMap.put("projSumCount", projSumCount);
		return tempMap;
	}
	
	/**
	 * 部门人员部分
	 * 
	 * @param personelData
	 * @param projectMap
	 * @param sheet
	 * @param rowNum
	 * @return
	 */
	private Map<String, Object> setPersonDeptToExcel(List<Map<String, Object>> personelData, Map<String, Object> projectMap, Sheet sheet, int rowNum, double projSumCount) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		if (StringUtil.isNotNull4List(personelData)) {
			Map<String, List<Map<String, Object>>> personMap = this.rebuilderList(personelData, "personelDeptName");
			Set<String> keySet = personMap.keySet();
			for (String personelDept : keySet) {
				if (!StringUtil.isNotNull(personelDept)) {
					continue;
				}
				List<Map<String, Object>> personList = personMap.get(personelDept);
				Double personelHourRate = 0.00; // 人员费率
				Double personHour = 0.00; // 人员工时
				Double personHourSum = 0.00;
				Double personAmount = 0.00; // 人员费用
				Double personAmountSum = 0.00;
				for (Map<String, Object> map : personList) {
					BigDecimal personelHourRateFromMap = (BigDecimal) map.get("personelHourRate");
					if (null == personelHourRateFromMap) {
						personelHourRateFromMap = new BigDecimal(0.00);
					}
					personelHourRate = personelHourRateFromMap.doubleValue();
					BigDecimal personelHourFromMap = (BigDecimal) map.get("personelHour");
					if (null == personelHourFromMap) {
						personelHourFromMap = new BigDecimal(0.00);
					}
					personHour = personelHourFromMap.doubleValue();
					personHourSum = personHourSum + personHour;
					personAmount = personelHourRate * personHour;
					personAmountSum = personAmountSum + personAmount;
				}
				projSumCount += personAmountSum;
				Row row = sheet.createRow(rowNum);
				this.setProjInfoToExcel(row, projectMap);
				row.createCell(13).setCellValue(personelDept);
				row.createCell(15).setCellValue(personHourSum);
				row.createCell(16).setCellValue(personAmountSum);
				if (0 == personHour.doubleValue()) {
					row.createCell(14).setCellValue(0);
				} else {
					row.createCell(14);
					// 定位计算
					CellReference cr1 = new CellReference(row.getRowNum(), row.getCell(16).getColumnIndex());
					CellReference cr2 = new CellReference(row.getRowNum(), row.getCell(15).getColumnIndex());
					row.createCell(14).setCellFormula(cr1.formatAsString() + "/" + cr2.formatAsString());
				}
				rowNum++;
			}
		}
		tempMap.put("rowNum", rowNum);
		tempMap.put("projSumCount", projSumCount);
		return tempMap;
	}
	
	/**
	 * 成本中心非项目报工部分
	 * 
	 * @param nonProjectData
	 * @param projectMap
	 * @param sheet
	 * @param rowNum
	 * @return
	 */
	private Map<String, Object> setNonProjectToExcel(List<Map<String, Object>> nonProjectData, Map<String, Object> projectMap, Sheet sheet, int rowNum, double projSumCount) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		if (StringUtil.isNotNull4List(nonProjectData)) {
			Map<String, List<Map<String, Object>>> nonProjectMap = this.rebuilderList(nonProjectData, "nonProjectCostCenter");
			Set<String> keySet = nonProjectMap.keySet();
			for (String nonProjectCostCenter : keySet) {
				if (!StringUtil.isNotNull(nonProjectCostCenter)) {
					continue;
				}
				List<Map<String, Object>> nonProjectList = nonProjectMap.get(nonProjectCostCenter);
				Double nonProjectHourRate = 0.00; // 非项目报工费率
				Double nonProjectHour = 0.00; // 非项目报工工时
				BigDecimal nonProjectHourRateFromMap = (BigDecimal) nonProjectList.get(0).get("nonProjectHourRate");
				if (null == nonProjectHourRateFromMap) {
					nonProjectHourRateFromMap = new BigDecimal(0.00);
				}
				nonProjectHourRate = nonProjectHourRateFromMap.doubleValue();
				for (Map<String, Object> map : nonProjectList) {
					BigDecimal nonProjectHourFromMap = (BigDecimal) map.get("nonProjectHour");
					if (null == nonProjectHourFromMap) {
						nonProjectHourFromMap = new BigDecimal(0.00);
					}
					nonProjectHour = nonProjectHour + nonProjectHourFromMap.doubleValue();
				}
				projSumCount += nonProjectHourRate * nonProjectHour;
				Row row = sheet.createRow(rowNum);
				this.setProjInfoToExcel(row, projectMap);
				row.createCell(13).setCellValue(nonProjectCostCenter);
				row.createCell(14).setCellValue(nonProjectHourRate);
				row.createCell(15).setCellValue(nonProjectHour);
				row.createCell(16);
				// 定位计算
				CellReference cr1 = new CellReference(row.getRowNum(), row.getCell(14).getColumnIndex());
				CellReference cr2 = new CellReference(row.getRowNum(), row.getCell(15).getColumnIndex());
				row.createCell(16).setCellFormula(cr1.formatAsString() + "*" + cr2.formatAsString());
				rowNum++;
			}
		}
		tempMap.put("rowNum", rowNum);
		tempMap.put("projSumCount", projSumCount);
		return tempMap;
	}
	
	/**
	 * 部门非项目报工部分
	 * 
	 * @param nonProjectData
	 * @param projectMap
	 * @param cacheCostCenterList
	 * @param sheet
	 * @param rowNum
	 * @return
	 */
	private Map<String, Object> setNonProjectDeptToExcel(List<Map<String, Object>> nonProjectData, Map<String, Object> projectMap, Sheet sheet, int rowNum, double projSumCount) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		if (StringUtil.isNotNull4List(nonProjectData)) {
			Map<String, List<Map<String, Object>>> nonProjectMap = this.rebuilderList(nonProjectData, "nonProjectDeptName");
			Set<String> keySet = nonProjectMap.keySet();
			for (String nonProjectDept : keySet) {
				if (!StringUtil.isNotNull(nonProjectDept)) {
					continue;
				}
				List<Map<String, Object>> nonProjectList = nonProjectMap.get(nonProjectDept);
				Double nonProjectHourRate = 0.00; // 人员费率
				Double nonProjectHour = 0.00; // 人员工时
				Double nonProjectHourSum = 0.00;
				Double nonProjectAmount = 0.00; // 人员费用
				Double nonProjectAmountSum = 0.00;
				for (Map<String, Object> map : nonProjectList) {
					BigDecimal nonProjectHourRateFromMap = (BigDecimal) map.get("nonProjectHourRate");
					if (null == nonProjectHourRateFromMap) {
						nonProjectHourRateFromMap = new BigDecimal(0.00);
					}
					nonProjectHourRate = nonProjectHourRateFromMap.doubleValue();
					BigDecimal nonProjectHourFromMap = (BigDecimal) map.get("nonProjectHour");
					if (null == nonProjectHourFromMap) {
						nonProjectHourFromMap = new BigDecimal(0.00);
					}
					nonProjectHour = nonProjectHourFromMap.doubleValue();
					nonProjectHourSum = nonProjectHourSum + nonProjectHour;
					nonProjectAmount = nonProjectHourRate * nonProjectHour;
					nonProjectAmountSum = nonProjectAmountSum + nonProjectAmount;
				}
				projSumCount += nonProjectAmountSum;
				Row row = sheet.createRow(rowNum);
				this.setProjInfoToExcel(row, projectMap);
				row.createCell(13).setCellValue(nonProjectDept);
				row.createCell(15).setCellValue(nonProjectHourSum);
				row.createCell(16).setCellValue(nonProjectAmountSum);
				if (0 == nonProjectHour.doubleValue()) {
					row.createCell(14).setCellValue(0);
				} else {
					row.createCell(14);
					// 定位计算
					CellReference cr1 = new CellReference(row.getRowNum(), row.getCell(16).getColumnIndex());
					CellReference cr2 = new CellReference(row.getRowNum(), row.getCell(15).getColumnIndex());
					row.createCell(14).setCellFormula(cr1.formatAsString() + "/" + cr2.formatAsString());
				}
				rowNum++;
			}
		}
		tempMap.put("rowNum", rowNum);
		tempMap.put("projSumCount", projSumCount);
		return tempMap;
	}
	
	/**
	 * TEF部分
	 * 
	 * @param tefData
	 * @param sheet
	 * @param rowNum
	 * @return
	 */
	private Map<String, Object> setTefToExcel(List<Map<String, Object>> tefData, Sheet sheet, int rowNum, double projSumCount) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		if (StringUtil.isNotNull4List(tefData)) {
			Map<String, List<Map<String, Object>>> tefMap = this.rebuilderList(tefData, "tefCostCenterName");
			Set<String> keySet = tefMap.keySet();
			for (String tefCostCenterName : keySet) {
				if (!StringUtil.isNotNull(tefCostCenterName)) {
					continue;
				}
				List<Map<String, Object>> tefList = tefMap.get(tefCostCenterName);
				Double tefHourRate = 0.00; // tef费率
				Double tefHour = 0.00; // tef工时
				Double tefAmount = 0.00; // tef费用
				Double tefHourSum = 0.00;
				Double tefAmountSum = 0.00;
				for (Map<String, Object> map : tefList) {
					BigDecimal tefHourRateFromMap = (BigDecimal) map.get("tefHourRate");
					if (null == tefHourRateFromMap) {
						tefHourRateFromMap = new BigDecimal(0.00);
					}
					tefHourRate = tefHourRateFromMap.doubleValue();
					BigDecimal tefHourFromMap = (BigDecimal) map.get("tefHour");
					if (null == tefHourFromMap) {
						tefHourFromMap = new BigDecimal(0.00);
					}
					tefHour = tefHourFromMap.doubleValue();
					tefHourSum = tefHourSum + tefHour;
					tefAmount = tefAmount + tefHourRate * tefHour;
				}
				tefAmountSum = tefAmountSum + tefAmount;
				projSumCount += tefAmountSum;
				Row row = sheet.createRow(rowNum);
				row.createCell(0).setCellValue((String) tefList.get(0).get("projectInfoId"));
				row.createCell(2).setCellValue((String) tefList.get(0).get("projectInfoDescription"));
				row.createCell(4).setCellValue((String) tefList.get(0).get("hnte"));
				row.createCell(6).setCellValue((String) tefList.get(0).get("ph1"));
				row.createCell(7).setCellValue((String) tefList.get(0).get("ph2"));
				row.createCell(13).setCellValue(tefCostCenterName);
				row.createCell(15).setCellValue(tefHourSum);
				row.createCell(16).setCellValue(tefAmountSum);
				if (0 == tefHourSum) {
					row.createCell(14).setCellValue(0);
				} else {
					row.createCell(14);
					// 定位计算
					CellReference cr1 = new CellReference(row.getRowNum(), row.getCell(16).getColumnIndex());
					CellReference cr2 = new CellReference(row.getRowNum(), row.getCell(15).getColumnIndex());
					row.createCell(14).setCellFormula(cr1.formatAsString() + "/" + cr2.formatAsString());
				}
				rowNum++;
			}
		}
		tempMap.put("rowNum", rowNum);
		tempMap.put("projSumCount", projSumCount);
		return tempMap;
	}
	
	/**
	 * SAP部分
	 * 
	 * @param sapData
	 * @param projectMap
	 * @param sheet
	 * @param rowNum
	 * @param key
	 * @return
	 */
	private Map<String, Object> setSapToExcel(List<Map<String, Object>> sapData, Map<String, Object> projectMap, Sheet sheet, int rowNum, String key, double projSumCount) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		if (StringUtil.isNotNull4List(sapData)) {
			for (Map<String, Object> map : sapData) {
				if (!StringUtil.isNotNull((String) map.get(key))) {
					continue;
				}
				Row row = sheet.createRow(rowNum);
				this.setProjInfoToExcel(row, projectMap);
				projSumCount += ((BigDecimal) map.get("sapCOCost")).doubleValue();
				row.createCell(23).setCellValue((String) map.get(key));
				row.createCell(24).setCellValue((String) map.get("sapCostElement"));
				row.createCell(25).setCellValue((String) map.get("sapTransactionType"));
				row.createCell(26).setCellValue(((BigDecimal) map.get("sapTransactionCost")).doubleValue());
				BigDecimal sapCOCostFromMap = (BigDecimal) map.get("sapCOCost");
				if (null == sapCOCostFromMap) {
					sapCOCostFromMap = new BigDecimal(0.00);
				}
				row.createCell(27).setCellValue(sapCOCostFromMap.doubleValue());
				rowNum++;
			}
		}
		tempMap.put("rowNum", rowNum);
		tempMap.put("projSumCount", projSumCount);
		return tempMap;
	}
	
	/**
	 * 设备部分
	 * 
	 * @param equipmentData
	 * @param projectMap
	 * @param sheet
	 * @param rowNum
	 * @return
	 */
	private Map<String, Object> setEquipmentToExcel(List<Map<String, Object>> equipmentData, Map<String, Object> projectMap, Sheet sheet, int rowNum, double projSumCount) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		if (StringUtil.isNotNull4List(equipmentData)) {
			Map<String, List<Map<String, Object>>> equipGroupMap = this.rebuilderList(equipmentData, "equipmentGroup");
			Set<String> keySet = equipGroupMap.keySet();
			//同一设备组
			for (String equipmentGroup : keySet) {
				if (!StringUtil.isNotNull(equipmentGroup)) {
					continue;
				}
				List<Map<String, Object>> equipGroupTimesheetList = equipGroupMap.get(equipmentGroup);
				Map<String, List<Map<String, Object>>> equipDeptMap = this.rebuilderList(equipGroupTimesheetList, "equipmentDeptName");
				Set<String> deptKeySet = equipDeptMap.keySet();
				//同一部门
				for (String equipmentDept : deptKeySet) {
					if (!StringUtil.isNotNull(equipmentDept)) {
						continue;
					}
					List<Map<String, Object>> equipDeptTimesheetList = equipDeptMap.get(equipmentDept);
					Map<String, List<Map<String, Object>>> equipNameMap = this.rebuilderList(equipDeptTimesheetList, "equipmentName");
					Set<String> nameKeySet = equipNameMap.keySet();
					//同一设备名称(因为部分设备名称为空,但是又需要出现报工数据,所以此处不判空)
					for (String equipmentName : nameKeySet) {
						List<Map<String, Object>> equipNameTimesheetList = equipNameMap.get(equipmentName);
						Double equipmentHourRate = 0.00;
						Double equipmentHour = 0.00;
						Double equipmentAmount = 0.00;
						Double equipmentHourSum = 0.00;
						for (Map<String, Object> map : equipNameTimesheetList) {
							BigDecimal equipmentHourRateFromMap = (BigDecimal) map.get("equipmentHourRate");
							if (null == equipmentHourRateFromMap) {
								equipmentHourRateFromMap = new BigDecimal(0.00);
							}
							equipmentHourRate = equipmentHourRateFromMap.doubleValue();
							BigDecimal equipmentHourFromMap = (BigDecimal) map.get("equipmentHour");
							if (null == equipmentHourFromMap) {
								equipmentHourFromMap = new BigDecimal(0.00);
							}
							equipmentHour = equipmentHourFromMap.doubleValue();
							equipmentHourSum += equipmentHour;
						}
						equipmentAmount = equipmentHourRate * equipmentHourSum;
						
						projSumCount += equipmentAmount;
						Row row = sheet.createRow(rowNum);
						this.setProjInfoToExcel(row, projectMap);
						row.createCell(17).setCellValue(equipmentDept);
						row.createCell(18).setCellValue(equipmentGroup);
						row.createCell(19).setCellValue(equipmentName);
						row.createCell(20).setCellValue(equipmentHourRate);
						row.createCell(21).setCellValue(equipmentHourSum);
						// 定位计算
						CellReference cr1 = new CellReference(row.getRowNum(), row.getCell(20).getColumnIndex());
						CellReference cr2 = new CellReference(row.getRowNum(), row.getCell(21).getColumnIndex());
						row.createCell(22).setCellFormula(cr1.formatAsString() + "*" + cr2.formatAsString());
						rowNum++;
					}
				}
			}
		}
		tempMap.put("rowNum", rowNum);
		tempMap.put("projSumCount", projSumCount);
		return tempMap;
	}
	
	/**
	 * 项目信息写入EXCEL定位
	 * 
	 * @param row
	 * @param projectMap
	 */
	private void setProjInfoToExcel(Row row, Map<String, Object> projectMap) {
		row.createCell(0).setCellValue((String) projectMap.get("projectInfoId"));
		row.createCell(1).setCellValue((String) projectMap.get("internalOrder"));
//		if (StringUtil.isNotNull((String) projectMap.get("projectInfoCategory"))) {
//			if ("Productive support work".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
//				row.createCell(2).setCellValue("Productive support work");
//			} else if ("Non productive support work".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
//				row.createCell(2).setCellValue("Non productive support work");
//			} else {
//				row.createCell(2).setCellValue((String) projectMap.get("projectInfoDescription"));
//			}
//		} else {
			row.createCell(2).setCellValue((String) projectMap.get("projectInfoDescription"));
//		}
		if (StringUtil.isNotNull((String) projectMap.get("projectInfoCategory"))) {
			if ("Bosch Project".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
				row.createCell(3).setCellValue("customer project--BOSCH project number");
			} else if ("custSupport.project".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
				row.createCell(3).setCellValue("customer project--Global support project for customer");
			} else if ("inner1".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
				row.createCell(3).setCellValue("EN internal project--Platform project");
			} else if ("inner2001".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
				row.createCell(3).setCellValue("EN internal project--Process improvement, sustain and support project");
			} else if ("inner4001".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
				row.createCell(3).setCellValue("EN internal project--Development for BOSCH/GS project");
			} else if ("inner6001".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
				row.createCell(3).setCellValue("EN internal project--Product improvement project includes ECR and localization etc.");
			} else if ("inner7001".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
				row.createCell(3).setCellValue("EN internal project--Maintenance & service");
			} else if ("inner8001".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
				row.createCell(3).setCellValue("EN internal project--Technical competency building");
			} else if ("inner8501".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
				row.createCell(3).setCellValue("EN internal project--Human resource competency buliding");
			} else if ("inner9001".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
				row.createCell(3).setCellValue("EN internal project--General administration");
			} else if ("inner9501".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
				row.createCell(3).setCellValue("EN internal project--Non production work includes illness, vacation etc.");
			} else if ("SubPlatform".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
				row.createCell(3).setCellValue("EN internal project--Platform Project--Sub Platform Project");
			} else if ("Productive support work".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
				row.createCell(3).setCellValue("Productive支持工作");
			} else if ("Non productive support work".equals(((String) projectMap.get("projectInfoCategory")).trim())) {
				row.createCell(3).setCellValue("Non-Productive支持工作");
			} else {
				row.createCell(3).setCellValue((String) projectMap.get("projectInfoCategory"));
			}
		} else {
			row.createCell(3).setCellValue((String) projectMap.get("projectInfoCategory"));
		}
		row.createCell(4).setCellValue((String) projectMap.get("hnte"));
		row.createCell(5).setCellValue((String) projectMap.get("technologyCategory"));
		row.createCell(6).setCellValue((String) projectMap.get("ph1"));
		row.createCell(7).setCellValue((String) projectMap.get("ph2"));
		row.createCell(8).setCellValue((String) projectMap.get("ph1Code"));
		row.createCell(9).setCellValue((String) projectMap.get("ph2Code"));
		row.createCell(10).setCellValue((String) projectMap.get("customerGroupCode"));
		row.createCell(11).setCellValue((String) projectMap.get("customerSapCode"));
		row.createCell(12).setCellValue((String) projectMap.get("customerSapName"));
	}
	
	/**
	 * 重置人员信息
	 * 
	 * @param personList
	 * @param Key
	 * @return
	 */
	private Map<String, List<Map<String, Object>>> rebuilderList(List<Map<String, Object>> personList, String Key) {
		Map<String, List<Map<String, Object>>> dataMap = new HashMap<String, List<Map<String, Object>>>();
		for (Map<String, Object> map : personList) {
			String personelKey = StringUtil.toString4Object(map.get(Key));
			List<Map<String, Object>> list = dataMap.get(personelKey);
			if (!StringUtil.isNotNull4List(list)) {
				list = new ArrayList<Map<String, Object>>();
			}
			list.add(map);
			dataMap.put(personelKey, list);
		}
		return dataMap;
	}
	
	/**
	 * 重新构建初始数据(设备,人员,SAP)
	 * 
	 * @param equipList
	 * @param sapList
	 * @param personelList
	 * @param nonProjectList
	 * @param tefList
	 * @return
	 */
	private Map<String, Map<String, List<Map<String, Object>>>> rebuilderData(List<Map<String, Object>> equipList, List<Map<String, Object>> sapList, List<Map<String, Object>> personelList) {
		Map<String, Map<String, List<Map<String, Object>>>> newData = new HashMap<String, Map<String, List<Map<String, Object>>>>();
		if (StringUtil.isNotNull4List(personelList)) {
			newData.putAll(this.putDataToMap(personelList, newData, ExportExcel.personKey));
		}
		if (StringUtil.isNotNull4List(sapList)) {
			newData.putAll(this.putDataToMap(sapList, newData, ExportExcel.sapKey));
		}
		if (StringUtil.isNotNull4List(equipList)) {
			newData.putAll(this.putDataToMap(equipList, newData, ExportExcel.equipmentKey));
		}
		return newData;
	}
	
	/**
	 * 重新构建初始数据：非项目、Tef
	 * 
	 * @param nonProjectList
	 * @return
	 */
	private Map<String, Map<String, List<Map<String, Object>>>> rebuilderData(List<Map<String, Object>> nonProjectList, List<Map<String, Object>> sapList) {
		Map<String, Map<String, List<Map<String, Object>>>> newNPData = new HashMap<String, Map<String, List<Map<String, Object>>>>();
		if (StringUtil.isNotNull4List(nonProjectList)) {
			newNPData.putAll(this.putNonProjectDataToMap(nonProjectList, newNPData, ExportExcel.nonProjectKey));
		}
		if (StringUtil.isNotNull4List(sapList)) {
			newNPData.putAll(this.putNonProjectDataToMap(sapList, newNPData, ExportExcel.sapKey));
		}
		return newNPData;
	}
	
	/**
	 * 重新构建初始数据：Tef
	 * 
	 * @param nonProjectList
	 * @return
	 */
	private Map<String, List<Map<String, Object>>> rebuilderData(List<Map<String, Object>> tefList) {
		Map<String, List<Map<String, Object>>> newNPData = new HashMap<String, List<Map<String, Object>>>();
		if (StringUtil.isNotNull4List(tefList)) {
			for (Map<String, Object> map : tefList) {
				String projNo = StringUtil.toString4Object(map.get("projectInfoId"));
				if (!StringUtil.isNotNull(projNo)) {
					continue;
				}
				List<Map<String, Object>> data = newNPData.get(projNo);
				if (!StringUtil.isNotNull4List(data)) {
					data = new ArrayList<Map<String, Object>>();
				}
				data.add(map);
				newNPData.put(projNo, data);
			}
		}
		return newNPData;
	}
	
	/**
	 * 将数据放入到Map中
	 * 
	 * @param list
	 * @param newData
	 * @param key
	 */
	private Map<String, Map<String, List<Map<String, Object>>>> putDataToMap(List<Map<String, Object>> list, Map<String, Map<String, List<Map<String, Object>>>> newData, String key) {
		for (Map<String, Object> map : list) {
			String projNo = StringUtil.toString4Object(map.get("projectInfoId"));
			if (!StringUtil.isNotNull(projNo)) {
				continue;
			}
			Map<String, List<Map<String, Object>>> mapData = newData.get(projNo);
			if (null == mapData) {
				mapData = new HashMap<String, List<Map<String, Object>>>(3);
			}
			List<Map<String, Object>> data = mapData.get(key);
			if (!StringUtil.isNotNull4List(data)) {
				data = new ArrayList<Map<String, Object>>();
			}
			data.add(map);
			mapData.put(key, data);
			newData.put(projNo, mapData);
		}
		return newData;
	}
	
	private Map<String, Map<String, List<Map<String, Object>>>> putNonProjectDataToMap(List<Map<String, Object>> list, Map<String, Map<String, List<Map<String, Object>>>> newData, String key) {
		for (Map<String, Object> map : list) {
			String projNo = StringUtil.toString4Object(map.get("projectInfoId"));
			if (!StringUtil.isNotNull(projNo)) {
				continue;
			}
			Map<String, List<Map<String, Object>>> mapData = newData.get(projNo);
			if (null == mapData) {
				mapData = new HashMap<String, List<Map<String, Object>>>(2);
			}
			List<Map<String, Object>> data = mapData.get(key);
			if (!StringUtil.isNotNull4List(data)) {
				data = new ArrayList<Map<String, Object>>();
			}
			data.add(map);
			mapData.put(key, data);
			newData.put(projNo, mapData);
		}
		return newData;
	}
	
	/**
	 * 往Excel中写入开始-结束日期
	 * 
	 * @param sheet
	 * @param paramStartDate
	 * @param paramEndDate
	 */
	private static void exportDateData(Workbook wb, Sheet sheet, String paramStartDate, String paramEndDate) {
		
		Row dateRow = sheet.getRow(0);
		Cell dateCell = dateRow.createCell(13);
		CellStyle styleCenter = wb.createCellStyle();
		styleCenter.setAlignment(CellStyle.ALIGN_CENTER);
		dateCell.setCellValue(paramStartDate + " - " + paramEndDate);
		dateCell.setCellStyle(styleCenter);
	}
	
	/**
	 * 判断传递的项目编号是已经存在于缓存中
	 * 
	 * @param projectInfoId
	 * @param cacheMap
	 * @return
	 */
	public boolean isExistCacheProjMap(String projectInfoId, Map<String, Map<String, Object>> cacheProjMap) {
		boolean flag = false;
		Set<Entry<String, Map<String, Object>>> set = cacheProjMap.entrySet();
		Iterator<Entry<String, Map<String, Object>>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Entry<String, Map<String, Object>> entry = iterator.next();
			if (projectInfoId.equals(entry.getKey())) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 为部门提供日期参数(日期字段为2005年到2015年)
	 * 
	 * @param requestStartDate
	 * @param requestEndDate
	 * @return
	 * @throws ServiceException
	 */
	private Map<String, Object> matchDateForEquipment(String requestStartDate, String requestEndDate) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		Date sd = DateHelper.toDate(requestStartDate);
		Date ed = DateHelper.toDate(requestEndDate);
		Date rsd = DateHelper.toDate("2005-1-1");
		Date red = DateHelper.toDate("2015-12-31");
		if (sd.compareTo(rsd) < 0) {
			map.put("startDate", DateHelper.formatDate(rsd));
		} else if (sd.compareTo(red) > 0) {
			map.put("startDate", DateHelper.formatDate(red));
		} else {
			map.put("startDate", requestStartDate);
		}
		if (ed.compareTo(rsd) < 0) {
			map.put("endDate", DateHelper.formatDate(rsd));
		} else if (ed.compareTo(red) > 0) {
			map.put("endDate", DateHelper.formatDate(red));
		} else {
			map.put("endDate", requestEndDate);
		}
		return map;
	}
	
	public IProjectService getProjectService() {
		return projectService;
	}
	
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
	
	public IEquipmentService getEquipmentService() {
		return equipmentService;
	}
	
	public void setEquipmentService(IEquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}
	
	public IThreeFeeService getThreeFeeService() {
		return threeFeeService;
	}
	
	public void setThreeFeeService(IThreeFeeService threeFeeService) {
		this.threeFeeService = threeFeeService;
	}
	
	public ITefService getTefService() {
		return tefService;
	}
	
	public void setTefService(ITefService tefService) {
		this.tefService = tefService;
	}
	
	public IPersonService getPersonService() {
		return personService;
	}
	
	public void setPersonService(IPersonService personService) {
		this.personService = personService;
	}
	
	public INonProjectService getNonProjectService() {
		return nonProjectService;
	}
	
	public void setNonProjectService(INonProjectService nonProjectService) {
		this.nonProjectService = nonProjectService;
	}
	
}
