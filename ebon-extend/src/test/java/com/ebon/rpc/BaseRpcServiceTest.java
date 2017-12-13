package com.ebon.rpc;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.platform.BaseTest;
import com.ebon.platform.service.ServiceException;
import com.ebon.rpc.error.Error;
import com.ebon.rpc.error.ErrorCode;

public class BaseRpcServiceTest extends BaseTest{
	@Autowired
	BaseRpcService baseRpcService;
	
	
	public BaseRpcService getBaseRpc() {
		return baseRpcService;
	}


	public void setBaseRpc(BaseRpcService baseRpcService) {
		this.baseRpcService = baseRpcService;
	}


	@Test
	public void testGetErrorCodeInfo(){
		Error error = new Error();
		error.setErrorCode(ErrorCode.STARTDATE_IS_ERROR);
		error.setClassName("A");
		error.setMethodName("B");
		String errorMessage = null;
		try {
			errorMessage = baseRpcService.getErrorCodeInfo(error);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		System.out.println(errorMessage);
	}
	
}
