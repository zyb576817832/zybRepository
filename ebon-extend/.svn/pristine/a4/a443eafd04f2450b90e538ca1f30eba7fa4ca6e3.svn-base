package com.ebon.rpc.oplaction;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.platform.BaseTest;
import com.ebon.rpc.oplaction.vo.OplActionInfo;
import com.ebon.rpc.oplaction.vo.OplActionInsertRequest;
import com.ebon.rpc.oplaction.vo.OplActionInsertResponse;
import com.ebon.rpc.oplaction.vo.OplActionListRequest;
import com.ebon.rpc.oplaction.vo.OplActionListResponse;

public class OplActionServiceTest extends BaseTest {
	
	@Autowired
	public IRpcOplActionService	oplActionService;
	
	public IRpcOplActionService getOplActionListService() {
		return oplActionService;
	}
	
	public void setOplActionListService(IRpcOplActionService oplActionListService) {
		this.oplActionService = oplActionListService;
	}
	
	@Test
	public void testList() {
		OplActionListRequest request = new OplActionListRequest();
		request.setSystemSource("QA");
		request.setOplSource("9BC1A6111A154CCCA5DA132A48756378");
		//		request.setOplId("7ddd286c-3fd9-40b7-ac9c-d06895f2eb22");
		String[] oplIds = { "dd1060e2-03f1-44f6-aad9-7610cc3e8c6a", "36a8a4ee-cd6c-4859-8698-e3971ef368b8" };
		request.setOplIds(oplIds);
		//		request.setActionRespName("AAZ");
		//				request.setActionStatus("2");
		//		request.setOplActionId("4bbdaec6-5cbe-4270-9b37-87a42b63c7bb");
		//		request.setOplRespName("qing.yang");
		//		request.setProjectCode("test5");
		//		request.setStatus("OPEN");
		OplActionListResponse response = oplActionService.list(request);
		System.out.println(response.getOplActionInfos().length);
		System.out.println(response.getIsOk());
		System.out.println(response.getErrorCode());
	}
	
	@Test
	public void testInsert() {
		OplActionInsertRequest request = new OplActionInsertRequest();
		OplActionInfo oplActionInfo = new OplActionInfo();
		request.setSystemSource("QA");
		oplActionInfo.setActionName("testActionName06");
		//		oplActionInfo.setOplActionId("testOplActionId");
		oplActionInfo.setOplId("5b3d1743-d629-4c34-b480-a76859c92438");
		String[] respNames = {};
		oplActionInfo.setRespNames(respNames);
		oplActionInfo.setStatus("1");
		oplActionInfo.setTargetStartDate("2012-1-1");
		//		oplActionInfo.setTargetEndDate("2012-1-2");
		//		oplActionInfo.setAclStartDate("2011-2-1");
		//		oplActionInfo.setAclEndDate("2012-3-1");
		oplActionInfo.setOritargetEndDate("2012-4-1");
		//		oplActionInfo.setCreatDate("2011-5-24");
		OplActionInfo[] oplActionInfoA = new OplActionInfo[1];
		oplActionInfoA[0] = oplActionInfo;
		request.setOplActionInfos(oplActionInfoA);
		OplActionInsertResponse response = oplActionService.insert(request);
		System.out.println(response);
		System.out.println(response.getIsOk());
		System.out.println(response.getErrorCode());
		//		System.out.println(response.getRtnOplActionInfos()[0].getOplActionId());
		//		System.out.println(response.getRtnOplActionInfos()[0].getOplId());
		//		System.out.println(response.getRtnOplActionInfos()[0].getErrorCode());
	}
}
