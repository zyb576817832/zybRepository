package com.ebon.v2.eai.lims.model;

import java.util.Date;

public class ProjectInfo {
	
	/**
	 * ProjectInfo 对象
	 */
	
	String projectCode;	
	String projectName;
	String projectType; 
	
	String ph1;
	String ph2;
	String numberB;//b号码
	String numberO;//O号码
	String projectPm;
	String customerCode;
	String customerName;
	Date SOP;
	Date createDate;
	String CSOP;
	String customerABBR;
	
	String projectSize;
	String projectStage;
	String projectStatus;
	String sapName;
	
	String internalOrder;
	
	public String getInternalOrder() {
		return internalOrder;
	}
	public void setInternalOrder(String internalOrder) {
		this.internalOrder = internalOrder;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
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
	public String getNumberO() {
		return numberO;
	}
	public void setNumberO(String numberO) {
		this.numberO = numberO;
	}
	public String getProjectPm() {
		return projectPm;
	}
	public void setProjectPm(String projectPm) {
		this.projectPm = projectPm;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getSOP() {
		return SOP;
	}
	public void setSOP(Date sOP) {
		SOP = sOP;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCSOP() {
		return CSOP;
	}
	public void setCSOP(String cSOP) {
		CSOP = cSOP;
	}
	public String getCustomerABBR() {
		return customerABBR;
	}
	public void setCustomerABBR(String customerABBR) {
		this.customerABBR = customerABBR;
	}
	public String getProjectSize() {
		return projectSize;
	}
	public void setProjectSize(String projectSize) {
		this.projectSize = projectSize;
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
	public String getSapName() {
		return sapName;
	}
	public void setSapName(String sapName) {
		this.sapName = sapName;
	}
	
	String projectInfoCommandId;
	public String getProjectInfoCommandId() {
		return projectInfoCommandId;
	}
	public void setProjectInfoCommandId(String projectInfoCommandId) {
		this.projectInfoCommandId = projectInfoCommandId;
	}
	

}
