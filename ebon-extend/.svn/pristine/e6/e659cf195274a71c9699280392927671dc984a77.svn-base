package com.ebon.rpc.oplaction;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.ebon.rpc.BaseRpc;
import com.ebon.rpc.oplaction.vo.OplActionInsertRequest;
import com.ebon.rpc.oplaction.vo.OplActionInsertResponse;
import com.ebon.rpc.oplaction.vo.OplActionListRequest;
import com.ebon.rpc.oplaction.vo.OplActionListResponse;

@WebService
public interface IRpcOplActionService extends BaseRpc {
	
	/**
	 * oplAction信息数据列表
	 * @param request
	 * @return
	 */
	public OplActionListResponse list(@WebParam(name="request")OplActionListRequest request);
	
	/**
	 * 添加oplAction数据
	 * @param request
	 * @return
	 */
	public OplActionInsertResponse  insert(@WebParam(name="request")OplActionInsertRequest request);

}
