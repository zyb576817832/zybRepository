package com.ebon.v2.eai.budget.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyNum {

	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM"); 
		Calendar lastMonth = Calendar.getInstance();
		lastMonth.add(Calendar.MONTH, -2);
		System.out.println(format.format(lastMonth.getTime()));
		
	}
	
	static void operator(StringBuffer x,StringBuffer y){
		
		StringBuffer sb1 = new StringBuffer("A");
		StringBuffer sb2 = new StringBuffer("B");
		operator(sb1,sb2);
		System.out.println("SB1======"+sb1);
		System.out.println("SB2======"+sb2);
		
		
		x.append(y);
		x=y;
		x.append("C");
	}
	
	static String threePlus(String a, String b, String c){
		
		threePlus("11","22.2","33.33");
		System.out.println(a);
		
		return (Double.parseDouble(a)+Double.parseDouble(b)+Double.parseDouble(c))+"";
	}
	
	
	void myTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
		System.out.println("当前系统时间是："+ df.format(new Date()));// new Date()为获取当前系统时间
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		System.out.println("当前月份是："+month);
	}

}
