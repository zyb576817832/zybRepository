package com.ebon.rpc.oplaction.vo;

import java.io.Serializable;

public class OplActionInsertResponse implements Serializable{

	private static final long serialVersionUID = -6255467446657213866L;
	
	private String isOk;//是否成功标识
	private RtnOplActionInfo[] RtnOplActionInfos;//返回参数信息
	private String errorCode;//错误代码
	private String errorMessage;//错误信息
	
	public String getIsOk() {
		return isOk;
	}
	public void setIsOk(String isOk) {
		this.isOk = isOk;
	}
	public RtnOplActionInfo[] getRtnOplActionInfos() {
		return RtnOplActionInfos;
	}
	public void setRtnOplActionInfos(RtnOplActionInfo[] rtnOplActionInfos) {
		RtnOplActionInfos = rtnOplActionInfos;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
