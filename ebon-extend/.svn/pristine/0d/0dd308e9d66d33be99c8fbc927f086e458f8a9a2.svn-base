package com.ebon.rpc.project.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.ebon.platform.BaseTest;
import com.ebon.rpc.project.IRpcProjectInfoService;
import com.ebon.rpc.project.vo.ProjectRequest;
import com.ebon.rpc.project.vo.ProjectResponse;

public class ProjectServiceTest extends BaseTest {
	
	@Resource
	private IRpcProjectInfoService	rpcProjectInfoService;
	
	@Test
	public void testGetProjectInfo() {
		ProjectRequest request = new ProjectRequest();
		request.setSystemSource("QA");
		request.setStartDate("2012-6-26");
		request.setEndDate("2012-6-26");
		//		request.setProjectName("GEELY INST,CD-1,CD-1,ECU-M7-8");
		//		request.setProjectCode("P/BE006-ECU FI 0");
		//		request.setProjectType("SYS");
		//		request.setProjectStage("S-A");
		//		request.setProjectStatus("Carry Over");
		//		request.setPh1("ECU FI");
		
		//		request.setExtendFields("p.proj_name like '%qh%' order by p.proj_name");
		//		request.setExtendFields("1=1 order by p.proj_name");
		ProjectResponse rp = rpcProjectInfoService.getProjectInfo(request);
		//		System.out.println(rp.getProjectInfos()[0].getProjectStage());
		
		System.out.println("IsOk:" + rp.getIsOk());
		System.out.println("errorCode:" + rp.getErrorCode());
		System.out.println(rp.getProjectInfos().length);
	}
	
	public IRpcProjectInfoService getRpcProjectInfoService() {
		return rpcProjectInfoService;
	}
	
	public void setRpcProjectInfoService(IRpcProjectInfoService rpcProjectInfoService) {
		this.rpcProjectInfoService = rpcProjectInfoService;
	}
	
}
