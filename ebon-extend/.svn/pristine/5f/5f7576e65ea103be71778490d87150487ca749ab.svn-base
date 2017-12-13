package com.ebon.app.service.nonproj.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebon.app.service.nonproj.INonProjectService;
import com.ebon.platform.dao.DaoException;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.service.ServiceException;
import com.ebon.platform.util.StringUtil;

@Service
public class NonProjectService extends BaseService implements INonProjectService {
	
	@Override
	public List<Map<String, Object>> getNonProjectInfo(Map<String, Object> requestMap) throws ServiceException {
		List<Map<String, Object>> list = null;
		try {
			list = myBatisDao.getList("nonProjectMapper.getNonProjectTimesheet", requestMap);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return list;
	}
	
	@Override
	public Map<String, Object> getNonProjectInfoById(String projectInfoId) {
		Map<String, Object> map = null;
		List<Map<String, Object>> list = null;
		try {
			list = myBatisDao.getList("nonProjectMapper.getNonProjectInfoById", projectInfoId);
			if (StringUtil.isNotNull4List(list)) {
				map = list.get(0);
			}
			log.info("com.ebon.app.nonproj.service.impl.getNonProjectInfoById执行时，查询到的Map为" + map);
		} catch (DaoException e) {
			log.error(e);
			e.printStackTrace();
		}
		return map;
	}
	
}
