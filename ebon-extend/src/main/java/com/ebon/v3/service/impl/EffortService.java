package com.ebon.v3.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ebon.framework.util.StringUtil;
import com.ebon.platform.service.ServiceException;
import com.ebon.v3.dto.VAppUserHours;
import com.ebon.v3.dto.VAppUserTaskPlatfrom;
import com.ebon.v3.service.IAppUserHoursService;
import com.ebon.v3.service.IAppUserTaskPlatfromService;
import com.ebon.v3.vo.AppUserHours;

@Service
public class EffortService {

	@Autowired
	private IAppUserTaskPlatfromService appUserTaskPlatfromService;
	@Autowired
	private IAppUserHoursService appUserHoursService;
	
	@Scheduled(cron="0 00 00 ? * MON-FRI")//周一到周五00:10分执行
	public void aotoRegist() throws ServiceException{
		
		List<AppUserHours> tasks = new ArrayList<AppUserHours>();
		
		Date date = new Date();
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
		String today = format.format(date);
		
		//先取出 app_user_task_platform表中的人员
		List<String> appUserIds = appUserTaskPlatfromService.getAllRespUsers();
		//获取系统排除有任务的其余所有用户
		List<String> cmUsers = appUserTaskPlatfromService.getAllUsers();
		
		if(StringUtil.isNotNull(appUserIds)){
			for(String userId : appUserIds){
				List<VAppUserHours> list = appUserHoursService.getVByUserId(userId);
				//如果该用户在当天已经填报过任务，则不需要自动填充
				if(list.size() != 0){
					continue;
				}
				//此处查询的为是否有任务
				Map<String,String> map = new HashMap<String, String>();
				map.put("userId", userId);
				List<VAppUserTaskPlatfrom> v = appUserTaskPlatfromService.getVByResp(map);
				if(StringUtil.isNotNull(v)){
					//查询出是否有进行中的任务
					Integer num = appUserTaskPlatfromService.getOngoingTasks(userId);
					if(num!=0){
						map.put("status", "2");
						v = appUserTaskPlatfromService.getVByResp(map);
						tasks=this.calculateEffort(v,today);
						appUserHoursService.batchAdd(tasks);
					}else{//如果存在任务，但是并无进行中的任务，那么也默认分配一个8小时的工
						AppUserHours appUserHours = new AppUserHours();
						appUserHours.setUserId(userId);
						appUserHours.setRegistDate(today);
						appUserHours.setRegistHours("8.00");
						appUserHoursService.add(appUserHours);
					}
				}
			}
		}
		
		if(StringUtil.isNotNull(cmUsers)){
			for(int i = 0; i<cmUsers.size(); i+=500){//数据量的庞大，将数据分为500一组进行批量插入
				List<String> newList = null;
				if((i+500)>=cmUsers.size()){
					newList = cmUsers.subList(i, cmUsers.size());
				}else{
					newList = cmUsers.subList(i, i+499);
				}
				appUserHoursService.batchUserIdsAdd(newList);
			}
		}
	}
	
	/**
	 * 用于计算工时
	 * @param list
	 * @param today
	 * @return
	 */
	public List<AppUserHours> calculateEffort(List<VAppUserTaskPlatfrom> list,String today){
		
		List<AppUserHours> userHours = new ArrayList<AppUserHours>();
		DecimalFormat df = new DecimalFormat("0.00");
		String averageTaskHours = df.format((float)Integer.parseInt("8")/list.size());//获取平均工时
		for(VAppUserTaskPlatfrom v : list){
			AppUserHours appUserHours = new AppUserHours();
			appUserHours.setTaskId(v.getId());
			appUserHours.setUserId(v.getRespUser());
			appUserHours.setRegistDate(today);
			appUserHours.setRegistHours(averageTaskHours);
			userHours.add(appUserHours);
		}
		return userHours;
	}
}
