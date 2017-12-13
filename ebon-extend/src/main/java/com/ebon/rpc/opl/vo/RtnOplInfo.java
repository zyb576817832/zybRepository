package com.ebon.rpc.opl.vo;

import java.io.Serializable;

public class RtnOplInfo implements Serializable {

	private static final long serialVersionUID = -6665571923543329593L;
	
	private String status;
	
	private String oplId;
	
	private String oplName;
	
	private String projectCode;

	private String errorCode;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOplId() {
		return oplId;
	}

	public void setOplId(String oplId) {
		this.oplId = oplId;
	}

	public String getOplName() {
		return oplName;
	}

	public void setOplName(String oplName) {
		this.oplName = oplName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
