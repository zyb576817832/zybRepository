package com.ebon.v3.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ebon.framework.util.StringUtil;
import com.ebon.platform.action.BaseAction;
import com.ebon.platform.service.ServiceException;
import com.ebon.v3.dto.VAppUserHours;
import com.ebon.v3.dto.VAppUserTaskPlatfrom;
import com.ebon.v3.service.IAppUserHoursService;
import com.ebon.v3.service.IAppUserTaskPlatfromService;
import com.ebon.v3.service.IMileStoneService;
import com.ebon.v3.vo.AppProjInfo;
import com.ebon.v3.vo.AppUserHours;
import com.ebon.v3.vo.AppUserTaskPlatfrom;

@Controller
@RequestMapping("/v3/app/menu")
public class V3MenuController extends BaseAction {
	
	@Autowired
	IAppUserTaskPlatfromService appUserTaskPlatfromService;
	
	@Autowired
	private IAppUserHoursService appUserHoursService;
	
	@Autowired
	private IMileStoneService mileStoneService;

	
	/**
	 * 个人统一工作中心主页面
	 * @param userId
	 * @param type 默认选中哪一个tabs
	 * @return
	 */
	@RequestMapping("/getMainPage")
	public String getMainPage(String userId, String type, Model model){
		model.addAttribute("userId", userId);
		model.addAttribute("type", type);
		model.addAttribute("randomNum", getTimes());//用于解决IE缓存问题
		return "v3/main";
	}
	
	@RequestMapping("/getMain6Page")
	public String getMain6Page(String userId,  String registDate, Model model){
		model.addAttribute("userId", userId);
		if(StringUtil.isNotNull(registDate)){
			model.addAttribute("registDate", registDate.split(",")[registDate.split(",").length-1]);
		}
		model.addAttribute("randomNum", getTimes());//用于解决IE缓存问题
		return "v3/tab6/main";
	}
	
	@RequestMapping("/getTaskMainPage")
	public String getTaskMainPage(String userId, String taskId, String type, Model model){
		model.addAttribute("userId", userId);
		VAppUserTaskPlatfrom userTask = appUserTaskPlatfromService.getVById(taskId);
		
		//父任务信息
		if(StringUtil.isNotNull(userTask.getParentId())){
			AppUserTaskPlatfrom parentUserTask = appUserTaskPlatfromService.getById(userTask.getParentId());
			model.addAttribute("parentUserTask", parentUserTask);
		}
		model.addAttribute("type", type);
		model.addAttribute("userTask", userTask);
		model.addAttribute("randomNum", getTimes());//用于解决IE缓存问题
		return "v3/task/main";
	}
	
	@RequestMapping("/task/getCalendarPage")
	public String getCalendarPage(String userId, String taskId, Model model) throws ServiceException{
		model.addAttribute("userId", userId);
		model.addAttribute("taskId", taskId);

		//查询这个任务下的总工时
		String totalHours = "";
		VAppUserHours query = new VAppUserHours();
		query.setTaskId(taskId);
		List<AppUserHours> list = appUserHoursService.getList(query);
		if(list.size()==0){
			
		}else if(list.size()==1){
			totalHours = list.get(0).getRegistHours();
		}else{
			BigDecimal bs = new BigDecimal(list.get(0).getRegistHours()); 
			for (int i = 1; i < list.size(); i++) {
				bs = bs.add(new BigDecimal(list.get(i).getRegistHours()));
			}
			bs = bs.setScale(0, BigDecimal.ROUND_HALF_UP);
			totalHours = bs.toString();
		}
		model.addAttribute("totalHours", totalHours);
		model.addAttribute("randomNum", getTimes());//用于解决IE缓存问题
		return "v3/task/calendar";
	}
	
	@RequestMapping("/task/getChildPage")
	public String getChildPage(String userId, String taskId, Model model){
		model.addAttribute("userId", userId);
		model.addAttribute("taskId", taskId);
		model.addAttribute("randomNum", getTimes());//用于解决IE缓存问题
		return "v3/task/child";
	}
	
	@RequestMapping("/task/getListPage")
	public String getListPage(String userId,String type, Model model){
		model.addAttribute("userId", userId);
		VAppUserTaskPlatfrom v = appUserTaskPlatfromService.getVByUserId(userId);
		if(StringUtil.isNotNull(v)&&v.getIsLeader().equals("1")){
			model.addAttribute("leader", "1");
		}
		if(userId.equals("95A62E63-07EA-BCF1-00A2-0982C0A80115")){
			model.addAttribute("leader", "1");
		}
		model.addAttribute("type", type);
		model.addAttribute("randomNum", getTimes());//用于解决IE缓存问题
		return "v3/task/list";
	}
	
	@RequestMapping("/task/getProjInfoPage")
	public String getProjInfoPage(String projId, Model model){
		if(StringUtil.isNotNull(projId)){
			AppProjInfo proj = appUserTaskPlatfromService.getProjById(projId);
			model.addAttribute("proj", proj);
			model.addAttribute("randomNum", getTimes());//用于解决IE缓存问题
		}
		return "v3/task/projinfo";
	}
	
	@RequestMapping("/task/getMileStonePage")
	public String getMileStonePage(String projId, Model model){
		if(StringUtil.isNotNull(projId)){
			AppProjInfo proj = appUserTaskPlatfromService.getProjById(projId);

			if(StringUtil.isNotNull(proj.getPrimProjId())){
				model.addAttribute("primProjId", proj.getPrimProjId());
			}
			model.addAttribute("projId", projId);
			model.addAttribute("randomNum", getTimes());//用于解决IE缓存问题
		}
		return "v3/task/milestone";
	}
	
	@RequestMapping("/task/getDocPage")
	public String getDocPage(String userId, String taskId, Model model){
		model.addAttribute("userId", userId);
		model.addAttribute("taskId", taskId);
		model.addAttribute("randomNum", getTimes());//用于解决IE缓存问题
		return "v3/task/doc";
	}
	/**
	 * 新里程碑tab跳转路径
	 * @param projId
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping("/mileStone/getListPage")
	public String mileStoneData(String projId,String userId,String v3Language,Model model){
		
		String path ="";
		Map<String,String> map = new HashMap<String, String>();
		AppProjInfo appProjInfo = appUserTaskPlatfromService.getProjById(projId);
		model.addAttribute("userId", userId);
		model.addAttribute("randomNum", getTimes());//用于解决IE缓存问题
		
		model.addAttribute("projId", projId);
		if(StringUtil.isNotNull(appProjInfo.getPrimProjId())){//根据是否存在父Id来跳转页面 （零部件项目）
			map.put("childId", projId);
			map.put("projId", appProjInfo.getPrimProjId());
			map.remove("childId");
			String fatherBaseLine = mileStoneService.maxBaseLine(map);
			model.addAttribute("primProjId", appProjInfo.getPrimProjId());
			model.addAttribute("fatherBaseLine", fatherBaseLine);//目前主项目的最新版本，不包括版本为空的
			path ="v3/mileStone/childList";
		}else{//主项目
			map.put("projId", projId);
			path ="v3/mileStone/list";
		}
		return path;
	}
	
	@RequestMapping("/oplHistory/getListPage")
	public String getOplHitoryPage(String oplId,Model model){
		model.addAttribute("randomNum", getTimes());//用于解决IE缓存问题
		if(StringUtil.isNotNull(oplId)){
			model.addAttribute("oplId", oplId);
		}
		return "v3/oplHistory/list";
	}
	
	private String getTimes(){
		return String.valueOf(new Date().getTime());
	}
	
}
