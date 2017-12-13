package com.ebon.rpc.equipment;

import java.util.List;
import java.util.Map;

import com.ebon.platform.service.ServiceException;
import com.ebon.rpc.BaseRpc;

public interface IEquipmentService extends BaseRpc {
	
	/**
	 * 获得全部的设备信息
	 * 
	 * @param requestMap
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> getEquipmentInfo(Map<String, Object> requestMap) throws ServiceException;
	
	/**
	 * 获得NE3部门的设备信息
	 * 
	 * @param requestMap
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> getEquipmentInfoNE3(Map<String, Object> requestMap) throws ServiceException;
	
	/**
	 * 获得NE4部门的设备信息
	 * 
	 * @param requestMap
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> getEquipmentInfoNE4(Map<String, Object> requestMap) throws ServiceException;
	
	/**
	 * 将设备数据存放到CM_MAE_COST中
	 * 
	 * @throws ServiceException
	 */
	public void putEquipmentDataToTable() throws ServiceException;
	
}
