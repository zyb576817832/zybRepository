package com.ebon.rpc.sap.service;

import java.util.List;
import java.util.Map;

import com.ebon.platform.service.ServiceException;

public interface IThreeFeeService {
	
	public void getThreeFee();
	
	public void getThreeFeeAllData() throws ServiceException;
	
	/**
	 * 获得项目sap报工数据信息
	 * 
	 * @param requestMap
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> getSAPInfo(Map<String, Object> requestMap) throws ServiceException;
	
	/**
	 * 获得非项目sap报工数据信息
	 * 
	 * @param requestMap
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> getSAPInfoForNonProject(Map<String, Object> requestMap) throws ServiceException;
	
}
