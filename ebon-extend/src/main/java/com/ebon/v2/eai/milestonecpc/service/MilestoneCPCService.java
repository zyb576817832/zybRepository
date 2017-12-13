/** 
 * Project Name:ebon-extend 
 * File Name:MilestoneCPCService.java 
 * Package Name:com.ebon.v2.eai.milestonecpc.service 
 * Date:2015年11月9日下午2:10:09 
 * Copyright (c) 2015, quanxinsky@163.com All Rights Reserved. 
 * qx
 */  
package com.ebon.v2.eai.milestonecpc.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ebon.platform.dao.DaoException;
import com.ebon.platform.service.BaseService;
import com.ebon.v2.eai.milestonecpc.model.MilestoneCPC;

/** 
 * ClassName: MilestoneCPCService
 * Function: 里程碑点跟踪表Service层
 * date: 2015年11月9日 下午2:10:09
 * @author qx 
 * @version  
 * @since JDK 1.6 
 */
public class MilestoneCPCService extends BaseService {
	
	/**
	 * 月初定时执行
	 * insertMilestoneCPC:(持久化操作)
	 * 插入里程碑点跟踪表
	 * @author qx 
	 * 2015年11月9日
	 * @throws DaoException 
	 * @since JDK 1.6
	 */
	@SuppressWarnings("unused")
	private void insertMilestoneCPC() throws DaoException{
		
		//本月同步上月数据，定义上月的日期格式
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM"); 
		Calendar lastMonth = Calendar.getInstance();
		lastMonth.add(Calendar.MONTH, -1);
		String month = format.format(lastMonth.getTime());
		
		List<MilestoneCPC> list =  prepareForInsert();
		for (MilestoneCPC milestoneCPC : list) {
			try {
				milestoneCPC.setMonth(month);
				super.myBatisDao.save("V2Mapper.insertMilestoneCPC", milestoneCPC);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * prepareForInsert:(构建成需要持久化的milestoneCPC对象)
	 * @author qx 
	 * 2015年11月9日
	 * @throws DaoException 
	 * @since JDK 1.6
	 */
	private List<MilestoneCPC> prepareForInsert() throws DaoException{
		List<MilestoneCPC> list = new ArrayList<MilestoneCPC>();
		List<MilestoneCPC> list1 = new ArrayList<MilestoneCPC>();
		list = super.myBatisDao.getList("V2Mapper.selectSYSMilestones");
		list1 = super.myBatisDao.getList("V2Mapper.selectOtherMilestones");
		
		for (MilestoneCPC milestoneCPC : list1) {
			list.add(milestoneCPC);
		}
		return list;
	}

	/**
	 * getMilestoneCPCByProj:(根据起始与结束月份得到里程碑点图的数据集合)
	 * @author qx 
	 * 2015年11月10日
	 * @param endMonth 
	 * @param startMonth 
	 * @throws DaoException 
	 * @since JDK 1.6 
	 */
	public List<MilestoneCPC> getMilestoneCPCByProj(String proj_id, String startMonth, String endMonth) throws DaoException {
		List<MilestoneCPC> list = new ArrayList<MilestoneCPC>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("proj_id", proj_id);
		map.put("startMonth", startMonth);
		map.put("endMonth", endMonth);
		list = super.myBatisDao.getList("V2Mapper.getMilestoneCPCByProj", map);
		return list;
	}

	/**
	 * getProjTime:(得到项目的起始与结束日期)
	 * @author qx 
	 * 2015年11月10日
	 * @throws DaoException 
	 * @since JDK 1.6 
	 */
	public Map<String, String> getProjTime(String proj) throws DaoException {
		Map<String,String> map = super.myBatisDao.getOne("V2Mapper.getProjTime", proj);
		return map;
	}
	
}
