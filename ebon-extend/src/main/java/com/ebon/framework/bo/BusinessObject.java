/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.ebon.framework.bo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class BusinessObject implements Serializable, Cloneable {
	private static final long serialVersionUID = 2277110399162776740L;
	protected String id;
	protected String name;
	protected String code;
	protected String createUser;
	protected String updateUser;
	protected String createTime;
	protected String updateTime;
	protected String deleteFlag;
	private String tVersion;
	protected int sortNo;
	protected Map<String, String> cusPropMap = new HashMap();
	protected String searchFalg;
	protected String language = "zh_CN";
	private String metaCode;
	protected String viewCode;
	
	// 克隆方法
	@Override
	public Object clone() throws CloneNotSupportedException {
		try {
			BusinessObject result = (BusinessObject)super.clone();
			Map<String, String> map = new HashMap<String, String>();
			map.putAll(cusPropMap);
			result.setCusPropMap(map);
			return result;
		} catch (CloneNotSupportedException e) {
			throw e;
		}
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String gettVersion() {
		return this.tVersion;
	}

	public void settVersion(String tVersion) {
		this.tVersion = tVersion;
	}

	public int getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMetaCode() {
		return this.metaCode;
	}

	public void setMetaCode(String metaCode) {
		this.metaCode = metaCode;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Map<String, String> getCusPropMap() {
		return this.cusPropMap;
	}

	public void setCusPropMap(Map<String, String> cusPropMap) {
		this.cusPropMap = cusPropMap;
	}

	public String getSearchFalg() {
		return this.searchFalg;
	}

	public void setSearchFalg(String searchFalg) {
		this.searchFalg = searchFalg;
	}

	public String getViewCode() {
		return this.viewCode;
	}

	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}
}