package com.ebon.rpc.project.impl;

import org.springframework.stereotype.Service;

import com.ebon.platform.util.SpringContextUtil;
import com.ebon.platform.util.StringUtil;
import com.ebon.rpc.BaseRpcService;
import com.ebon.rpc.error.ErrorCode;
import com.ebon.rpc.project.IRpcProjectInfoService;
import com.ebon.rpc.project.vo.ProjectInfo;
import com.ebon.rpc.project.vo.ProjectRequest;
import com.ebon.rpc.project.vo.ProjectResponse;
import com.ebon.rpc.task.vo.TaskRequest;
import com.ebon.rpc.task.vo.TaskResponse;

@Service
public class RpcProjectInfoService extends BaseRpcService implements IRpcProjectInfoService {
	
	@Override
	public ProjectResponse getProjectInfo(ProjectRequest request) {
		ProjectResponse projectResponse = new ProjectResponse();
		if (!StringUtil.isNotNull(request.getSystemSource())) {
			projectResponse.setIsOk(ErrorCode.FALSE);
			projectResponse.setErrorCode(ErrorCode.SYSTEM_SOURCE_IS_NULL);
		} else {
			IRpcProjectInfoService projectInfoService = null;
			if ("FMEA".equals(request.getSystemSource())) {
				projectInfoService = (IRpcProjectInfoService) SpringContextUtil.getBean("rpcProjectInfoForFMEAService");
				projectResponse = projectInfoService.getProjectInfo(request);
			} else if ("QA".equals(request.getSystemSource())) {
				projectInfoService = (IRpcProjectInfoService) SpringContextUtil.getBean("rpcProjectInfoForQAService");
				projectResponse = projectInfoService.getProjectInfo(request);
			} else {
				ProjectInfo[] infos = new ProjectInfo[0];
				projectResponse.setProjectInfos(infos);
				projectResponse.setIsOk(ErrorCode.FALSE);
				projectResponse.setErrorCode(ErrorCode.SYSTEMSOURCE_IS_ERROR);
			}
		}
		return projectResponse;
	}
	
	@Override
	public TaskResponse getTaskInfo(TaskRequest request) {
		TaskResponse taskResponse = new TaskResponse();
		if (!StringUtil.isNotNull(request.getSystemSource())) {
			taskResponse.setIsOk(ErrorCode.FALSE);
			taskResponse.setErrorCode(ErrorCode.SYSTEM_SOURCE_IS_NULL);
		} else {
			IRpcProjectInfoService rpcTaskInfoService = null;
			if ("FMEA".equals(request.getSystemSource())) {
				rpcTaskInfoService = (IRpcProjectInfoService) SpringContextUtil.getBean("rpcProjectInfoForFMEAService");
				taskResponse = rpcTaskInfoService.getTaskInfo(request);
			} else if ("QA".equals(request.getSystemSource())) {
				rpcTaskInfoService = (IRpcProjectInfoService) SpringContextUtil.getBean("rpcProjectInfoForQAService");
				taskResponse = rpcTaskInfoService.getTaskInfo(request);
			} else {
				taskResponse.setIsOk(ErrorCode.FALSE);
				taskResponse.setErrorCode(ErrorCode.SYSTEMSOURCE_IS_ERROR);
			}
		}
		return taskResponse;
	}
}
