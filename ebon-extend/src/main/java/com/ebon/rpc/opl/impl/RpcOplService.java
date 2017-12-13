package com.ebon.rpc.opl.impl;

import org.springframework.stereotype.Service;

import com.ebon.platform.util.SpringContextUtil;
import com.ebon.platform.util.StringUtil;
import com.ebon.rpc.BaseRpcService;
import com.ebon.rpc.error.ErrorCode;
import com.ebon.rpc.opl.IRpcOplService;
import com.ebon.rpc.opl.vo.OplInsertRequest;
import com.ebon.rpc.opl.vo.OplInsertResponse;
import com.ebon.rpc.opl.vo.OplListRequest;
import com.ebon.rpc.opl.vo.OplListResponse;

@Service
public class RpcOplService extends BaseRpcService implements IRpcOplService {
	
	/**
	 * 查询OPL信息
	 */
	@Override
	public OplListResponse list(OplListRequest request) {
		OplListResponse oplListResponse = new OplListResponse();
		if (!StringUtil.isNotNull(request.getSystemSource())) {
			oplListResponse.setIsOk(ErrorCode.FALSE);
			oplListResponse.setErrorCode(ErrorCode.SYSTEM_SOURCE_IS_NULL);
		} else {
			IRpcOplService rpcOplService = null;
			if ("FMEA".equals(request.getSystemSource())) {
				rpcOplService = (IRpcOplService) SpringContextUtil.getBean("rpcOplForFMEAService");
				oplListResponse = rpcOplService.list(request);
			} else if ("QA".equals(request.getSystemSource())) {
				rpcOplService = (IRpcOplService) SpringContextUtil.getBean("rpcOplForQAService");
				oplListResponse = rpcOplService.list(request);
			} else {
				oplListResponse.setIsOk(ErrorCode.FALSE);
				oplListResponse.setErrorCode(ErrorCode.SYSTEMSOURCE_IS_ERROR);
			}
		}
		return oplListResponse;
	}
	
	/**
	 * 插入OPL信息
	 */
	@Override
	public OplInsertResponse insert(OplInsertRequest request) {
		OplInsertResponse oplInsertResponse = new OplInsertResponse();
		if (!StringUtil.isNotNull(request.getSystemSource())) {
			oplInsertResponse.setIsOk(ErrorCode.FALSE);
			oplInsertResponse.setErrorCode(ErrorCode.SYSTEM_SOURCE_IS_NULL);
		} else {
			IRpcOplService rpcOplService = null;
			if ("FMEA".equals(request.getSystemSource())) {
				rpcOplService = (IRpcOplService) SpringContextUtil.getBean("rpcOplForFMEAService");
				oplInsertResponse = rpcOplService.insert(request);
			} else if ("QA".equals(request.getSystemSource())) {
				rpcOplService = (IRpcOplService) SpringContextUtil.getBean("rpcOplForQAService");
				oplInsertResponse = rpcOplService.insert(request);
			} else {
				oplInsertResponse.setIsOk(ErrorCode.FALSE);
				oplInsertResponse.setErrorCode(ErrorCode.SYSTEMSOURCE_IS_ERROR);
			}
		}
		return oplInsertResponse;
	}
}