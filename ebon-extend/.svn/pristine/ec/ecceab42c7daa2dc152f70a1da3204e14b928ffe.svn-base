package com.ebon.app.service.oplaction;

import java.util.List;
import java.util.Map;

import com.ebon.platform.service.ServiceException;
import com.ebon.rpc.oplaction.vo.OplActionInfo;
import com.ebon.rpc.oplaction.vo.OplActionListRequest;

public interface IOplActionService {
	
	public List<OplActionInfo> list(OplActionListRequest request) throws ServiceException;
	
	public boolean updateOplActionInfo(Map<String, Object> paramMap) throws ServiceException;
	
	public boolean insertOplActionInfo(Map<String, Object> paramMap) throws ServiceException;
	
	public String getOplActionIdByOplIdAndOplActionName(Map<String, Object> paramMap) throws ServiceException;
}
