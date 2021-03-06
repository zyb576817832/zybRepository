package com.ebon.v3.vo;


public class AppUserHours extends V3BusinessObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5029858000851450877L;
	protected String userId;
	protected String registDate;//报工日期
	protected String taskId;
	protected String registHours;//报工小时数
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getRegistHours() {
		return registHours;
	}
	public void setRegistHours(String registHours) {
		this.registHours = registHours;
	}
	@Override
	public String toString() {
		return "AppUserHours [id=" + id + ", userId=" + userId
				+ ", registDate=" + registDate + ", taskId=" + taskId
				+ ", registHours=" + registHours + "]";
	}

}
