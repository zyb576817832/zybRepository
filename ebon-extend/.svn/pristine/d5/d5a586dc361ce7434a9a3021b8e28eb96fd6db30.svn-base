package com.ebon.app.service.user;

import java.util.List;

import com.ebon.app.service.user.vo.User;
import com.ebon.platform.dao.pager.Page;
import com.ebon.platform.service.ServiceException;

public interface IUserService {
	
	/**
	 * 获取用户信息列表
	 * 
	 * @param page
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	public List<Object> getUser(Page page, User user) throws ServiceException;
	
	/**
	 * 根据用户姓名获取用户ID
	 * 
	 * @param userName
	 * @return
	 * @throws ServiceException
	 */
	public String getUserIdByUserName(String userName) throws ServiceException;
	
}
