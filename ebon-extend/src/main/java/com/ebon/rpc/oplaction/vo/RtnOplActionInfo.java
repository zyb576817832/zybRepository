package com.ebon.rpc.oplaction.vo;

import java.io.Serializable;

public class RtnOplActionInfo implements Serializable {
	
	private static final long	serialVersionUID	= -3811750615436145282L;
	
	private String				status;										//是否成功标识
																				
	private String				oplActionId;									//OPL Action ID
																				
	private String				oplId;											//OPL ID
																				
	private String				actionName;									//措施名称
																				
	private String				errorCode;
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getOplId() {
		return oplId;
	}
	
	public void setOplId(String oplId) {
		this.oplId = oplId;
	}
	
	public String getActionName() {
		return actionName;
	}
	
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getOplActionId() {
		return oplActionId;
	}
	
	public void setOplActionId(String oplActionId) {
		this.oplActionId = oplActionId;
	}
	
}
