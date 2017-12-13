package com.ebon.rpc.project.vo;

import java.io.Serializable;

public class ProjectInfo implements Serializable {
	
	private static final long serialVersionUID = 2010920745315044469L;

	private String projectName; // 项目名称
	private String projectCode; // 项目编号
	private String projectType; // 项目类型
	private String projectStage; // 项目阶段
	private String projectStatus; // 项目状态
	private String ph1; // 产品大类
	private String ph2; // 产品小类
	private String customerName; // 客户名称
	private String customerCode; // 客户编号
	private String numberB; // B号码
	private String number0; // 0号码
	private String pm; // 项目经理
	private String projectSize; // 项目等级
	private String sop; // SOP
	private String csop; // CSOP
	private String customerAbbr; // 客户缩写
	private String miniType; // 是否mini类型 0:不是 1:是
	private String projectId; // 项目Id
	private String lastUpdateDate; // 最后修改时间

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectStage() {
		return projectStage;
	}

	public void setProjectStage(String projectStage) {
		this.projectStage = projectStage;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getPh1() {
		return ph1;
	}

	public void setPh1(String ph1) {
		this.ph1 = ph1;
	}

	public String getPh2() {
		return ph2;
	}

	public void setPh2(String ph2) {
		this.ph2 = ph2;
	}

	public String getNumberB() {
		return numberB;
	}

	public void setNumberB(String numberB) {
		this.numberB = numberB;
	}

	public String getNumber0() {
		return number0;
	}

	public void setNumber0(String number0) {
		this.number0 = number0;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getProjectSize() {
		return projectSize;
	}

	public void setProjectSize(String projectSize) {
		this.projectSize = projectSize;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getSop() {
		return sop;
	}

	public void setSop(String sop) {
		this.sop = sop;
	}

	public String getCsop() {
		return csop;
	}

	public void setCsop(String csop) {
		this.csop = csop;
	}

	public String getCustomerAbbr() {
		return customerAbbr;
	}

	public void setCustomerAbbr(String customerAbbr) {
		this.customerAbbr = customerAbbr;
	}

	public String getMiniType() {
		return miniType;
	}

	public void setMiniType(String miniType) {
		this.miniType = miniType;
	}

}
