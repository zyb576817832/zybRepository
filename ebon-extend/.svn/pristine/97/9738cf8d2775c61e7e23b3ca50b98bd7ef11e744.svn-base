/** 
 * Project Name:ebon-extend 
 * File Name:BudgetCPC.java 
 * Package Name:com.ebon.v2.eai.budgetcpc.service 
 * Date:2015年11月2日上午10:05:07 
 * Copyright (c) 2015, quanxinsky@163.com All Rights Reserved. 
 * qx
 */  
package com.ebon.v2.eai.budgetcpc.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.platform.dao.DaoException;
import com.ebon.platform.service.BaseService;
import com.ebon.v2.eai.budget.model.Budget;
import com.ebon.v2.eai.budget.service.BudgetService;
import com.ebon.v2.eai.budgetcpc.model.BudgetCPC;

/** 
 * ClassName: BudgetCPC
 * Function: TODO ADD FUNCTION
 * date: 2015年11月2日 上午10:05:07
 * @author qx 
 * @version  
 * @since JDK 1.6 
 */
public class BudgetCPCService extends BaseService {
	
	@Autowired
	BudgetService budgetService;
	/**
	 * 实例化BudgetCPC集合
	 * insertBudgetCPC:(同步项目预算的数据，<!-- 每月1号凌晨1点执行一次 -->)
	 * @author qx 
	 * 2015年11月2日
	 * @throws DaoException 
	 * @since JDK 1.6
	 */
	public void insertBudgetCPC(){
		List<BudgetCPC> budgetCPCs = getDataforBudgetCPC();
		for (BudgetCPC budgetCPC : budgetCPCs) {
			try {
				myBatisDao.save("V2Mapper.putBudgetCPC", budgetCPC);
			} catch (DaoException e) {
				e.printStackTrace();
				log.error("保存项目预算的cpc失败>>>项目Id是" + budgetCPC.getProjId());
				continue;
			}
		}
		
	}
	
	/**
	 * 
	 * getDataforBudgetCPC:(准备构建项目预算的BudgetCPC对象集合)
	 * @author qx 
	 * 2015年11月11日
	 * @since JDK 1.6
	 */
	private List<BudgetCPC> getDataforBudgetCPC(){
		List<Budget> budgets = new ArrayList<Budget>();
		try {
			budgets = budgetService.getAllBudget();
		} catch (DaoException e) {
			e.printStackTrace();
			log.error("获取budgets出错"+e);
		}
		List<BudgetCPC> budgetCPCs = new ArrayList<BudgetCPC>();
		
		
		//将Budget对象的集合整合为budgetCPC对象的集合
		for (Budget budget : budgets) {
			BudgetCPC budgetCPC = new BudgetCPC();
			String proj_code = budget.getProj_code();
			
			budgetCPC.setProjId(proj_code);
			//获取当前月项目预算三部分费用之和
			double d = Double.parseDouble(budget.getPEO())+Double.parseDouble(budget.getMAD())+Double.parseDouble(budget.getSER());
			budgetCPC.setBudget(d+"");
			
			//本月同步上月数据，定义上月的日期格式
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM"); 
			Calendar lastMonth = Calendar.getInstance();
			lastMonth.add(Calendar.MONTH, -1);
			budgetCPC.setMonth(format.format(lastMonth.getTime()));
			
			//同步当前项目上月实际成本
			if(getLastMonthActualCost(proj_code)!=null){
				budgetCPC.setMonthCost(getLastMonthActualCost(proj_code));
			}
			
			//获取截至目前项目总费用
			budgetCPC.setActualTotal(budget.getActualTotal());
			budgetCPCs.add(budgetCPC);
		}

		return budgetCPCs;
	}
	
	/**
	 * 
	 * getLastMonthActualCost:(得到上个月项目费用)
	 * 包括三部分费用
	 * @author qx 
	 * 2015年11月4日
	 * @since JDK 1.6
	 */
	private String getLastMonthActualCost(String proj_code){
		//获取上月起始日期和结束日期，用于持久化每月项目实际花费
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd"); 
		Calendar lastMonthFirstDay = Calendar.getInstance();
		lastMonthFirstDay.add(Calendar.MONTH, -1);
		lastMonthFirstDay.set(Calendar.DAY_OF_MONTH,1);
		
		
		Calendar lastMonthLastDay = Calendar.getInstance();   
		lastMonthLastDay.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
	     
	    String requestStartDate = format.format(lastMonthFirstDay.getTime());;
	    String requestEndDate = format.format(lastMonthLastDay.getTime());;
	    HashMap<String, String> map = new HashMap<String, String>();
		map.put("proj_code", proj_code);
		map.put("requestStartDate", requestStartDate);
		map.put("requestEndDate", requestEndDate);
		
		String MAD = "";
		String employee = ""; 
		String SER = "";
		try {
			 MAD = myBatisDao.getOne("V2Mapper.selectMAD", map);
			 employee = myBatisDao.getOne("V2Mapper.selectEmployee", map);
			 SER = myBatisDao.getOne("V2Mapper.selectSER", map);
		} catch (DaoException e) {
			e.printStackTrace();
			log.info("获取项目编号为"+proj_code+"的上月成本失败"+e.getMessage());
		}
		
		/**
		 * 处理为空的情况，统一设置为0000.00
		 */
		if(SER==null||"".equals(SER)){
			SER="0000.00";
		}
		
		if(employee==null||"".equals(employee)){
			employee="0000.00";
		}
		
		if(MAD==null||"".equals(MAD)){
			MAD="0000.00";
		}
		
		//返回值为当前项目三部分费用之和
		return (Double.parseDouble(MAD)+Double.parseDouble(employee)+Double.parseDouble(SER))+"";
		
	}

	/**
	 * getListByProjId:(根据项目编号查询V2_Budget_cpc中的数据
	 * 如果是系统项目则汇总，所以加上%模糊查询
	 * @author qx
	 * 2015年11月3日
	 * @throws DaoException 
	 * @since JDK 1.6 
	 */
	public List<BudgetCPC> getListByProjId(
			String proj_id,String startMonth, String endMonth) throws DaoException { 
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("proj_id", proj_id+"%");
		map.put("startMonth", startMonth);
		map.put("endMonth", endMonth);
		List<BudgetCPC> list = myBatisDao.getList("V2Mapper.getBudgetCPC", map);
		return list;
	}
	
	
	
}
