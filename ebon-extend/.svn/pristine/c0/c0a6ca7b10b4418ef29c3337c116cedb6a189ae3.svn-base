package com.ebon.v2.eai.budget.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ebon.framework.util.StringUtil;
import com.ebon.platform.dao.DaoException;
import com.ebon.v2.eai.base.controller.BaseEaiController;
import com.ebon.v2.eai.budget.model.Budget;
import com.ebon.v2.eai.budget.service.BudgetService;

@Controller
@Scope()
@RequestMapping("/budgetController")
public class BudgetController extends BaseEaiController {
	@Autowired
	BudgetService budgetService;
	/**
	 * 项目预算的回显
	 * @param model
	 * @param projId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getEmployee")
	public String getEmployee(Model model,String projId) throws Exception {
		
		Budget budget = new Budget();
		Budget subBudget = new Budget();
		if(projId!=null&&projId!=""){
			//根据项目ID得到项目名称
			String projName = budgetService.getNameById(projId);
			//得到所有子项目信息，本次仅仅得到项目名称即可 加%是为了得到子项目时候模糊查询
			List<String> list= budgetService.getSubproj(projName+"%");
			
			//初始化一下三个值，用来填充前台三个项目预算的实际值
			Double employee = 0.0;
			Double MAD = 0.0;
			Double SER = 0.0;
			//根据size判断是否是子项目>1则是系统项目，需要根据子项目各部分预算值累加
			
			boolean isSYS;//定义变量是否为系统项目 是则为真 不是为假 判断前台是否设置保存按钮
			if(list.size()>1){
				//子项目所有的项目预算对象
				List<Budget> budgetList = new ArrayList<Budget>();

				//三个值用于对系统项目         实际值         进行累加计算 如果是则累加
				for (String subProjName : list) {
					employee=employee+get2Dou(budgetService.getEmployee(subProjName));
					MAD=MAD+get2Dou(budgetService.getMAD(subProjName));
					SER=SER+get2Dou(budgetService.getSER(subProjName));
					
					subBudget = budgetService.getPlanBudget(subProjName);
					//如果该子项目有预算值，则加入到List中
					if(subBudget!=null){
						budgetList.add(subBudget);
					}
				}
				
				//三个值用于对系统项目         计划值         进行累加计算 如果是则累加
				Double employee1 = 0.0;
				Double MAD1 = 0.0;
				Double SER1 = 0.0;
				
				for (Budget budget1 : budgetList) {
					employee1 = employee1 +get2Dou(budget1.getPEO());
					MAD1 = MAD1 + get2Dou(budget1.getMAD());
					SER1 = SER1 + get2Dou(budget1.getSER());
				}
				
				budget.setPEO(employee1.toString());
				budget.setMAD(MAD1.toString());
				budget.setSER(SER1.toString());
				
				isSYS = true;
			}else{
				//如果是子项目，直接得到项目实际花费和计划的budget对象
				//先转换为double为了显示的时候带小数点
				employee = Double.parseDouble((budgetService.getEmployee(projName)));
				MAD =Double.parseDouble((budgetService.getMAD(projName)));
				SER = Double.parseDouble((budgetService.getSER(projName)));
				budget = budgetService.getPlanBudget(projName);
				isSYS = false;
			}
			model.addAttribute("employee", get2Double(employee.toString()));
			model.addAttribute("mad", get2Double(MAD.toString()));
			model.addAttribute("ser", get2Double(SER.toString()));
			model.addAttribute("budget", budget);
			model.addAttribute("isSYS", isSYS);
			model.addAttribute("projName", projName);
		}else{
			model.addAttribute("isSYS", true);
		}
		
		return "v2/projbudget";
	}
	
	/**
	 * 项目预算的保存
	 * @param _employee
	 * @param _mad
	 * @param _ser
	 * @param projId
	 * @return
	 * @throws DaoException
	 */
	@RequestMapping("/putBudget")
	public String putBudget(String _employee,String _mad,String _ser,String projId) throws DaoException{
		//根据项目ID得到项目名称
		String projName = budgetService.getNameById(projId);
		Budget budget = new Budget();
		budget.setProj_code(projName);
		//确保数据库存储的无千分符
		if(StringUtil.isNotNull(_employee)){
			_employee = _employee.replace(",", "");
		}
		if(StringUtil.isNotNull(_mad)){
			_mad = _mad.replace(",", "");
		}
		if(StringUtil.isNotNull(_employee)){
			_ser = _ser.replace(",", "");
		}
		budget.setPEO(_employee);
		budget.setMAD(_mad);
		budget.setSER(_ser);
		
		budgetService.putBudget(budget);
		return "v2/projbudget";
	}
	
	//保留2位小数  
	private static String get2Double(String  str){ 
	    DecimalFormat df=new DecimalFormat("######0.00");  
	    return df.format(new BigDecimal(str));  
	} 
	
	//判断是否为空
	private static double get2Dou(String str){
		if(str==null||"".equals(str)){
			return 0.00;
		}
		return Double.parseDouble(str);
	}
}
