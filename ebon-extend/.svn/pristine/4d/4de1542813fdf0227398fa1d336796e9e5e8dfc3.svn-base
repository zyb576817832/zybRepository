package com.ebon.rpc.task;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.ebon.rpc.BaseRpc;
import com.ebon.rpc.task.vo.TaskRequest;
import com.ebon.rpc.task.vo.TaskResponse;

@WebService
public interface IRpcTaskInfoService extends BaseRpc {
	
	/**
	 * 获取Task信息
	 * @param request
	 * @return
	 */
	public TaskResponse getTaskInfo(@WebParam(name = "request") TaskRequest request);
	
}
