package com.ebon.app.service.user.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebon.app.service.user.IUserService;
import com.ebon.app.service.user.vo.User;
import com.ebon.platform.dao.DaoException;
import com.ebon.platform.dao.pager.Page;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.service.ServiceException;
import com.ebon.platform.util.StringUtil;

@Service
public class UserService extends BaseService implements IUserService {
	
	@Override
	public List<Object> getUser(Page page, User user) throws ServiceException {
		List<Object> dataList = null;
		try {
			dataList = super.myBatisDao.getList("UserMapper.getUser", user, page);
			log.info("com.ebon.app.opl.service.impl.getOplInfo执行时，查询到的数据条数为:" + dataList.size());
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return dataList;
	}
	
	@Override
	public String getUserIdByUserName(String userName) throws ServiceException {
		List<String> list = null;
		String userId = null;
		try {
			list = myBatisDao.getList("UserMapper.getUserIdByUserName", userName);
			if (StringUtil.isNotNull4List(list)) {
				userId = list.get(0);
				log.info("com.ebon.app.opl.service.impl.getUserIdByUserName执行时，查询到的用户ID为:" + userId);
			} else {
				log.info("com.ebon.app.opl.service.impl.getUserIdByUserName执行时，没有查到用户:" + userName);
			}
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return userId;
	}
	
}
