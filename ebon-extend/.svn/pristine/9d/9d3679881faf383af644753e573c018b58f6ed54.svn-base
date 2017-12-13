package com.ebon.app.service.cost.export;

import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ebon.platform.action.BaseAction;
import com.ebon.platform.util.DateHelper;

@Controller
@Scope()
@RequestMapping("/export")
public class ExportController extends BaseAction {
	
	@Autowired
	private IExportExcel exportExcel;
	
	@RequestMapping("/cost")
	public ModelAndView exportCost(String costStartDate, String costEndDate, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String ctxPath = request.getSession().getServletContext().getRealPath("/");
		ctxPath = ctxPath + "/template/CostAllocationBaseOnCost.xls";
		response.setContentType("aapplication/vnd.ms-excel; charset=UTF-8");
		OutputStream output = response.getOutputStream();
		String fileName = null;
		try {
			fileName = URLEncoder.encode("成本归集-cost.xls", "UTF-8");
		} catch (Exception e2) {
			log.error(e2);
			throw new Exception(e2);
		}
		try {
			String startDate = DateHelper.toString(DateHelper.getFirstDateOfMonth(costStartDate + "-02"), "yyyy-MM-dd");
			String endDate = DateHelper.toString(DateHelper.getLastDateOfMonth(costEndDate + "-02"), "yyyy-MM-dd");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			Workbook book = exportExcel.exportCostExcel(startDate, endDate, ctxPath);
			book.write(output);
			output.flush();
		} catch (Exception e) {
			log.error(e);
			throw new Exception(e);
		} finally {
			output.close();
		}
		return null;
	}
	
	@RequestMapping("/dept")
	public ModelAndView exportDept(String deptStartDate, String deptEndDate, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String ctxPath = request.getSession().getServletContext().getRealPath("/");
		ctxPath = ctxPath + "/template/CostAllocationBaseOnDept.xls";
		response.setContentType("aapplication/vnd.ms-excel; charset=UTF-8");
		OutputStream output = response.getOutputStream();
		String fileName = null;
		try {
			fileName = URLEncoder.encode("成本归集-dept.xls", "UTF-8");
		} catch (Exception e2) {
			log.error(e2);
			throw new Exception(e2);
		}
		try {
			String startDate = DateHelper.toString(DateHelper.getFirstDateOfMonth(deptStartDate + "-02"), "yyyy-MM-dd");
			String endDate = DateHelper.toString(DateHelper.getLastDateOfMonth(deptEndDate + "-02"), "yyyy-MM-dd");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			Workbook book = exportExcel.exportDeptExcel(startDate, endDate, ctxPath);
			book.write(output);
			output.flush();
		} catch (Exception e) {
			log.error(e);
			throw new Exception(e);
		} finally {
			output.close();
		}
		return null;
	}
	
	public IExportExcel getExportExcel() {
		return exportExcel;
	}
	
	public void setExportExcel(IExportExcel exportExcel) {
		this.exportExcel = exportExcel;
	}
	
}
