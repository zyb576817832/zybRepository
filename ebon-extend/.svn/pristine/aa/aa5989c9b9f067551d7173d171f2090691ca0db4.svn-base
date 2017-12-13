package com.ebon.rpc.sap.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.platform.BaseTest;
import com.ebon.platform.service.ServiceException;

public class TestThreeFeeService extends BaseTest {
	
	@Autowired
	private IThreeFeeService	threeFeeService;
	
	public IThreeFeeService getThreeFeeService() {
		return threeFeeService;
	}
	
	public void setThreeFeeService(IThreeFeeService threeFeeService) {
		this.threeFeeService = threeFeeService;
	}
	
	@Test
	public void testGetThreeFee() {
		threeFeeService.getThreeFee();
	}
	
	@Test
	public void testGetSAPInfo() throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", "2011-1-1");
		map.put("endDate", "2011-12-31");
		List<Map<String, Object>> sapList = null;
		sapList = threeFeeService.getSAPInfo(map);
		System.out.println(sapList.size());
	}
}
