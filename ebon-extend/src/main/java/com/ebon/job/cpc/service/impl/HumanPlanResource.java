package com.ebon.job.cpc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.ebon.job.cpc.service.IHumanPlanResource;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.util.Config;
import com.ebon.platform.util.DateHelper;

@Service
public class HumanPlanResource extends BaseService implements IHumanPlanResource {
	
	@Config("config.dateScope")
	private String dateScope;

	@Override
	public void sumResourcePlantime() {
		if (null != dateScope && dateScope.trim().length() > 0) {
			String[] dateScopes = dateScope.split("_");
			if (dateScopes.length == 2) {
				String updateDate = DateHelper.addDate(new Date(), -Integer.valueOf(dateScopes[0]), Integer.valueOf(dateScopes[1]), "yyyy-MM-dd");
				SqlSession session = null;
				try {
					List<Map<String, Object>> planTimeList = super.myBatisDao.getList("usertaskPlanTime.getTaskForUpdate", updateDate);
					if(null != planTimeList && planTimeList.size() > 0) {
						session = super.getSession();
						for (Map<String, Object> planTime : planTimeList) {
							String usertaskId = planTime.get("USERTASK_ID") + "";
							String startDate = planTime.get("PLAN_START_DATE") + "";
							String endDate = planTime.get("PLAN_END_DATE") + "";
							int betweenDays = DateHelper.getDaysOfTowDiffDate(startDate, endDate) + 1;
							int allHours = Integer.valueOf(planTime.get("PLAN_TIME") + "");
							int hours = allHours/betweenDays;
							int remainderHours = allHours%betweenDays;
							for(int i = 0; i < betweenDays; i++){
								if(i == betweenDays - 1){
									hours += remainderHours;
								}
								String timeSheetDate = DateHelper.addDate(DateHelper.getStringToDate(startDate),i, 6, "yyyy-MM-dd");
								if(hours != 0) {
									if(! this.updateResourcePlan(session, usertaskId, timeSheetDate, hours)){
										this.insertResourcePlan(session, usertaskId, timeSheetDate, hours);
									}
								}
							}
						}
						session.commit();
					}
				} catch (Exception e) {
					session.rollback();
					log.error(e);
				} finally {
					super.closeSession(session);
				}
			} else {
				log.error("config。properties文件中的config.dateScope配置错误！");
			}
		} else {
			log.error("请配置config。properties文件中的config.dateScope！");
		}
	}
	
	private boolean updateResourcePlan(SqlSession session, String userTaskId, String timeSheetDate, int hours) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userTaskId", userTaskId);
		paramMap.put("timeSheetDate", timeSheetDate);
		paramMap.put("hours", hours);
		return super.myBatisDao.update(session, "usertaskPlanTime.update", paramMap);
	}
	
	private boolean insertResourcePlan(SqlSession session, String userTaskId, String timeSheetDate, int hours) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userTaskId", userTaskId);
		paramMap.put("timeSheetDate", timeSheetDate);
		paramMap.put("hours", hours);
		return super.myBatisDao.update(session, "usertaskPlanTime.insert", paramMap);
	}

}
