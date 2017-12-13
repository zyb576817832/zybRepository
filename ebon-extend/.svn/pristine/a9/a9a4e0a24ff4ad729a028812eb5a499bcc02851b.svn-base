package com.ebon.app.oplaction;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.app.service.oplaction.IOplActionService;
import com.ebon.app.service.user.IUserService;
import com.ebon.platform.BaseTest;
import com.ebon.platform.service.ServiceException;

public class IOplActionServiceTest extends BaseTest {
	
	@Autowired
	private IOplActionService	oplActionService	= null;
	
	@Resource
	private IUserService	userService;
	
	public IOplActionService getOplActionService() {
		return oplActionService;
	}
	
	public void setOplActionService(IOplActionService oplActionService) {
		this.oplActionService = oplActionService;
	}
	
	public IUserService getUserService() {
		return userService;
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	@Test
	public void testUpdateOplActionInfo() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("oplId", "e15011d0-fa32-4683-85b7-aaff269abcb0");
		paramMap.put("oplActionId", "f5376e11-a7ae-48d6-b484-5b5d0c818604");
		paramMap.put("actionName", "we");
		try {
			System.out.println(oplActionService.updateOplActionInfo(paramMap));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testInsertUser() {
		//		OplActionInfo oplActionInfo=new OplActionInfo();
		//		String[] userNames = oplActionInfo.getRespNames();
		//		for (int i = 0; i < userNames.length; i++) {
		String userName = "yong.wang4";
		//			userName = userNames[i];
		String userId = null;
		try {
			userId = userService.getUserIdByUserName(userName);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		System.out.println(userId);
	}
	
	//	}
	
}
