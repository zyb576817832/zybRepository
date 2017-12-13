package com.ebon.v3.service;

import java.util.List;

import com.ebon.platform.dao.pager.Page;
import com.ebon.platform.service.ServiceException;
import com.ebon.v3.dto.VAppUserHours;
import com.ebon.v3.vo.AppUserHours;

public interface IAppUserHoursService {
	
	public String add(AppUserHours appUserHours) throws ServiceException;
	
	public String update(AppUserHours appUserHours) throws ServiceException;
	
	public List<VAppUserHours> getVList(VAppUserHours query, Page page) throws ServiceException;
	
	public List<AppUserHours> getList(VAppUserHours query) throws ServiceException;
	
	public String deleteByDate(String userId, String registDate) throws ServiceException;
	
	public String autoResigt(String userId, String registDate) throws ServiceException;
	
	/**
	 * 根据userid判断是否已经填报过工时
	 * @param userId
	 * @return
	 * @throws ServiceException 
	 */
	public List<VAppUserHours> getVByUserId(String userId) throws ServiceException;
	
	/**
	 * 批量定时填报工时（一人对应多任务）
	 * @return
	 */
	public String batchAdd(List<AppUserHours> appUserHours) throws ServiceException;
	
	/**
	 * 批量定时填报工时（多人对应无任务）
	 * @param userIds
	 * @return
	 * @throws ServiceException
	 */
	public String batchUserIdsAdd(List<String> userIds) throws ServiceException;

}
