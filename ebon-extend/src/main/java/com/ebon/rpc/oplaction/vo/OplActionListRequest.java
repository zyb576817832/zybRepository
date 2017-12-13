package com.ebon.rpc.oplaction.vo;

import java.io.Serializable;

public class OplActionListRequest implements Serializable {

	private static final long serialVersionUID = -4087312996165932815L;

	private String projectCode;// 项目编号
	private String systemSource;// 系统来源
	private String oplSource;// OPL来源
	private String oplRespName;// OPL责任人
	private String status;// OPL状态
	private String oplId;// OPL ID
	private String[] oplIds;//Opl的唯一标识的集合
	private String oplActionId;// OPL Action ID
	private String actionStatus;// OPLAction状态
	private String actionRespName;// Opl措施责任人

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getSystemSource() {
		return systemSource;
	}

	public void setSystemSource(String systemSource) {
		this.systemSource = systemSource;
	}

	public String getOplSource() {
		return oplSource;
	}

	public void setOplSource(String oplSource) {
		this.oplSource = oplSource;
	}

	public String getOplRespName() {
		return oplRespName;
	}

	public void setOplRespName(String oplRespName) {
		this.oplRespName = oplRespName;
	}

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

	public String getOplActionId() {
		return oplActionId;
	}

	public void setOplActionId(String oplActionId) {
		this.oplActionId = oplActionId;
	}

	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	public String getActionRespName() {
		return actionRespName;
	}

	public void setActionRespName(String actionRespName) {
		this.actionRespName = actionRespName;
	}

	public String[] getOplIds() {
		return oplIds;
	}

	public void setOplIds(String[] oplIds) {
		this.oplIds = oplIds;
	}

}
