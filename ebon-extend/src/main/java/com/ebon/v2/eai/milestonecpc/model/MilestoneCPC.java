/** 
 * Project Name:ebon-extend 
 * File Name:MilestoneCPC1.java 
 * Package Name:com.ebon.v2.eai.milestonecpc.model 
 * Date:2015年11月9日下午6:59:37 
 * Copyright (c) 2015, quanxinsky@163.com All Rights Reserved. 
 * qx
 */  
package com.ebon.v2.eai.milestonecpc.model;

/** 
 * ClassName: MilestoneCPC前台展示模型
 * Function: 
 * date: 2015年11月9日 下午6:59:37
 * @author qx 
 * @version  
 * @since JDK 1.6 
 */
public class MilestoneCPC {
	
	String projShortName;
	String QA0;
	String QA1;//如果是系统项目则对应项目启动会议
	String QA2;//如果是系统项目则对应正式批量供货
	String QA3;
	String QA4;
	String month;//月份标记
	String onTimeMonth;//定义标准线
	
	public String getOnTimeMonth() {
		return onTimeMonth;
	}

	public void setOnTimeMonth(String onTimeMonth) {
		this.onTimeMonth = onTimeMonth;
	}

	public MilestoneCPC(){}
	
	public MilestoneCPC(String month, String onTimeMonth){
		this.month = month;
		this.onTimeMonth = onTimeMonth;
	}
	
	public String getProjShortName() {
		return projShortName;
	}
	public void setProjShortName(String projShortName) {
		this.projShortName = projShortName;
	}
	public String getQA0() {
		return QA0;
	}
	public void setQA0(String qA0) {
		QA0 = qA0;
	}
	public String getQA1() {
		return QA1;
	}
	public void setQA1(String qA1) {
		QA1 = qA1;
	}
	public String getQA2() {
		return QA2;
	}
	public void setQA2(String qA2) {
		QA2 = qA2;
	}
	public String getQA3() {
		return QA3;
	}
	public void setQA3(String qA3) {
		QA3 = qA3;
	}
	public String getQA4() {
		return QA4;
	}
	public void setQA4(String qA4) {
		QA4 = qA4;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
}
