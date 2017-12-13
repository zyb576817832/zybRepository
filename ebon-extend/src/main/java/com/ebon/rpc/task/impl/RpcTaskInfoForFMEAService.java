package com.ebon.rpc.task.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebon.app.service.task.ITaskService;
import com.ebon.platform.util.StringUtil;
import com.ebon.rpc.BaseRpcService;
import com.ebon.rpc.error.ErrorCode;
import com.ebon.rpc.task.IRpcTaskInfoService;
import com.ebon.rpc.task.vo.TaskInfo;
import com.ebon.rpc.task.vo.TaskRequest;
import com.ebon.rpc.task.vo.TaskResource;
import com.ebon.rpc.task.vo.TaskResponse;

@Service
public class RpcTaskInfoForFMEAService extends BaseRpcService implements IRpcTaskInfoService {
	
	@Autowired
	private ITaskService	taskService;
	
	@Override
	public TaskResponse getTaskInfo(TaskRequest request) {
		TaskResponse response = new TaskResponse();
		if (!StringUtil.isNotNull(request.getProjectCode())) {
			response.setIsOk(ErrorCode.FALSE);
			response.setErrorCode(ErrorCode.PROJECTCODE_IS_NULL);
		} else {
			try {
				List<TaskInfo> list = null;
				list = taskService.getTaskInfo(request);
				if (StringUtil.isNotNull4List(list)) {
					for (int i = 0; i < list.size(); i++) {
						TaskInfo taskInfo = list.get(i);
						List<TaskResource> taskResourceList = taskInfo.getTaskResourceList();
						if (StringUtil.isNotNull4List(taskResourceList)) {
							TaskResource[] taskResources = new TaskResource[taskResourceList.size()];
							taskResourceList.toArray(taskResources);
							taskInfo.setTaskResources(taskResources);
						} else {
							TaskResource[] taskResources = new TaskResource[0];
							taskInfo.setTaskResources(taskResources);
						}
					}
					TaskInfo[] infos = new TaskInfo[list.size()];
					list.toArray(infos);
					response.setTaskInfos(infos);
					response.setIsOk(ErrorCode.TRUE);
				} else {
					TaskInfo[] infos = new TaskInfo[0];
					response.setTaskInfos(infos);
					response.setIsOk(ErrorCode.FALSE);
					response.setErrorCode(ErrorCode.DATA_IS_NULL);
				}
			} catch (Exception e) {
				response.setIsOk(ErrorCode.FALSE);
				response.setErrorCode(ErrorCode.PMS_IS_ERROR);
			}
		}
		return response;
	}
	
	public ITaskService getTaskService() {
		return taskService;
	}
	
	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}
	
}
