package com.ebon.rpc.opl.vo;

import java.io.Serializable;

public class OplInfo implements Serializable{

	private static final long serialVersionUID = 8618135649234159212L;
	
	private String projectCode; // 项目编号
	private String oplId;//OPL ID
	private String oplName;//OPL名称
	private String[] respNames;//责任人
	private String respName;
	private String source;//OPL来源
	private String createDate;//创建时间
	private String remark;//OPL备注
	private String status;//OPL状态
	
	public String getRespName() {
		return respName;
	}
	public void setRespName(String respName) {
		this.respName = respName;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String[] getRespNames() {
		if(null == respNames || respNames.length == 0) {
			respNames = new String[]{this.getRespName()};
		}
		return respNames;
	}
	public void setRespNames(String[] respNames) {
		this.respNames = respNames;
	}
}