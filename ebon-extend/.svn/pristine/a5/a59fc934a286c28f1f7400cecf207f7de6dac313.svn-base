package com.ebon.v2.eai.ucs.handler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ebon.v2.eai.base.handler.BaseHandler;
import com.ebon.v2.eai.base.model.BatchInfo;
import com.ebon.v2.eai.lims.model.Equipment;
import com.ebon.v2.eai.lims.model.Usage;
import com.ebon.v2.eai.lims.model.UsageItem;
import com.ebon.v2.eai.ucs.model.Employee;
import com.ebon.v2.eai.ucs.model.TimesheetItem;
import com.ebon.v2.eai.ucs.model.Timesheet;



public class UcsHandler extends BaseHandler {
	
	private Timesheet timesheet = new Timesheet();
	
	@Override
	public BatchInfo getBatchInfo(){
		return timesheet;
	}
	
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException{
		
		/*
		<timesheets SD="2014-12-01">
			<timesheet>
				<UG DT="2014-12-08" PC="PJ001" EI="EP001"  HU="5" DP1="" DP2="" DP3=""/>
				<UG DT="2014-12-23" PC="PJ001" EI="EP001"  HU="7" DP1="" DP2="" DP3=""/>
				<UG DT="2014-12-18" PC="PJ003" EI="EP002"  HU="10" DP1="" DP2="" DP3=""/>
				<UG DT="2014-12-09" PC="PJ004" EI="EP003"  HU="4" DP1="" DP2="" DP3=""/>
			</ timesheet >
		</ timesheets>
		*/
		
		if("TS".equals(qName)){
			String startDate = attributes.getValue("SD");
			timesheet.setStartDate(startDate);
		}
		
		if("TI".equals(qName)){
			
			TimesheetItem timesheetItem = new TimesheetItem();
			timesheetItem.setTimesheetId(timesheet.getId());
			Employee employee = new Employee();
			
			//解析item
			timesheetItem.setDate(attributes.getValue("DT"));
			timesheetItem.setProjectCode(attributes.getValue("PC"));
			timesheetItem.setHours(attributes.getValue("HU"));
			timesheetItem.setEmployeeId(attributes.getValue("EI"));
			
			//解析设备信息
			timesheetItem.setDept1(attributes.getValue("DP1"));
			timesheetItem.setDept2(attributes.getValue("DP2"));
			timesheetItem.setDept3(attributes.getValue("DP3"));
			timesheetItem.setDate(attributes.getValue("DT"));
//			System.out.println("|"+attributes.getValue("DP2")+"|");
//			System.out.println("|"+attributes.getValue("DP3")+"|");
			timesheetItem.setTimesheetId(timesheet.getId());
			//添加数据。
			timesheet.addItem(timesheetItem);
//			timesheet.addEmployee(employee);
		}		
	}
}