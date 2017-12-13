package com.ebon.v2.rpt.controller;

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
import com.ebon.v2.rpt.service.ExportExcelService;

@Controller
@Scope()
@RequestMapping("/xlsexport")
public class XlsExportController extends BaseAction {

	@Autowired
	private ExportExcelService exportExcelService;

	@RequestMapping("/cost")
	public ModelAndView exportCost(String costStartDate, String costEndDate,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String template = getTemplate(request,"/template/CostAllocationBaseOnCost.xls");
		String startDate = DateHelper.toString(	DateHelper.getFirstDateOfMonth(costStartDate + "-02"),"yyyy-MM-dd");
		String endDate = DateHelper.toString(DateHelper.getLastDateOfMonth(costEndDate + "-02"),"yyyy-MM-dd");
		Workbook book = exportExcelService.exportCostExcel(startDate, endDate,template);
		return this.export(request, response, template, book);
	}

	@RequestMapping("/dept")
	public ModelAndView exportDept(String deptStartDate, String deptEndDate,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String template = getTemplate(request,
				"/template/CostAllocationBaseOnDept.xls");
		String startDate = DateHelper.toString(
				DateHelper.getFirstDateOfMonth(deptStartDate + "-02"),
				"yyyy-MM-dd");
		String endDate = DateHelper.toString(
				DateHelper.getLastDateOfMonth(deptEndDate + "-02"),
				"yyyy-MM-dd");

		Workbook book = exportExcelService.exportDeptExcel(startDate, endDate,
				template);
		return this.export(request, response, template, book);
	}

	private String getTemplate(HttpServletRequest request, String template) {
		String ctxPath = request.getSession().getServletContext()
				.getRealPath("/");
		return ctxPath + template;
	}

	private ModelAndView export(HttpServletRequest request, HttpServletResponse response,
			String template, Workbook book) throws Exception {
		response.setContentType("text/html;charset=utf-8");

		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		OutputStream output = response.getOutputStream();
		String fileName = null;
		try {
			fileName = URLEncoder.encode("成本归集-cost.xls", "UTF-8");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
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

}
