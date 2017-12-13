/** 
 * Project Name:ebon-extend 
 * File Name:Test2.java 
 * Package Name:com.ebon.v2.eai.milestonecpc 
 * Date:2015年11月10日下午5:41:53 
 * Copyright (c) 2015, quanxinsky@163.com All Rights Reserved. 
 * qx
 */  
package com.ebon.v2.eai.milestonecpc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * ClassName: Test2
 * Function: TODO ADD FUNCTION
 * date: 2015年11月10日 下午5:41:53
 * @author qx 
 * @version  
 * @since JDK 1.6 
 */
public class Test2 {

	/**
	 * main:(给定两个日期算出相差天数)
	 * @author qx 
	 * 2015年11月10日
	 * @since JDK 1.6 
	 */
	public static void main(String[] args) {
		 String t1 = "2015/09/10";
		 String t2 = "2015/09/21";
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");  
	        try {
				Date date1 = sdf.parse(t1);
				Date date2 = sdf.parse(t2);
				long nd = 1000*24*60*60;//一天的毫秒数
				System.out.println("折两个日期相差"+(date2.getTime()-date1.getTime())/nd);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  

	}

}
