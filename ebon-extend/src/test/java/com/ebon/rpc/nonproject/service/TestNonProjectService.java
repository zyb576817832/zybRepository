package com.ebon.rpc.nonproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.app.service.nonproj.INonProjectService;
import com.ebon.platform.BaseTest;
import com.ebon.platform.service.ServiceException;

public class TestNonProjectService extends BaseTest {
	
	@Autowired
	public INonProjectService	nonProjectService;
	
	public INonProjectService getNonProjectService() {
		return nonProjectService;
	}
	
	public void setNonProjectService(INonProjectService nonProjectService) {
		this.nonProjectService = nonProjectService;
	}
	
	@Test
	public void testGetNonProjectInfo() throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", "2011-1-1");
		map.put("endDate", "2011-12-31");
		List<Map<String, Object>> NonProjectInfoList = null;
		NonProjectInfoList = nonProjectService.getNonProjectInfo(map);
		System.out.println(NonProjectInfoList.size());
	}
	
}
