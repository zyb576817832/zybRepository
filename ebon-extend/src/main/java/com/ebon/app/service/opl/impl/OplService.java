package com.ebon.app.service.opl.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.ebon.app.service.opl.IOplService;
import com.ebon.platform.dao.DaoException;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.service.ServiceException;
import com.ebon.platform.util.StringUtil;
import com.ebon.rpc.opl.vo.OplInfo;
import com.ebon.rpc.opl.vo.OplListRequest;

@Service
public class OplService extends BaseService implements IOplService {
	
	@Override
	public List<OplInfo> getOplInfo(OplListRequest request) throws ServiceException {
		List<OplInfo> list = null;
		try {
			list = myBatisDao.getList("oplMapper.getOplInfo", request);
			log.info("com.ebon.app.opl.service.impl.getOplInfo执行时，查询到的数据条数为：" + list.size());
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return list;
	}
	
	@Override
	public String getOplIdByProjectCodeAndOplName(Map<String, Object> paramMap) throws ServiceException {
		List<String> list = new ArrayList<String>();
		String oplId = null;
		try {
			list = myBatisDao.getList("oplMapper.getOplIdByProjectCodeAndOplName", paramMap);
			oplId = list.get(0);
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return oplId;
	}
	
	@Override
	public boolean updateOplInfo(Map<String, Object> paramMap) throws ServiceException {
		boolean flag = false;
		SqlSession session = null;
		try {
			session = super.getSession();
			flag = myBatisDao.update(session, "oplMapper.updateOplInfo", paramMap);
			session.commit();
			log.info("com.ebon.app.opl.service.impl.updateOplInfo执行时，更新结果为：" + flag);
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		} finally {
			super.closeSession(session);
		}
		return flag;
	}
	
	@Override
	public boolean insertOplInfo(Map<String, Object> paramMap) throws ServiceException {
		boolean flag = false;
		SqlSession session = null;
		try {
			session = super.getSession();
			flag = myBatisDao.save(session, "oplMapper.insertOplInfo", paramMap);
			log.info("com.ebon.app.opl.service.impl.insertOplInfo执行时，插入结果为：" + flag);
			session.commit();
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		} finally {
			super.closeSession(session);
		}
		return flag;
	}
	
	@Override
	public boolean getOplIsExist(String oplId) throws ServiceException {
		boolean flag = false;
		List<String> list = null;
		try {
			list = myBatisDao.getList("oplMapper.getOplIsExist", oplId);
			if (StringUtil.isNotNull4List(list)) {
				flag = true;
				log.info("com.ebon.app.opl.service.impl.getOplIsExist执行时，oplId:" + oplId + "在库中存在");
			} else {
				log.info("com.ebon.app.opl.service.impl.getOplIsExist执行时，oplId:" + oplId + "在库中不存在");
			}
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return flag;
	}
	
}
