package com.ebon.rpc;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.ebon.platform.service.ServiceException;
import com.ebon.rpc.error.Error;

@WebService
public interface BaseRpc {
	/**
	 * 通过Error对象获取error信息
	 * @param error
	 * @return
	 * @throws ServiceException
	 */
	public String getErrorCodeInfo(@WebParam(name="error") Error error) throws ServiceException;

}
