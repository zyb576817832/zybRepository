package com.ebon.app.service.nonproj;

import java.util.List;
import java.util.Map;

import com.ebon.platform.service.ServiceException;

public interface INonProjectService {
	
	/**
	 * 获得非项目报工信息列表
	 * 
	 * @param requestMap
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> getNonProjectInfo(Map<String, Object> requestMap) throws ServiceException;
	
	public Map<String, Object> getNonProjectInfoById(String projectId);
	
}
