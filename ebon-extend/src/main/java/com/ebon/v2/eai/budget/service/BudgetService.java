package com.ebon.v2.eai.budget.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ebon.platform.dao.DaoException;
import com.ebon.platform.service.BaseService;
import com.ebon.v2.eai.budget.model.Budget;

public class BudgetService extends BaseService {
	
	/**
	 * 得到项目的设备费用
	 * @param projName
	 * @return
	 * @throws DaoException
	 */
	public String getMAD(String projName) throws DaoException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("proj_code", projName);
		String MAD = myBatisDao.getOne("V2Mapper.selectMAD", map);
		if(MAD==null||"".equals(MAD)){
			MAD="0000.00";
		}
		return MAD;
	}
	/**
	 * 得到项目人员费用
	 * @param projName
	 * @return
	 * @throws DaoException
	 */
	public String getEmployee(String projName) throws DaoException {
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("proj_code", projName);
		String employee = myBatisDao.getOne("V2Mapper.selectEmployee", map);
		if(employee==null||"".equals(employee)){
			employee="0000.00";
		}
		return employee;
	}
	/**
	 * 得到项目三项费用
	 * @param projName
	 * @return
	 * @throws DaoException
	 */
	public String getSER(String projName) throws DaoException {
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("proj_code", projName);
		String SER = myBatisDao.getOne("V2Mapper.selectSER", map);
		if(SER==null||"".equals(SER)){
			SER="0000.00";
		}
		return SER;
	}

	/**
	 * 项目预算的保存
	 * @param budget
	 * @throws DaoException
	 */
	public void putBudget(Budget budget) throws DaoException {
		myBatisDao.save("V2Mapper.putBudget", budget);
	}
	
	/**
	 * 项目预算的回显
	 * @param projName
	 * @return
	 * @throws DaoException
	 */
	public Budget getPlanBudget(String projName) throws DaoException {
		Budget budget = myBatisDao.getOne("V2Mapper.getPlanBudget", projName);
		return budget;
	}

	/**
	 * 根据系统项目编号查询子项目编号 projName+"%"
	 * @param projName
	 * @return
	 * @throws DaoException
	 */
	public List<String> getSubproj(String projName) throws DaoException {
		List<String> list = myBatisDao.getList("V2Mapper.getSubproj", projName);
		return list;
	}

	public String getNameById(String projId) throws DaoException {
		String projName = myBatisDao.getOne("V2Mapper.getNameById", projId);
		return projName;
	}
	/**
	 * 
	 * getAllBudget:(得到所有的项目预算Budget对象，包括系统项目与子项目)
	 * @author qx 
	 * 2015年11月4日
	 * @since JDK 1.6
	 */
	public List<Budget> getAllBudget() throws DaoException{
		
		List<String> projIds = myBatisDao.getList("V2Mapper.getAllBudgetProjIds");
		
		List<Budget> budgets = new ArrayList<Budget>();
		
		for (String projId : projIds) {
			Budget budget = myBatisDao.getOne("V2Mapper.getPlanBudget", projId);
			
			//得到项目当前实际费用
			String MAD = getMAD(projId);
			String employee = getEmployee(projId);
			String SER = getSER(projId);
			String actual = (Double.parseDouble(MAD)+Double.parseDouble(employee)+Double.parseDouble(SER))+"";
			budget.setActualTotal(actual);
			budgets.add(budget);
		}
		return budgets;
	}

}
