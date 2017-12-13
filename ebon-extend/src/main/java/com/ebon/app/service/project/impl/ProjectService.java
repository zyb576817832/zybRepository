package com.ebon.app.service.project.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebon.app.service.project.IProjectService;
import com.ebon.platform.dao.DaoException;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.service.ServiceException;
import com.ebon.platform.util.StringUtil;
import com.ebon.rpc.project.vo.ProjectInfo;
import com.ebon.rpc.project.vo.ProjectRequest;

@Service
public class ProjectService extends BaseService implements IProjectService {
	
	@Override
	public List<ProjectInfo> getProjectInfo(ProjectRequest request) throws ServiceException {
		List<ProjectInfo> list = null;
		try {
			list = myBatisDao.getList("projectMapper.getProjectInfo", request);
			log.info("com.ebon.app.project.service.impl.getProjectInfo执行时，查询到的数据条数为：" + list.size());
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return list;
	}
	
	@Override
	public Map<String, Object> getProjectInfoById(String projectId) throws ServiceException {
		Map<String, Object> map = null;
		List<Map<String, Object>> list = null;
		try {
			list = myBatisDao.getList("projectMapper.getProjectInfoById", projectId);
			if (StringUtil.isNotNull4List(list)) {
				map = list.get(0);
			}
			log.info("com.ebon.app.project.service.impl.getProjectInfoById执行时，查询到的Map为" + map);
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return map;
	}
	
	@Override
	public String getProjectIdByProjectShortName(String projectShortName) throws ServiceException {
		String projectId = null;
		try {
			projectId = (String) myBatisDao.getList("projectMapper.getProjectIdByProjectShortName", projectShortName).get(0);
			log.info("com.ebon.app.project.service.impl.getProjectInfoById执行时，查询到的项目ID为" + projectId);
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return projectId;
	}
	
}
