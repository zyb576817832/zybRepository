package com.ebon.rpc.sap.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ebon.rpc.sap.IProcessor;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.Table;

public class ThreeFeeProcessor implements IProcessor {
	
	private int		year;
	
	private Date	date;
	
	public ThreeFeeProcessor(int year, Date date) {
		this.year = year;
		this.date = date;
	}
	
	private SimpleDateFormat	format	= new SimpleDateFormat("yyyyMMdd");
	
	@Override
	public Object doBusiness(Function r_function) {
		Table t_table = r_function.getTableParameterList().getTable("T_OUTPUT");
		return t_table;
	}
	
	@Override
	public void doFilter(Function r_function) {
		r_function.getTableParameterList().clear();
		JCO.Table S_KSTAR = null;
		try {
			// 设置cost elements 
			S_KSTAR = r_function.getTableParameterList().getTable("S_KSTAR");
			this.SetS_KSTAR_Values(S_KSTAR);
			// 设置其他参数
			r_function.getImportParameterList().clear();
			r_function.getImportParameterList().setValue(String.valueOf(this.year), "IGJAHR");
			r_function.getImportParameterList().setValue(year + "0101", "BUDAT_S");
			r_function.getImportParameterList().setValue(format.format(date), "BUDAT_E");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String fname() {
		return "Z_RFC_PMS1";
	}
	
	private void SetS_KSTAR_Values(JCO.Table r_table) {
		try {
			r_table.appendRow();
			r_table.setValue("I", "SIGN");
			r_table.setValue("EQ", "OPTION");
			r_table.setValue("9990072020", "LOW");
			r_table.setValue("", "HIGH");
			
			r_table.appendRow();
			r_table.setValue("I", "SIGN");
			r_table.setValue("EQ", "OPTION");
			r_table.setValue("9990072025", "LOW");
			r_table.setValue("", "HIGH");
			
			r_table.appendRow();
			r_table.setValue("I", "SIGN");
			r_table.setValue("EQ", "OPTION");
			r_table.setValue("9990072030", "LOW");
			r_table.setValue("", "HIGH");
			
			r_table.appendRow();
			r_table.setValue("I", "SIGN");
			r_table.setValue("EQ", "OPTION");
			r_table.setValue("9990072040", "LOW");
			r_table.setValue("", "HIGH");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
