package com.ebon.v2.eai.base.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Command implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return "Command [startDate=" + startDate + ", sendedTime=" + sendedTime
				+ ", type=" + type + ", sys=" + sys + ", id=" + id + ", data="
				+ Arrays.toString(data) + ", status=" + status
				+ ", description=" + description + ", comments=" + comments
				+ "]";
	}
	public static final int TYPE_AUTO = 0;
	public static final int TYPE_MANUL = 1;
	
	
	public static final int SYS_UCS = 0;
	public static final int SYS_LIMS = 1;
	
	public static final int STATUS_FAIL = 0;
	public static final int STATUS_SUCCESS= 1;
	
	String startDate;//startDate报错
	String sendedTime ;	//sendedTime报错
	int type;
	int sys;
	String id;
	byte[] data;
	int status;
	String description;
	String comments;
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Command() {
		this.id = System.currentTimeMillis()+"";
		sendedTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getSendedTime() {
		return sendedTime;
	}
	public void setSendedTime(String sendedTime) {
		this.sendedTime = sendedTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSys() {
		return sys;
	}
	public void setSys(int sys) {
		this.sys = sys;
	}
	
}
