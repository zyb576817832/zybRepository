package com.ebon.rpc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebon.platform.dao.DaoException;
import com.ebon.platform.service.BaseService;
import com.ebon.platform.service.ServiceException;
import com.ebon.rpc.error.Error;
@Service
public class BaseRpcService extends BaseService implements BaseRpc{
	
	@Override
	public String getErrorCodeInfo(Error error) throws ServiceException {
		List<String> list = null;
		String errorMessage = null;
		try {
			list = myBatisDao.getList("errorMapper.getErrorMessage",error);
			errorMessage = list.get(0);
			log.info("com.ebon.rpc.BaseRpcService执行时，查询到的数据为：" + errorMessage);
		} catch (DaoException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return error.getErrorCode() + ": " + errorMessage;
	}
	

}
