package com.ebon.app.service.task;

import java.util.List;

import com.ebon.platform.service.ServiceException;
import com.ebon.rpc.task.vo.TaskInfo;
import com.ebon.rpc.task.vo.TaskRequest;

public interface ITaskService {
	
	/**
	 * 获取Task信息列表
	 * 
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public List<TaskInfo> getTaskInfo(TaskRequest request) throws ServiceException;
	
	/**
	 * 获取QA类型的TASK信息列表
	 * 
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public List<TaskInfo> getTaskInfoForQA(TaskRequest request) throws ServiceException;
	
}
