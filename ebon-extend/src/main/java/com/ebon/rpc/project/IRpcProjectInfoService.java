package com.ebon.rpc.project;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.ebon.rpc.BaseRpc;
import com.ebon.rpc.project.vo.ProjectRequest;
import com.ebon.rpc.project.vo.ProjectResponse;
import com.ebon.rpc.task.vo.TaskRequest;
import com.ebon.rpc.task.vo.TaskResponse;

@WebService
public interface IRpcProjectInfoService extends BaseRpc {
	
	/**
	 * 获取项目信息
	 * @param request
	 * @return
	 */
	public ProjectResponse getProjectInfo(@WebParam(name="request")ProjectRequest request);
	/**
	 * 获取Task信息
	 * @param request
	 * @return
	 */
	public TaskResponse getTaskInfo(@WebParam(name = "request") TaskRequest request);
}
