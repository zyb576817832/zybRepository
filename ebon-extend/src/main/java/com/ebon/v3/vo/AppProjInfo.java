package com.ebon.v3.vo;

public class AppProjInfo {
	
	private String projId;
	private String primProjId;
	private String code;//编号
	private String customer;//客户
	private String vehicle;//车型
	private String engine;//发动机型号
	
	private String name;//项目名称
	private String projType;
	
	private String cbuNo;
	
	private String sod;
	private String sop;
	private String eop;
	private String ceop;
	private String csod;
	private String csop;
	
	private String engParaC;//Engine Parameter Cylinder 缸数
	private String engParaV;//Engine Parameter Valves 阀数
	private String engParaD;//Engine Parameter Displacement 排量
	
	private String projPM;
	private String sales;
	private String projStage;//项目阶段
	private String ph1;
	private String ph2;
	private String timelight;
	private String budgetlight;
	private String costlight;
	private String qualitylight;
	private String status;//项目状态 项目状态，NEW/	是指立项前的状态；PLANNING/是指打基线前的状态；RUNNING/是指项目关闭前的状态；CLOSED/是指项目关闭；CANCEL/是指项目中止；INACTIVE/是指项目暂停的状态
	private String projSize;//项目级别
	
	public String getProjPM() {
		return projPM;
	}
	public void setProjPM(String projPM) {
		this.projPM = projPM;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public String getProjStage() {
		return projStage;
	}
	public void setProjStage(String projStage) {
		this.projStage = projStage;
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
	public String getTimelight() {
		return timelight;
	}
	public void setTimelight(String timelight) {
		this.timelight = timelight;
	}
	public String getBudgetlight() {
		return budgetlight;
	}
	public void setBudgetlight(String budgetlight) {
		this.budgetlight = budgetlight;
	}
	public String getCostlight() {
		return costlight;
	}
	public void setCostlight(String costlight) {
		this.costlight = costlight;
	}
	public String getQualitylight() {
		return qualitylight;
	}
	public void setQualitylight(String qualitylight) {
		this.qualitylight = qualitylight;
	}
	public String getProjId() {
		return projId;
	}
	public void setProjId(String projId) {
		this.projId = projId;
	}
	public String getPrimProjId() {
		return primProjId;
	}
	public void setPrimProjId(String primProjId) {
		this.primProjId = primProjId;
	}
	public String getProjType() {
		return projType;
	}
	public void setProjType(String projType) {
		this.projType = projType;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCbuNo() {
		return cbuNo;
	}
	public void setCbuNo(String cbuNo) {
		this.cbuNo = cbuNo;
	}
	public String getSod() {
		return sod;
	}
	public void setSod(String sod) {
		this.sod = sod;
	}
	public String getSop() {
		return sop;
	}
	public void setSop(String sop) {
		this.sop = sop;
	}
	public String getEop() {
		return eop;
	}
	public void setEop(String eop) {
		this.eop = eop;
	}
	public String getCeop() {
		return ceop;
	}
	public void setCeop(String ceop) {
		this.ceop = ceop;
	}
	public String getCsod() {
		return csod;
	}
	public void setCsod(String csod) {
		this.csod = csod;
	}
	public String getCsop() {
		return csop;
	}
	public void setCsop(String csop) {
		this.csop = csop;
	}
	public String getEngParaC() {
		return engParaC;
	}
	public void setEngParaC(String engParaC) {
		this.engParaC = engParaC;
	}
	public String getEngParaV() {
		return engParaV;
	}
	public void setEngParaV(String engParaV) {
		this.engParaV = engParaV;
	}
	public String getEngParaD() {
		return engParaD;
	}
	public void setEngParaD(String engParaD) {
		this.engParaD = engParaD;
	}
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProjSize() {
		return projSize;
	}
	public void setProjSize(String projSize) {
		this.projSize = projSize;
	}
	@Override
	public String toString() {
		return "AppProjInfo [projId=" + projId + ", primProjId=" + primProjId
				+ ", code=" + code + ", customer=" + customer + ", vehicle="
				+ vehicle + ", engine=" + engine + ", name=" + name
				+ ", projType=" + projType + ", cbuNo=" + cbuNo + ", sod="
				+ sod + ", sop=" + sop + ", eop=" + eop + ", ceop=" + ceop
				+ ", csod=" + csod + ", csop=" + csop + ", engParaC="
				+ engParaC + ", engParaV=" + engParaV + ", engParaD="
				+ engParaD + ", projPM=" + projPM + ", sales=" + sales
				+ ", projStage=" + projStage + ", ph1=" + ph1 + ", ph2=" + ph2
				+ ", timelight=" + timelight + ", budgetlight=" + budgetlight
				+ ", costlight=" + costlight + ", qualitylight=" + qualitylight
				+ ", status=" + status + ", projSize=" + projSize + "]";
	}
	
	
}
