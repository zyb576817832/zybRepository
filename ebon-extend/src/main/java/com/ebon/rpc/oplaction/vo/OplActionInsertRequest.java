package com.ebon.rpc.oplaction.vo;

import java.io.Serializable;

public class OplActionInsertRequest implements Serializable{

	private static final long serialVersionUID = -5042017022497751538L;
	
	private String systemSource;//系统来源
	private OplActionInfo[] oplActionInfos;//OPL信息
	
	public String getSystemSource() {
		return systemSource;
	}
	public void setSystemSource(String systemSource) {
		this.systemSource = systemSource;
	}
	public OplActionInfo[] getOplActionInfos() {
		return oplActionInfos;
	}
	public void setOplActionInfos(OplActionInfo[] oplActionInfos) {
		this.oplActionInfos = oplActionInfos;
	}

}
