package com.ebon.rpc.opl.vo;

import java.io.Serializable;

public class OplListRequest implements Serializable {

	private static final long serialVersionUID = -1708295861466429961L;

	private String projectCode;// 项目编号
	private String systemSource;// 系统来源
	private String oplSource;// OPL来源
	private String respName;// 责任人
	private String status;// OPL状态
	private String oplId;// OPL ID
	private String[] oplIds;// OPL IDS

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

	public String getRespName() {
		return respName;
	}

	public void setRespName(String respName) {
		this.respName = respName;
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

	public String[] getOplIds() {
		return oplIds;
	}

	public void setOplIds(String[] oplIds) {
		this.oplIds = oplIds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
