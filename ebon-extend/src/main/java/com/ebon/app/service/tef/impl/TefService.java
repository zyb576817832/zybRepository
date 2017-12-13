package com.ebon.app.service.tef.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.ebon.app.service.tef.ITefService;
import com.ebon.app.service.tef.vo.TefInfo;
import com.ebon.platform.dao.DaoException;
import com.ebon.platform.dao.pager.Page;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.service.ServiceException;

@Service
public class TefService extends BaseService implements ITefService {
	
	/**
	 * 得到分页后的数据，包含查询条件
	 * 
	 * @param page
	 *        分页参数
	 * @param info
	 *        查询条件
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<TefInfo> getPageList(Page page, TefInfo info) throws ServiceException {
		List<TefInfo> listData = null;
		try {
			listData = super.myBatisDao.getList("app.tefMapper.list", info, page);
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return listData;
	}
	
	@Override
	public TefInfo getTefById(String tefId) throws ServiceException {
		TefInfo info = null;
		try {
			info = super.myBatisDao.getOne("app.tefMapper.getTefById", tefId);
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return info;
	}
	
	@Override
	public boolean insert(TefInfo info) throws ServiceException {
		boolean b = false;
		SqlSession session = null;
		try {
			session = super.getSession();
			b = super.myBatisDao.save(session, "app.tefMapper.insert", info);
			session.commit();
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		} finally {
			super.closeSession(session);
		}
		return b;
	}
	
	@Override
	public boolean update(TefInfo info) throws ServiceException {
		boolean b = false;
		SqlSession session = null;
		try {
			session = super.getSession();
			b = super.myBatisDao.update(session, "app.tefMapper.update", info);
			session.commit();
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		} finally {
			super.closeSession(session);
		}
		return b;
	}
	
	@Override
	public boolean delete(String tefId) throws ServiceException {
		boolean b = false;
		SqlSession session = null;
		try {
			session = super.getSession();
			b = super.myBatisDao.delete(session, "app.tefMapper.delete", tefId);
			session.commit();
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		} finally {
			super.closeSession(session);
		}
		return b;
	}
	
	@Override
	public List<Map<String, Object>> getTefInfo(Map<String, Object> paramMap) throws ServiceException {
		List<Map<String, Object>> list = null;
		try {
			list = myBatisDao.getList("app.tefMapper.getTefCost", paramMap);
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return list;
	}
	
}
