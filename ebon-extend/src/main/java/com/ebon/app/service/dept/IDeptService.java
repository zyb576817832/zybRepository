package com.ebon.app.service.dept;

import java.util.List;
import java.util.Map;

import com.ebon.platform.service.ServiceException;

public interface IDeptService {
	
	public List<Map<String, Object>> getListByFlag(String flag) throws ServiceException;
	
}
