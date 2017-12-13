package com.ebon.v2.eai.lims.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.ebon.v2.eai.base.model.BatchInfo;
import com.ebon.v2.eai.base.model.Item;

public class Usage extends BatchInfo{
	public List getEquipments(){
		List equipments = new ArrayList();
		List items = this.getItems();
		for(int i=0;i<items.size();i++){
			UsageItem usageItem = (UsageItem )items.get(i);
			this.addEquipment(equipments, usageItem.getEquipment());
		}
		return equipments;
	}
	
	private void addEquipment(List equipments,Equipment equipment ){
		boolean existed = false;
		for(int i=0;i<equipments.size();i++){
			Equipment equipment0 = (Equipment)equipments.get(i);
			if(equipment0.getCode().equals(equipment.getCode())){
				existed = true;
				if(this.after(equipment.getDate(), equipment0.getDate())){
					equipments.set(i, equipment);
				}
				break;
			}
		}
		if(!existed){
			equipments.add(equipment);
		}
	}
	
	
	boolean after(String d1,String d2){
		 Calendar calendar1 = toCalender(d1);
		 Calendar calendar2 = toCalender(d2);
		return calendar1.after(calendar2);
	}
	
	
	private Calendar toCalender(String str){
		Calendar calendar = Calendar.getInstance();
		String[] strs = str.split("-");
		calendar.set(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]), Integer.parseInt(strs[2]));
		return calendar;
	}

	
	
}
