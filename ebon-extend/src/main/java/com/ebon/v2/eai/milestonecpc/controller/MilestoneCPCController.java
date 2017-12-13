/** 
 * Project Name:ebon-extend 
 * File Name:MilestoneCPCController.java 
 * Package Name:com.ebon.v2.eai.milestonecpc.controller 
 * Date:2015年11月9日下午6:17:54 
 * Copyright (c) 2015, quanxinsky@163.com All Rights Reserved. 
 * qx
 */  
package com.ebon.v2.eai.milestonecpc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebon.v2.eai.base.controller.BaseEaiController;
import com.ebon.v2.eai.milestonecpc.model.MilestoneCPC;
import com.ebon.v2.eai.milestonecpc.service.MilestoneCPCService;

/** 
 * ClassName: MilestoneCPCController
 * Function: TODO ADD FUNCTION
 * date: 2015年11月9日 下午6:17:54
 * @author qx 
 * @version  
 * @since JDK 1.6 
 */
@Controller
@RequestMapping("milestoneCPC")
public class MilestoneCPCController extends BaseEaiController {
	
	@Autowired
	MilestoneCPCService milestoneCPCService;
	
	@RequestMapping("getMilestoneCPC")
	@ResponseBody
	private String getMilestoneCPC(String proj_id, String startMonth, String endMonth) throws Exception{
		List<MilestoneCPC> list = new ArrayList<MilestoneCPC>();
		List<MilestoneCPC> retlist = new ArrayList<MilestoneCPC>();
		list = milestoneCPCService.getMilestoneCPCByProj(proj_id, startMonth, endMonth);
		String[] months = getAllMonths(startMonth,endMonth);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");  
		long formatStartMonth = sdf.parse(startMonth+"/01").getTime();
		for (String month : months) {
			System.out.println("统计的项目月份为:"+ month);
			for (int i = 0; i < list.size(); i++) {
				System.out.println("对象集合的月份是:"+list.get(i).getMonth());
				if(month.equals(list.get(i).getMonth())){
					list.get(i).setQA0(Long.toString(sdf.parse(list.get(i).getQA0()).getTime()-formatStartMonth));
					list.get(i).setQA1(Long.toString(sdf.parse(list.get(i).getQA1()).getTime()-formatStartMonth));
					list.get(i).setQA2(Long.toString(sdf.parse(list.get(i).getQA2()).getTime()-formatStartMonth));
					list.get(i).setQA3(Long.toString(sdf.parse(list.get(i).getQA3()).getTime()-formatStartMonth));
					list.get(i).setQA4(Long.toString(sdf.parse(list.get(i).getQA4()).getTime()-formatStartMonth));
					
					//设置标准时间:也就是onTime的准线
					list.get(i).setOnTimeMonth(Long.toString(getOnTime(month).getTime()-formatStartMonth));
					retlist.add(list.get(i));
					//开始月份数组的下一次循环
					break;
				}
				//如果对象数组里不存在，当前月份新建milestoneCPC对象
				//为了保证每个月份必须有一个milestoneCPC对象
				if(i == list.size()-1){
					retlist.add(new MilestoneCPC(month,Long.toString(getOnTime(month).getTime()-formatStartMonth)));
				}
			}
			
		}
				
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"data\" : [");
		for (int i = 0; i < retlist.size(); i++) {
			sb.append("{ \"month\" : \"" + retlist.get(i).getMonth() + "\",");
			sb.append("\"onTimeMonth\" : \"" + retlist.get(i).getOnTimeMonth() + "\",");
			sb.append("\"QA0\" : \"" + retlist.get(i).getQA0() + "\",");
			sb.append("\"QA1\" : \"" + retlist.get(i).getQA1() + "\",");
			sb.append("\"QA2\" : \"" + retlist.get(i).getQA2() + "\",");
			sb.append("\"QA3\" : \"" + retlist.get(i).getQA3() + "\",");
			sb.append("\"QA4\" : \"" + retlist.get(i).getQA4() + "\"");
			
			if(i==(retlist.size()-1)){
				sb.append("}");
			}else{
				sb.append("},");
			}
		}
		sb.append("] }");
		
		return sb.toString();
	}
	/**
	 * 
	 * getMilestoneCPCPage:(跳转到里程碑点页面，并传递过去默认起始时间和项目名称)
	 * @author qq 
	 * 2015年12月15日
	 * @since JDK 1.6
	 */
	@RequestMapping("getMilestoneCPCPage")
	public String getMilestoneCPCPage(Model model, String proj_id){
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");
		Calendar currentMonth = Calendar.getInstance();
		Calendar defalutStartMonth = Calendar.getInstance();
		currentMonth.add(Calendar.MONTH, 0);
		defalutStartMonth.add(Calendar.MONTH, -12);
		model.addAttribute("currentMonth",format.format(currentMonth.getTime()));
		model.addAttribute("defalutStartMonth",format.format(defalutStartMonth.getTime()));
		model.addAttribute("proj_id", proj_id);
		return "v2/bi/milestone";
	}
	
	
	/**
	 * 
	 * getAllMonths:(根据开始和结束日期得到月份集合)
	 * @author qx 
	 * 2015年11月10日
	 * @since JDK 1.6
	 */
	private String[] getAllMonths(String start, String end){
        String splitSign="/";
        String regex="\\d{4}"+splitSign+"(([0][1-9])|([1][012]))"; //判断YYYY/MM时间格式的正则表达式
        if(!start.matches(regex) || !end.matches(regex)) return new String[0];
        
        List<String> list=new ArrayList<String>();
        if(start.compareTo(end)>0){
            //start大于end日期时，互换
            String temp=start;
            start=end;
            end=temp;
        }
        
        String temp=start; //从最小月份开始
        while(temp.compareTo(start)>=0 && temp.compareTo(end)<0){
            list.add(temp); //首先加上最小月份,接着计算下一个月份
            String[] arr=temp.split(splitSign);
            int year=Integer.valueOf(arr[0]);
            int month=Integer.valueOf(arr[1])+1;
            if(month>12){
                month=1;
                year++;
            }
            if(month<10){//补0操作
                temp=year+splitSign+"0"+month;
            }else{
                temp=year+splitSign+month;
            }
        }
        
        int size=list.size();
        String[] result=new String[size]; 
        for(int i=0;i<size;i++){
            result[i]=list.get(i);
        }
        return result;
    }
	
	
	/**
	 * 
	 * getOnTime:(给定一个日期返回一个该日期所在月份最后一天)
	 * @author qq 
	 * 2015年11月22日
	 * @since JDK 1.6
	 */
	private Date getOnTime(String str){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");  
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		// 本月同步上月数据，定义上月的日期格式
		Calendar calendar = Calendar.getInstance();  
		// 设置时间,当前时间不用设置  
		calendar.setTime(date);  
		// 设置日期为本月最大日期  
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));  
		return calendar.getTime();
		
	}

}


