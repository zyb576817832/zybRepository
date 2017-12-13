package com.ebon.v2.eai.base.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BatchInfo {
	
	public BatchInfo(){
		this.id = System.currentTimeMillis()+"";
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	String id;
	
	String startDate;

	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void addItem(Item item){
		this.items.add(item);
	}
	
	List items = new ArrayList();

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}
	
	String commandId;


	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
}
