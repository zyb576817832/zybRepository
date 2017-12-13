package com.ebon.app.service.task.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebon.app.service.task.ITaskService;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.service.ServiceException;
import com.ebon.rpc.task.vo.TaskInfo;
import com.ebon.rpc.task.vo.TaskRequest;

@Service
public class TaskService extends BaseService implements ITaskService {
	
	@Override
	public List<TaskInfo> getTaskInfo(TaskRequest request) throws ServiceException {
		List<TaskInfo> list = null;
		try {
			list = myBatisDao.getList("taskMapper.getTaskInfo", request);
			log.info("com.ebon.app.task.service.impl.getTaskInfo执行时，查询到的数据条数为：" + list.size());
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return list;
	}
	
	@Override
	public List<TaskInfo> getTaskInfoForQA(TaskRequest request) throws ServiceException {
		List<TaskInfo> list = null;
		try {
			list = myBatisDao.getList("taskMapper.getTaskInfoForQA", request);
			log.info("com.ebon.app.task.service.impl.getTaskInfoForQA执行时，查询到的数据条数为：" + list.size());
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return list;
	}
	
}
