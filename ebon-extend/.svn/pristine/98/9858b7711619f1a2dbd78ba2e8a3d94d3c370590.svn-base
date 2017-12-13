package com.ebon.app.service.opl;

import java.util.List;
import java.util.Map;

import com.ebon.platform.service.ServiceException;
import com.ebon.rpc.opl.vo.OplInfo;
import com.ebon.rpc.opl.vo.OplListRequest;

public interface IOplService {
	
	/**
	 * 获取OPL信息列表
	 * 
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public List<OplInfo> getOplInfo(OplListRequest request) throws ServiceException;
	
	/**
	 * 根据项目编号和OPL名称获取OPL编号
	 * 
	 * @param paramMap
	 * @return
	 * @throws ServiceException
	 */
	public String getOplIdByProjectCodeAndOplName(Map<String, Object> paramMap) throws ServiceException;
	
	/**
	 * 获取此oplId是否存在在本系统
	 * 
	 * @param paramMap
	 * @return
	 * @throws ServiceException
	 */
	public boolean getOplIsExist(String oplId) throws ServiceException;
	
	/**
	 * 更新OPL信息
	 * 
	 * @param paramMap
	 * @return
	 * @throws ServiceException
	 */
	public boolean updateOplInfo(Map<String, Object> paramMap) throws ServiceException;
	
	/**
	 * 插入OPL信息
	 * 
	 * @param paramMap
	 * @return
	 * @throws ServiceException
	 */
	public boolean insertOplInfo(Map<String, Object> paramMap) throws ServiceException;
}
