/** 
 * Project Name:ebon-extend 
 * File Name:BudgetCPCController.java 
 * Package Name:com.ebon.v2.eai.budgetcpc.controller 
 * Date:2015年11月2日上午10:07:49 
 * Copyright (c) 2015, quanxinsky@163.com All Rights Reserved. 
 * qx
 */  
package com.ebon.v2.eai.budgetcpc.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebon.v2.eai.base.controller.BaseEaiController;
import com.ebon.v2.eai.budgetcpc.model.BudgetCPC;
import com.ebon.v2.eai.budgetcpc.service.BudgetCPCService;

/** 
 * ClassName: BudgetCPCController
 * Function: TODO ADD FUNCTION
 * date: 2015年11月2日 上午10:07:49
 * @author qx 
 * @version  
 * @since JDK 1.6 
 */
@Controller
@Scope()
@RequestMapping("/budgetCPC")
public class BudgetCPCController extends BaseEaiController {
	@Autowired
	BudgetCPCService budgetCPCService;
	@RequestMapping("/getBudgetCPC")
	public
	@ResponseBody
	String getBudgetCPC(String proj_id, String startMonth, String endMonth) throws Exception{
		List<BudgetCPC> list = budgetCPCService.getListByProjId(proj_id,startMonth,endMonth);
		//构建适合前端的模型
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"data\" : [");
		for (int i = 0; i < list.size(); i++) {
			sb.append("{ \"month\" : '" + list.get(i).getMonth() + "',");
			sb.append("\"budget\" : '" + list.get(i).getBudget() + "',");
			sb.append("\"monthCost\" : '" + list.get(i).getMonthCost() + "',");
			sb.append("\"actualTotal\" : '"+ list.get(i).getActualTotal() + "'");
			
			if(i==(list.size()-1)){
				sb.append("}");
			}else{
				sb.append("},");
			}
		}
		sb.append("]}");
		
		return sb.toString();
	}
	
	/**
	 * 
	 * getBudgetCPCPage:(跳转到开发费用页面，并且为起始结束日期默认初始值)
	 * @author qq 
	 * 2015年12月14日
	 * @since JDK 1.6
	 */
	@RequestMapping("/getBudgetCPCPage")
	public String getBudgetCPCPage(Model model, String proj_id) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");
		Calendar currentMonth = Calendar.getInstance();
		Calendar defalutStartMonth = Calendar.getInstance();
		currentMonth.add(Calendar.MONTH, 0);
		defalutStartMonth.add(Calendar.MONTH, -5);
		model.addAttribute("currentMonth",format.format(currentMonth.getTime()));
		model.addAttribute("defalutStartMonth",format.format(defalutStartMonth.getTime()));
		model.addAttribute("proj_id", proj_id);
		return "v2/bi/trmb";
	}
	
}
