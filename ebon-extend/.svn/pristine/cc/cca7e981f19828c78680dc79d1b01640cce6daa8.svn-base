package com.ebon.rpc.person.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.app.service.cost.person.IPersonService;
import com.ebon.platform.BaseTest;
import com.ebon.platform.service.ServiceException;

public class TestPersonService extends BaseTest {
	
	@Autowired
	public IPersonService	PersonService;
	
	public IPersonService getPersonService() {
		return PersonService;
	}
	
	public void setPersonService(IPersonService personService) {
		PersonService = personService;
	}
	
	@Test
	public void testGetPersonelInfo() throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", "2011-12-1");
		map.put("endDate", "2011-12-31");
		List<Map<String, Object>> personList = null;
		personList = PersonService.getPersonelInfo(map);
		System.out.println(personList.size());
	}
	
}
