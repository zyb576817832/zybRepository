package com.ebon.v3.dto;

import com.ebon.v3.vo.AppUserHours;

public class VAppUserHours extends AppUserHours {
	private String taskName;
	private String registMonth;//虚拟字段月份：根据月份查询我的工时

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

	@Override
	public String toString() {
		return "VAppUserHours [taskName=" + taskName + ", registMonth="
				+ registMonth + ", id=" + id + ", userId=" + userId
				+ ", registDate=" + registDate + ", taskId=" + taskId
				+ ", registHours=" + registHours + "]";
	}
	
	
}
