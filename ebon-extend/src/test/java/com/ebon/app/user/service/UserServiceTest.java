package com.ebon.app.user.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.app.service.user.IUserService;
import com.ebon.platform.BaseTest;
import com.ebon.platform.service.ServiceException;

public class UserServiceTest extends BaseTest {
	
	@Autowired
	private IUserService	userService	= null;
	
	@Test
	public void testGetUserIdByUserName() {
		String userName = "yichun.mao";
		String userId = null;
		try {
			userId = userService.getUserIdByUserName(userName);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		System.out.println(userId);
	}
	
	public IUserService getUserService() {
		return userService;
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
}
