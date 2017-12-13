package com.ebon.app.service.tef;

import java.util.List;
import java.util.Map;

import com.ebon.app.service.tef.vo.TefInfo;
import com.ebon.platform.dao.pager.Page;
import com.ebon.platform.service.ServiceException;

public interface ITefService {
	
	public List<TefInfo> getPageList(Page page, TefInfo info) throws ServiceException;
	
	public TefInfo getTefById(String tefId) throws ServiceException;
	
	public boolean insert(TefInfo info) throws ServiceException;
	
	public boolean update(TefInfo info) throws ServiceException;
	
	public boolean delete(String tefId) throws ServiceException;
	
	/**
	 * 获得Tef报工信息列表
	 * 
	 * @param paramMap
	 * @return
	 * @throws ServiceException
	 */
	List<Map<String, Object>> getTefInfo(Map<String, Object> paramMap) throws ServiceException;
	
}
