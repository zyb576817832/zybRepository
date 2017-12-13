package com.ebon.rpc.opl;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.ebon.rpc.BaseRpc;
import com.ebon.rpc.opl.vo.OplInsertRequest;
import com.ebon.rpc.opl.vo.OplInsertResponse;
import com.ebon.rpc.opl.vo.OplListRequest;
import com.ebon.rpc.opl.vo.OplListResponse;

@WebService
public interface IRpcOplService extends BaseRpc{
	
	/**
	 * 获取OPL信息
	 * @param request
	 * @return
	 */
	public OplListResponse list(@WebParam(name="request")OplListRequest request);
	
	/**
	 * 插入OPL信息
	 * @param request
	 * @return
	 */
	public OplInsertResponse insert(@WebParam(name="request")OplInsertRequest request);
	
}
