package com.ebon.rpc.cost.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.platform.service.ServiceException;
import com.ebon.rpc.equipment.IEquipmentService;

public class CostServiceTest {
	@Autowired
	public IEquipmentService	iEquipmentService;
	
	public IEquipmentService getiEquipmentService() {
		return iEquipmentService;
	}
	
	public void setiEquipmentService(IEquipmentService iEquipmentService) {
		this.iEquipmentService = iEquipmentService;
	}
	
	//	@Test
	//	public void testGetEquipmentInfo(){
	//		Map<String,Object> map = new HashMap<String, Object>();
	//		map.put("startDate", "2010-5-1");
	//		map.put("endDate", "2012-4-30");
	//		List<Map<String,Object>> equipList = iEquipmentService.getEquipmentInfo(map);
	//		System.out.println(equipList.size());
	//	}
	 
	public void testGetEquipmentInfo() throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", "2011-6-1");
		map.put("endDate", "2011-6-30");
		List<Map<String, Object>> equipList;
		equipList = iEquipmentService.getEquipmentInfo(map);
		System.out.println(equipList.size());
		
	}
}
