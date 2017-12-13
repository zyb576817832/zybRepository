package com.ebon.app.service.dict.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebon.app.service.dict.IDictionaryService;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.service.ServiceException;

@Service
public class DictionaryService extends BaseService implements IDictionaryService {
	
	@Override
	public List<Map<String, Object>> getList(String type) throws ServiceException {
		List<Map<String, Object>> listData = null;
		try {
			listData = super.myBatisDao.getList("app.dictMapper.getListByType", type);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException();
		}
		return listData;
	}
	
	@Override
	public List<Map<String, Object>> getList(String type, String remark) throws ServiceException {
		List<Map<String, Object>> listData = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("type", type);
			paramMap.put("remark", remark);
			listData = super.myBatisDao.getList("app.dictMapper.getListByTypeAndRemark", paramMap);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException();
		}
		return listData;
	}
	
}
