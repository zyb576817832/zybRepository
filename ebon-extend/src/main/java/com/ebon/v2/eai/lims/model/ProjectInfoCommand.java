package com.ebon.v2.eai.lims.model;

import java.util.Date;

public class ProjectInfoCommand {
	
	public static final int STATUS_NODATA = -1;
	
	
	public static final int STATUS_FAIL = 0;
	
	public ProjectInfoCommand() {
		super();
		this.id = ""+System.currentTimeMillis();
	}

	public static final int STATUS_SUCCESS = 1;
	
	String id;
	
	Date sendedDate;
	
	int status;
	
	String description;
	
	String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSendedDate() {
		return sendedDate;
	}

	public void setSendedDate(Date sendedTime) {
		this.sendedDate = sendedTime;
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

}
