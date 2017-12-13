package com.ebon.app.service.dept.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebon.app.service.dept.IDeptService;
import com.ebon.platform.dao.DaoException;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.service.ServiceException;

@Service
public class DeptService extends BaseService implements IDeptService {
	
	@Override
	public List<Map<String, Object>> getListByFlag(String flag) throws ServiceException {
		List<Map<String, Object>> listData = null;
		try {
			listData = super.myBatisDao.getList("app.deptMapper.getListByFlag", flag);
		} catch (DaoException e) {
			log.error(e);
		}
		return listData;
	}
	
}
