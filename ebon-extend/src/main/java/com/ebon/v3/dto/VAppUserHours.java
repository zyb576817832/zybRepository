package com.ebon.v3.dto;

import com.ebon.v3.vo.AppUserHours;

public class VAppUserHours extends AppUserHours {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String taskName;
	private String registMonth;//虚拟字段月份：根据月份查询我的工时

	private String assignUserName;//发包人
	private String projCode;//项目编号
	private String planHours;//计划工时
	private String prodOcode;//0number
	private String prodBcode;//Bnumber
	private String actStartDate;//实际开始时间
	private String type;//任务类型
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getRegistMonth() {
		return registMonth;
	}

	public void setRegistMonth(String registMonth) {
		this.registMonth = registMonth;
	}


	public String getProjCode() {
		return projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}

	public String getPlanHours() {
		return planHours;
	}

	public void setPlanHours(String planHours) {
		this.planHours = planHours;
	}

	public String getProdOcode() {
		return prodOcode;
	}

	public void setProdOcode(String prodOcode) {
		this.prodOcode = prodOcode;
	}

	public String getProdBcode() {
		return prodBcode;
	}

	public void setProdBcode(String prodBcode) {
		this.prodBcode = prodBcode;
	}

	public String getActStartDate() {
		return actStartDate;
	}

	public void setActStartDate(String actStartDate) {
		this.actStartDate = actStartDate;
	}

	public String getAssignUserName() {
		return assignUserName;
	}

	public void setAssignUserName(String assignUserName) {
		this.assignUserName = assignUserName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "VAppUserHours [taskName=" + taskName + ", registMonth="
				+ registMonth + ", assignUsername=" + assignUserName
				+ ", projCode=" + projCode + ", planHours=" + planHours
				+ ", prodOcode=" + prodOcode + ", prodBcode=" + prodBcode
				+ ", actDate=" + actStartDate + "]";
	}

	
}
