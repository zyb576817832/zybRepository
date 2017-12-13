package com.ebon.app.service.costcenter.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebon.app.service.costcenter.ICostCenterService;
import com.ebon.platform.dao.DaoException;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.service.ServiceException;

@Service
public class CostCenterService extends BaseService implements ICostCenterService {
	
	@Override
	public List<Map<String, Object>> getListByIsTEF(String istef) throws ServiceException {
		List<Map<String, Object>> list = null;
		try {
			list = myBatisDao.getList("app.service.costCenterMapper.getListByIsTEF", istef);
			log.info("com.ebon.app.service.costcenter.impl.CostCenterService.getListByIsTEF执行时,查到的记录数为" + list.size());
			
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return list;
	}
}
