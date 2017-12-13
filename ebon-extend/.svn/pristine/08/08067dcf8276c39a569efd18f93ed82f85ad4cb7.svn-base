package com.ebon.rpc.project.impl;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebon.app.service.project.IProjectService;
import com.ebon.app.service.task.ITaskService;
import com.ebon.platform.util.StringUtil;
import com.ebon.rpc.BaseRpcService;
import com.ebon.rpc.error.ErrorCode;
import com.ebon.rpc.project.IRpcProjectInfoService;
import com.ebon.rpc.project.vo.ProjectInfo;
import com.ebon.rpc.project.vo.ProjectRequest;
import com.ebon.rpc.project.vo.ProjectResponse;
import com.ebon.rpc.task.vo.TaskInfo;
import com.ebon.rpc.task.vo.TaskRequest;
import com.ebon.rpc.task.vo.TaskResource;
import com.ebon.rpc.task.vo.TaskResponse;

@Service
public class RpcProjectInfoForQAService extends BaseRpcService implements IRpcProjectInfoService {
	
	@Autowired
	private IProjectService	projectService;
	
	@Override
	public ProjectResponse getProjectInfo(ProjectRequest request) {
		ProjectResponse response = new ProjectResponse();
		String startDate = request.getStartDate();
		String endDate = request.getEndDate();
		Pattern p = Pattern.compile("^\\d{1,4}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[1,2][0-9]|3[0,1]|[1-9])$");
		if (StringUtil.isNotNull(startDate) && !p.matcher(startDate.trim()).find()) {
			response.setIsOk(ErrorCode.FALSE);
			response.setErrorCode(ErrorCode.STARTDATE_IS_ERROR);
		} else if (StringUtil.isNotNull(endDate) && !p.matcher(endDate.trim()).find()) {
			response.setIsOk(ErrorCode.FALSE);
			response.setErrorCode(ErrorCode.ENDDATE_IS_ERROR);
		} else {
			try {
				List<ProjectInfo> list = null;
				list = projectService.getProjectInfo(request);
				if (null != list && list.size() > 0) {
					ProjectInfo[] infos = new ProjectInfo[list.size()];
					list.toArray(infos);
					response.setIsOk(ErrorCode.TRUE);
					response.setProjectInfos(infos);
				} else {
					ProjectInfo[] infos = new ProjectInfo[0];
					response.setProjectInfos(infos);
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
	
	public IProjectService getProjectService() {
		return projectService;
	}
	
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
	
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
				list = taskService.getTaskInfoForQA(request);
				if (null != list && list.size() > 0) {
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
