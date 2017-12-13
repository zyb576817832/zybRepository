package com.ebon.rpc.task.vo;

import java.io.Serializable;
import java.util.List;

public class TaskInfo implements Serializable {

	private static final long serialVersionUID = 1656454945722315886L;

	private String projectName; // 项目名称
	private String projectCode; // 项目编号
	private String taskId;
	private String taskName; // 任务名称
	private String taskType; // 任务类型
	private String taskCode; // 任务编号
	private String taskStatus; // 任务状态
	private String taskPlanStartDate; // 任务计划开始时间
	private String taskPlanEndDate; // 任务计划结束时间
	private String taskAclStartDate; // 任务实际开始时间
	private String taskAclEndDate; // 任务实际 结束时间
	private String qaType; // QA类型
	private List<TaskResource> taskResourceList;
	private TaskResource[] taskResources; // 任务资源

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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskPlanStartDate() {
		return taskPlanStartDate;
	}

	public void setTaskPlanStartDate(String taskPlanStartDate) {
		this.taskPlanStartDate = taskPlanStartDate;
	}

	public String getTaskPlanEndDate() {
		return taskPlanEndDate;
	}

	public void setTaskPlanEndDate(String taskPlanEndDate) {
		this.taskPlanEndDate = taskPlanEndDate;
	}

	public String getTaskAclStartDate() {
		return taskAclStartDate;
	}

	public void setTaskAclStartDate(String taskAclStartDate) {
		this.taskAclStartDate = taskAclStartDate;
	}

	public String getTaskAclEndDate() {
		return taskAclEndDate;
	}

	public void setTaskAclEndDate(String taskAclEndDate) {
		this.taskAclEndDate = taskAclEndDate;
	}

	public TaskResource[] getTaskResources() {
		return taskResources;
	}

	public void setTaskResources(TaskResource[] taskResources) {
		this.taskResources = taskResources;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<TaskResource> getTaskResourceList() {
		return taskResourceList;
	}

	public void setTaskResourceList(List<TaskResource> taskResourceList) {
		this.taskResourceList = taskResourceList;
	}

	public String getQaType() {
		return qaType;
	}

	public void setQaType(String qaType) {
		this.qaType = qaType;
	}

}