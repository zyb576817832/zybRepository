package com.ebon.app.task.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebon.app.service.task.ITaskService;
import com.ebon.platform.BaseTest;
import com.ebon.platform.service.ServiceException;
import com.ebon.rpc.task.vo.TaskRequest;

public class TaskServiceTest extends BaseTest {
	
	@Autowired
	private ITaskService	taskService	= null;
	
	@Test
	public void testGetTaskInfo() {
		TaskRequest request = new TaskRequest();
		try {
			taskService.getTaskInfo(request);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTaskInfoForQA() {
		TaskRequest request = new TaskRequest();
		try {
			taskService.getTaskInfoForQA(request);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public ITaskService getTaskService() {
		return taskService;
	}
	
	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}
	
}
