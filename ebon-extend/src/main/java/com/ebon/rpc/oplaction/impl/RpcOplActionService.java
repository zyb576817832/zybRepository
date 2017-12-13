package com.ebon.rpc.oplaction.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebon.app.service.opl.IOplService;
import com.ebon.app.service.oplaction.IOplActionService;
import com.ebon.app.service.user.IUserService;
import com.ebon.platform.service.ServiceException;
import com.ebon.platform.util.Guid;
import com.ebon.platform.util.StringUtil;
import com.ebon.rpc.BaseRpcService;
import com.ebon.rpc.error.ErrorCode;
import com.ebon.rpc.oplaction.IRpcOplActionService;
import com.ebon.rpc.oplaction.vo.OplActionInfo;
import com.ebon.rpc.oplaction.vo.OplActionInsertRequest;
import com.ebon.rpc.oplaction.vo.OplActionInsertResponse;
import com.ebon.rpc.oplaction.vo.OplActionListRequest;
import com.ebon.rpc.oplaction.vo.OplActionListResponse;
import com.ebon.rpc.oplaction.vo.RtnOplActionInfo;

@Service
public class RpcOplActionService extends BaseRpcService implements IRpcOplActionService {
	
	@Autowired
	private IOplActionService	oplActionListService;
	
	@Autowired
	private IOplService			oplService;
	
	@Resource
	private IUserService		userService;
	
	@Override
	public OplActionListResponse list(OplActionListRequest request) {
		OplActionListResponse response = new OplActionListResponse();
		String systemSource = request.getSystemSource();
		if (!StringUtil.isNotNull(systemSource)) {
			response.setIsOk(ErrorCode.FALSE);
			response.setErrorCode(ErrorCode.SYSTEM_SOURCE_IS_NULL);
		} else {
			if ("FMEA".equals(systemSource) || "QA".equals(systemSource)) {
				response = getOplActionList(request, response);
			} else {
				response.setIsOk(ErrorCode.FALSE);
				response.setErrorCode(ErrorCode.SYSTEMSOURCE_IS_ERROR);
			}
		}
		return response;
	}
	
	/**
	 * 获得oplAction信息数据
	 * 
	 * @param request
	 * @param response
	 */
	private OplActionListResponse getOplActionList(OplActionListRequest request, OplActionListResponse response) {
		String oplSource = request.getOplSource();
		if (StringUtil.isNotNull(oplSource)) {
			List<OplActionInfo> list = null;
			try {
				list = oplActionListService.list(request);
				if (StringUtil.isNotNull4List(list)) {
					OplActionInfo[] infos = new OplActionInfo[list.size()];
					list.toArray(infos);
					response.setOplActionInfos(infos);
					response.setIsOk(ErrorCode.TRUE);
				} else {
					OplActionInfo[] infos = new OplActionInfo[0];
					response.setOplActionInfos(infos);
					response.setIsOk(ErrorCode.FALSE);
					response.setErrorCode(ErrorCode.DATA_IS_NULL);
				}
			} catch (Exception e) {
				response.setIsOk(ErrorCode.FALSE);
				response.setErrorCode(ErrorCode.PMS_IS_ERROR);
			}
		} else {
			response.setIsOk(ErrorCode.FALSE);
			response.setErrorCode(ErrorCode.OPLSOURCE_IS_NULL);
		}
		return response;
	}
	
	@Override
	public OplActionInsertResponse insert(OplActionInsertRequest request) {
		OplActionInsertResponse response = new OplActionInsertResponse();
		String systemSource = request.getSystemSource();
		if (!StringUtil.isNotNull(systemSource)) {
			response.setIsOk(ErrorCode.FALSE);
			response.setErrorCode(ErrorCode.SYSTEM_SOURCE_IS_NULL);
		} else {
			if ("FMEA".equals(systemSource) || "QA".equals(systemSource)) {
				OplActionInfo[] oplActionInfos = request.getOplActionInfos();
				if (null == oplActionInfos && oplActionInfos.length == 0) {
					response.setIsOk(ErrorCode.FALSE);
					response.setErrorCode(ErrorCode.PARAMETER_ARRAY_IS_NULL);
				} else {
					
					try {
						RtnOplActionInfo[] rtnOplActionInfos = savaOplActionInsert(oplActionInfos);
						response.setRtnOplActionInfos(rtnOplActionInfos);
						response.setIsOk(ErrorCode.TRUE);
					} catch (Exception e) {
						response.setIsOk(ErrorCode.FALSE);
						response.setErrorCode(ErrorCode.PMS_IS_ERROR);
					}
				}
			} else {
				response.setIsOk(ErrorCode.FALSE);
				response.setErrorCode(ErrorCode.SYSTEMSOURCE_IS_ERROR);
			}
			
		}
		return response;
	}
	
	/**
	 * oplAction数据的添加
	 * 
	 * @param oplActionInfos
	 * @return
	 * @throws ServiceException
	 */
	private RtnOplActionInfo[] savaOplActionInsert(OplActionInfo[] oplActionInfos) throws ServiceException {
		List<RtnOplActionInfo> rtnOplActionInfoList = new ArrayList<RtnOplActionInfo>();
		for (int i = 0; i < oplActionInfos.length; i++) {
			RtnOplActionInfo rtnOplActionInfo = new RtnOplActionInfo();
			OplActionInfo oplActionInfo = oplActionInfos[i];
			String oplId = oplActionInfo.getOplId();
			String[] respNames = oplActionInfo.getRespNames();
			String targetStartDate = oplActionInfo.getTargetStartDate();
			String targetEndDate = oplActionInfo.getTargetEndDate();
			String aclStartDate = oplActionInfo.getAclStartDate();
			String aclEndDate = oplActionInfo.getAclEndDate();
			String oritargetEndDate = oplActionInfo.getOritargetEndDate();
			if (!StringUtil.isNotNull(oplId)) {
				rtnOplActionInfo = setErrorInfo(rtnOplActionInfo, oplActionInfo, oplId, ErrorCode.FALSE, ErrorCode.OPLID_IS_NULL);
				rtnOplActionInfoList.add(rtnOplActionInfo);
			} else if (!StringUtil.isNotNull(oplActionInfo.getActionName())) {
				rtnOplActionInfo = setErrorInfo(rtnOplActionInfo, oplActionInfo, oplId, ErrorCode.FALSE, ErrorCode.OPLACTIONNAME_IS_NULL);
				rtnOplActionInfoList.add(rtnOplActionInfo);
			} else if (null == respNames || respNames.length == 0) {
				rtnOplActionInfo = setErrorInfo(rtnOplActionInfo, oplActionInfo, oplId, ErrorCode.FALSE, ErrorCode.RESPNAMEARRAY_IS_NULL);
				rtnOplActionInfoList.add(rtnOplActionInfo);
			} else {
				Pattern p = Pattern.compile("^\\d{1,4}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[1,2][0-9]|3[0,1]|[1-9])$");
				if (StringUtil.isNotNull(targetStartDate) && !p.matcher(targetStartDate.trim()).find()) {
					rtnOplActionInfo = setErrorInfo(rtnOplActionInfo, oplActionInfo, oplId, ErrorCode.FALSE, ErrorCode.TARGETSTARTDATE_IS_ERROR);
					rtnOplActionInfoList.add(rtnOplActionInfo);
				} else if (StringUtil.isNotNull(targetEndDate) && !p.matcher(targetEndDate.trim()).find()) {
					rtnOplActionInfo = setErrorInfo(rtnOplActionInfo, oplActionInfo, oplId, ErrorCode.FALSE, ErrorCode.TARGETENDDATE_IS_ERROR);
					rtnOplActionInfoList.add(rtnOplActionInfo);
				} else if (StringUtil.isNotNull(aclStartDate) && !p.matcher(aclStartDate.trim()).find()) {
					rtnOplActionInfo = setErrorInfo(rtnOplActionInfo, oplActionInfo, oplId, ErrorCode.FALSE, ErrorCode.ACLSTARTDATE_IS_ERROR);
					rtnOplActionInfoList.add(rtnOplActionInfo);
				} else if (StringUtil.isNotNull(aclEndDate) && !p.matcher(aclEndDate.trim()).find()) {
					rtnOplActionInfo = setErrorInfo(rtnOplActionInfo, oplActionInfo, oplId, ErrorCode.FALSE, ErrorCode.ACLENDDATE_IS_ERROR);
					rtnOplActionInfoList.add(rtnOplActionInfo);
				} else if (StringUtil.isNotNull(oritargetEndDate) && !p.matcher(oritargetEndDate.trim()).find()) {
					rtnOplActionInfo = setErrorInfo(rtnOplActionInfo, oplActionInfo, oplId, ErrorCode.FALSE, ErrorCode.ORITARGETENDDATE_IS_ERROR);
					rtnOplActionInfoList.add(rtnOplActionInfo);
				} else {
					if (oplService.getOplIsExist(oplId)) {
						String oplActionId = oplActionInfo.getOplActionId();
						int respCount = 0; // action执行人在我们数据库的存在个数
						List<String> respIdList = new ArrayList<String>();
						for (int j = 0; j < respNames.length; j++) {
							String respName = respNames[j];
							String respId = userService.getUserIdByUserName(respName);
							if (StringUtil.isNotNull(respId)) {
								respIdList.add(respId);
							}
						}
						if (respIdList.size() == respNames.length && respIdList.size() != 0 && respNames.length != 0) {
							Map<String, Object> paramMap = putOplActionInfoToMap(oplActionInfo, oplId, respIdList, true);
							boolean flag = oplActionListService.updateOplActionInfo(paramMap);
							if (!StringUtil.isNotNull(oplActionId)) {
								paramMap = putOplActionInfoToMap(oplActionInfo, oplId, respIdList, false);
							}
							if (!flag) {
								oplActionListService.insertOplActionInfo(paramMap);
							}
							if (!StringUtil.isNotNull(oplActionId)) {
								Map<String, Object> oplActionIdRequestMap = new HashMap<String, Object>();
								oplActionIdRequestMap.put("oplId", oplId);
								oplActionIdRequestMap.put("actionName", oplActionInfo.getActionName());
								//此处还需要构建查询参数,对OPLACTIONID进行查询提取,放如到OPLACTIONINFO中
								oplActionInfo.setOplActionId(oplActionListService.getOplActionIdByOplIdAndOplActionName(oplActionIdRequestMap));
							}
							rtnOplActionInfo = setErrorInfo(rtnOplActionInfo, oplActionInfo, oplId, ErrorCode.TRUE, null);
							rtnOplActionInfoList.add(rtnOplActionInfo);
						} else {
							rtnOplActionInfo = setErrorInfo(rtnOplActionInfo, oplActionInfo, oplId, ErrorCode.FALSE, ErrorCode.RESPNAME_IS_NOT_EXIST);
							rtnOplActionInfoList.add(rtnOplActionInfo);
						}
					} else {
						rtnOplActionInfo = setErrorInfo(rtnOplActionInfo, oplActionInfo, oplId, ErrorCode.FALSE, ErrorCode.OPLID_IS_NULL);
						rtnOplActionInfoList.add(rtnOplActionInfo);
					}
				}
			}
		}
		RtnOplActionInfo[] rtnOplActionInfos = new RtnOplActionInfo[rtnOplActionInfoList.size()];
		rtnOplActionInfoList.toArray(rtnOplActionInfos);
		return rtnOplActionInfos;
	}
	
	private Map<String, Object> putOplActionInfoToMap(OplActionInfo oplActionInfo, String oplId, List<String> respIdList, boolean flag) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("oplId", oplId);
		if (flag) {
			paramMap.put("oplActionId", oplActionInfo.getOplActionId());
		} else {
			paramMap.put("oplActionId", Guid.getGuid()); // 此处需要设置uuid
		}
		paramMap.put("actionName", oplActionInfo.getActionName());
		paramMap.put("targetStartDate", oplActionInfo.getTargetStartDate());
		paramMap.put("targetEndDate", oplActionInfo.getTargetEndDate());
		paramMap.put("aclStartDate", oplActionInfo.getAclStartDate());
		paramMap.put("aclEndDate", oplActionInfo.getAclEndDate());
		if (StringUtil.isNotNull(oplActionInfo.getOritargetEndDate())) {
			paramMap.put("oritargetEndDate", oplActionInfo.getOritargetEndDate());
		} else {
			paramMap.put("oritargetEndDate", oplActionInfo.getTargetEndDate());
		}
		paramMap.put("respIdList", respIdList);
		paramMap.put("status", oplActionInfo.getStatus());
		return paramMap;
	}
	
	/**
	 * 获得返回的oplaction信息
	 * 
	 * @param oplActionInfos
	 * @return
	 */
	private RtnOplActionInfo setErrorInfo(RtnOplActionInfo rtnOplActionInfo, OplActionInfo oplActionInfo, String oplId, String status, String errorCode) {
		rtnOplActionInfo.setStatus(status);
		rtnOplActionInfo.setOplActionId(oplActionInfo.getOplActionId());
		rtnOplActionInfo.setActionName(oplActionInfo.getActionName());
		rtnOplActionInfo.setOplId(oplId);
		rtnOplActionInfo.setErrorCode(errorCode);
		return rtnOplActionInfo;
	}
	
	public IOplActionService getOplActionListService() {
		return oplActionListService;
	}
	
	public void setOplActionListService(IOplActionService oplActionListService) {
		this.oplActionListService = oplActionListService;
	}
	
	public IUserService getUserService() {
		return userService;
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	public IOplService getOplService() {
		return oplService;
	}
	
	public void setOplService(IOplService oplService) {
		this.oplService = oplService;
	}
	
}
