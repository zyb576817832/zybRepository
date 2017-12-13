package com.ebon.rpc.equipment.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.ebon.platform.extend.DataSourceConstant;
import com.ebon.platform.service.ServiceException;
import com.ebon.platform.util.DateHelper;
import com.ebon.rpc.BaseRpcService;
import com.ebon.rpc.equipment.IRpcInsertNewNE4Info;
@Service
public class InsertNewNE4Info extends BaseRpcService implements IRpcInsertNewNE4Info {
	
	@Override
	public void insert() throws ServiceException {
		List<Map<String, Object>> list = this.selectNE4Info();
		Map<String, Object> map2 = new HashMap<String, Object>();
		SqlSession session = null;
		try {
			session = super.getSession(DataSourceConstant.PMS_DATASOURCE_NAME);
			for (Map<String, Object> map : list) {
				
				
				String startDate = (String) map.get("startDate");
				String endDate = (String) map.get("endDate");
				String startShortDate = null;
				Date startDate1 = null;
				Date endDate1 = null;
				if (null != startDate && null != endDate) {
					int diffDate = DateHelper.getDaysOfTowDiffDate(startDate,endDate);
					if ( startDate.length() > 10) {
						startDate1 = DateHelper.getStringToLongDate(startDate);
					} else {
						startDate1 = DateHelper.getStringToDate(startDate);
					}
					if ( endDate.length() > 10) {
						endDate1 = DateHelper.getStringToLongDate(endDate);
					}else {
						endDate1 = DateHelper.getStringToDate(endDate);
					}
					startShortDate = DateHelper.toString(startDate1, "yyyy-MM-dd");
					String orderId = (String) map.get("orderId");
					String facilityId = (String) map.get("facilityId");
					if (diffDate == 0) {
						int hours = Integer.valueOf(map.get("hours")+"");
						map2.put("facilityId", facilityId);
						map2.put("orderId", orderId);
						map2.put("time_num_date", startShortDate);
						map2.put("hours", hours);
						this.insertNE4Time(session, map2);
					} else if (diffDate > 0) {
						for (int j = 0; j <= diffDate; j++) {
							map2.put("facilityId", facilityId);
							map2.put("orderId", orderId);
							if (j == 0) {
								map2.put("hours", 24 - DateHelper.getHourOfDate(startDate1));
								map2.put("time_num_date",startDate1);
							} else if (0 < j && j < diffDate) {
								String d = DateHelper.toString(DateHelper.getDateOfNexts(startDate1, j),"yyyy-MM-dd");
								map2.put("time_num_date",d );
								map2.put("hours", 24);
							} else if (j == diffDate) {
								map2.put("time_num_date",endDate1);
								map2.put("hours", DateHelper.getHourOfDate(endDate1)+12);
							}
							this.insertNE4Time(session, map2);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			super.closeSession(session);
		}
	}

	@Override
	public boolean insertNE4Time(SqlSession session, Map<String, Object> paramMap)throws ServiceException {
		boolean flag = false;
		try {
			flag = myBatisDao.save(session,"newNE4InfoMapper.insertNewNE4Info", paramMap);
		} catch (Exception e) {
			log.error(e);
		} finally {
			session.commit();
		}
		return flag;
	}

	@Override
	public List<Map<String, Object>> selectNE4Info() throws ServiceException {
		List<Map<String, Object>> list = null;
		SqlSession session = null;
		try {
			session = super.getSession(DataSourceConstant.PMS_DATASOURCE_NAME);
			list = myBatisDao.getList(session,"newNE4InfoMapper.getTime");
		} catch (Exception e) {
			log.error(e);
		} finally {
			super.closeSession(session);
		}
		return list;
	}
	
}
