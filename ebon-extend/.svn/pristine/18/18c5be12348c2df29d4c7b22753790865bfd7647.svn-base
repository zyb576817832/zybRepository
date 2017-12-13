package com.ebon.v2.eai.lims.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ebon.v2.eai.base.handler.BaseHandler;
import com.ebon.v2.eai.base.model.BatchInfo;
import com.ebon.v2.eai.lims.model.Equipment;
import com.ebon.v2.eai.lims.model.Usage;
import com.ebon.v2.eai.lims.model.UsageItem;


/**
 * 
 * @author G
 *
 */
public class LimsHandler extends BaseHandler{
	
	
	private static final String DATASET = "DataSet";
	
	private static final String ATTR_STARTDATE = "startDate";
	
	private static final String TABLE = "Table";
	
	
	private static final String DATE = "A";
	private static final String PROJECTCODE = "B";
	private static final String CODE = "C";
	private static final String NAME = "D";
	private static final String DESCRIPTION = "E";
	private static final String DEPT = "F";
	private static final String HOURS = "G";
	private static final String RATE = "H";
	
	private Usage usage = new Usage();
	
	UsageItem usageItem;
	Equipment equipment;
	String currentTag;
	
	@Override
	public BatchInfo getBatchInfo(){
		return usage;
	}
	
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException{
		currentTag = qName;
//		if(DATASET.equals(qName)){
//			String startDate = attributes.getValue(ATTR_STARTDATE);
//			usage.setStartDate(startDate);
//		}
		
		if(TABLE.equals(qName)){
			usageItem = new UsageItem();
			equipment = new Equipment();		
		}
		
		
	}

    public void characters(char[] ch, int start, int length)    throws SAXException {
        
        if(currentTag != null) {
        	String v = new String(ch, start, length); //获取当前标签里的内容
            if(currentTag.equals(DATE)) {  
            	this.usageItem.setDate(v);
            	this.equipment.setDate(v);
            }else if(currentTag.equals(PROJECTCODE)) {
            	this.usageItem.setProjectCode(v);
            }else if(currentTag.equals(CODE)) {
            	this.usageItem.setEquipmentCode(v);
            	this.equipment.setCode(v);
            }else if(currentTag.equals(NAME)) {
            	this.equipment.setName(v);
            }else if(currentTag.equals(DESCRIPTION)) {
            	this.equipment.setCnName(v);
            	this.equipment.setDescription(v);
            }else if(currentTag.equals(DEPT)) {
            	this.equipment.setDepartment(v);
            }else if(currentTag.equals(HOURS)) {
            	this.usageItem.setHours(v);
            }else if(currentTag.equals(RATE)) {
            	this.usageItem.setRate(v);
            }
        }
    }
 
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals(TABLE) && (usageItem != null)&& (equipment != null)) {
        	if(usageItem.getHours() != null && !usageItem.getHours().trim().equals("")){
	        	usageItem.setEquipment(equipment);
				usageItem.setUsageId(usage.getId());
				equipment.setUsageId(usage.getId());
				//添加数据。
				usage.addItem(usageItem);
        	}else{
        		usage.setStartDate(usageItem.getDate());
        	}
			
        }
        currentTag = null;
       
    }
}