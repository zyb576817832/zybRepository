package com.ebon.v3.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebon.framework.vo.Message;
import com.ebon.framework.vo.Result;
import com.ebon.platform.dao.DaoException;
import com.ebon.platform.dao.pager.Page;
import com.ebon.platform.service.BaseService;
import com.ebon.v3.service.IOplHistoryService;
import com.ebon.v3.vo.OplHistory;
@Service
public class OplHitoryService extends BaseService implements  IOplHistoryService{

	@Override
	public Result add(OplHistory oplHistory) {
		
		Result result = new Result();
		try {
			if(this.myBatisDao.save("com.ebon.v3.oplhistory.add", oplHistory)){
				
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
	public List<OplHistory> getByOplId(String oplId,Page page) {
		
		List<OplHistory> list = null;
		
		try {
			list = this.myBatisDao.getList("com.ebon.v3.oplhistory.getByOplId",oplId,page);
		} catch (DaoException e) {
			log.error(e);
		}
		return list;
	}

	
}
