package com.ebon.v3.vo;

import java.io.Serializable;

public class V3BusinessObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6484224722378486479L;
	protected String id;
	protected String name;
	protected String code;
	protected String createUser;
	protected String updateUser;
	protected String createTime;
	protected String updateTime;
	protected String deleteFlag;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	@Override
	public String toString() {
		return "V3BusinessObject [id=" + id + ", name=" + name + ", code="
				+ code + ", createUser=" + createUser + ", updateUser="
				+ updateUser + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", deleteFlag=" + deleteFlag + "]";
	}
	
}
