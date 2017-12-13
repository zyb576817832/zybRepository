/** 
 * Project Name:ebon-extend 
 * File Name:Test3.java 
 * Package Name:com.ebon.v2.eai.milestonecpc 
 * Date:2015年11月22日下午6:58:01 
 * Copyright (c) 2015, quanxinsky@163.com All Rights Reserved. 
 * qq
 */
package com.ebon.v2.eai.milestonecpc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * ClassName: Test3 Function: TODO ADD FUNCTION date: 2015年11月22日 下午6:58:01
 * 
 * @author qq
 * @version
 * @since JDK 1.6
 */
public class Test3 {

	/**
	 * main:(给定一个月份的最后一天)
	 * 
	 * @author qq 2015年11月22日
	 * @throws ParseException 
	 * @since JDK 1.6
	 */
	public static void main(String[] args) throws ParseException {
		String str = "2015/09";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");  
		java.util.Date date=sdf.parse(str);  
		// 本月同步上月数据，定义上月的日期格式
		Calendar calendar = Calendar.getInstance();  
		// 设置时间,当前时间不用设置  
		calendar.setTime(date);  
		// 设置日期为本月最大日期  
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));  
		  
		// 打印  
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");  
		System.out.println(format.format(calendar.getTime())); 
	}

}
