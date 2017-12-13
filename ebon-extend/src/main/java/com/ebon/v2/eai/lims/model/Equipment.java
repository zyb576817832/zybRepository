package com.ebon.v2.eai.lims.model;

/**
 * 设备表
 * @author G
 *
 */
public class Equipment {

	private String code;
	private String name; 
	private String cnName;
	private String description;
	private String department;
	private String date;
	private String usageId;
	
	public String getUsageId() {
		return usageId;
	}
	public void setUsageId(String usageId) {
		this.usageId = usageId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String equipmentCode) {
		this.code = equipmentCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
