package com.ebon.app.service.cost.person.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebon.app.service.cost.person.IPersonService;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.service.ServiceException;

@Service
public class PersonService extends BaseService implements IPersonService {
	
	@Override
	public List<Map<String, Object>> getPersonelInfo(Map<String, Object> requestMap) throws ServiceException {
		List<Map<String, Object>> list = null;
		try {
			list = myBatisDao.getList("personCostMapper.getPersonelInfo", requestMap);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return list;
	}
	
}
