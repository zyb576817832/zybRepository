package com.ebon.app.service.project;

import java.util.List;
import java.util.Map;

import com.ebon.platform.service.ServiceException;
import com.ebon.rpc.project.vo.ProjectInfo;
import com.ebon.rpc.project.vo.ProjectRequest;

public interface IProjectService {
	
	/**
	 * 获取项目信息列表
	 * 
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public List<ProjectInfo> getProjectInfo(ProjectRequest request) throws ServiceException;
	
	/**
	 * 根据项目编号获取项目信息
	 * 
	 * @param projectId
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> getProjectInfoById(String projectId) throws ServiceException;
	
	/**
	 * 根据项目编号获取项目ID
	 * 
	 * @param projectShortName
	 * @return
	 * @throws ServiceException
	 */
	public String getProjectIdByProjectShortName(String projectShortName) throws ServiceException;
	
}
