package com.ebon.rpc.task;

import javax.annotation.Resource;

import org.junit.Test;

import com.ebon.platform.BaseTest;
import com.ebon.rpc.task.vo.TaskInfo;
import com.ebon.rpc.task.vo.TaskRequest;
import com.ebon.rpc.task.vo.TaskResponse;

public class TaskServiceTest extends BaseTest {
	
	@Resource
	private IRpcTaskInfoService	rpcTaskInfoService;
	
	@Test
	public void testGetTaskInfo() {
		TaskRequest request = new TaskRequest();
		request.setSystemSource("QA");
		request.setProjectCode("585/EH1CPR.2-ECU FI 0");
		//		request.setTaskName("Idle speed control");
		TaskResponse response = rpcTaskInfoService.getTaskInfo(request);
		TaskInfo[] infos = response.getTaskInfos();
		for (int i = 0; i < infos.length; i++) {
			TaskInfo vo = infos[i];
			System.out.println("projCode:" + vo.getProjectCode());
			System.out.println("taskName:" + vo.getTaskName());
			System.out.println("qaType:" + vo.getQaType());
			//			System.out.println("资源人:" + vo.getTaskResources()[0].getUsername());
			//			System.out.println("计划时间:" + vo.getTaskResources()[0].getPlanTime());
			//			System.out.println("资源人:" + vo.getTaskResources()[1].getUsername());
			//			System.out.println("计划时间:" + vo.getTaskResources()[1].getPlanTime());
			System.out.println("------------------------------------------");
		}
	}
	
	public IRpcTaskInfoService getRpcTaskInfoService() {
		return rpcTaskInfoService;
	}
	
	public void setRpcTaskInfoService(IRpcTaskInfoService rpcTaskInfoService) {
		this.rpcTaskInfoService = rpcTaskInfoService;
	}
	
}
