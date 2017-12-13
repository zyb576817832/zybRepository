package com.ebon.rpc.equipment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.platform.BaseTest;
import com.ebon.platform.service.ServiceException;

public class EquipmentServiceTest extends BaseTest {
	
	@Autowired
	public IEquipmentService	equipmentService;
	
	public IEquipmentService getEquipmentService() {
		return equipmentService;
	}
	
	public void setEquipmentService(IEquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}
	
	//	@Test
	//	public void testGetEquipmentInfo(){
	//		Map<String,Object> map = new HashMap<String, Object>();
	//		map.put("startDate", "2010-5-1");
	//		map.put("endDate", "2012-4-30");
	//		List<Map<String,Object>> equipList = iEquipmentService.getEquipmentInfo(map);
	//		System.out.println(equipList.size());
	//	}
	@Test
	public void testGetEquipmentInfo() throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", "2011-6-1");
		map.put("endDate", "2011-6-30");
		List<Map<String, Object>> equipList;
		equipList = equipmentService.getEquipmentInfo(map);
		System.out.println(equipList.size());
	}
	
	@Test
	public void testPutEquipmentDataToTable() {
		try {
			equipmentService.putEquipmentDataToTable();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	//	@Test
	//	public void testGetEquipmentInfoNE3ForCPC() {
	//		Map<String, Object> requestMap = new HashMap<String, Object>();
	//		List<Map<String, Object>> list = null;
	//		try {
	//			requestMap.put("startDate", "2011-1-1");
	//			requestMap.put("endDate", "2011-1-31");
	//			requestMap.put("currentYear", 2011);
	//			list = equipmentService.getEquipmentInfoNE3ForCPC(requestMap);
	//			System.out.println(list.size());
	//		} catch (ServiceException e) {
	//			e.printStackTrace();
	//		}
	//	}
	//	
	//	@Test
	//	public void testGetEquipmentInfoForCPC() {
	//		Map<String, Object> requestMap = new HashMap<String, Object>();
	//		List<Map<String, Object>> list = null;
	//		try {
	//			requestMap.put("startDate", "2011-1-1 00:00:00");
	//			requestMap.put("endDate", "2011-1-31 23:59:59");
	//			requestMap.put("currentYear", 2011);
	//			requestMap.put("monthCount", 1);
	//			list = equipmentService.getEquipmentInfoNE4ForCPC(requestMap);
	//			System.out.println(list.size());
	//		} catch (ServiceException e) {
	//			e.printStackTrace();
	//		}
	//	}
}
