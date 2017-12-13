package com.ebon.rpc.tef.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.app.service.tef.ITefService;
import com.ebon.platform.BaseTest;
import com.ebon.platform.service.ServiceException;

public class TestTefService extends BaseTest {
	
	@Autowired
	public ITefService	iTefService;
	
	public final ITefService getiTefService() {
		return iTefService;
	}
	
	public final void setiTefService(ITefService iTefService) {
		this.iTefService = iTefService;
	}
	
	@Test
	public void testGetTEFInfo() throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", "2011-6-1");
		map.put("endDate", "2011-6-30");
		List<Map<String, Object>> tefList = null;
		tefList = iTefService.getTefInfo(map);
		System.out.println(tefList.size());
	}
	
}
