package com.ebon.rpc.task.impl;

import org.springframework.stereotype.Service;

import com.ebon.platform.util.SpringContextUtil;
import com.ebon.platform.util.StringUtil;
import com.ebon.rpc.BaseRpcService;
import com.ebon.rpc.error.ErrorCode;
import com.ebon.rpc.task.IRpcTaskInfoService;
import com.ebon.rpc.task.vo.TaskRequest;
import com.ebon.rpc.task.vo.TaskResponse;

@Service
public class RpcTaskInfoService extends BaseRpcService implements IRpcTaskInfoService {
	
	@Override
	public TaskResponse getTaskInfo(TaskRequest request) {
		TaskResponse taskResponse = new TaskResponse();
		if (!StringUtil.isNotNull(request.getSystemSource())) {
			taskResponse.setIsOk(ErrorCode.FALSE);
			taskResponse.setErrorCode(ErrorCode.SYSTEM_SOURCE_IS_NULL);
		} else {
			IRpcTaskInfoService rpcTaskInfoService = null;
			if ("FMEA".equals(request.getSystemSource())) {
				rpcTaskInfoService = (IRpcTaskInfoService) SpringContextUtil.getBean("rpcTaskInfoForFMEAService");
				taskResponse = rpcTaskInfoService.getTaskInfo(request);
			} else if ("QA".equals(request.getSystemSource())) {
				rpcTaskInfoService = (IRpcTaskInfoService) SpringContextUtil.getBean("rpcTaskInfoForQAService");
				taskResponse = rpcTaskInfoService.getTaskInfo(request);
			} else {
				taskResponse.setIsOk(ErrorCode.FALSE);
				taskResponse.setErrorCode(ErrorCode.SYSTEMSOURCE_IS_ERROR);
			}
		}
		return taskResponse;
	}
	
}
