package com.ebon.v3.action;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebon.framework.util.StringUtil;
import com.ebon.platform.action.BaseAction;
import com.ebon.platform.dao.pager.Page;
import com.ebon.platform.service.ServiceException;
import com.ebon.platform.vo.CusGridData;
import com.ebon.v3.dto.VAppUserHours;
import com.ebon.v3.service.IAppUserHoursService;
import com.ebon.v3.service.IAppUserTaskPlatfromService;
import com.ebon.v3.vo.AppUserHours;
import com.ebon.v3.vo.AppUserTaskPlatfrom;

@Controller
@RequestMapping("/app/userHours")
public class AppUserHoursController extends BaseAction {
	
	@Autowired
	private IAppUserHoursService appUserHoursService;
	
	@Autowired
	IAppUserTaskPlatfromService appUserTaskPlatfromService;
	
	@RequestMapping("/getHoursByMonth")
	@ResponseBody
	public Map<String, String> getHoursByMonth(VAppUserHours query) throws ServiceException{
		Map<String, String> map = new HashMap<String, String>();
		List<AppUserHours> appUserhours = appUserHoursService.getList(query);
		for (AppUserHours one : appUserhours) {
			//同一天在不同任务下都会有工时,因此相加
			if(StringUtil.isNotNull(map.get(one.getRegistDate()))){
				String s1 = map.get(one.getRegistDate());
				String s2 = one.getRegistHours();
				BigDecimal bs1 = new BigDecimal(s1);  
		        BigDecimal bs2 = new BigDecimal(s2);  
				map.put(one.getRegistDate(), bs1.add(bs2).setScale(0, BigDecimal.ROUND_HALF_UP)+"");//四舍五入取整
			}else{
				map.put(one.getRegistDate(), new BigDecimal(one.getRegistHours()).setScale(0, BigDecimal.ROUND_HALF_UP)+"");//四舍五入取整
			}
		}
		return map;
	}
	
	@RequestMapping("/getVList")
	@ResponseBody
	public CusGridData<VAppUserHours> getVList(VAppUserHours query, Page page) throws ServiceException{
		List<VAppUserHours> list = appUserHoursService.getVList(query, page);
		CusGridData<VAppUserHours> gridData = new CusGridData<VAppUserHours>(page, list);
		return gridData;
	}
	
	@RequestMapping("/getListOrAddPage")
	public String getListOrAddPage(String registDate, String userId, String sumTaskHours, Model model) throws ServiceException{
		model.addAttribute("randomNum", getTimes());//用于解决IE缓存问题
		VAppUserHours query = new VAppUserHours();
		query.setRegistDate(registDate);
		query.setUserId(userId);
		List<AppUserHours> appUserhours = appUserHoursService.getList(query);//判断该用户这天是否有报工
		model.addAttribute("registDate", registDate);
		model.addAttribute("userId", userId);
		model.addAttribute("sumTaskHours", sumTaskHours);
		
		if(appUserhours.size()>0){
			if(!StringUtil.isNotNull(appUserhours.get(0).getTaskId())){//如果没有作业,放入这条报工
				model.addAttribute("noTaskUserhour", appUserhours.get(0));
			}else{
				StringBuilder sb1 = new StringBuilder();
				for (AppUserHours userHours : appUserhours) {
					sb1.append(userHours.getTaskId()).append(",");
				}
				model.addAttribute("registTaskIds", sb1.toString());
			}
			return "v3/tab6/list";
		}else{
			
			//获取该用户正在进行中的任务IDS
			AppUserTaskPlatfrom queryTask = new AppUserTaskPlatfrom();
			queryTask.setRespUser(userId);
			queryTask.setStatus("2");
			List<AppUserTaskPlatfrom> tasks = appUserTaskPlatfromService.getList(queryTask);
			StringBuilder sb = new StringBuilder();
			for (AppUserTaskPlatfrom one : tasks) {
				sb.append(one.getId()).append(",");
			}
			model.addAttribute("taskIds", sb.toString());
			return "v3/tab6/add";
		}
	}
	
	@RequestMapping("/getUpdateAgainPage")
	public String getUpdateAgainPage(String registDate, String userId, Model model) throws ServiceException{
		model.addAttribute("registDate", registDate);
		model.addAttribute("userId", userId);
		return "v3/tab6_update";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(String registDate, String registHours, String userId, String taskIds) throws ServiceException{
		
		String code = "0";
		if(StringUtil.isNotNull(taskIds)){//如果没有作业也允许分配工时
			String[] taskIdsArr = taskIds.split(",");
			
			int taskCount = taskIdsArr.length;
			DecimalFormat df = new DecimalFormat("0.00");
			String taskHours = df.format((float)Integer.parseInt(registHours)/taskCount);//获取平均工时
			
			
			for(String taskId : taskIdsArr){
				AppUserHours appUserHours = new AppUserHours();
				appUserHours.setRegistDate(registDate);
				appUserHours.setRegistHours(taskHours);
				appUserHours.setUserId(userId);
				appUserHours.setTaskId(taskId);
				code =  appUserHoursService.add(appUserHours);
				if("0".equals(code)) break;
			}
		}else{
			AppUserHours appUserHours = new AppUserHours();
			appUserHours.setRegistDate(registDate);
			appUserHours.setRegistHours(registHours);
			appUserHours.setUserId(userId);
			code =  appUserHoursService.add(appUserHours);
		}
		
		return code;
	}
	
	@RequestMapping("/updateAgain")
	@ResponseBody
	public String updateAgain(String noTaskUserhourId, String registDate, String userId, String registHours, String registTaskIds) throws ServiceException{
		String code = "0";
		if(StringUtil.isNotNull(noTaskUserhourId)){//不存在任务时修改工时数
			AppUserHours update = new AppUserHours();
			update.setId(noTaskUserhourId);
			update.setRegistHours(registHours);
			code = appUserHoursService.update(update);
		}else{
			code = appUserHoursService.deleteByDate(userId, registDate);
			if("0".equals(code)) return code;
			
			int taskCount = registTaskIds.split(",").length;
			DecimalFormat df = new DecimalFormat("0.00");
			String averageTaskHours = df.format((float)Integer.parseInt(registHours)/taskCount);//获取平均工时
			for(String taskId: registTaskIds.split(",")){
				AppUserHours appUserHours = new AppUserHours();
				appUserHours.setRegistDate(registDate);
				appUserHours.setRegistHours(averageTaskHours);
				appUserHours.setUserId(userId);
				appUserHours.setTaskId(taskId);
				code =  appUserHoursService.add(appUserHours);
				if("0".equals(code)) break;
			}
		}
		return code;
	}
	
	
	@RequestMapping("/aotoRegist")
	@ResponseBody
	public String aotoRegist(String userId, String registDate, String taskIds) throws ServiceException{
		String code = "0";
		if(StringUtil.isNotNull(taskIds)){
			int taskCount = taskIds.split(",").length;
			DecimalFormat df = new DecimalFormat("0.00");
			String averageTaskHours = df.format((float)Integer.parseInt("8")/taskCount);//获取平均工时
			for(String taskId: taskIds.split(",")){
				AppUserHours appUserHours = new AppUserHours();
				appUserHours.setRegistDate(registDate);
				appUserHours.setRegistHours(averageTaskHours);
				appUserHours.setUserId(userId);
				appUserHours.setTaskId(taskId);
				code =  appUserHoursService.add(appUserHours);
				if("0".equals(code)) break;
			}
		}else{
			code = appUserHoursService.autoResigt(userId, registDate);
		}
		return code;
	}
	
	@RequestMapping("/updateByTaskIds")
	@ResponseBody
	public String updateByTaskIds(String userHoursDatas) throws ServiceException{
		String code = "0";
		for (String idAndHours : userHoursDatas.split(",")) {
			AppUserHours update = new AppUserHours();
			update.setId(idAndHours.split(":")[0]);
			update.setRegistHours(idAndHours.split(":")[1]);
			code = appUserHoursService.update(update);
			if("0".equals(code)) break;
		}
		return code;
	}
	
	private String getTimes(){
		return String.valueOf(new Date().getTime());
	}
	
	
	
	
	public static void main(String[] args) {
		String registHours = "17";
		int taskCount = 3;
		DecimalFormat df = new DecimalFormat("0.00");
		String taskHours = df.format((float)Integer.parseInt(registHours)/taskCount);
		System.out.println(taskHours);//5.67
		
		String s1 = "0.00001";  
        String s2 = "0.00002";  
        String s = s1+ s2;  
        Double db1 = Double.valueOf(s1);  
        Double db2 = Double.valueOf(s2);  
          
        BigDecimal bs1 = new BigDecimal(s1);  
        BigDecimal bs2 = new BigDecimal(s2);  
          
        BigDecimal bs11 = new BigDecimal(db1);  
        System.out.println("s:"+s);//s:0.000010.00002
        System.out.println("db1:"+bs1.add(bs2));//db1:0.00003
        System.out.println("bs11:"+bs11.add(new BigDecimal(db2)));//bs11:0.000030000000000000002454091617420939286375869414769113063812255859375 
        System.out.println("db1+db2:"+(db1+db2)); //db1+db2:3.0000000000000004E-5
        
        System.out.println("四舍五入取整:(2.01)=" + new BigDecimal("2.01").setScale(0, BigDecimal.ROUND_HALF_UP)); 
        
        String str = "1:1,2:2";
        System.out.println("1:1,2:2--数组分割长度是" + str.split(",").length);//2
        String str1 = "1:1,2:2,";
        System.out.println("1:1,2:2,--数组分割长度是" +str1.split(",").length);//2

	}

}
