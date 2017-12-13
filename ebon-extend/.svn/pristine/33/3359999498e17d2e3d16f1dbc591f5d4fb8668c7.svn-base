package com.ebon.v3.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebon.framework.vo.Message;
import com.ebon.framework.vo.Result;
import com.ebon.platform.dao.DaoException;
import com.ebon.platform.dao.pager.Page;
import com.ebon.platform.service.BaseService;
import com.ebon.v3.service.IAppTaskDocService;
import com.ebon.v3.vo.AppTaskDoc;

@Service
public class AppTaskDocService extends BaseService implements
		IAppTaskDocService {
	
	private static String mapperDomain = "com.ebon.v3.appTaskDoc";

	@Override
	public Result add(AppTaskDoc doc) {
		Result result = new Result();
		try {
			if(this.myBatisDao.save(mapperDomain + ".add", doc)){
				result.setCode(Message.SUCCESS_CODE);
			}
		} catch (DaoException e) {
			result.setCode(Message.FAILED_CODE);
			result.setMsg(e.toString());
			log.error(e);
		}
		return result;
	}

	@Override
	public List<AppTaskDoc> getVlist(AppTaskDoc query, Page page) {
		List<AppTaskDoc> list = null;
		try {
			list = this.myBatisDao.getList(mapperDomain + ".getVList", query, page);
		} catch (DaoException e) {
			log.error(e);
		}
		return list;
	}

	@Override
	public Result delete(String[] ids, String updateUser) {
		Result result = new Result();
		try {
			Map<String, Object> params = new HashMap<String,Object>();
			params.put("ids", ids);
			params.put("updateUser", updateUser);
			if(this.myBatisDao.save(mapperDomain + ".delete", params)){
				result.setCode(Message.SUCCESS_CODE);
			}
		} catch (DaoException e) {
			result.setCode(Message.FAILED_CODE);
			result.setMsg(e.toString());
			log.error(e);
		}
		return result;
	}

}
