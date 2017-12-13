package com.ebon.rpc.oplaction.vo;

import java.io.Serializable;

public class OplActionInfo implements Serializable {
	
	private static final long	serialVersionUID	= 2491784539739214384L;
	
	private String				oplId;										//OPL ID
	
	private String				oplActionId;								//OPL Action ID
	
	private String				actionName;									//措施名称
	
	private String				targetStartDate;							//计划开始时间
	
	private String				targetEndDate;								//计划结束时间
	
	private String				aclStartDate;								//实际开始时间
	
	private String				aclEndDate;									//实际结束时间
	
	private String				oritargetEndDate;							//原定计划结束时间
	
	private String				status;										//措施状态
	
	private String[]			respNames;									//责任人
	
	private String				creatDate;									//创建时间
																			
	public String getOplId() {
		return oplId;
	}
	
	public void setOplId(String oplId) {
		this.oplId = oplId;
	}
	
	public String getOplActionId() {
		return oplActionId;
	}
	
	public void setOplActionId(String oplActionId) {
		this.oplActionId = oplActionId;
	}
	
	public String getActionName() {
		return actionName;
	}
	
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	public String getTargetStartDate() {
		return targetStartDate;
	}
	
	public void setTargetStartDate(String targetStartDate) {
		this.targetStartDate = targetStartDate;
	}
	
	public String getTargetEndDate() {
		return targetEndDate;
	}
	
	public void setTargetEndDate(String targetEndDate) {
		this.targetEndDate = targetEndDate;
	}
	
	public String getAclStartDate() {
		return aclStartDate;
	}
	
	public void setAclStartDate(String aclStartDate) {
		this.aclStartDate = aclStartDate;
	}
	
	public String getAclEndDate() {
		return aclEndDate;
	}
	
	public void setAclEndDate(String aclEndDate) {
		this.aclEndDate = aclEndDate;
	}
	
	public String getOritargetEndDate() {
		return oritargetEndDate;
	}
	
	public void setOritargetEndDate(String oritargetEndDate) {
		this.oritargetEndDate = oritargetEndDate;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String[] getRespNames() {
		return respNames;
	}
	
	public void setRespNames(String[] respNames) {
		this.respNames = respNames;
	}
	
	public final String getCreatDate() {
		return creatDate;
	}
	
	public final void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}
	
}
