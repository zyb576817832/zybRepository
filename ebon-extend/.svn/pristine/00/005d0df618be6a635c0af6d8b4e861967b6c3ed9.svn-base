package com.ebon.app.service.oplaction.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.ebon.app.service.oplaction.IOplActionService;
import com.ebon.platform.dao.DaoException;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.service.ServiceException;
import com.ebon.platform.util.StringUtil;
import com.ebon.rpc.oplaction.vo.OplActionInfo;
import com.ebon.rpc.oplaction.vo.OplActionListRequest;

@Service(value = "olActionListService2")
public class OplActionService extends BaseService implements IOplActionService {
	
	@Override
	public List<OplActionInfo> list(OplActionListRequest request) throws ServiceException {
		List<OplActionInfo> list = null;
		try {
			list = myBatisDao.getList("oplActionMap.getOplActionList", request);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return list;
	}
	
	@Override
	public String getOplActionIdByOplIdAndOplActionName(Map<String, Object> paramMap) throws ServiceException {
		List<String> list = null;
		String oplActionId = null;
		try {
			list = myBatisDao.getList("oplActionMap.getOplActionIdByOplIdAndOplActionName", paramMap);
			if (StringUtil.isNotNull4List(list)) {
				oplActionId = list.get(0);
				log.info("com.ebon.app.oplaction.service.impl.getOplActionIdByOplIdAndOplActionName执行时，查询到的oplActionId为：" + oplActionId);
			} else {
				log.info("com.ebon.app.oplaction.service.impl.getOplActionIdByOplIdAndOplActionName执行时，没有查询到oplActionId");
			}
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return oplActionId;
	}
	
	@Override
	public boolean insertOplActionInfo(Map<String, Object> paramMap) throws ServiceException {
		boolean flag = false;
		SqlSession session = null;
		List<String> respIdList = (List<String>) paramMap.get("respIdList");
		Map<String, Object> oplActionUserTaskParamMap = new HashMap<String, Object>();
		boolean flag4 = false;
		try {
			session = super.getSession();
			boolean flag1 = myBatisDao.save(session, "oplActionMap.insertOplActionInfo", paramMap);
			String oplActionId = myBatisDao.getOne(session, "oplActionMap.getOplActionIdByOplIdAndOplActionName", paramMap);
			if (StringUtil.isNotNull(oplActionId)) {
				myBatisDao.delete(session, "oplActionMap.deleteOplActionUserTask", oplActionId);
				oplActionUserTaskParamMap.put("oplActionId", oplActionId);
				if (StringUtil.isNotNull4List(respIdList)) {
					int count = 0;
					for (int i = 0; i < respIdList.size(); i++) {
						oplActionUserTaskParamMap.put("respId", respIdList.get(i));
						boolean flag3 = myBatisDao.save(session, "oplActionMap.insertOplActionUserTask", oplActionUserTaskParamMap);
						if (flag3) {
							count++;
						}
					}
					if (respIdList.size() == count) {
						flag4 = true;
					}
				} else {
					log.info("com.ebon.app.oplaction.service.impl.insertOplActionInfo执行时，有效执行人列表为空");
				}
			}
			if (flag1 && flag4) {
				flag = true;
			}
			session.commit();
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		} finally {
			super.closeSession(session);
		}
		return flag;
	}
	
	@Override
	public boolean updateOplActionInfo(Map<String, Object> paramMap) throws ServiceException {
		boolean flag = false;
		SqlSession session = null;
		List<String> respIdList = (List<String>) paramMap.get("respIdList");
		Map<String, Object> oplActionUserTaskParamMap = new HashMap<String, Object>();
		boolean flag4 = false;
		try {
			session = super.getSession();
			boolean flag1 = myBatisDao.update(session, "oplActionMap.updateOplActionInfo", paramMap);
			String oplActionId = myBatisDao.getOne(session, "oplActionMap.getOplActionIdByOplIdAndOplActionName", paramMap);
			if (StringUtil.isNotNull(oplActionId)) {
				myBatisDao.delete(session, "oplActionMap.deleteOplActionUserTask", oplActionId);
				oplActionUserTaskParamMap.put("oplActionId", oplActionId);
				if (StringUtil.isNotNull4List(respIdList)) {
					int count = 0;
					for (int i = 0; i < respIdList.size(); i++) {
						oplActionUserTaskParamMap.put("respId", respIdList.get(i));
						boolean flag3 = myBatisDao.save(session, "oplActionMap.insertOplActionUserTask", oplActionUserTaskParamMap);
						if (flag3) {
							count++;
						}
					}
					if (respIdList.size() == count) {
						flag4 = true;
					}
				} else {
					log.info("com.ebon.app.oplaction.service.impl.updateOplActionInfo执行时，有效执行人列表为空");
				}
			}
			if (flag1 && flag4) {
				flag = true;
			}
			session.commit();
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		} finally {
			super.closeSession(session);
		}
		return flag;
	}
}
