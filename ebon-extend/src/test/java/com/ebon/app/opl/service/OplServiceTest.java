package com.ebon.app.opl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.app.service.opl.IOplService;
import com.ebon.platform.BaseTest;
import com.ebon.platform.service.ServiceException;
import com.ebon.rpc.opl.vo.OplInfo;
import com.ebon.rpc.opl.vo.OplListRequest;

public class OplServiceTest extends BaseTest {
	
	@Autowired
	private IOplService	oplService	= null;
	
	@Test
	public void testGetOplInfo() {
		OplListRequest request = new OplListRequest();
		request.setOplSource("Meeting");
		//		request.setOplId("e15011d0-fa32-4683-85b7-aaff269abcb0");
		String[] oplIds = { "c126bf29-5c2c-4cdc-bc3b-87d34535b712", "5e3c8a47-4d6a-4093-ad23-7fb00767aac4" };
		request.setOplIds(oplIds);
		List<OplInfo> list = null;
		try {
			list = oplService.getOplInfo(request);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		System.out.println(list.size());
	}
	
	@Test
	public void testUpdateOplInfo() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectInfoId", "testprojectid01");
		//		paramMap.put("oplId", "B235A5FA702B4760BD3A19DB00FEB688");
		paramMap.put("oplName", "test04");
		paramMap.put("status", "OPEN");
		try {
			boolean flag = oplService.updateOplInfo(paramMap);
			System.out.println(flag);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInsertOplInfo() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectInfoId", "testprojectId01");
		paramMap.put("oplId", "B235A5FA702B4760B33A19DB0fdEB689");
		//		paramMap.put("oplName", "test02");
		//		paramMap.put("status", "OPEN");
		try {
			boolean flag = oplService.insertOplInfo(paramMap);
			System.out.println(flag);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testGetOplIsExist() {
		String oplId = "B235A5FA702B4760B33A19DB0fdEB689";
		try {
			System.out.println(oplService.getOplIsExist(oplId));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public IOplService getOplService() {
		return oplService;
	}
	
	public void setOplService(IOplService oplService) {
		this.oplService = oplService;
	}
	
}
