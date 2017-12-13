package com.ebon.rpc.opl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.platform.BaseTest;
import com.ebon.rpc.opl.vo.OplInfo;
import com.ebon.rpc.opl.vo.OplInsertRequest;
import com.ebon.rpc.opl.vo.OplInsertResponse;
import com.ebon.rpc.opl.vo.OplListRequest;
import com.ebon.rpc.opl.vo.OplListResponse;

public class IRpcOplServiceTest extends BaseTest {
	
	@Autowired
	private IRpcOplService	rpcOplService;
	
	public IRpcOplService getRpcOplService() {
		return rpcOplService;
	}
	
	public void setRpcOplService(IRpcOplService rpcOplService) {
		this.rpcOplService = rpcOplService;
	}
	
	@Test
	public void testList() {
		OplListRequest request = new OplListRequest();
		request.setSystemSource("QA");
		//request.setOplSource("BAC713239455265EE04022AEAA32043A"); //FMEA.OPLSOURCEID
		request.setOplSource("9BC1A6111A154CCCA5DA132A48756378"); //QA.OPLSOURCEID
		//		String[] oplIds = { "c126bf29-5c2c-4cdc-bc3b-87d34535b712", "5e3c8a47-4d6a-4093-ad23-7fb00767aac4" };
		//		request.setOplIds(oplIds);
		OplListResponse response = rpcOplService.list(request);
		System.out.println(response.getIsOk());
		//		System.out.println(response.getErrorCode());
		System.out.println(response.getOplInfos().length);
		System.out.println(response.getOplInfos()[0].getOplName());
	}
	
	@Test
	public void testInsert() {
		OplInsertRequest oplInsertRequest = new OplInsertRequest();
		oplInsertRequest.setSystemSource("QA");
		OplInfo oplInfo = new OplInfo();
		oplInfo.setProjectCode("585/EH1CPR.2");
		//		oplInfo.setSource("BAC713239455265EE04022AEAA32043A");//FMEA.OPLSOURCEID
		oplInfo.setSource("9BC1A6111A154CCCA5DA132A48756378"); //QA.OPLSOURCEID
		String[] respNames = new String[1];
		respNames[0] = "yichun.mao";
		oplInfo.setRespNames(respNames);
		//		oplInfo.setOplId("testOplId3");
		oplInfo.setOplName("testOplName01");
		oplInfo.setCreateDate("2012-1-5");
		oplInfo.setRemark("remark12");
		OplInfo[] oplInfos = new OplInfo[1];
		oplInfos[0] = oplInfo;
		oplInsertRequest.setOplInfos(oplInfos);
		OplInsertResponse response = rpcOplService.insert(oplInsertRequest);
		System.out.println(response);
		//		System.out.println(response.getRtnOplInfos()[0].getProjectCode());
		//		System.out.println(response.getRtnOplInfos()[0].getOplName());
		System.out.println(response.getRtnOplInfos()[0].getOplId());
	}
	
}
