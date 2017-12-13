package com.ebon.rpc.opl.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebon.app.service.opl.IOplService;
import com.ebon.app.service.project.IProjectService;
import com.ebon.app.service.user.IUserService;
import com.ebon.platform.util.Guid;
import com.ebon.platform.util.StringUtil;
import com.ebon.rpc.BaseRpcService;
import com.ebon.rpc.error.ErrorCode;
import com.ebon.rpc.opl.IRpcOplService;
import com.ebon.rpc.opl.vo.OplInfo;
import com.ebon.rpc.opl.vo.OplInsertRequest;
import com.ebon.rpc.opl.vo.OplInsertResponse;
import com.ebon.rpc.opl.vo.OplListRequest;
import com.ebon.rpc.opl.vo.OplListResponse;
import com.ebon.rpc.opl.vo.RtnOplInfo;

@Service
public class RpcOplForQAService extends BaseRpcService implements IRpcOplService {
	
	@Autowired
	private IOplService		oplService;
	
	@Autowired
	private IProjectService	projectService;
	
	@Resource
	private IUserService	userService;
	
	@Override
	public OplListResponse list(OplListRequest request) {
		OplListResponse response = new OplListResponse();
		if (!StringUtil.isNotNull(request.getOplSource())) {
			response.setIsOk(ErrorCode.FALSE);
			response.setErrorCode(ErrorCode.OPLSOURCE_IS_NULL);
		} else {
			try {
				List<OplInfo> list = null;
				list = oplService.getOplInfo(request);
				if (StringUtil.isNotNull4List(list)) {
					OplInfo[] infos = new OplInfo[list.size()];
					list.toArray(infos);
					response.setOplInfos(infos);
					response.setIsOk(ErrorCode.TRUE);
				} else {
					OplInfo[] infos = new OplInfo[0];
					response.setOplInfos(infos);
					response.setIsOk(ErrorCode.FALSE);
					response.setErrorCode(ErrorCode.DATA_IS_NULL);
				}
			} catch (Exception e) {
				response.setIsOk(ErrorCode.FALSE);
				response.setErrorCode(ErrorCode.PMS_IS_ERROR);
			}
		}
		return response;
	}
	
	@Override
	public OplInsertResponse insert(OplInsertRequest request) {
		OplInsertResponse response = new OplInsertResponse();
		List<RtnOplInfo> rtnOplInfoList = new ArrayList<RtnOplInfo>();
		OplInfo[] oplInfos = request.getOplInfos();
		if (null == oplInfos || oplInfos.length == 0) {
			response.setIsOk(ErrorCode.FALSE);
			response.setErrorCode(ErrorCode.PARAMETER_ARRAY_IS_NULL);
		} else {
			try {
				for (int i = 0; i < oplInfos.length; i++) {
					RtnOplInfo rtnOplInfo = new RtnOplInfo();
					OplInfo oplInfo = oplInfos[i];
					String projectCode = oplInfo.getProjectCode();
					String createDate = oplInfo.getCreateDate();
					if (!StringUtil.isNotNull(projectCode)) { //对项目编号进行判空
						rtnOplInfo = setErrorInfo(rtnOplInfo, oplInfo, projectCode, ErrorCode.FALSE, ErrorCode.PROJECTCODE_IS_NULL);
						rtnOplInfoList.add(rtnOplInfo);
					} else if (!StringUtil.isNotNull(oplInfo.getOplName())) {
						rtnOplInfo = setErrorInfo(rtnOplInfo, oplInfo, projectCode, ErrorCode.FALSE, ErrorCode.OPLNAME_IS_NULL);
						rtnOplInfoList.add(rtnOplInfo);
					} else if (null == oplInfo.getRespNames() || oplInfo.getRespNames().length == 0) {
						rtnOplInfo = setErrorInfo(rtnOplInfo, oplInfo, projectCode, ErrorCode.FALSE, ErrorCode.RESPNAMEARRAY_IS_NULL);
						rtnOplInfoList.add(rtnOplInfo);
					} else if (!StringUtil.isNotNull(oplInfo.getSource())) {
						rtnOplInfo = setErrorInfo(rtnOplInfo, oplInfo, projectCode, ErrorCode.FALSE, ErrorCode.OPLSOURCE_IS_NULL);
						rtnOplInfoList.add(rtnOplInfo);
					} else {
						Pattern p = Pattern.compile("^\\d{1,4}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[1,2][0-9]|3[0,1]|[1-9])$");
						if (StringUtil.isNotNull(createDate) && !p.matcher(createDate.trim()).find()) {
							rtnOplInfo = setErrorInfo(rtnOplInfo, oplInfo, projectCode, ErrorCode.FALSE, ErrorCode.CREATEDATE_IS_ERROR);
							rtnOplInfoList.add(rtnOplInfo);
						} else {
							String projectInfoId = projectService.getProjectIdByProjectShortName(projectCode); //获得projectId
							if (StringUtil.isNotNull(projectInfoId)) { //如果项目编号在系统存在
								String oplId = oplInfo.getOplId();
								String[] userNames = oplInfo.getRespNames();
								for (int j = 0; j < userNames.length; j++) {
									//oplId不为空的时候,要判断oplId是否在uPMS数据库存在
									String userName = userNames[j];
									String userId = userService.getUserIdByUserName(userName);
									if (StringUtil.isNotNull(userId)) {
										Map<String, Object> paramMap = putOplInfoToMap(oplInfo, projectInfoId, userId, true);
										boolean flag = oplService.updateOplInfo(paramMap);
										if (!StringUtil.isNotNull(oplId)) {
											paramMap = putOplInfoToMap(oplInfo, projectInfoId, userId, false);
										}
										if (!flag) {
											oplService.insertOplInfo(paramMap);
										}
										if (!StringUtil.isNotNull(oplId)) {
											Map<String, Object> oplIdRequestMap = new HashMap<String, Object>();
											oplIdRequestMap.put("oplSource", oplInfo.getSource());
											oplIdRequestMap.put("projectCode", oplInfo.getProjectCode());
											oplIdRequestMap.put("oplName", oplInfo.getOplName());
											oplInfo.setOplId(oplService.getOplIdByProjectCodeAndOplName(oplIdRequestMap));
										}
										rtnOplInfo = setErrorInfo(rtnOplInfo, oplInfo, projectCode, ErrorCode.TRUE, null);
										rtnOplInfoList.add(rtnOplInfo);
									} else {
										rtnOplInfo = setErrorInfo(rtnOplInfo, oplInfo, projectCode, ErrorCode.FALSE, ErrorCode.RESPNAME_IS_NOT_EXIST);
										rtnOplInfoList.add(rtnOplInfo);
									}
								}
							} else {
								rtnOplInfo = setErrorInfo(rtnOplInfo, oplInfo, projectCode, ErrorCode.FALSE, ErrorCode.PROJECT_IS_NOT_EXIST);
								rtnOplInfoList.add(rtnOplInfo);
							}
						}
					}
				}
				RtnOplInfo[] rtnOplInfos = new RtnOplInfo[rtnOplInfoList.size()];
				rtnOplInfoList.toArray(rtnOplInfos);
				response.setIsOk(ErrorCode.TRUE);
				response.setRtnOplInfos(rtnOplInfos);
			} catch (Exception e) {
				response.setIsOk(ErrorCode.FALSE);
				response.setErrorCode(ErrorCode.PMS_IS_ERROR);
			}
		}
		return response;
	}
	
	/**
	 * 将OplInfo信息放入到Map中
	 * 
	 * @param oplInfo
	 * @param projectInfoId
	 * @param userId
	 * @param flag
	 * @return
	 */
	private Map<String, Object> putOplInfoToMap(OplInfo oplInfo, String projectInfoId, String userId, boolean flag) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectInfoId", projectInfoId);
		if (flag) {
			paramMap.put("oplId", oplInfo.getOplId());
		} else {
			paramMap.put("oplId", Guid.getGuid()); // 此处需要设置uuid
		}
		paramMap.put("oplName", oplInfo.getOplName());
		paramMap.put("respId", userId);
		paramMap.put("source", oplInfo.getSource());
		paramMap.put("createDate", oplInfo.getCreateDate());
		paramMap.put("remark", oplInfo.getRemark());
		paramMap.put("status", oplInfo.getStatus());
		return paramMap;
	}
	
	/**
	 * 构建RtnOplInfo对象
	 * 
	 * @param rtnOplInfo
	 * @param oplInfo
	 * @param projectCode
	 * @param errorCode
	 * @return
	 */
	private RtnOplInfo setErrorInfo(RtnOplInfo rtnOplInfo, OplInfo oplInfo, String projectCode, String status, String errorCode) {
		rtnOplInfo.setStatus(status);
		rtnOplInfo.setOplId(oplInfo.getOplId());
		rtnOplInfo.setOplName(oplInfo.getOplName());
		rtnOplInfo.setProjectCode(projectCode);
		rtnOplInfo.setErrorCode(errorCode);
		return rtnOplInfo;
	}
	
	public IOplService getOplService() {
		return oplService;
	}
	
	public void setOplService(IOplService oplService) {
		this.oplService = oplService;
	}
	
	public IProjectService getProjectService() {
		return projectService;
	}
	
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
	
	public IUserService getUserService() {
		return userService;
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
